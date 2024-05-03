import aljbra.*;
import aljbra.trig.Trig;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Variable x = new Variable('x');
        System.out.println(x.sq().divide(x.multiply(Scalar.TWO)));
    }
}