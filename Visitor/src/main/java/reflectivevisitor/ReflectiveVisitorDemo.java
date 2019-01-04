package reflectivevisitor;

public class ReflectiveVisitorDemo {
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
        ExpressionPrinter.print(additionExpession, stringBuilder);
        System.out.println(stringBuilder);
    }
}

abstract class Expression {
}

class DoubleExpression extends Expression {
    public double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

}

class AdditionExpession extends Expression {
    public Expression left, right;

    public AdditionExpession(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}

class ExpressionPrinter {

    public static void print(Expression e, StringBuilder sb) {
        if (e.getClass() == DoubleExpression.class) {
            sb.append(((DoubleExpression)e).value);
        } else if (e.getClass() == AdditionExpession.class) {
            AdditionExpession ae = (AdditionExpession)e;
            sb.append("(");
            print(ae.left, sb);
            sb.append("+");
            print(ae.right, sb);
            sb.append(")");
        }
    }

}
