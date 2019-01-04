import java.util.ArrayList;
import java.util.List;

public class MediatorDemo {
    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();
        Person person = new Person("John");
        Person jane = new Person("Jane");

        chatRoom.join(person);
        chatRoom.join(jane);

        person.say("hi room");
        jane.say("oh, hey john");

        Person simon = new Person("Simon");
        chatRoom.join(simon);

        simon.say("hi Everyone!!");

        jane.privateMessage("Simon", "glad see you!!");
    }
}

class Person {
    public String name;
    public ChatRoom room;
    private List<String> chatlog = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public void receiv(String sender, String message) {
        String s = sender + ": " + message + "'";
        System.out.println("[" + name + "'s chat session]" + s);
     }

     public void say(String message) {
        room.broadcast(name, message);
     }

     public void privateMessage(String who, String message) {
        room.message(name, who, message);
     }
}

class ChatRoom {
    private List<Person> people = new ArrayList<>();

    public void broadcast(String source, String message) {
        for (Person person : people) {
            if (!person.name.equals(source)) {
                person.receiv(source, message);
            }


        }
    }

    public void  join ( Person p) {
        String joinMsg = p.name + " joins the room";
        broadcast("room", joinMsg);

        p.room = this;
        people.add(p);
    }

    public  void message(String source, String destination, String message) {
        people.stream()
                .filter(p -> p.name.equals(destination))
                .findFirst()
                .ifPresent(person -> person.receiv(source, message));
    }
}
