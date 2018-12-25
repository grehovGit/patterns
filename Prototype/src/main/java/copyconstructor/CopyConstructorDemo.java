package copyconstructor;

public class CopyConstructorDemo {
    public static void main(String[] args) {
        Emplyee john = new Emplyee("John",
                new Address("123", "London", "UK"));

        Emplyee chris = new Emplyee(john);
        chris.name = "Chris";

        System.out.println(john);
        System.out.println(chris);
    }
}

class Address {
     public String streetAddress, city, country;

    public Address(String streetAddress, String city, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

    public Address(Address address) {
        this(address.streetAddress, address.city, address.country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

class Emplyee {
    public String name;
    public Address addtress;

    public Emplyee(String name, Address addtress) {
        this.name = name;
        this.addtress = addtress;
    }

    public Emplyee(Emplyee other) {
        name = other.name;
        addtress = new Address(other.addtress);
    }

    @Override
    public String toString() {
        return "Emplyee{" +
                "name='" + name + '\'' +
                ", addtress='" + addtress + '\'' +
                '}';
    }
}

