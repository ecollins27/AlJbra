package aljbra;

class ASin extends Trig {

    ASin(Expression e){
        super(e,Math::asin, ASin.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ONE.subtract(operand.pow(Scalar.TWO)).sqrt().invert().multiply(operand.derivative(v));
    }
}
