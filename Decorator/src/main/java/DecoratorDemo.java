public class DecoratorDemo {

    //want to augment an aobject with additional functionality
    // do not want to rewrite or altr existing functionality
    // want to keep new functionality separate (SRP)
    // need to be able to interact with exising structures

    //Two options:
    //  - inherit from required object if possible; some classes are final
    //  - Bild a decorator, which simply references the decorated objects (aggregation)

    public static void main(String[] args) {
        MagicString magicString = new MagicString("hello");
        System.out.println(magicString + " has "  + magicString.getNumberOfVowels());
    }
}

class MagicString {
    private String string;

    public MagicString(String string) {
        this.string = string;
    }

    public int length() {
        return string.length();
    }

    public  long getNumberOfVowels() {
        return string.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> "aeio".contains(c.toString()))
                .count();
    }

    @Override
    public String toString() {
        return string;
    }
}
