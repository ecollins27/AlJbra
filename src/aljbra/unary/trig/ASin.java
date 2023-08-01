package aljbra.unary.trig;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

public class ASin extends Trig {

    ASin(Expression e){
        super(e,Math::asin, ASin.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.subtract(operand.pow(Scalar.TWO)).sqrt().invert().multiply(operand.derivative(v));
    }
}
