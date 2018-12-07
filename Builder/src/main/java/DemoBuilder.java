import java.util.ArrayList;
import java.util.Collections;

class HtmlElement {
        public String name, text;
        public ArrayList<HtmlElement> elements = new ArrayList<>();
        private final int indentSize = 2;
        private final String newLine = System.lineSeparator();

        public HtmlElement(String name, String text) {
            this.name = name;
            this.text = text;
        }

        public HtmlElement() {
        }

        private String toString(int indent) {
            StringBuilder sb = new StringBuilder();
            String i = String.join("", Collections.nCopies(indent * indentSize, " "));
            sb.append(String.format("%s<%s>%s", i, name, newLine));
            if (text != null && !text.isEmpty()) {
                sb.append(String.join("", Collections.nCopies(indentSize * (indent + 1), " ")))
                        .append(text)
                        .append(newLine);
            }

            for (HtmlElement e : elements) {
                sb.append(e.toString( indent + 1));
            }

            sb.append(String.format("%s</%s>%s", i, name, newLine));
            return sb.toString();
        }

        @Override
        public String toString() {
            return toString(0);
        }
    }

    class HtmlBuilder {
        private String rootName;
        private HtmlElementFluent root = new HtmlElementFluent();

        public HtmlBuilder(String rootName) {
            this.rootName = rootName;
            root.name = rootName;
        }

        public void addChild(String childName, String childText) {
            HtmlElementFluent e = new HtmlElementFluent(childName, childText);
            root.elements.add(e);
        }

        public void clear() {
            root = new HtmlElementFluent();
            root.name = rootName;
        }

        @Override
        public String toString() {
            return root.toString();
        }
    }

public class DemoBuilder {


    public static void main(String... args) {
        StringBuffer stringBuffer = new StringBuffer();

        HtmlBuilderFluent htmlBuilderFluent = new HtmlBuilderFluent("ul");
        htmlBuilderFluent.addChild("li", "hello");
        htmlBuilderFluent.addChild("li", "hello");
        System.out.println(htmlBuilderFluent);
    }
}
