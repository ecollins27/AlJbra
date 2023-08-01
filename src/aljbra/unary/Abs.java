package aljbra.unary;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

import java.util.HashMap;

public class Abs extends Unary {
    public Abs(Expression e) {
        super(e);
    }

    @Override
    public Expression derivative(Variable v) {
        return operand.divide(this).multiply(operand.derivative(v));
    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof Abs && operand.equals(((Abs) e).operand);
    }

    @Override
    public double eval(HashMap<String, Double> values) {
        return Math.abs(operand.eval(values));
    }

    @Override
    public Expression replace(Expression e, Expression with) {
        if (this.equals(e)){
            return with;
        }
        return operand.replace(e,with).abs();
    }

    @Override
    public String toString() {
        return "|" + operand.toString() + "|";
    }

    @Override
    public String toLaTeX() {
        return "|" + operand.toLaTeX() + "|";
    }

    @Override
    public Expression simplify() {
        return operand.simplify().abs();
    }
}
