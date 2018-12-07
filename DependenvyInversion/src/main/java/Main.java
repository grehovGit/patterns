import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void  main(String... args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationship = new Relationships();
        relationship.addParentAndChild(parent,child1);
        relationship.addParentAndChild(parent,child2);

        Research research = new Research(relationship);
    }

}

enum Relationship {
    PARENT,
    CHILD,
    SIBLING
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}


class Relationships implements RelationshipBrowser { // low-level
    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    private List<Triplet<Person, Relationship, Person>> relations
            = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child){
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(x -> Objects.equals(x.getValue0().name, name)
                        && x.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }
}

class Research { // high-level
/*    public Research(Relationships relationships){
        List<Triplet<Person, Relationship, Person>> relations =
                relationships.getRelations();
    }*/

    public Research(RelationshipBrowser browser) {
        browser.findAllChildrenOf("John")
                .stream()
                .forEach(person -> System.out.println(person.name));
    }
}

