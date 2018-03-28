package io.github.stepheniskander.equationsolver;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class RationalNumberTest {

    @Test
    public void testRationalNumber() {
        assertEquals("(17/81)", new RationalNumber(new BigInteger("680"), new BigInteger("3240")).toString());
        assertEquals("(3/4)", new RationalNumber("3", "4").toString());
    }

    @Test
    public void testAdd() {
        RationalNumber a = new RationalNumber("1", "2");
        RationalNumber b = new RationalNumber("1", "4");

        assertEquals(new RationalNumber("3", "4"), a.add(b));
    }
}