package aljbra.trig;

import aljbra.Constant;
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

    protected static Expression lookup(Expression e){
        for (int i = 0; i < 12; i++) {
            if (Cos.LOOKUP_TABLE[i].equals(e)){
                return Constant.PI.multiply(Scalar.valueOf(i)).divide(Scalar.valueOf(12));
            }
        }
        return null;
    }
}
