package aljbra.trig;

import aljbra.Constant;
import aljbra.Expression;
import aljbra.Scalar;
import aljbra.Variable;

class ASin extends Trig {

    ASin(Expression e){
        super(e,Math::asin, ASin.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.subtract(operand.pow(Scalar.TWO)).sqrt().invert().multiply(operand.derivative(v));
    }

    @Override
    public Expression withDecimals() {
        return Trig.asin(operand.withDecimals());
    }

    protected static Expression lookup(Expression e){
        for (int i = -6; i < 6; i++) {
            if (Sin.LOOKUP_TABLE[(i + 24) % 24].equals(e)){
                return Constant.PI.multiply(Scalar.valueOf(i)).divide(Scalar.valueOf(12));
            }
        }
        return null;
    }
}
