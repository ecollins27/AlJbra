import aljbra.*;

public class Main {
    public static void main(String[] args) {
        Expression test = fibonacci(Scalar.ONE);
        Expression test1 = test.fullSimplify();
        System.out.println(test1.toLaTeX());
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