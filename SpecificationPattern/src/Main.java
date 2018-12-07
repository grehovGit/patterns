import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String... args)  throws Exception{
        Product apple = new Product(Color.GREEN,  Size.SMALL);
        Product tree = new Product(Color.GREEN,  Size.BIG);
        Product house = new Product(Color.YELLOW,  Size.BIG);

        Filter<Product> filter = new GoodFilterImpl();
        List<Product> products = Arrays.asList(apple, tree, house);

        filter.filter(products, new ColorSpecification(Color.GREEN))
                .forEach(p -> System.out.println(p.getColor() + "" + p.getSize()));

        filter.filter(Arrays.asList(apple, tree, house), new SizeSpecification(Size.SMALL)).forEach(p -> System.out.println(p));
    }
}

class Product {
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

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
        return item.getColor() == color;
    }
}

class SizeSpecification implements Specification<Product> {
    Size size;

    SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.getSize() == size;
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
