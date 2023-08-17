package aljbra.examples;

import aljbra.Constant;
import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;
import aljbra.unary.trig.Trig;

import java.util.Arrays;
import java.util.HashMap;

public class DerivativeExample {

    public static void main(String[] args){
        // Construct f and its gradient
        Variable x = new Variable("x"), y = new Variable("y"), z = new Variable("z");
        Expression F = Trig.cos((Scalar.TWO.multiply(x).add(y).add(new Scalar(5).multiply(z))).pow(Scalar.TWO)).fullSimplify();
        System.out.println("f(x,y,z) = " + F.toLaTeX());
        Expression[] gradient = new Expression[]{
                F.derivative(x).fullSimplify(),
                F.derivative(y).fullSimplify(),
                F.derivative(z).fullSimplify()
        };
        // replace x,y,z with precise values and simplify gradient
        Expression xValue = new Scalar(3);
        Expression yValue = Scalar.NEG_ONE;
        Expression zValue = Constant.E.pow(Scalar.TWO);
        for (int i = 0; i < gradient.length;i++){
            gradient[i] = gradient[i].replace(x,xValue).replace(y,yValue).replace(z,zValue).fullSimplify();
        }

        // calculate decimal approximations of gradient
        double[] doubleGradient = new double[]{gradient[0].eval(null),gradient[1].eval(null),gradient[2].eval(null)};
        System.out.println("The gradient of f with respect to x at (3,-1,e^2) is " + gradient[0] + " ≈ " + doubleGradient[0]);
        System.out.println("The gradient of f with respect to y at (3,-1,e^2) is " + gradient[1] + " ≈ " + doubleGradient[1]);
        System.out.println("The gradient of f with respect to z at (3,-1,e^2) is " + gradient[2] + " ≈ " + doubleGradient[2]);
    }
}
