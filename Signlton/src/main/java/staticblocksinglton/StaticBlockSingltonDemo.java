package staticblocksinglton;

import java.io.File;
import java.io.IOException;

public class StaticBlockSingltonDemo {
    public static void main(String[] args) {

    }
}

class StaticBlockSinglton {
    private StaticBlockSinglton() throws IOException {
        System.out.println("INIT");
        File.createTempFile(".", ".");
    }

    public static StaticBlockSinglton getInstance() {
        return instance;
    }

    private static StaticBlockSinglton instance;

    static {
        try {
            StaticBlockSinglton staticBlockSinglton = new StaticBlockSinglton();
        } catch (IOException e) {
            System.err.println("failed to create singlton");
        }
    }
}
