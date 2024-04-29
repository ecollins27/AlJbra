import aljbra.*;
import aljbra.trig.Trig;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println(Trig.tan(Constant.PI.divide(Scalar.TWO)).toLaTeX());
    }
}