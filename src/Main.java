import aljbra.*;
import aljbra.trig.Trig;

public class Main {

    public static void main(String[] args) {
        Expression e = ExpressionParser.parse("2^2 * 3^3");
        System.out.println(e);
        System.out.println(e.getClass());
    }
}