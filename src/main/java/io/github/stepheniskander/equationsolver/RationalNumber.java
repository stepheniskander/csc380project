package io.github.stepheniskander.equationsolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;

public class RationalNumber implements Comparable<RationalNumber> {
    public static final RationalNumber ZERO = new RationalNumber("0");
    public static final RationalNumber ONE = new RationalNumber("1");

    private BigInteger numerator;
    private BigInteger denominator;

    public RationalNumber(String numerator, String denominator) {
        this(new BigInteger(numerator), new BigInteger(denominator));
    }

    public RationalNumber(BigInteger numerator, BigInteger denominator) throws IllegalArgumentException {
        if (denominator.equals(BigInteger.ZERO))
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

    static public RationalNumber sum(RationalNumber... args) {
        return Arrays.stream(args).reduce(RationalNumber.ZERO, RationalNumber::add);
    }

    static public RationalNumber product(RationalNumber... args) {
        return Arrays.stream(args).reduce(RationalNumber.ONE, RationalNumber::multiply);
    }

    public RationalNumber subtract(RationalNumber n) {
        BigInteger gcd = this.denominator.gcd(n.denominator);
        BigInteger lcd = this.denominator.divide(gcd).multiply(n.denominator);

        BigInteger resultNumerator = this.numerator.multiply(lcd.divide(this.denominator))
                .subtract(n.numerator.multiply(lcd.divide(n.denominator)));

        return new RationalNumber(resultNumerator, lcd);
    }

    public RationalNumber multiply(RationalNumber n) {
        return new RationalNumber(this.numerator.multiply(n.numerator),
                this.denominator.multiply(n.denominator));
    }

    public Optional<RationalNumber> divide(RationalNumber n) {
        return n.inverse().map(this::multiply); // we functional now bois
    }

    public Optional<RationalNumber> inverse() {
        if (numerator.equals(BigInteger.ZERO))
            return Optional.empty();

        return Optional.of(new RationalNumber(denominator, numerator));
    }

    public BigDecimal asBigDecimal() {
        return new BigDecimal(numerator).divide(new BigDecimal(denominator));
    }

    private void simplify() {
        if (denominator.compareTo(BigInteger.ZERO) < 0) {
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
        if(!denominator.equals(BigInteger.ONE)) {
            return String.format("(%s/%s)", numerator, denominator);
        } else
            return numerator.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof RationalNumber)) return false;

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

    @Override
    public int compareTo(RationalNumber o) {
        BigInteger gcd = this.denominator.gcd(o.denominator);
        BigInteger lcd = this.denominator.divide(gcd).multiply(o.denominator);

        BigInteger thisNumerator = this.numerator.multiply(lcd.divide(this.denominator));
        BigInteger oNumerator = o.numerator.multiply(lcd.divide(o.denominator));

        return thisNumerator.compareTo(oNumerator);
    }
}
