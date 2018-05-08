package io.github.stepheniskander.equationsolver;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RationalNumberTest {

    @Test
    public void testRationalNumber() {
        assertEquals("(17/81)", new RationalNumber(new BigInteger("680"), new BigInteger("3240")).toString());
        assertEquals("(3/4)", new RationalNumber("3", "4").toString());
        assertEquals(new RationalNumber("1"), new RationalNumber("-1", "-1"));
        assertEquals(new RationalNumber("-1", "2"), new RationalNumber("1", "-2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroDenominator() {
        new RationalNumber("1", "0");
    }

    @Test
    public void testFromDecimalString() {
        RationalNumber val = RationalNumber.fromDecimalString("4.2");
        RationalNumber expected = new RationalNumber("42", "10");
        assertEquals(expected, val);
    }

    @Test
    public void testAdd() {
        RationalNumber a = new RationalNumber("1", "2");
        RationalNumber b = new RationalNumber("1", "4");
        RationalNumber c = new RationalNumber("1", "3");

        assertEquals(new RationalNumber("3", "4"), a.add(b));
        assertEquals(new RationalNumber("13", "12"), RationalNumber.sum(a, b, c));
    }

    @Test
    public void testSubtract() {
        RationalNumber a = new RationalNumber("3", "4");
        RationalNumber b = new RationalNumber("1", "2");

        assertEquals(new RationalNumber("1", "4"), a.subtract(b));
    }

    @Test
    public void testMultiply() {
        RationalNumber a = new RationalNumber("3", "4");
        RationalNumber b = new RationalNumber("1", "2");
        RationalNumber c = new RationalNumber("1", "3");

        assertEquals(new RationalNumber("3", "8"), a.multiply(b));
        assertEquals(new RationalNumber("3", "24"), RationalNumber.product(a, b, c));
    }

    @Test
    public void testInverse() {
        assertEquals(new RationalNumber("4", "3"), new RationalNumber("3", "4").inverse().get());
        assertEquals(Optional.empty(), new RationalNumber("0").inverse());
    }

    @Test
    public void testDivide() {
        RationalNumber a = new RationalNumber("3", "4");
        RationalNumber b = new RationalNumber("1", "2");

        assertEquals(new RationalNumber("3", "2"), a.divide(b).get());
        assertEquals(Optional.empty(), a.divide(new RationalNumber("0")));
    }

    @Test
    public void testAsBigDecimal() {
        assertEquals(new BigDecimal(".75"), new RationalNumber("3", "4").asBigDecimal());
    }

    @Test
    public void testCompareTo() {
        assertTrue(new RationalNumber("4", "5").compareTo(new RationalNumber("7", "6")) < 0);
        assertTrue(new RationalNumber("13", "12").compareTo(new RationalNumber("5", "6")) > 0);
        assertTrue(new RationalNumber("4", "5").compareTo(new RationalNumber("8", "10")) == 0);
    }

    @Test
    public void testEqualsSymmetric() {
        RationalNumber a = new RationalNumber("3", "4");
        RationalNumber b = new RationalNumber("6", "8");

        assertEquals(a, b);
        assertEquals(b, a);

        assertEquals(a.hashCode(), b.hashCode());
    }
}