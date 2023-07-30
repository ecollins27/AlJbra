package aljbra;

import java.util.HashMap;

public class Constant extends Variable {

    public final static Constant PI = new Constant("pi","\\pi",Math.PI);
    public final static Constant PHI = new Constant("phi","\\phi",(1 + Math.sqrt(5)) / 2.0);
    public final static Constant E = new Constant("e",Math.E);

    double value;
    public Constant(String name, double value) {
        super(name);
        this.value = value;
    }

    public Constant(String name, String laTeXName, double value){
        super(name,laTeXName);
        this.value = value;
    }

    @Override
    public double eval(HashMap<String, Double> values) {
        return value;
    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof Constant && name.equals(((Constant) e).name) && ((Constant) e).value == value;
    }
}
