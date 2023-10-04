package aljbra;

import aljbra.trig.Trig;

public final class ExpressionParser {

    public static Expression parse(String str) {
        str = str.replace(" ", "");
        if (str.equals("")) {
            return Scalar.ZERO;
        } else if (str.charAt(0) == '(' && Expression.getMatchingDelimeter(str, 0) == str.length() - 1) {
            return parse(str.substring(1, str.length() - 1));
        } else if (Sum.isSum(str)){
            return Sum.parseSum(str);
        } else if (Product.isProduct(str)){
            return Product.parseProduct(str);
        } else if (Exponential.isExponential(str)){
            return Exponential.parseExponential(str);
        } else if (Trig.isTrig(str)){
            return Trig.parseTrig(str);
        } else if (Log.isLog(str)){
            return Log.parseLog(str);
        } else if (Abs.isAbs(str)){
            return Abs.parseAbs(str);
        } else if (Scalar.isScalar(str)){
            return Scalar.parseScalar(str);
        } else if (Decimal.isDecimal(str)){
            return Decimal.parseDecimal(str);
        } else if (Constant.isConstant(str)){
            return Constant.parseConstant(str);
        } else if (Variable.isVariable(str)) {
            return Variable.parseVariable(str);
        }
        throw new RuntimeException("Cannot convert " + str + " to Expression");
    }
}
