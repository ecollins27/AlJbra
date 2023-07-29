package aljbra;

import java.util.ArrayList;
import java.util.Arrays;

public class Fraction extends Expression {

    Scalar num,den;

    Fraction(Scalar num, Scalar den){
        this.num = num;
        this.den = den;
        if (this.den.isNegative()){
            den = (Scalar) den.negate();
            num = (Scalar) num.negate();
        }
    }
    @Override
    public Expression negate() {
        return num.negate().divide(den);
    }

    @Override
    public Expression invert() {
        return den.divide(num);
    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof Fraction && num.equals(((Fraction) e).num) && den.equals(((Fraction) e).den);
    }

    @Override
    public String toString() {
        return num.toString() + " / " + den.toString();
    }

    @Override
    public String toLaTeX() {
        return "\\frac{" + num.toLaTeX() + "}{" + den.toLaTeX() +"}";
    }

    @Override
    public Expression simplify() {
        ArrayList<long[]> numFactors = clone(num.primeFactorization);
        ArrayList<long[]> denFactors = clone(den.primeFactorization);
        for (int i = denFactors.size() - 1; i >= 0; i--){
            long[] factor = denFactors.get(i);
            int index = indexOf(factor[0],numFactors);
            if (index >= 0){
                if (numFactors.get(index)[1] == factor[1]){
                    numFactors.remove(index);
                    denFactors.remove(i);
                } else if (numFactors.get(index)[1] > factor[1]){
                    denFactors.remove(i);
                    numFactors.get(index)[1] -= factor[1];
                } else {
                    denFactors.get(i)[1] -= numFactors.get(index)[1];
                    numFactors.remove(index);
                }
            }
        }
        return new Scalar(numFactors).divide(new Scalar(denFactors));
    }

    @Override
    Expression __add__(Expression e) {
        Scalar num2,den2;
        if (e instanceof Scalar){
            num2 = (Scalar) e;
            den2 = Scalar.ONE;
        } else {
            num2 = ((Fraction)e).num;
            den2 = ((Fraction)e).den;
        }
        return num.multiply(den2).add(num2.multiply(den)).divide(den.multiply(den2));
    }

    @Override
    Expression __multiply__(Expression e) {
        if (e instanceof Scalar){
            return new Fraction((Scalar) num.multiply(e),den);
        }
        Scalar num2 = ((Fraction)e).num, den2 = ((Fraction)e).den;
        return new Fraction((Scalar) num.multiply(num2), (Scalar) den.multiply(den2));
    }

    @Override
    Expression __pow__(Expression e) {
        return num.pow(e).divide(den.pow(e));
    }

    @Override
    boolean isAdditionCompatible(Expression e) {
        return e instanceof Scalar || e instanceof Fraction;
    }

    @Override
    boolean isMultiplicationCompatible(Expression e) {
        return e instanceof Scalar || e instanceof Fraction;
    }

    @Override
    boolean isPowCompatible(Expression e) {
        return num.isPowCompatible(e) && den.isPowCompatible(e);
    }

    private int indexOf(long n, ArrayList<long[]> arrayList){
        for (int i = 0; i < arrayList.size();i++){
            if (arrayList.get(i)[0] == n){
                return i;
            }
        }
        return -1;
    }

    boolean isNegative(){
        return num.isNegative();
    }
}
