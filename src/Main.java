import aljbra.*;
import aljbra.trig.Trig;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        VariableMap variableMap = new VariableMap();
        Variable x = new Variable('x');
        Variable y = new Variable('y');
        variableMap.put(x,2.0);
        variableMap.put(y,3.0);
        System.out.println(variableMap.entrySet());
    }
}