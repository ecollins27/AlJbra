package aljbra;

public class Test {

    public static void main(String[] args){
        Expression f1 = new Variable("x").add(new Variable("y")).invert();
        Expression f2 = new Scalar(6).sqrt().invert();
        System.out.println(f2.getClass());
        System.out.println(f1.isMultiplicationCompatible(f2));
        System.out.println(f1.multiply(f2).toLaTeX());
    }
}
