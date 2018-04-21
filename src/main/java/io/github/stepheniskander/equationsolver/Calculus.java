package io.github.stepheniskander.equationsolver;
import java.util.ArrayList;
import java.util.regex.*;
public class Calculus {
    public static double integrate(String input, double start, double end){
        Polynomial expression = new Polynomial(input);
        return 0.;
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
        System.out.println(mapped);
        ExpressionParser exp = new ExpressionParser();
        Expression derived = exp.parse(mapped);
        return derived.evaluateRpn();
    }

}
