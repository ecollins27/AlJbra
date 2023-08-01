package aljbra;

import java.util.ArrayList;
import java.util.HashMap;

public class Log extends Expression {

    public static Expression log(Expression base, Expression operand){
        if (base.equals(operand)){
            return Scalar.ONE;
        } else if (operand instanceof Exponential){
            return log(base,((Exponential) operand).base).multiply(((Exponential) operand).exponent);
        } else if (operand instanceof Product){
            Expression sum = Scalar.ZERO;
            for (Expression term: ((Product) operand).terms){
                sum = sum.add(log(base,term));
            }
            return sum;
        } else if (base instanceof Scalar){
            if (operand instanceof Fraction){
                if (!((Fraction) operand).num.equals(Scalar.ONE)){
                    return log(base,((Fraction) operand).num).subtract(log(base,((Fraction) operand).den));
                } else {
                    return log(base,operand.invert()).negate();
                }
            } else if (operand instanceof Scalar){
                ArrayList<long[]> baseFactors = clone(((Scalar) base).primeFactorization);
                ArrayList<long[]> operandFactors = clone(((Scalar) operand).primeFactorization);
                long minCount = Long.MAX_VALUE;
                for (long[] factor: baseFactors){
                    int index = indexOf(factor[0],operandFactors);
                    if (index < 0){
                        minCount = 0;
                        break;
                    } else {
                        long count = operandFactors.get(index)[1] / factor[1];
                        minCount = Math.min(minCount,count);
                        if (operandFactors.get(index)[1] == factor[1] * count){
                            operandFactors.remove(index);
                        } else {
                            operandFactors.get(index)[1] -= factor[1] * count;
                        }
                    }
                }
                Expression sum = new Scalar(minCount == Long.MAX_VALUE? 0:minCount);
                for (long[] factor: operandFactors){
                    sum = sum.add(new Log(base,new Scalar(factor[0])).multiply(new Scalar(factor[1])));
                }
                return sum;
            }
        }
        return new Log(base,operand);
    }

    public static Expression ln(Expression operand){
        return log(Constant.E,operand);
    }
    private static int indexOf(long n, ArrayList<long[]> arrayList){
        for (int i = 0; i < arrayList.size();i++){
            if (arrayList.get(i)[0] == n){
                return i;
            }
        }
        return -1;
    }

    Expression base,operand;
    Log(Expression base, Expression operand){
        this.base = base;
        this.operand = operand;
    }
    @Override
    public Expression negate() {
        return this.multiply(Scalar.NEG_ONE);
    }

    @Override
    public Expression invert() {
        return this.pow(Scalar.NEG_ONE);
    }

    @Override
    public Expression derivative(Variable v) {
        if (base.equals(Constant.E)){
            return operand.invert().multiply(operand.derivative(v));
        }
        return ln(operand).divide(ln(base)).derivative(v);
    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof Log && ((Log) e).base.equals(base) && ((Log) e).operand.equals(operand);
    }

    @Override
    public double eval(HashMap<String, Double> values) {
        return Math.log(operand.eval(values)) / Math.log(base.eval(values));
    }

    @Override
    public boolean contains(Expression e) {
        if (this.equals(e)){
            return true;
        }
        return base.contains(e) || operand.contains(e);
    }

    @Override
    public Expression replace(Expression e, Expression with) {
        if (this.equals(e)){
            return with;
        }
        return log(base.replace(e,with),operand.replace(e,with));
    }

    @Override
    public String toString() {
        if (base.equals(Constant.E)){
            return "ln(" + operand.toString() + ")";
        } else {
            return "logBase(" + base.toString() + "," + operand.toString() + ")";
        }
    }

    @Override
    public String toLaTeX() {
        if (base.equals(Constant.E)){
            return "\\ln(" + operand.toLaTeX() + ")";
        } else {
            return "\\log_{" + base.toLaTeX() + "}(" + operand.toLaTeX() + ")";
        }
    }

    @Override
    public Expression simplify() {
        Expression simplifiedBase = base.simplify();
        Expression simplifiedOperand = operand.simplify();
        return log(simplifiedBase,simplifiedOperand);
    }

    @Override
    protected Expression __add__(Expression e) {
        return null;
    }

    @Override
    protected Expression __multiply__(Expression e) {
        return null;
    }

    @Override
    protected Expression __pow__(Expression e) {
        return null;
    }

    @Override
    protected boolean isAdditionCompatible(Expression e) {
        return false;
    }

    @Override
    protected boolean isMultiplicationCompatible(Expression e) {
        return false;
    }

    @Override
    protected boolean isPowCompatible(Expression e) {
        return false;
    }
}
