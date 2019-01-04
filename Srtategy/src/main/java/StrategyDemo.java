import java.util.Arrays;
import java.util.List;

public class StrategyDemo {
    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor(OutputFormat.MARKDOWN);
        List a = Arrays.asList(new String[] {"liberte", "egalite", "fraterniet"});
        textProcessor.appendList(a);
        System.out.println(textProcessor);

        textProcessor.clear();
        textProcessor.setOutputFormat(OutputFormat.HTML);
        textProcessor.appendList(Arrays.asList(new String[] {"inherit", "encapsul", "polyform"}));
        System.out.println(textProcessor);
    }
}

enum OutputFormat {
    MARKDOWN, HTML
}

interface ListStrategy {
    default void start(StringBuilder sb){};
    default void end(StringBuilder sb){};
    void addListItem(StringBuilder sb, String item);
}

class MarkdownListStrategy implements ListStrategy {
    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append(" * ").append(item)
                .append(System.lineSeparator());
    }
}

class HtmlListStrategy implements ListStrategy {

    @Override
    public void start(StringBuilder sb) {
        sb.append("<ul>").append(System.lineSeparator());
    }

    @Override
    public void end(StringBuilder sb) {
        sb.append("</ul>").append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append(" <li>")
            .append(item)
            .append("</li>")
            .append(System.lineSeparator());
    }
}

class TextProcessor {
    private StringBuilder sb = new StringBuilder();
    private  ListStrategy listStrategy;

    public TextProcessor(OutputFormat format) {
        setOutputFormat(format);
    }

    public void setOutputFormat(OutputFormat format){
        switch (format) {
            case MARKDOWN:
                listStrategy = new MarkdownListStrategy();
                break;
            case HTML:
                listStrategy = new HtmlListStrategy();
                break;
        }
    }

    public void appendList(List<String> items) {
        listStrategy.start(sb);
        for (String item : items) {
            listStrategy.addListItem(sb, item);
        }
        listStrategy.end(sb);
    }

    public void clear() {
        sb.setLength(0);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}