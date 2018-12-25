package copyviaserialization;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class CopyViaSerialization {
    public static void main(String[] args) {
        Foo foo = new Foo(42, "life");
//        Foo foo1 = SerializationUtils.roundtrip(foo);
        Foo foo1 = SerializationUtils.clone(foo);

        foo1.whatever = "zyz";

        System.out.println(foo);
        System.out.println(foo1);

    }
}

class Foo implements Serializable {
    public int stuff;
    public String whatever;

    public Foo(int stuff, String whatever) {
        this.stuff = stuff;
        this.whatever = whatever;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "stuff=" + stuff +
                ", whatever='" + whatever + '\'' +
                '}';
    }
}
