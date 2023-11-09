import aljbra.*;
import aljbra.trig.Trig;

public class Main {

    public static void main(String[] args) {
        System.out.println(ExpressionParser.parse("x + (11/10 * y) ^ (2/5)").toLaTeX());
    }
}