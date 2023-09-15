# AlJbra

## Documentation
```java
/* The Expression class is the parent class of all Objects included in this library.  As a result, all methods included 
   in the Expression class are inherited by AlJbra classes.  When using AlJbra, all objects should be declared as an 
   Expression instead of as their specific class.  For example:
        Expression naturalLog = Log.ln(value);
   instead of 
        Log naturalLog = Log.ln(value);
   */
public abstract class Expression {
    
    /* returns sum with passed Expression */
    public Expression add(Expression e);
    
    /* returns difference with passed Expression */
    public Expression subtract(Expression e);

    /* returns product with passed Expression */
    public Expression multiply(Expression e);

    /* returns quotient with passed Expression */
    public Expression divide(Expression e);

    /* returns called Expression raised to passed Expression */
    public Expression pow(Expression e);

    /* returns negation of called Expression */
    public Expression negate();

    /* returns inversion of called Expression */
    public Expression invert();

    /* returns square root of called Expression */
    public Expression sqrt();
    
    /* returns the square of called Expression */
    public Expression sq();

    /* returns e raised to called Expression */
    public Expression exp();

    /* returns specified root of called Expression */
    public Expression nRoot(Expression e);

    /* returns absolute value of called Expression */
    public Expression abs();

    /* returns next simplest form of Expression */
    public Expression simplify();

    /* returns simplest form of Expression.  Calls .simplify() until Expression is in simplest form */
    public Expression fullSimplify();

    /* returns true is Expression contains no Variables */
    public boolean isEvaluable();

    /* returns String form of Expression */
    public Expression toString();

    /* returns Expression as LaTeX String */
    public Expression toLaTeX();

    /* returns double approximation of Expression with specified mapping of variable names to double */
    public double eval(HashMap<String,Double> values);

    /* returns true if called Expression contains an instance(s) of specified Expression */
    public boolean contains(Expression e);

    /* returns Expression in which all instances of Expression e are replaced with instances of Expression with */
    public boolean replace(Expression e, Expression with);

    /* returns the derivative of the called Expression with respect to specified Variable v */
    public Expression derivative(Variable v);

    /* returns true if specified Expression is identifal to called Expression */
    public boolean equals(Expression e);
}
```
```java
public class Scalar extends Expression {

    public final static Scalar NEG_ONE; // Scalar of value -1
    public final static Scalar ZERO; // Scalar of value 0
    public final static Scalar ONE; // Scalar of value 1
    public final static Scalar TWO; // Scalar of value 2

    /* constructs Scalar with specified long value */
    public Scalar(long value);

    /* returns ArrayList of long pairs containing a prime factor and its associated power respectively */
    public ArrayList<long[]> primeFactorization();
}
```
```java
/* The Decimal class stores decimals as the Scalar class stores integers.  However, unlike Scalar or Constant, when performing
   an operation with a Decimal, the resulting Expression will always be a Decimal, assuming both expressions are evaluable.
   For example, while Fraction.valueOf(2.64).divide(Scalar.TWO) will result in 33/25, new Decimal(2.64).divide(Scalar.TWO)
   will result in 1.32.  Do NOT use the Decimal class if exact fractional and radical results are preferred */
public class Decimal extends Expression { // Decimals store 
    
    public final static Decimal NEG_ONE; // Decimal value of -1
    public final static Decimal ZERO; // Decimal value of 0
    public final static Decimal ONE; // Decimal value of 1
    public final static Decimal TWO; // Decimal value of 2

    /* constructs Decimal with specified double value */
    public Decimal(double value);
    
    /* returns fractional value of called Decimal */
    public Expression asFraction();
    
    /* returns fractional value of called Decimal assuming the last specified number of digits repeat infinitely */
    public Expression asFraction(int repeatingLength);
}
```
```java
public class Fraction extends Expression {
    
    /* returns fractional value of specified decimal */
    public static Expression valueOf(double n);

    /* returns fractional value of specified decimal assuming the last specified number of digits repeat infinitely */
    public static Expression valueOf(double n, int repeatingLength);
}
```
```java
public class Variable extends Expression {

    /* constructs Variable with specified String name and matching name in LaTeX */
    public Variable(String name);

    /* constructs Variable with specified String name and specified String laTeXName */
    public Variable(String name, String laTeXName);

    /* returns name of Variable */
    public String getName();

    /* returns name of Variable in LaTeX */
    public String getLaTeXName();
}
```
```java
/* Constants act identical to Variables (except when in operations with Decimals) but do not require a value when
   eval(HashMap<String,Double> values) is called */
public class Constant extends Variable {

    public final static Constant PI; // Constant with value of pi
    public final static Constant PHI; // Constant with value of golden ratio, phi
    public final static Constant E; // Constant with value of Euler's number, e

    /* constructs Constant with specifed String name and double value */
    public Constant(String name, double value);

    /* constructs Constant with specified String name, String laTeXName, and double value */
    public Constant(String name, String laTeXName, double value);

    /* returns value of Constant */
    public double getValue(); 
}
```
```java
public class Log extends Expression {

    /* returns logarithm with specified base and operand */
    public static Expression log(Expression base, Expression operand);

    /* returns natural logarithm (base e) with specified operand */
    public static Expression ln(Expression operand);
    
}
```

```java
public abstract class Trig extends Expression {

    /* returns the sine of specified Expression */
    public static Expression sin(Expression e);

    /* returns the cosine of specified Expression */
    public static Expression cos(Expression e);

    /* returns the tangent of specified Expression */
    public static Expression tan(Expression e);

    /* returns the arcsine of specified Expression */
    public static Expression asin(Expression e);

    /* returns the arccosine of specified Expression */
    public static Expression acos(Expression e);

    /* returns the arctangent of specified Expression */
    public static Expression atan(Expression e);
}
```
