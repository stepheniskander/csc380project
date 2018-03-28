package io.github.stepheniskander.equationsolver;

import java.math.BigInteger;

public class RationalNumber {
    private BigInteger numerator;
    private BigInteger denominator;

    public RationalNumber(String numerator, String denominator) {
        this(new BigInteger(numerator), new BigInteger(denominator));
    }

    public RationalNumber(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;

        this.simplify();
    }

    public RationalNumber(BigInteger n) {
        this.numerator = n;
        this.denominator = BigInteger.ONE;
    }

    public RationalNumber add(RationalNumber n) {
        BigInteger gcd = this.denominator.gcd(n.denominator);
        BigInteger lcd = this.denominator.divide(gcd).multiply(n.denominator);

        BigInteger resultNumerator = this.numerator.multiply(lcd.divide(this.denominator))
                .add(n.numerator.multiply(lcd.divide(n.denominator)));

        return new RationalNumber(resultNumerator, lcd);
    }

    private void simplify() {
        BigInteger gcd = numerator.gcd(denominator);

        if (!(gcd.equals(BigInteger.ONE))) {
            numerator = numerator.divide(gcd);
            denominator = denominator.divide(gcd);
        }
    }

    @Override
    public String toString() {
        return String.format("(%s/%s)", numerator, denominator);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof RationalNumber)) return false;

        RationalNumber n = (RationalNumber) obj;
        return this.numerator.equals(n.numerator) && this.denominator.equals(n.denominator);
    }
}
