import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InterpreterDemo {
    public static void main(String[] args) {
        String s = "(13+4)-(12+1)";

        List<Token> tokens = lex(s);
        System.out.println(tokens.stream()
            .map(t -> t.toString())
            .collect(Collectors.joining("\t")));

        Element parsed = parse(tokens);
        System.out.println(s + parsed.eval());
    }

    static List<Token> lex(String input) {
        ArrayList<Token> result = new ArrayList<>();

        for (int i = 0; i < input.length(); ++i) {
            switch (input.charAt(i)) {
                case '+':
                    result.add(new Token(Token.Type.PLUS, "+"));
                    break;
                case '-':
                    result.add(new Token(Token.Type.MINUS, "-"));
                    break;
                case '(':
                    result.add(new Token(Token.Type.LPAREN, "("));
                    break;
                case ')':
                    result.add(new Token(Token.Type.RPAREN, ")"));
                    break;
                default:
                    StringBuilder sb = new StringBuilder("" + input.charAt(i));
                    for (int j = i+1; j < input.length(); ++j) {
                        if (Character.isDigit(input.charAt(j))) {
                            sb.append(input.charAt(j));
                            ++i;
                        } else {
                            result.add(new Token(
                                    Token.Type.INTEGER, sb.toString()
                            ));
                            break;
                        }
                    }
            }
        }
        return result;
    }

    static Element parse(List<Token> tokens) {
        BinaryOpperation result = new BinaryOpperation();
        boolean haveLHS = false;

        for (int i = 0; i< tokens.size(); ++i) {
            Token token = tokens.get(i);

            switch (token.type) {
                case INTEGER:
                    Integer integer = new Integer(java.lang.Integer.parseInt(token.text));
                    if (!haveLHS ) {
                        result.left = integer;
                        haveLHS = true;
                    } else {
                        result.right = integer;
                    }
                    break;
                case PLUS:
                    result.type = BinaryOpperation.Type.ADITION;
                    break;
                case MINUS:
                    result.type = BinaryOpperation.Type.SUBTRACTION;
                    break;
                case LPAREN:
                    int j = 0;
                    for (; j < tokens.size(); ++j) {
                        if (tokens.get(j).type == Token.Type.RPAREN) {
                            break;
                        }
                    }
                    List<Token> subExpression = tokens.stream()
                            .skip(i + 1)
                            .limit(j - i - 1)
                            .collect(Collectors.toList());
                    Element element = parse(subExpression);

                    if (!haveLHS) {
                        result.left = element;
                        haveLHS = true;
                    } else {
                        result.right = element;
                    }
                    i = j;
                    break;
            }
        }
        return result;
    }
}

interface Element {
    int eval();
}

class Integer implements Element {

    private int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOpperation implements Element {

    public enum Type {
        ADITION,
        SUBTRACTION
    }

    public Type type;
    public Element left, right;

    @Override
    public int eval() {
        switch (type) {
            case ADITION:
                return left.eval() + right.eval();
            case SUBTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;
        }
    }
}

class Token {
    public enum Type {
        INTEGER,
        PLUS,
        MINUS,
        LPAREN,
        RPAREN
    }

    public Type type;
    public String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "`" + text + "`";
    }
}

