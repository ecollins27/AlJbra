import aljbra.*;
import aljbra.unary.trig.Trig;

public class Main {

    /*
    TODO:
     */
    public static void main(String[] args) {
        Expression test = Log.ln(new Variable("x"));
        System.out.println(test.fullSimplify().toLaTeX());
    }

    public static Expression cubic(Expression A, Expression B, Expression C, Expression D){
        Expression P = B.negate().divide(new Scalar(3).multiply(A));
        Expression Q = P.pow(new Scalar(3)).add(B.multiply(C).subtract(new Scalar(3).multiply(A).multiply(D)).divide(new Scalar(6).multiply(A.pow(Scalar.TWO))));
        Expression R = C.divide(new Scalar(3).multiply(A));
        Expression cubic1 = Q.add(Q.pow(Scalar.TWO).add(R.subtract(P.pow(Scalar.TWO)).pow(new Scalar(3))).sqrt()).nRoot(new Scalar(3));
        Expression cubic2 = Q.subtract(Q.pow(Scalar.TWO).add(R.subtract(P.pow(Scalar.TWO)).pow(new Scalar(3))).sqrt()).nRoot(new Scalar(3));
        return cubic1.add(cubic2).add(P).fullSimplify();
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