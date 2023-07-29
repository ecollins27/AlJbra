package aljbra;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Expression {

    final static Comparator<Expression> termComparator = new Comparator<Expression>() {
        @Override
        public int compare(Expression o1, Expression o2) {
            if (o1.getClass().equals(o2.getClass())){
                return 0;
            }
            int value1;
            if (o1 instanceof Fraction || o1 instanceof Scalar){
                value1 = 0;
            } else if (o1 instanceof Exponential){
                value1 = 1;
            } else {
                value1 = 2;
            }
            int value2;
            if (o2 instanceof Fraction || o2 instanceof Scalar){
                value2 = 0;
            } else if (o2 instanceof Exponential){
                value2 = 1;
            } else {
                value2 = 2;
            }
            return Integer.compare(value1,value2);
        }
    };

    public final Expression add(Expression e){
        if (e.equals(Scalar.ZERO)){
            return this;
        } else if (this.isAdditionCompatible(e)){
            return this.__add__(e);
        } else if (e.isAdditionCompatible(this)){
            return e.add(this);
        } else {
            return new Sum(this,e);
        }
    }
    public final Expression subtract(Expression e){
        return this.add(e.negate());
    }
    public final Expression multiply(Expression e){
        if (e.equals(Scalar.ZERO)){
            return Scalar.ZERO;
        } else if (e.equals(Scalar.ONE)){
            return this;
        } else if (this.isMultiplicationCompatible(e)){
            return this.__multiply__(e);
        } else if (e.isMultiplicationCompatible(this)){
            return e.multiply(this);
        } else {
            return new Product(this,e);
        }
    }
    public final Expression divide(Expression e){
        return this.multiply(e.invert());
    }
    public final Expression pow(Expression e){
        if (e.equals(Scalar.ONE)){
            return this;
        } else if (e.equals(Scalar.ZERO)){
            return Scalar.ONE;
        } else if (e.equals(Scalar.NEG_ONE)){
            return new Exponential(this,e);
        } else if (this.isPowCompatible(e)){
            return this.__pow__(e);
        } else {
            return new Exponential(this,e);
        }
    }
    public abstract Expression negate();
    public abstract Expression invert();
    public final Expression sqrt(){
        return this.nRoot(Scalar.TWO);
    }
    public final Expression nRoot(Expression e){
        return this.pow(e.invert());
    }
    public abstract boolean equals(Expression e);
    public abstract String toString();
    public abstract String toLaTeX();
    public abstract Expression simplify();
    public final Expression fullSimplify(){
        Expression simplified = this.simplify();
        Expression prev = this;
        while (!prev.equals(simplified)){
            prev = simplified;
            simplified = simplified.simplify();
        }
        return simplified;
    }
    abstract Expression __add__(Expression e);
    abstract Expression __multiply__(Expression e);
    abstract Expression __pow__(Expression e);
    abstract boolean isAdditionCompatible(Expression e);
    abstract boolean isMultiplicationCompatible(Expression e);
    abstract boolean isPowCompatible(Expression e);

    final static ArrayList<long[]> clone(ArrayList<long[]> arrayList){
        ArrayList<long[]> clone = new ArrayList<>();
        for (int i = 0; i < arrayList.size();i++){
            clone.add(arrayList.get(i).clone());
        }
        return clone;
    }
}
