import aljbra.*;

import java.math.BigDecimal;
import java.util.Formatter;

public class Main {

    public static void main(String[] args) {
        Expression a = new Scalar(5);
        Expression b = Scalar.TWO;
        Expression log = Log.log(b,a);
        System.out.println(Log.log(b,a));
        System.out.println(b.pow(log));
    }
}