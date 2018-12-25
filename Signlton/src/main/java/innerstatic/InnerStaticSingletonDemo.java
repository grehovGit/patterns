package innerstatic;

public class InnerStaticSingletonDemo {
    public static void main(String[] args) {
    }
}

class InnerStaticSignlton {
    private InnerStaticSignlton() {
    }

    private static class Impl {
        private static final InnerStaticSignlton
            INSTANCE = new InnerStaticSignlton();
    }
    public InnerStaticSignlton getInstance() {
        return Impl.INSTANCE;
    }
}