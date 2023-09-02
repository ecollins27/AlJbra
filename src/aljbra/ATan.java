package aljbra;

class ATan extends Trig {

    ATan(Expression e){
        super(e,Math::atan,ATan.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.add(operand.pow(Scalar.TWO)).invert().multiply(operand.derivative(v));
    }
}
