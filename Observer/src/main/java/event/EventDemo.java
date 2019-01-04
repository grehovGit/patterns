package event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventDemo {
    public static void main(String[] args) throws Exception {
        Person person = new Person();
        Event<PropertyChangedEventArgs>.Subscription subscription =
                person.propertyChaned.addHandler(x -> {
                    System.out.println("Person's " + x.propertyName +
                    " ahs changed");
        });

        person.setAge(17);
        person.setAge(25);
        subscription.close();
        person.setAge(19);

    }
}


class Event<TArgs>{
    private int count = 0;
    private Map<Integer, Consumer<TArgs>> handlers =
            new HashMap<>();

    public Subscription addHandler(Consumer<TArgs> handler) {
        int i = count;
        handlers.put(count++, handler);
        return new Subscription(this, i);
    }

    public void fire(TArgs args) {
        for (Consumer<TArgs> handler : handlers.values()) {
            handler.accept(args);
        }
    }

    public class Subscription implements AutoCloseable {
        private Event<TArgs> event;
        private int id;

        public Subscription(Event<TArgs> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() throws Exception {
            event.handlers.remove(id);
        }
    }
}

class PropertyChangedEventArgs {
    public Object source;
    public String propertyName;

    public PropertyChangedEventArgs(Object source, String propertyName) {
        this.source = source;
        this.propertyName = propertyName;
    }
}

class Person {
    public Event<PropertyChangedEventArgs> propertyChaned =
            new Event<>();

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (this.age == age) return;

        this.age = age;
        propertyChaned.fire(new PropertyChangedEventArgs(
                this,
                "age"
        ));
    }
}
