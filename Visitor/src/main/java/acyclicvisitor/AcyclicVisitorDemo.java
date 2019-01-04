package acyclicvisitor;

// Uncle Bob
public class AcyclicVisitorDemo {
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
    }
}

interface Visitor {}    //marker interface

interface ExpressionVisitor extends Visitor {
    void visit(Expression obj);
}

interface DoubleExpressionVisitor extends Visitor {
    void visit(DoubleExpression obj);
}

interface AdditionExpressionVisitor extends Visitor {
    void visit(AdditionExpession obj);
}

abstract class Expression {
    public void accept(Visitor visitor) {
        if (visitor instanceof ExpressionVisitor) {
            ((ExpressionVisitor) visitor).visit(this);
        }
    };
}

class DoubleExpression extends Expression {
    public double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
        if (visitor instanceof DoubleExpressionVisitor) {
            ((DoubleExpressionVisitor) visitor).visit(this);
        }
    };
}

class AdditionExpession extends Expression {
    public Expression left, right;

    public AdditionExpession(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(Visitor visitor) {
        if (visitor instanceof AdditionExpressionVisitor) {
            ((AdditionExpressionVisitor) visitor).visit(this);
        }
    };
}

class ExpressionPrinter implements
    DoubleExpressionVisitor,
    AdditionExpressionVisitor {

    private StringBuilder sb = new StringBuilder();

    @Override
    public String toString() {
        return sb.toString();
    }

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
}
