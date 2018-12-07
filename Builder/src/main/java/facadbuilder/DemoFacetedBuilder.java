package facadbuilder;

import java.util.ArrayList;
import java.util.Collections;

class Person {
    public String streetAddress, postcode, city;

    public String companyName, position;

    @Override
    public String toString() {
        return "Person{" +
                "streetAddress='" + streetAddress + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

class PersonBuilder {
    protected Person person = new Person();

    public Person build() {
        return person;
    }

    public PersonAddressBuilder lives() {
        return new PersonAddressBuilder(person);
    }

    public PersonJobBuilder works() {
        return new PersonJobBuilder(person);
    }
}

class PersonAddressBuilder extends PersonBuilder {
    public PersonAddressBuilder(Person person) {
        this.person = person;
    }

    public PersonAddressBuilder at(String streetAddress) {
        person.streetAddress = streetAddress;
        return this;
    }

    public PersonAddressBuilder withPostcode(String postcode) {
        person.postcode = postcode;
        return this;
    }

    public PersonAddressBuilder in(String city) {
        person.city = city;
        return this;
    }
}

class PersonJobBuilder extends PersonBuilder {
    public PersonJobBuilder(Person person) {
        this.person = person;
    }

    public PersonJobBuilder at(String companyName) {
        person.companyName = companyName;
        return this;
    }

}

public class DemoFacetedBuilder {

    public static void main(String... args) {
        PersonBuilder personBuilder = new PersonBuilder();
        Person person = personBuilder
                .lives()
                .at("London str")
                .in("London")
                .withPostcode("000")
                .works()
                .at("Fabrikam")
                .build();

        System.out.println(person);

    }
}
