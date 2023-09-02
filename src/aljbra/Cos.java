package aljbra;

class Cos extends Trig {

    Cos(Expression e){
        super(e,Math::cos,Cos.class);
    }

    @Override
    public Expression derivative(Variable v) {
        return Trig.sin(operand).negate().multiply(operand.derivative(v));
    }
}
