package aljbra;

public class Exponential extends Expression {

    Expression base,exponent;

    Exponential(Expression base, Expression exponent){
        this.base = base;
        this.exponent = exponent;
    }
    @Override
    public Expression negate() {
        return this.multiply(Scalar.NEG_ONE);
    }

    @Override
    public Expression invert() {
        return base.pow(exponent.negate());
    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof Exponential && base.equals(((Exponential) e).base) && exponent.equals(((Exponential) e).exponent);
    }

    @Override
    public String toString() {
        String toString = "";
        if (base instanceof Sum && ((Sum) base).terms.length > 1){
            toString += "(" + base.toString() + ")";
        } else if (base instanceof Product && ((Product) base).terms.length > 1){
            toString += "(" + base.toString() + ")";
        } else {
            toString += base.toString();
        }

        if (exponent instanceof Sum && ((Sum) exponent).terms.length > 1){
            toString += "^(" + exponent.toString() + ")";
        } else if (exponent instanceof Product && ((Product) exponent).terms.length > 1){
            toString += "^(" + exponent.toString() + ")";
        } else if (exponent instanceof Fraction){
            toString += "^(" + exponent.toString() + ")";
        } else {
            toString += "^" + exponent.toString();
        }
        return toString;
    }

    @Override
    public String toLaTeX() {
        if (exponent instanceof Scalar && ((Scalar) exponent).isNegative()){
            return "\\frac{1}{" + base.pow(exponent.negate()).toLaTeX() + "}";
        } else if (exponent instanceof Fraction){
            if (((Fraction) exponent).num.isNegative()){
                return "\\frac{1}{" + base.pow(exponent.negate()).toLaTeX() + "}";
            }
            String toLaTeX = "";
            if (((Fraction) exponent).den.equals(Scalar.TWO)) {
                toLaTeX += "\\sqrt{";
            } else {
                toLaTeX += "\\sqrt[" + ((Fraction) exponent).den.toLaTeX() +"]{";
            }
            return toLaTeX + base.pow(((Fraction) exponent).num).toLaTeX() + "}";
        }
        String toLaTeX = "";
        if (base instanceof Sum && ((Sum) base).terms.length > 1){
            toLaTeX += "(" + base.toLaTeX() + ")";
        } else if (base instanceof Product && ((Product) base).terms.length > 1){
            toLaTeX += "(" + base.toLaTeX() + ")";
        }
        return toLaTeX + "^{" + exponent.toLaTeX() + "}";
    }

    @Override
    public Expression simplify() {
        return base.simplify().pow(exponent.simplify());
    }

    @Override
    Expression __add__(Expression e) {
        return null;
    }

    @Override
    Expression __multiply__(Expression e) {
        if (e instanceof Exponential){
            if (base.equals(((Exponential) e).base)) {
                return base.pow(exponent.add(((Exponential) e).exponent));
            } else {
                return this.invert().multiply(e.invert()).invert();
            }
        } else {
            return base.pow(exponent.add(Scalar.ONE));
        }
    }

    @Override
    Expression __pow__(Expression e) {
        return base.pow(exponent.multiply(e));
    }

    @Override
    boolean isAdditionCompatible(Expression e) {
        return false;
    }

    @Override
    boolean isMultiplicationCompatible(Expression e) {
        if (e instanceof Exponential){
            if (base.equals(((Exponential) e).base)){
                return true;
            } else if (exponent instanceof Scalar && ((Scalar) exponent).isNegative()){
                if (((Exponential) e).exponent instanceof Scalar && ((Scalar) ((Exponential) e).exponent).isNegative()){
                    return true;
                } else if (((Exponential) e).exponent instanceof Fraction && ((Fraction) ((Exponential) e).exponent).isNegative()){
                    return true;
                } else {
                    return false;
                }
            } else if (exponent instanceof Fraction && ((Fraction) exponent).isNegative()){
                if (((Exponential) e).exponent instanceof Scalar && ((Scalar) ((Exponential) e).exponent).isNegative()){
                    return true;
                } else if (((Exponential) e).exponent instanceof Fraction && ((Fraction) ((Exponential) e).exponent).isNegative()){
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return base.equals(e);
        }
    }

    @Override
    boolean isPowCompatible(Expression e) {
        return true;
    }
}
