package aljbra.trig;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

class ATan extends Trig {

    ATan(Expression e){
        super(e,Math::atan,ATan.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.add(operand.pow(Scalar.TWO)).invert().multiply(operand.derivative(v));
    }

    @Override
    public Expression withDecimals() {
        return Trig.atan(operand.withDecimals());
    }
}
