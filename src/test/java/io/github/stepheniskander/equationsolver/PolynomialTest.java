package io.github.stepheniskander.equationsolver;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class PolynomialTest {
    @Test
    public void testToString() {
        RationalNumber[] coefficients = new RationalNumber[3];
        coefficients[0] = RationalNumber.ONE;
        coefficients[1] = new RationalNumber(BigInteger.valueOf(2));
        coefficients[2] = RationalNumber.ONE;
        assertEquals("x^2+2x+1", new Polynomial("x^2+2x+1").toString());
    }
}