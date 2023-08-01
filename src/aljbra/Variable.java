package aljbra;

import java.util.HashMap;

public class Variable extends Expression {

    String name;
    String laTeXName;

    public Variable(String name){
        this.name = name;
        this.laTeXName = name;
    }
    public Variable(String name,String laTeXName){
        this.name = name;
        this.laTeXName = laTeXName;
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
        return this.equals(v)? Scalar.ONE: Scalar.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Variable && !(o instanceof Constant) && name.equals(((Variable) o).name);
    }

    @Override
    public double eval(HashMap<String, Double> values) {
        return values.get(name);
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
        return name;
    }

    @Override
    public String toLaTeX() {
        return laTeXName;
    }

    @Override
    public Expression simplify() {
        return this;
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
