package ChainObserverMediator;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ChainObserverMediator {
    public static void main(String[] args) {
        Game game = new Game();
        Creature creature = new Creature(game,"goblin1", 1, 1);
        System.out.println(creature);
        IncreaseDeffenseModifier increaseDeffenseModifier = new IncreaseDeffenseModifier(game, creature);
        System.out.println(creature);
        DoubleAttackModifier doubleAttackModifier = new DoubleAttackModifier(game, creature);
        System.out.println(creature);

    }
}

class Event<Args> {
    private Map<Integer, Consumer<Args>> handlers = new HashMap<>();
    private int index = 0;

    public int addHandler(Consumer<Args> consumer) {
        int i = index;
        this.handlers.put(++index, consumer);
        return i;
    }

    public void unsubscribe(int key) {
        this.handlers.remove(key);
    }

    public void fire(Args args) {
        for (Consumer handler : handlers.values()) {
            handler.accept(args);
        }
    }
}

class Query {
    public String cratureName;

    enum Argument {
        ATTACK, DEFENSE
    }

    Argument argument;
    public int result;

    public Query(String cratureName, Argument argument, int result) {
        this.cratureName = cratureName;
        this.argument = argument;
        this.result = result;
    }
}

class Game {
    public Event<Query> queries = new Event<Query>();
}

class Creature {
    private Game game;
    public String name;
    public int baseAttack, baseDefense;

    public Creature(Game game, String name, int baseAttack, int baseDefense) {
        this.game = game;
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    int getAttack() {
        Query query = new Query(name, Query.Argument.ATTACK, baseAttack);
        game.queries.fire(query);
        return query.result;
    }

    int getDeffense() {
        Query query = new Query(name, Query.Argument.DEFENSE, baseDefense);
        game.queries.fire(query);
        return query.result;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "game=" + game +
                ", name='" + name + '\'' +
                ", baseAttack=" + getAttack() +
                ", baseDefense=" + getDeffense() +
                '}';
    }
}

class CreatureModifier {
    protected Game game;
    protected Creature creature;

    public CreatureModifier(Game game, Creature creature) {
        this.game = game;
        this.creature = creature;
    }
}
class DoubleAttackModifier
        extends CreatureModifier
        implements Closeable {
    private final int token;

    public DoubleAttackModifier(Game game, Creature creature) {
        super(game, creature);
        token = game.queries.addHandler(q -> {
            if (q.cratureName.equals(creature.name)
                    && q.argument == Query.Argument.ATTACK) {
                q.result *= 2;
            }
        });
    }

    @Override
    public void close() {
        this.game.queries.unsubscribe(token);
    }
}

class IncreaseDeffenseModifier
        extends CreatureModifier
        implements AutoCloseable {
    private final int token;

    public IncreaseDeffenseModifier(Game game, Creature creature) {
        super(game, creature);
        token = game.queries.addHandler(q -> {
            if (q.cratureName.equals(creature.name)
                    && q.argument == Query.Argument.DEFENSE) {
                q.result += 3;
            }
        });
    }

    @Override
    public void close()  {
        this.game.queries.unsubscribe(token);
    }
}