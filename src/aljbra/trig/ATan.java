package aljbra.trig;

import aljbra.Constant;
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

    protected static Expression lookup(Expression e){
        for (int i = -6; i < 6; i++) {
            if (Tan.LOOKUP_TABLE[(i + 24) % 24].equals(e)){
                return Constant.PI.multiply(Scalar.valueOf(i)).divide(Scalar.valueOf(12));
            }
        }
        return null;
    }
}
