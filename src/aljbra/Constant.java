package aljbra;

import java.util.HashMap;

public class Constant extends Variable {

    public final static Constant PI = new Constant("pi","\\pi",Math.PI);
    public final static Constant PHI = new Constant("phi","\\phi",(1 + Math.sqrt(5)) / 2.0);
    public final static Constant E = new Constant("e",Math.E);

    double value;
    public Constant(String name, double value) {
        super(name);
        this.value = value;
    }

    public Constant(String name, String laTeXName, double value){
        super(name,laTeXName);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean isEvaluable() {
        return true;
    }

    @Override
    public double eval(HashMap<String, Double> values) {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Constant && name.equals(((Constant) o).name) && ((Constant) o).value == value;
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ZERO;
    }

    @Override
    protected Expression __add__(Expression e) {
        return new Decimal(value + ((Decimal)e).value);
    }

    @Override
    protected Expression __multiply__(Expression e) {
        return new Decimal(value * ((Decimal)e).value);
    }

    @Override
    protected Expression __pow__(Expression e) {
        return new Decimal(Math.pow(value,((Decimal)e).value));
    }

    @Override
    protected boolean isAdditionCompatible(Expression e) {
        return e instanceof Decimal;
    }

    @Override
    protected boolean isMultiplicationCompatible(Expression e) {
        return e instanceof Decimal;
    }

    @Override
    protected boolean isPowCompatible(Expression e) {
        return e instanceof Decimal;
    }
}
