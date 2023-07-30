# AlJbra

## Dependencies
AlJbra requires two additional libraries: [JMathTeX](https://jmathtex.sourceforge.net/) and its dependency [JDOM 1.0](http://www.jdom.org/downloads/index.html)
Note that although new versions of JDOM are available, JMathTeX and this library require JDOM 1.0 and will NOT work with JDOM 2.0.  At the time of writing, the latest stable release is [JDOM 1.1.3](http://www.jdom.org/dist/binary/archive/jdom-1.1.3.zip)

## Structure
The AlJbra library represents all algebraic expressions as instances of one of 7 different core classes: ```Scalar```, ```Fraction```, ```Variable```, ```Constant```, ```Sum```, ```Product```, and ```Exponential```.  All of these classes extend the abstract class ```Expression``` from which all classes inherit basic methods such as ```.add(Expression e)```,```.subtract(Expression e)```,```.multiply(Expression e)```,```.divide(Expression e)```, ```.pow(Expression e```, and many more.

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
    public double eval(HashMap<String,Double> values); // returns double approximation of Expression with specified mapping of variable names to double
    public boolean contains(Expression e); // returns if called Expression contains an instance(s) of specified Expression
    public boolean replace(Expression e, Expression with); // returns Expression in which all instances of Expression e are replaced with instances of Expression with
    public void visualize(String equationLabel); // uses LaTeX to visualize called Expression in a new window with specified name
    public void visualize(); // uses LaTeX to visualize called Expression in a new window with blank name
    public void save(String fileName); // saves LaTeX visualization of called Expression as png with specified file name
    public boolean equals(Expression e); // returns if specified Expression is identifal to called Expression
}
```
```java
public class Scalar extends Expression {

    public final static Scalar NEG_ONE; // Scalar of value //1
    public final static Scalar ZERO; // Scalar of value 0
    public final static Scalar ONE; // Scalar of value 1
    public final static Scalar TWO; // Scalar of value 2

    public Scalar(long value); // constructs Scalar with specified long value

    public long[][] primeFactorization(); // returns array of long pairs containing the prime and raised power respectively
}
```
```java
public class Fraction extends Expression {
    
    public static Expression valueOf(double n, boolean repeating);  // returns fractional value of specified decimal. If decimal approximation is infinite, provide one repetition and set repeating to true
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
