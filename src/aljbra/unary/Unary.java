package aljbra.unary;

import aljbra.Expression;
import aljbra.Scalar;

import java.util.HashMap;
import java.util.function.Function;

public abstract class Unary extends Expression {

    protected Expression operand;

    protected Unary(Expression operand){
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
    public boolean contains(Expression e) {
        if (this.equals(e)){
            return true;
        }
        return operand.contains(e);
    }

    @Override
    public boolean isEvaluable() {
        return operand.isEvaluable();
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
