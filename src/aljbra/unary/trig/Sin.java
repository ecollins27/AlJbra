package aljbra.unary.trig;

import aljbra.Expression;
import aljbra.Variable;

public class Sin extends Trig {
    Sin(Expression e) {
        super(e, Math::sin, Sin.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Trig.cos(operand).multiply(operand.derivative(v));
    }
}
