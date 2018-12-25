package abstractfactory;

import javafx.util.Pair;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AbstractFactoryDemo {
    public static void main(String[] args) throws Exception {
        HotDrinkMachine hotDrinkMachine = new HotDrinkMachine();
        HotDrink drink = hotDrinkMachine.makeDrink();
        drink.consume();
    }
}

interface HotDrink {
    void consume();
}

class Tea implements HotDrink {
    @Override
    public void consume() {
        System.out.println("This tea is delicious");
    }
}

class Coffee implements HotDrink {
    @Override
    public void consume() {
        System.out.println("This coffee is delicious");
    }
}

interface HotDrinkFactory {
    HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory {

    @Override
    public HotDrink prepare(int amount) {
        System.out.println(
                "Put in tea bag, boil water, pour " +
                        amount + "ml, add lemon, enjoy!"
        );
        return new Tea();
    }
}

class CoffeFactory implements HotDrinkFactory {

    @Override
    public HotDrink prepare(int amount) {
        System.out.println(
                "Put in coffee bag, boil water, pour " +
                        amount + "ml, enjoy!"
        );
        return new Coffee();
    }
}

class HotDrinkMachine {
    private List<Pair<String, HotDrinkFactory>> namedFactories =
            new ArrayList<>();

    public HotDrinkMachine() throws NoSuchMethodException,
                                    IllegalAccessException,
                                    InvocationTargetException,
                                    InstantiationException {
        Set<Class<? extends HotDrinkFactory>> types = new Reflections("")
                .getSubTypesOf(HotDrinkFactory.class);

        for (Class<? extends HotDrinkFactory> type : types) {
            namedFactories.add(new Pair<>(
                    type.getSimpleName().replace("Factory", ""),
                    type.getDeclaredConstructor().newInstance()
            ));
        }
    }

    public HotDrink makeDrink() throws Exception {
        System.out.println("Available drinks");
        for ( int index = 0; index < namedFactories.size(); ++index) {
            Pair<String, HotDrinkFactory> item = namedFactories.get(index);
            System.out.println("" + index + ": " + item.getKey());
         }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String s;
            int i, amount;
            if((s = reader.readLine()) != null
                    && (i = Integer.parseInt(s)) >= 0
                    && i < namedFactories.size()) {
                System.out.println("Specify amount:");
                s = reader.readLine();
                if (s != null && (amount = Integer.parseInt(s)) > 0) {
                    return namedFactories.get(i).getValue().prepare(amount);
                }
                System.out.println("Incorrect input, try again.");
            }
        }
    }
}


