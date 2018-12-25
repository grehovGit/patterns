package staticdecorator;

import java.util.function.Supplier;

public class StaticDecoratorDemo {
    public static void main(String[] args) {
        ColoredShape<Square> blueSquare = new ColoredShape<>(
                () -> new Square(20),
                "blue"
        );

        System.out.println(blueSquare.info());

        TransparentShape<ColoredShape<Circle>> shapeTransparentShape =
                new TransparentShape<>(() ->
                        new ColoredShape<>(() -> new Circle(5), "green"),
                        50);

        System.out.println(shapeTransparentShape.info());
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

class ColoredShape<T extends Shape> implements Shape {
    private Shape shape;
    private String color;

    public ColoredShape(Supplier<? extends T> ctor, String color) {
        shape = ctor.get();
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " has the color " + color;
    }
}

class TransparentShape<T extends Shape> implements Shape {
    private Shape shape;
    private int transparency;

    public TransparentShape(Supplier<? extends T> ctor, int transparency) {
        shape = ctor.get();
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + " has the transparency " + transparency;
    }
}