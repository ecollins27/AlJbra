package aljbra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Product extends Expression {

    Expression[] terms;

    Product(Expression... terms){
        this.terms = terms;
        Arrays.sort(this.terms,termComparator);
    }
    @Override
    public Expression negate() {
        Expression product = Scalar.ONE;
        boolean containsNegative = false;
        for (Expression term: terms){
            if (!term.equals(Scalar.NEG_ONE)){
                product = product.multiply(term);
            } else {
                containsNegative = true;
            }
        }
        if (containsNegative){
            return product;
        } else {
            return product.multiply(Scalar.NEG_ONE);
        }
    }

    @Override
    public Expression invert() {
        Expression product = Scalar.ONE;
        for (Expression term: terms){
            product = product.multiply(term.invert());
        }
        return product;
    }

    @Override
    public boolean equals(Expression e) {
        if (!(e instanceof Product) || ((Product) e).terms.length != terms.length){
            return false;
        }
        for (int i = 0; i < terms.length;i++){
            if (!terms[i].equals(((Product) e).terms[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String toString = "";
        for (int i = 0; i < terms.length;i++){
            if (i > 0){
                toString += " * ";
            } if (terms[i] instanceof Sum && ((Sum) terms[i]).terms.length > 1) {
                toString += terms[i].toString();
            }
        }
        return toString;
    }

    @Override
    public String toLaTeX() {
        String toLaTeX = "";
        for (int i = 0; i < terms.length;i++){
            toLaTeX += terms[i].toLaTeX();
        }
        return toLaTeX;
    }

    @Override
    public Expression simplify() {
        ArrayList<Expression[]> map = new ArrayList<>();
        Queue<Expression> queue = new LinkedList<>(Arrays.asList(terms));
        while (!queue.isEmpty()){
            Expression simplified = queue.poll().simplify();
            Expression base,exponent;
            if (simplified instanceof Product){
                for (Expression eTerm: ((Product) simplified).terms){
                    queue.add(eTerm);
                }
                continue;
            } else if (simplified instanceof Exponential){
                base = ((Exponential) simplified).base;
                exponent = ((Exponential) simplified).exponent;
            } else {
                base = simplified;
                exponent = Scalar.ONE;
            }
            int index = indexOf(base,map);
            if (index >= 0){
                map.get(index)[1] = map.get(index)[1].add(exponent);
            } else {
                map.add(new Expression[]{base,exponent});
            }
        }
        Expression product = Scalar.ONE;
        for (Expression[] term: map){
            product = product.multiply(term[0].pow(term[1].simplify()));
        }
        return product;
    }

    @Override
    Expression __add__(Expression e) {
        Expression coefficient = getCoefficient();
        Expression term = getTerm();
        if (e instanceof Product){
            Expression coefficient2 = ((Product) e).getCoefficient();
            return coefficient.add(coefficient2).multiply(term);
        }
        return coefficient.add(Scalar.ONE).multiply(term);
    }

    @Override
    Expression __multiply__(Expression e) {
        if (e instanceof Product){
            Expression product = this;
            for (Expression eTerm: ((Product) e).terms){
                product = product.multiply(eTerm);
            }
        }
        boolean addTerm = true;
        for (Expression term: terms){
            if (term.isMultiplicationCompatible(e) || e.isMultiplicationCompatible(term)){
                addTerm = false;
            }
        }
        if (addTerm){
            Expression[] newTerms = Arrays.copyOf(terms,terms.length + 1);
            newTerms[terms.length] = e;
            return new Product(newTerms);
        } else {
            Expression[] newTerms = terms.clone();
            for (int i = 0; i < newTerms.length;i++){
                if (newTerms[i].isMultiplicationCompatible(e)){
                    newTerms[i] = newTerms[i].multiply(e);
                    break;
                } else if (e.isMultiplicationCompatible(newTerms[i])){
                    newTerms[i] = e.multiply(newTerms[i]);
                    break;
                }
            }
            return new Product(newTerms);
        }
    }

    @Override
    Expression __pow__(Expression e) {
        Expression product = Scalar.ONE;
        for (Expression term: terms){
            product = product.multiply(term.pow(e));
        }
        return product;
    }

    @Override
    boolean isAdditionCompatible(Expression e) {
        Expression term = getTerm();
        if (e instanceof Product){
            return ((Product) e).getTerm().equals(term);
        }
        return e.equals(term);
    }

    @Override
    boolean isMultiplicationCompatible(Expression e) {
        return !(e instanceof Sum);
    }

    @Override
    boolean isPowCompatible(Expression e) {
        return true;
    }

    Expression getCoefficient(){
        Expression coefficient = Scalar.ONE;
        for (Expression term: terms){
            if (term instanceof Fraction || term instanceof Scalar){
                coefficient = coefficient.multiply(term);
            }
        }
        return coefficient;
    }

    Expression getTerm(){
        Expression term = Scalar.ONE;
        for (Expression expression: terms){
            if (!(expression instanceof Scalar) && !(expression instanceof Fraction)){
                term = term.multiply(expression);
            }
        }
        return term;
    }

    private int indexOf(Expression e,ArrayList<Expression[]> arrayList){
        for (int i = 0; i < arrayList.size();i++){
            if (arrayList.get(i)[0].equals(e)){
                return i;
            }
        }
        return -1;
    }
}
