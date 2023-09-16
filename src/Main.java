import aljbra.*;

import java.math.BigDecimal;
import java.util.Formatter;

public class Main {

    public static void main(String[] args) {
        Expression a = new Decimal(5);
        Expression b = Scalar.TWO.sqrt();
        Expression log = Log.log(b,a);
        System.out.println(Log.log(b,a).toLaTeX());
        System.out.println(b.pow(log));
    }
}