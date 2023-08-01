package aljbra.unary.trig;

import aljbra.Expression;
import aljbra.Variable;

public class Cos extends Trig {

    Cos(Expression e){
        super(e,Math::cos,Cos.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Trig.sin(operand).negate().multiply(operand.derivative(v));
    }
}
