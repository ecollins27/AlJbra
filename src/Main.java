import aljbra.*;
import aljbra.unary.trig.Trig;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Expression test2 = Scalar.TWO.pow(Scalar.TWO);
        Expression test = Scalar.TWO.pow(Scalar.TWO).multiply(new Variable("x").multiply(new Variable("y")));
        System.out.println(test.derivative(new Variable("x")));
    }
}