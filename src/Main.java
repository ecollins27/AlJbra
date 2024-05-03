import aljbra.*;
import aljbra.trig.Trig;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Variable l = new Variable('l');
        System.out.println(l.add(Scalar.ONE).pow(Scalar.valueOf(25)).toLaTeX());
    }
}