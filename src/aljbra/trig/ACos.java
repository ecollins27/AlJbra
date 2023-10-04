package aljbra.trig;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

class ACos extends Trig {

    ACos(Expression e){
        super(e,Math::acos,ACos.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.subtract(operand.pow(Scalar.TWO)).sqrt().invert().multiply(operand.derivative(v).negate());
    }

    @Override
    public Expression withDecimals() {
        return Trig.acos(operand.withDecimals());
    }
}
