package io.github.stepheniskander.equationsolver;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.*;
public class Calculus {
    public static double integrate(String input, int start, int end){
        Polynomial expression = new Polynomial(input);
        RationalNumber[] coefficients = expression.getCoefficients();
        RationalNumber[] powers = expression.getPowers();
        double x;
        for(int i = 0; i < coefficients.length;i++){
            powers[i] = powers[i].add(RationalNumber.ONE);
            coefficients[i] = coefficients[i].multiply(new RationalNumber(powers[i].getDenominator(),powers[i].getNumerator()));
        }
        Polynomial poly = new Polynomial(coefficients,powers);
        String unMapped = poly.toString();
        String a = unMapped.replaceAll("[xX]\\^([0-9]+)","*("+ start + "^$1)");
        a = a.replaceAll("[xX]", "*" + start);
        a = "(" + a +")";
        String b = unMapped.replaceAll("[xX]\\^([0-9]+)","*("+ end + "^$1)");
        b = b.replaceAll("[xX]", "*" + end);
        b = "(" + b +")";
        String c = b + "-" + a;
        ExpressionParser exp = new ExpressionParser();
        Expression ex =exp.parse(c);
        x = ex.evaluateRpn();
        return x;
    }


    public static double derive(String input, int point){
        Polynomial expression = new Polynomial(input);
        RationalNumber[] coefficients = expression.getCoefficients();
        RationalNumber[] powers = expression.getPowers();
        double x;
        for(int i = 0; i < coefficients.length; i++){
            if(powers[i].equals(RationalNumber.ZERO)){
                coefficients[i] = RationalNumber.ZERO;
            }
            coefficients[i] = coefficients[i].multiply(powers[i]);
            powers[i] = powers[i].subtract(RationalNumber.ONE);
        }
        Polynomial poly = new Polynomial(coefficients,powers);
        String unMapped = poly.toString();
        String mapped = unMapped.replaceAll("[xX]\\^([0-9]+)","*("+ point + "^$1)" );
        mapped = mapped.replaceAll("[xX]", "*" + point);
        ExpressionParser exp = new ExpressionParser();
        Expression derived = exp.parse(mapped);
        return derived.evaluateRpn();
    }

}
