package extreactive;


import io.reactivex.Observable;
import io.reactivex.Observer;

import java.util.ArrayList;
import java.util.List;

public class ReactiveExtensionDemo {
    public static void main(String[] args) {
        EventBroker eventBroker = new EventBroker();
        FootballPlayer footballPlayer = new FootballPlayer(eventBroker, "jones");
        FootballCoach footballCoach = new FootballCoach(eventBroker);

        footballPlayer.score();
        footballPlayer.score();
        footballPlayer.score();
    }
}

class EventBroker extends Observable<Integer> {

    private List<Observer<? super Integer>> observers =
            new ArrayList<>();

    protected void subscribeActual(Observer<? super Integer> observer) {
        observers.add(observer);
    }

    public void publish(int n) {
        for (Observer<? super Integer> o : observers) {
            o.onNext(n);
        }
    }
}

class FootballPlayer {
    private int goalScored = 0;
    private EventBroker broker;
    public  String name;

    public FootballPlayer(EventBroker broker, String name) {
        this.broker = broker;
        this.name = name;
    }

    public void score(){
        broker.publish(++goalScored);
    }
}

class FootballCoach {
    public FootballCoach(EventBroker broker) {
        broker.subscribe(i -> {
            System.out.println("Hey, you scored" + i + " goals!");
        });
    }
}