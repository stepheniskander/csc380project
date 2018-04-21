package io.github.stepheniskander.equationsolver;

/**
 * A polynomial in Q[x]
 */
public class Polynomial {
    private RationalNumber[] coefficients;

    public Polynomial(RationalNumber[] coefficients) throws IllegalArgumentException {
        this.coefficients = coefficients;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for(int i = coefficients.length - 1; i > 0; i--) {
            if(!coefficients[i].equals(RationalNumber.ZERO)) {
                if(!coefficients[i].equals(RationalNumber.ONE)) {
                    result.append(coefficients[i]);
                }

                result.append("x");
                if(i != 1)
                    result.append("^").append(i);

                if(coefficients[i - 1].compareTo(RationalNumber.ZERO) > 0) {
                    result.append("+");
                }
            }
        }

        if(!coefficients[0].equals(RationalNumber.ZERO)) {
            result.append(coefficients[0]);
        }

        return result.toString();
    }
}
