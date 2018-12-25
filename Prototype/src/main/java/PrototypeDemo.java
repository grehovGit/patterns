import java.util.Arrays;

public class PrototypeDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person person = new Person(new String[]{"Jogn","Smmith"},
                new Address("London road", 123));

        Person jane = (Person) person.clone();
        jane.names[0] = "Jane";
        jane.address.houseNumber = 124;

        System.out.println(person);
        System.out.println(jane);



    }
}


class Address implements Cloneable {
    public String streetName;
    public int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    // deep copy
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Address(streetName, houseNumber);
    }
}

class Person implements Cloneable {
    public String [] names;
    public Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Person(
                names.clone(),
                (Address) address.clone()
        );
    }

    @Override
    public String toString() {
        return "Person{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }
}