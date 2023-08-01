package aljbra.unary.trig;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

public class Tan extends Trig {

    Tan(Expression e){
        super(e,Math::tan,Tan.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Trig.cos(operand).pow(Scalar.TWO).invert().multiply(operand.derivative(v));
    }
}
