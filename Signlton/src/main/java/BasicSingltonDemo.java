import java.io.*;

public class BasicSingltonDemo {

    static void saveToFile(BasicSinglton singlton, String filename) throws IOException {
        try(FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(singlton);
        }
    }

    static BasicSinglton readFromFile(String filename) throws IOException, ClassNotFoundException {
        try(FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (BasicSinglton) in.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BasicSinglton singlton = BasicSinglton.getInstance();
        singlton.setVal(123);
        System.out.println(singlton.getVal());

        // 1. Reflection
        // 2. serialization
        String filename = "singlton.bin";
        saveToFile(singlton, filename);
        singlton.setVal(222);

        BasicSinglton basicSinglton = readFromFile(filename);

        System.out.println(singlton == basicSinglton);
        System.out.println(singlton.getVal());
        System.out.println(basicSinglton.getVal());
    }
}

class BasicSinglton implements Serializable{
    private static final BasicSinglton INSTANCE
            = new BasicSinglton();

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    private int val = 0;

    private BasicSinglton() {
    }

    public static BasicSinglton getInstance() {
        return INSTANCE;
    }



    protected Object readResolve() {
        return INSTANCE;
    }
}


