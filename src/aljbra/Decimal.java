package aljbra;

import java.math.BigDecimal;
import java.math.MathContext;

public class Decimal extends Expression {

    public final static Decimal NEG_ONE =Decimal.valueOf(-1.0);
    public final static Decimal ZERO =Decimal.valueOf(0.0);
    public final static Decimal ONE =Decimal.valueOf(1.0);
    public final static Decimal TWO =Decimal.valueOf(2.0);

    public final static Decimal PI =Decimal.valueOf(Math.PI);
    public final static Decimal E =Decimal.valueOf(Math.E);
    public final static Decimal PHI =Decimal.valueOf((1 + Math.sqrt(5)) / 2.0);

    public final static Decimal NAN =Decimal.valueOf(Double.NaN);

    double value;

    public static final Decimal valueOf(double value){
        return new Decimal(value);
    }

    Decimal(double value){
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    boolean isNegative(){
        return value < 0;
    }
    @Override
    public Expression negate() {
        if (this.equals(Decimal.NAN)){
            return Decimal.NAN;
        }
        return Decimal.valueOf(-value);
    }

    @Override
    public Expression invert() {
        if (this.equals(Decimal.NAN)){
            return Decimal.NAN;
        }
        BigDecimal inversion = BigDecimal.ONE.divide(BigDecimal.valueOf(value), MathContext.DECIMAL128);
        return Decimal.valueOf(inversion.doubleValue());
    }

    @Override
    public Expression derivative(Variable v) {
        if (this.equals(Decimal.NAN)){
            return Decimal.NAN;
        }
        return Scalar.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Decimal && ((Decimal) o).value == value) || (o instanceof Decimal && Double.isNaN(value) && Double.isNaN(((Decimal) o).value));
    }

    @Override
    public double eval(VariableMap values) {
        return value;
    }

    @Override
    public Expression withDecimals() {
        return this;
    }

    @Override
    public boolean contains(Expression e) {
        return this.equals(e);
    }

    @Override
    public Expression replace(Expression e, Expression with) {
        if (this.equals(e)){
            return with;
        }
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toLaTeX() {
        return toString();
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public boolean isEvaluable() {
        return true;
    }

    @Override
    public Expression abs() {
        if (this.equals(Decimal.NAN)){
            return Decimal.NAN;
        }
        return Decimal.valueOf(Math.abs(value));
    }

    public Expression asFraction(){
        if (this.equals(Decimal.NAN)){
            return Decimal.NAN;
        }
        return Fraction.valueOf(value);
    }

    public Expression asFraction(int repeatingLength){
        if (this.equals(Decimal.NAN)){
            return Decimal.NAN;
        }
        return Fraction.valueOf(value,repeatingLength);
    }

    @Override
    protected Expression __add__(Expression e) {
        BigDecimal sum = BigDecimal.valueOf(value).add(BigDecimal.valueOf(e.eval(null)));
        return Decimal.valueOf(sum.doubleValue());
    }

    @Override
    protected Expression __multiply__(Expression e) {
        if (this.equals(Decimal.ZERO)){
            return Decimal.ZERO;
        } else if (this.equals(Decimal.ONE)){
            return e;
        }
        BigDecimal product = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(e.eval(null)));
        return Decimal.valueOf(product.doubleValue());
    }

    @Override
    protected Expression __pow__(Expression e) {
        return Decimal.valueOf(Math.pow(value,e.eval(null)));
    }

    @Override
    protected boolean isAdditionCompatible(Expression e) {
        return e.isEvaluable();
    }

    @Override
    protected boolean isMultiplicationCompatible(Expression e) {
        return e.isEvaluable();
    }

    @Override
    protected boolean isPowCompatible(Expression e) {
        return e.isEvaluable();
    }

    protected static boolean isDecimal(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    protected static Expression parseDecimal(String str){
        return Decimal.valueOf(Double.parseDouble(str));
    }
}
