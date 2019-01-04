public class VisitorDemo {

    //INTRUSIVE VISITOR (BREAKS Open/Closed principal)
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
        additionExpession.print(stringBuilder);
        System.out.println(stringBuilder);
    }
}

abstract class Expression {
    public abstract void print(StringBuilder sb);
}

class DoubleExpression extends Expression {
    private double value;

    public DoubleExpression(double value) {
        this.value = value;
    }


    @Override
    public void print(StringBuilder sb) {
        sb.append(value);
    }
}

class AdditionExpession extends Expression {
    private Expression left, right;

    public AdditionExpession(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(StringBuilder sb) {
        sb.append("(");
        left.print(sb);
        sb.append("+");
        right.print(sb);
        sb.append(")");
    }
}
