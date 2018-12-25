package enumbased;

import java.io.*;

public class EnumBasedSignltonDemo {
    static void saveToFile(EnumBasedSingleton singlton, String filename) throws IOException {
        try(FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(singlton);
        }
    }

    static EnumBasedSingleton readFromFile(String filename) throws IOException, ClassNotFoundException {
        try(FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (EnumBasedSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String s = "myfile.bin";

//        EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
//        singleton.setVal(111);
//        saveToFile(singleton, s);

        EnumBasedSingleton singleton1 = readFromFile(s);
        System.out.println(singleton1.getVal());
    }
}

enum EnumBasedSingleton {
    INSTANCE;

    EnumBasedSingleton() {
        this.val = 42;
    }

    private int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
