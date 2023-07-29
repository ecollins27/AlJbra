package aljbra;

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
    public boolean equals(Expression e) {
        return e instanceof Variable && !(e instanceof Constant) && name.equals(((Variable)e).name);
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
    Expression __add__(Expression e) {
        return null;
    }

    @Override
    Expression __multiply__(Expression e) {
        return null;
    }

    @Override
    Expression __pow__(Expression e) {
        return null;
    }

    @Override
    boolean isAdditionCompatible(Expression e) {
        return false;
    }

    @Override
    boolean isMultiplicationCompatible(Expression e) {
        return false;
    }

    @Override
    boolean isPowCompatible(Expression e) {
        return false;
    }
}
