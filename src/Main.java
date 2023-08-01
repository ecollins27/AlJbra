import aljbra.*;
import aljbra.unary.trig.Trig;

import java.util.HashMap;

public class Main {

    /*
    TODO:
     */
    public static void main(String[] args) {
        Expression f = Fraction.valueOf(0.33,true);
        Expression f2 = new Scalar(3).invert();
        System.out.println(f.equals(f2));
    }
}