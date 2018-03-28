package io.github.stepheniskander.equationsolver;

import java.math.BigInteger;

public class RationalNumber {
    private BigInteger numerator;
    private BigInteger denominator;

    public RationalNumber(String numerator, String denominator) {
        this(new BigInteger(numerator), new BigInteger(denominator));
    }

    public RationalNumber(BigInteger numerator, BigInteger denominator) throws IllegalArgumentException{
        if(denominator.equals(BigInteger.ZERO))
            throw new IllegalArgumentException("Denominator cannot be 0");

        this.numerator = numerator;
        this.denominator = denominator;

        this.simplify();
    }

    public RationalNumber(String n) {
        this(new BigInteger(n));
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

    public RationalNumber subtract (RationalNumber n) {
        BigInteger gcd = this.denominator.gcd(n.denominator);
        BigInteger lcd = this.denominator.divide(gcd).multiply(n.denominator);

        BigInteger resultNumerator = this.numerator.multiply(lcd.divide(this.denominator))
                .subtract(n.numerator.multiply(lcd.divide(n.denominator)));

        return new RationalNumber(resultNumerator, lcd);
    }

    private void simplify() {
        if(denominator.compareTo(BigInteger.ZERO) < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

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

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + numerator.hashCode();
        result = 31 * result + denominator.hashCode();

        return result;
    }
}
