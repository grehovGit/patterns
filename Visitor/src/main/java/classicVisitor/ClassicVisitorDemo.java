package classicVisitor;

public class ClassicVisitorDemo {
    public static void main(String[] args) {
        // 1 + (2 + 3)
        AdditionExpession additionExpession = new AdditionExpession(
                new DoubleExpression(1),
                new AdditionExpession(
                        new DoubleExpression(2),
                        new DoubleExpression(3)
                )
        );

        StringBuilder stringBuilder = new StringBuilder();
        ExpressionPrinter expressionPrinter = new ExpressionPrinter();
        expressionPrinter.visit(additionExpession);
        System.out.println(expressionPrinter);

        ExpressionCalculator expressionCalculator = new ExpressionCalculator();
        expressionCalculator.visit(additionExpession);
        System.out.println(expressionPrinter + " = " + expressionCalculator);
    }
}

interface ExpressionVisitor {

    void visit(DoubleExpression e);
    void visit(AdditionExpession e);
}

abstract class Expression {
    public abstract void accept(ExpressionVisitor visitor);
}

class DoubleExpression extends Expression {
    public double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

class AdditionExpession extends Expression {
    public Expression left, right;

    public AdditionExpession(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

class ExpressionPrinter implements ExpressionVisitor{

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpression e) {
        sb.append(e.value);
    }

    @Override
    public void visit(AdditionExpession e) {
        sb.append("(");
        e.left.accept(this);
        sb.append("+");
        e.right.accept(this);
        sb.append(")");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class ExpressionCalculator implements ExpressionVisitor {

    public double result;

    @Override
    public void visit(DoubleExpression e) {
        result = e.value;
    }

    @Override
    public void visit(AdditionExpession e) {
        e.left.accept(this);
        double a = result;
        e.right.accept(this);
        double b = result;
        result = a + b;
    }

    @Override
    public String toString() {
        return String.valueOf(result);
    }
}

