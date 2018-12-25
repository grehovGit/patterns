package dynamicdecorator;

public class DynamicDecoratorDemo {
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.println(circle.info());

        ColoredShape coloredShape = new ColoredShape(new Square(20), "blue");
        System.out.println(coloredShape.info());

        TransparentShape transparentShape = new TransparentShape(new ColoredShape(
                new Circle(5), "green"), 50);
        System.out.println(transparentShape.info());

    }
}

interface Shape {
    String info();
}

class Circle implements Shape {

    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    public Circle() {
    }

    void resize(float factor) {
        radius *= factor;
    }

    @Override
    public String info() {
        return "A circle of a side:" + radius;
    }
}

class Square implements Shape {

    private float side;

    public Square(float side) {
        this.side = side;
    }

    public Square() {
    }

    void resize(float factor) {
        side *= factor;
    }

    @Override
    public String info() {
        return "A square of a side:" + side;
    }
}

class ColoredShape implements Shape {

    private Shape shape;
    private String color;

    public ColoredShape(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " has the color " + color;
    }
}

class TransparentShape implements Shape {

    private Shape shape;
    private float transparency;

    public TransparentShape(Shape shape, float transparency) {
        this.shape = shape;
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + " has the transparency " + transparency;
    }
}
