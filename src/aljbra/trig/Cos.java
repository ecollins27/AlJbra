package aljbra.trig;

import aljbra.Expression;
import aljbra.Variable;

class Cos extends Trig {

    Cos(Expression e){
        super(e,Math::cos,Cos.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Trig.sin(operand).negate().multiply(operand.derivative(v));
    }

    @Override
    public Expression withDecimals() {
        return Trig.cos(operand.withDecimals());
    }
}
