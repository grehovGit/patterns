public class StateDemo {
    public static void main(String[] args) {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.on();
        lightSwitch.off();
        lightSwitch.off();
    }
}

class State {
    void on(LightSwitch ls) {
        System.out.println("Light is already on");
    }

    void off(LightSwitch ls) {
        System.out.println("Light is already off");
    }
}

class OnState extends State {
    public OnState() {
        System.out.println("Light on");
    }

    @Override
    void off(LightSwitch ls) {
        System.out.println("Switching light off..");
        ls.setState(new OffState());
    }
}

class OffState extends State {
    public OffState() {
        System.out.println("Light off");
    }

    @Override
    void on(LightSwitch ls) {
        System.out.println("Switching light on..");
        ls.setState(new OnState());
    }
}

class  LightSwitch {
    private State state;

    public LightSwitch() {
        this.state = new OffState();
    }

    public void setState(State state) {
        this.state = state;
    }

    void on(){
        state.on(this);
    }

    void off(){
        state.off(this);
    }
}
