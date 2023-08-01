package aljbra.examples;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;
import aljbra.unary.trig.Trig;

import java.util.Arrays;
import java.util.HashMap;

public class DerivativeExample {

    public static void main(String[] args){
        Variable x = new Variable("x"), y = new Variable("y"), z = new Variable("z");
        Expression F = Trig.cos((Scalar.TWO.multiply(x).add(y).add(new Scalar(5).multiply(z))).pow(Scalar.TWO)).fullSimplify();
        System.out.println("f(x,y,z) = " + F);
        Expression[] gradient = new Expression[]{
                F.derivative(x).fullSimplify(),
                F.derivative(y).fullSimplify(),
                F.derivative(z).fullSimplify()
        };

        HashMap<String,Double> valueMap = new HashMap<>();
        valueMap.put("x",3.0);
        valueMap.put("y",-1.0);
        valueMap.put("z",0.0);
        double[] doubleGradient = new double[]{gradient[0].eval(valueMap),gradient[1].eval(valueMap),gradient[2].eval(valueMap)};
        System.out.println("The gradient of f at (3,-1,0) is " + Arrays.toString(doubleGradient));
    }
}
