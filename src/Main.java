import aljbra.*;

public class Main {

    /*
    TODO:
     */
    public static void main(String[] args) {
        Expression f1 = new Scalar(5).divide(new Scalar(8));
        Expression f2 = new Scalar(6).divide(new Scalar(13));
        System.out.println(f1.multiply(new Scalar(8)));
        Expression fibonacci = fibonacci(new Scalar(10));
        fibonacci.visualize();
    }

    public static Expression fibonacci(Expression n){
        Expression PHI = Scalar.ONE.add(new Scalar(5).sqrt()).divide(Scalar.TWO);
        return PHI.pow(n).subtract(PHI.invert().negate().pow(n)).divide(new Scalar(5).sqrt()).fullSimplify();
    }

    public static Expression quadratic(Expression A, Expression B, Expression C){
        Expression det = B.pow(Scalar.TWO).subtract(new Scalar(4).multiply(A).multiply(C));
        return B.negate().add(det.pow(Scalar.TWO.invert())).divide(Scalar.TWO.multiply(A));
    }
}