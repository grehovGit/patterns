import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void Main(String... args) {

        Product apple = new Product(Color.GREEN,  Size.SMALL);
        Product tree = new Product(Color.GREEN,  Size.BIG);
        Product house = new Product(Color.YELLOW,  Size.BIG);

        Filter<Product> filter = new GoodFilterImpl();
        filter.filter(Arrays.asList(apple, tree, house), new ColorSpecification(Color.GREEN));
        filter.filter(Arrays.asList(apple, tree, house), new SizeSpecification(Size.SMALL));
    }
}

class Product {
    private Color color;
    private Size size;

    Product(Color color, Size size) {
        this.color = color;
        this.size = size;
    }
}

interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}

interface Specification <T> {
    boolean isSatisfied(T item);
}

class ColorSpecification implements Specification<Product> {
    Color color;

    ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return false;
    }
}

class SizeSpecification implements Specification<Product> {
    Size size;

    SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return false;
    }
}


class GoodFilterImpl implements Filter<Product>{

    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(product -> spec.isSatisfied(product));
    }
}

enum Color {
        GREEN, RED, YELLOW;
}

enum Size {
    BIG, MIDDLE, SMALL;
}
