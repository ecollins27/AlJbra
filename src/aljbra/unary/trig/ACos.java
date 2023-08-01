package aljbra.unary.trig;

import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

public class ACos extends Trig {

    public ACos(Expression e){
        super(e,Math::acos,ACos.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.subtract(operand.pow(Scalar.TWO)).sqrt().invert().multiply(operand.derivative(v).negate());
    }
}
