package io.github.stepheniskander.equationsolver;

import java.math.BigInteger;

public class RationalNumber {
    private BigInteger numerator;
    private BigInteger denominator;

    public RationalNumber(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;

        this.simplify();
    }

    public RationalNumber(BigInteger n) {
        this.numerator = n;
        this.denominator = BigInteger.ONE;
    }

    private void simplify() {
        BigInteger gcd = numerator.gcd(denominator);

        if(!(gcd.equals(BigInteger.ONE))) {
            numerator = numerator.divide(gcd);
            denominator = denominator.divide(gcd);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s/%s)", numerator, denominator);
    }
}
