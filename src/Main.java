import aljbra.*;

public class Main {

    /*
    TODO:
        Convert Scalar to represent BigInteger
        add eval() - return BigDecimal
        add replace(),contains,visualize(),save(),Fraction.valueOf()
     */
    public static void main(String[] args) {
        Expression test = new Variable("x").add(new Variable("y").add(new Variable("z"))).pow(new Scalar(4));
        System.out.println(test.fullSimplify().toLaTeX());
    }

    public static Expression fibonacci(Expression n){
        Expression PHI = Scalar.ONE.add(new Scalar(5).sqrt()).divide(Scalar.TWO);
        return PHI.pow(n).subtract(PHI.invert().negate().pow(n)).divide(new Scalar(5).sqrt());
    }

    public static Expression quadratic(Expression A, Expression B, Expression C){
        Expression det = B.pow(Scalar.TWO).subtract(new Scalar(4).multiply(A).multiply(C));
        return B.negate().add(det.pow(Scalar.TWO.invert())).divide(Scalar.TWO.multiply(A));
    }
}