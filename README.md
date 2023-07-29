# AlJbra

## Structure
The AlJbra library is composed 7 non-abstract libraries: Scalar, Fraction, Variable, Constant, Sum, Product, and Exponential.  Each of these classes represent
core aspects of algebra and all of them extend the abstract class Expression.

## Documentation
```java
public abstract class Expression {

    public Expression add(Expression e); // returns sum with passed Expression
    public Expression subtract(Expression e); // returns difference with passed Expression
    public Expression multiply(Expression e); // returns product with passed Expression
    public Expression divide(Expression e); // returns quotient with passed Expression
    public Expression pow(Expression e); // returns called Expression raised to passed Expression
    public Expression negate(); // returns negation of called Expression
    public Expression invert(); // returns inversion of called Expression
    public Expression sqrt(); // returns square root of called Expression
    public Expression nRoot(Expression e); // returns specified root of called Expression
    public Expression simplify(); // returns next simplest form of Expression
    public Expression fullSimplify(); // returns simplest form of Expression.  Calls .simplify() until Expression is in simplest form
    public Expression toString(); // returns String form of Expression
    public Expression toLaTeX(); // returns Expression as LaTeX String
    public boolean equals(Expression e); // returns if specified Expression is identifal to called Expression
}
```
```java
public class Scalar extends Expression {

    public final static Scalar NEG_ONE; // Scalar of value -1
    public final static Scalar ZERO; // Scalar of value 0
    public final static Scalar ONE; // Scalar of value 1
    public final static Scalar TWO; // Scalar of value 2

    public Scalar(long value); // constructs Scalar with specified long value

    public long[][] primeFactorization(); // returns array of long pairs containing the prime and raised power respectively
}
```
```java
public class Variable extends Expression {

    public Variable(String name); // constructs Variable with specified String name and matching name in LaTeX
    public Variable(String name, String laTeXName); // constructs Variable with specified String name and specified String laTeXName
}
```
```java
public class Constant extends Variable { // Constants act identical to variables but do not require a value when eval(HashMap<String,Double> values) is called

    public final static Constant PI; // Constant with value of pi
    public final static Constant PHI; // Constant with value of golden ratio, phi
    public final static Constant E; // Constant with value of Euler's number, e

    public Constant(String name, double value); // constructs Constant with specifed String name and double value
    public Constant(String name, String laTeXName, double value); // constructs Constant with specified String name, String laTeXName, and double value
}
```
