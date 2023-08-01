package aljbra;

import java.util.*;

public class Scalar extends Expression {

    public final static Scalar NEG_ONE = new Scalar(-1);
    public final static Scalar ZERO = new Scalar(0);
    public final static Scalar ONE = new Scalar(new ArrayList<>());
    public final static Scalar TWO = new Scalar(2);

    long value;

    private final static Comparator<long[]> primeComparator = new Comparator<long[]>() {
        @Override
        public int compare(long[] o1, long[] o2) {
            return Long.compare(o1[0],o2[0]);
        }
    };
    ArrayList<long[]> primeFactorization;

    public Scalar(long value){
        this.value = value;
        this.primeFactorization = getPrimeFactorization(value);
        this.primeFactorization.sort(primeComparator);
    }

    Scalar(ArrayList<long[]> primeFactorization){
        this.primeFactorization = primeFactorization;
        this.primeFactorization.sort(primeComparator);
        this.value = 1;
        for (long[] factor: this.primeFactorization){
            value *= Math.pow(factor[0],factor[1]);
        }
    }
    @Override
    public Expression negate() {
        ArrayList<long[]> primeFactors = clone(primeFactorization);
        int index = indexOf(-1,primeFactors);
        if (index >= 0){
            primeFactors.remove(index);
        } else {
            primeFactors.add(new long[]{-1,1});
        }
        return new Scalar(primeFactors);
    }

    @Override
    public Expression invert() {
        if (this.equals(ONE) || this.equals(NEG_ONE)){
            return this;
        }
        return new Fraction(ONE,this);
    }

    @Override
    public Expression derivative(Variable v) {
        return Scalar.ZERO;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Scalar && ((Scalar) o).value == value;
    }

    @Override
    public double eval(HashMap<String, Double> values) {
        return value;
    }

    @Override
    public boolean contains(Expression e) {
        return this.equals(e);
    }

    @Override
    public Expression replace(Expression e, Expression with) {
        if (this.equals(e)){
            return with;
        }
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public String toLaTeX() {
        return String.valueOf(value);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    protected Expression __add__(Expression e) {
        if (this.equals(ZERO)){
            return e;
        }
        return new Scalar(value + ((Scalar)e).value);
    }

    @Override
    protected Expression __multiply__(Expression e) {
        if (this.equals(ZERO)){
            return ZERO;
        } else if (this.equals(ONE)){
            return e;
        }
        ArrayList<long[]> primeFactors = merge(primeFactorization,((Scalar)e).primeFactorization);
        return new Scalar(primeFactors);
    }

    static ArrayList<long[]> merge(ArrayList<long[]> a1, ArrayList<long[]> a2){
        ArrayList<long[]> primeFactors = clone(a1);
        for (long[] factor: a2){
            int index = indexOf(factor[0],primeFactors);
            if (index >= 0){
                primeFactors.get(index)[1] += factor[1];
                if (factor[0] == -1){
                    primeFactors.get(index)[1] = primeFactors.get(index)[1] % 2;
                }
            } else {
                primeFactors.add(factor);
            }
        }
        return primeFactors;
    }

    @Override
    protected Expression __pow__(Expression e) {
        if (this.equals(ZERO) || this.equals(ONE)){
            return this;
        }
        boolean shouldInvert;
        long pow,root;
        if (e instanceof Scalar){
            shouldInvert = ((Scalar) e).isNegative();
            pow = Math.abs(((Scalar) e).value);
            root = 1;
        } else {
            shouldInvert = ((Fraction)e).num.isNegative();
            pow = Math.abs(((Fraction)e).num.value);
            root = Math.abs(((Fraction)e).den.value);
        }
        ArrayList<long[]> coefficientFactors = new ArrayList<>();
        ArrayList<long[]> rootFactors = new ArrayList<>();
        for (long[] factor: primeFactorization) {
            if (factor[1] * pow >= root){
                coefficientFactors.add(new long[]{factor[0],(factor[1] * pow) / root});
                if (factor[1] * pow % root != 0){
                    rootFactors.add(new long[]{factor[0],(factor[1] * pow) % root});
                }
            } else {
                rootFactors.add(factor);
            }
        }
        Expression product;
        if (shouldInvert){
            product = new Scalar(coefficientFactors).invert().multiply(new Exponential(new Scalar(rootFactors),new Scalar(root).invert().negate()));
        } else {
            product = new Scalar(coefficientFactors).multiply(new Exponential(new Scalar(rootFactors),new Scalar(root).invert()));
        }
        return product;
    }

    @Override
    protected boolean isAdditionCompatible(Expression e) {
        return this.equals(ZERO) || e instanceof Scalar;
    }

    @Override
    protected boolean isMultiplicationCompatible(Expression e) {
        return this.equals(ZERO) || this.equals(ONE) || e instanceof Scalar;
    }

    @Override
    protected boolean isPowCompatible(Expression e) {
        if (e instanceof Scalar){
            return true;
        } else if (e instanceof Fraction){
            long pow = Math.abs(((Scalar)((Fraction)e).num).value);
            long root = Math.abs(((Scalar)((Fraction)e).den).value);
            for (long[] factor: primeFactorization){
                if ((factor[1] * pow) % root < 0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public long[][] primeFactorization(){
        return clone(primeFactorization).toArray(new long[0][0]);
    }

    private static ArrayList<long[]> getPrimeFactorization(long n){
        HashMap<Long,Long> primeFactorization = new HashMap<>();
        if (n < 0){
            primeFactorization.put(-1l,1l);
            n = Math.abs(n);
        }
        while (n > 1){
            long lowestFactor = getLowestFactor(n);
            if (primeFactorization.containsKey(lowestFactor)){
                primeFactorization.replace(lowestFactor, primeFactorization.get(lowestFactor) + 1);
            } else {
                primeFactorization.put(lowestFactor,1l);
            }
            n /= lowestFactor;
        }
        ArrayList<long[]> finalFactorization = new ArrayList<>();
        for (Map.Entry<Long,Long> entry: primeFactorization.entrySet()){
            finalFactorization.add(new long[]{entry.getKey(),entry.getValue()});
        }
        return finalFactorization;
    }

    private static int indexOf(long n, ArrayList<long[]> arrayList){
        for (int i = 0; i < arrayList.size();i++){
            if (arrayList.get(i)[0] == n){
                return i;
            }
        }
        return -1;
    }

    boolean isNegative(){
        return indexOf(-1,primeFactorization) >= 0;
    }

    private static long getLowestFactor(long n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return (long) i;
            }
        }
        return n;
    }
}
