package aljbra.unary.trig;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

public class ATan extends Trig {

    public ATan(Expression e){
        super(e,Math::atan,ATan.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.add(operand.pow(Scalar.TWO)).invert().multiply(operand.derivative(v));
    }
}
