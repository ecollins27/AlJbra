package aljbra;

import aljbra.unary.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Expression implements Comparable<Expression>{
    private static int numOpened = 0;
    private final static int defaultWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width / 4;
    private final static int defaultHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 5;

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
        if (e.equals(Scalar.ONE) || this.equals(Scalar.ZERO) || this.equals(Scalar.ONE)){
            return this;
        } else if (e.equals(Scalar.ZERO)){
            return Scalar.ONE;
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
    public Expression abs(){return new Abs(this);}
    public final Expression nRoot(Expression e){
        return this.pow(e.invert());
    }
    public abstract Expression derivative(Variable v);
    public abstract boolean equals(Expression e);
    public abstract double eval(HashMap<String,Double> values);
    public abstract boolean contains(Expression e);
    public abstract Expression replace(Expression e, Expression with);
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
    protected abstract Expression __add__(Expression e);
    protected abstract Expression __multiply__(Expression e);
    protected abstract Expression __pow__(Expression e);
    protected abstract boolean isAdditionCompatible(Expression e);
    protected abstract boolean isMultiplicationCompatible(Expression e);
    protected abstract boolean isPowCompatible(Expression e);

    final static ArrayList<long[]> clone(ArrayList<long[]> arrayList){
        ArrayList<long[]> clone = new ArrayList<>();
        for (int i = 0; i < arrayList.size();i++){
            clone.add(arrayList.get(i).clone());
        }
        return clone;
    }

    @Override
    public int compareTo(Expression o) {
        int index1 = this.getSimplificationIndex();
        int index2 = o.getSimplificationIndex();
        if (index1 == index2){
            return this.toString().compareTo(o.toString());
        } else {
            return Integer.compare(index1,index2);
        }
    }

    private final int getSimplificationIndex(){
        if (this instanceof Exponential && ((Exponential) this).exponent instanceof Fraction){
            return 4;
        }
        try {
            eval(null);
        } catch (NullPointerException n){
            return 2;
        }
        if (this instanceof Fraction || this instanceof Scalar){
            return 0;
        } else {
            return 1;
        }
    }
}
