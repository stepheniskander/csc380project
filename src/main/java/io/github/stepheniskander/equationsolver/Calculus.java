package io.github.stepheniskander.equationsolver;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculus {
    public static String integrateFromString(String input) {
        Pattern integratePattern = Pattern.compile("int\\((.+), *(.+), *(.+) *\\)");
        Matcher integrateMatcher = integratePattern.matcher(input);
        integrateMatcher.matches();
        String expression = integrateMatcher.group(1);
        int start = Integer.parseInt(integrateMatcher.group(2));
        int end = Integer.parseInt(integrateMatcher.group(3));

        return integrate(expression, start, end).toString();
    }

    public static String deriveFromString(String input) {
        Pattern derivePattern = Pattern.compile("der\\((.+), *(.+) *\\)");
        Matcher deriveMatcher = derivePattern.matcher(input);
        deriveMatcher.matches();
        String expression = deriveMatcher.group(1);
        int point = Integer.parseInt(deriveMatcher.group(2));

        return Calculus.derive(expression, point).toString();
    }

    public static BigDecimal integrate(String input, int start, int end) {
        Polynomial expression = new Polynomial(input);
        RationalNumber[] coefficients = expression.getCoefficients();
        RationalNumber[] powers = expression.getPowers();
        BigDecimal x;
        BigDecimal ln = new BigDecimal(0);
        for (int i = 0; i < coefficients.length; i++) {
            if (!powers[i].equals(new RationalNumber("-1"))) {
                powers[i] = powers[i].add(RationalNumber.ONE);
                coefficients[i] = coefficients[i].multiply(new RationalNumber(powers[i].getDenominator(), powers[i].getNumerator()));
            } else {
                double dubStart = (double) start;
                double dubEnd = (double) end;
                double tempA = Math.log(dubStart);
                double tempB = Math.log(dubEnd);
                double tempDiff = tempB - tempA;
                BigDecimal tempC = new BigDecimal(tempDiff);
                BigDecimal coef = coefficients[i].asBigDecimal();
                coefficients[i] = RationalNumber.ZERO;
                ln = ln.add(tempC.multiply(coef));
            }
        }
        Polynomial poly = new Polynomial(coefficients, powers);
        String unMapped = poly.toString();
        String a = unMapped.replaceAll("[xX]\\^([0-9]+)", "*(" + start + "^$1)");
        a = a.replaceAll("[xX]", "*" + start);
        a = "(" + a + ")";
        String b = unMapped.replaceAll("[xX]\\^([0-9]+)", "*(" + end + "^$1)");
        b = b.replaceAll("[xX]", "*" + end);
        b = "(" + b + ")";
        String c = b + "-" + a;
        c = c.replaceAll("\\(\\*", "(");
        Expression ex = Parser.parseExpression(c);
        x = ex.evaluateRpn(); //FIX THIS
        x = x.add(ln);
        return x;
    }


    public static BigDecimal derive(String input, int point) {
        Polynomial expression = new Polynomial(input);
        RationalNumber[] coefficients = expression.getCoefficients();
        RationalNumber[] powers = expression.getPowers();
        for (int i = 0; i < coefficients.length; i++) {
            if (powers[i].equals(RationalNumber.ZERO)) {
                coefficients[i] = RationalNumber.ZERO;
            }
            coefficients[i] = coefficients[i].multiply(powers[i]);
            powers[i] = powers[i].subtract(RationalNumber.ONE);
        }
        Polynomial poly = new Polynomial(coefficients, powers);
        String unMapped = poly.toString();
        String mapped = unMapped.replaceAll("[xX]\\^([0-9]+)", "*(" + point + "^$1)");
        mapped = mapped.replaceAll("[xX]", "*" + point);
        Expression derived = Parser.parseExpression(mapped);
        return derived.evaluateRpn();
    }

}
