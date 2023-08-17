package aljbra;

import java.util.*;

public class Sum extends Expression {

    Expression[] terms;

    Sum(Expression... terms){
        this.terms = terms;
        Arrays.sort(this.terms,null);
    }
    @Override
    public Expression negate() {
        Expression sum = Scalar.ZERO;
        for (Expression term: terms){
            sum = sum.add(term.negate());
        }
        return sum;
    }

    @Override
    public Expression invert() {
        return this.pow(Scalar.NEG_ONE);
    }

    @Override
    public Expression derivative(Variable v) {
        Expression sum = Scalar.ZERO;
        for (Expression term: terms){
            sum = sum.add(term.derivative(v));
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sum) || ((Sum) o).terms.length != terms.length){
            return false;
        }
        for (int i = 0; i < terms.length;i++){
            if (!terms[i].equals(((Sum) o).terms[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public double eval(HashMap<String, Double> values) {
        double sum = 0;
        for (Expression term: terms){
            sum += term.eval(values);
        }
        return sum;
    }

    @Override
    public boolean contains(Expression e) {
        if (this.equals(e)){
            return true;
        }
        for (Expression term: terms){
            if (term.contains(e)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Expression replace(Expression e, Expression with) {
        if (this.equals(e)){
            return with;
        }
        Expression sum = Scalar.ZERO;
        for (Expression term: terms){
            sum = sum.add(term.replace(e,with));
        }
        return sum;
    }

    @Override
    public String toString() {
        String toString = "";
        for (int i = 0; i < terms.length;i++){
            if (i > 0){
                toString += " + ";
            }
            toString += terms[i].toString();
        }
        return toString;
    }

    @Override
    public String toLaTeX() {
        String toLaTeX = "";
        for (int i = 0; i < terms.length;i++){
            if (i > 0){
                toLaTeX += " + ";
            }
            toLaTeX += terms[i].toLaTeX();
        }
        return toLaTeX;
    }

    @Override
    public Expression simplify() {
        ArrayList<Expression[]> map = new ArrayList<>();
        Queue<Expression> queue = new LinkedList<>(Arrays.asList(terms));
        while (!queue.isEmpty()){
            Expression simplifiedTerm = queue.poll().simplify();
            Expression term,coefficient;
            if (simplifiedTerm instanceof Fraction || simplifiedTerm instanceof Scalar){
                coefficient = simplifiedTerm;
                term = Scalar.ONE;
            } else if (simplifiedTerm instanceof Sum){
                for (Expression eTerm: ((Sum) simplifiedTerm).terms){
                    queue.add(eTerm);
                }
                continue;
            } else if (simplifiedTerm instanceof Product){
                term = ((Product) simplifiedTerm).getTerm();
                coefficient = ((Product) simplifiedTerm).getCoefficient();
            } else {
                term = simplifiedTerm;
                coefficient = Scalar.ONE;
            }
            int index = indexOf(term,map);
            if (index >= 0){
                map.get(index)[1] = map.get(index)[1].add(coefficient);
            } else {
                map.add(new Expression[]{term,coefficient});
            }
        }
        Expression sum = Scalar.ZERO;
        for (Expression[] term: map){
            sum = sum.add(term[1].simplify().multiply(term[0]));
        }
        return sum;
    }

    @Override
    public boolean isEvaluable() {
        for (Expression term: terms){
            if (!term.isEvaluable()){
                return false;
            }
        }
        return true;
    }

    @Override
    protected Expression __add__(Expression e) {
        if (e instanceof Sum){
            Expression sum = this;
            for (Expression eTerm: ((Sum) e).terms){
                sum = sum.add(eTerm);
            }
            return sum;
        }
        boolean addTerm = true;
        for (Expression term: terms){
            if (term.isAdditionCompatible(e) || e.isAdditionCompatible(term)){
                addTerm = false;
            }
        }
        if (addTerm){
            Expression[] newTerms = Arrays.copyOf(terms,terms.length + 1);
            newTerms[terms.length] = e;
            return new Sum(newTerms);
        } else {
            Expression[] newTerms = terms.clone();
            for (int i = 0; i < newTerms.length;i++){
                if (newTerms[i].isAdditionCompatible(e)){
                    newTerms[i] = newTerms[i].add(e);
                    break;
                } else if (e.isAdditionCompatible(newTerms[i])){
                    newTerms[i] = e.add(newTerms[i]);
                    break;
                }
            }
            return new Sum(newTerms);
        }
    }

    @Override
    protected Expression __multiply__(Expression e) {
        if (e instanceof Sum){
            Expression sum = Scalar.ZERO;
            for (Expression eTerm: ((Sum) e).terms){
                sum = sum.add(this.multiply(eTerm));
            }
            return sum;
        }
        Expression sum = Scalar.ZERO;
        for (int i = 0; i < terms.length;i++){
            sum = sum.add(terms[i].multiply(e));
        }
        return sum;
    }


    @Override
    protected Expression __pow__(Expression e) {
        boolean shouldInvert = ((Scalar)e).isNegative();
        Expression product = Scalar.ONE;
        for (int i = 0; i < Math.abs(((Scalar)e).value);i++){
            product = product.multiply(this);
        }
        return shouldInvert? product.invert():product;
    }

    @Override
    protected boolean isAdditionCompatible(Expression e) {
        return true;
    }

    @Override
    protected boolean isMultiplicationCompatible(Expression e) {
        return true;
    }

    @Override
    protected boolean isPowCompatible(Expression e) {
        return e instanceof Scalar && !e.equals(Scalar.NEG_ONE);
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
