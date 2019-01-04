package hansome;


import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HandsomeSMDemo {
    private static Map<State, List<Pair<Trigger,State>>> rules =
            new HashMap<>();

    static {
        List arrayList = new ArrayList();
        arrayList.add(new Pair<>(Trigger.CALL_DIALED, State.CONNECTING));
        arrayList.add(new Pair<>(Trigger.STOP_USING_PHONE, State.ON_HOOK));
        rules.put(State.OFF_HOOK, arrayList);

        List arrayList2 = new ArrayList();
        arrayList2.add(new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK));
        arrayList2.add(new Pair<>(Trigger.CALL_CONNECTED, State.CONNECTED));
        rules.put(State.CONNECTING, arrayList2);

        List arrayList3 = new ArrayList();
        arrayList3.add(new Pair<>(Trigger.LEFT_MESSAGE, State.OFF_HOOK));
        arrayList3.add(new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK));
        arrayList3.add(new Pair<>(Trigger.PLACED_ON_HOLD, State.ON_HOLD));
        rules.put(State.CONNECTED, arrayList3);

        List arrayList4 = new ArrayList();
        arrayList4.add(new Pair<>(Trigger.TAKEN_OFF_HOLD, State.CONNECTED));
        arrayList4.add(new Pair<>(Trigger.HUNG_UP, State.OFF_HOOK));
        rules.put(State.ON_HOLD, arrayList4);
    }

    private static State currentState = State.OFF_HOOK;
    private static State exitState = State.ON_HOLD;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        while (true) {
            System.out.println("The phone is currently " + currentState);
            System.out.println("Select a trigger:");

            for (int i = 0; i < rules.get(currentState).size(); ++i){
                Trigger trigger = rules.get(currentState).get(i).getValue0();
                System.out.println("" + i + "." + trigger);
            }
            boolean parseOK;
            int choice = 0;

            do {
                try {
                    System.out.println("Enter  choice:");

                    choice = Integer.parseInt(bufferedReader.readLine());
                    parseOK = choice >= 0 &&
                            choice < rules.get(currentState).size();
                } catch (Exception e) {
                    parseOK = false;
                }
            } while(!parseOK);

            currentState = rules.get(currentState).get(choice).getValue1();

            if (currentState == exitState) break;

        }
        System.out.println(" We don! ");
    }
}

enum State {
    OFF_HOOK,   //starting
    ON_HOOK,    // terminal
    CONNECTING,
    CONNECTED,
    ON_HOLD
}

enum Trigger {
    CALL_DIALED,
    HUNG_UP,
    CALL_CONNECTED,
    PLACED_ON_HOLD,
    TAKEN_OFF_HOLD,
    LEFT_MESSAGE,
    STOP_USING_PHONE
}
