package io.github.stepheniskander.equationsolver;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class RationalNumberTest {

    @Test
    public void testRationalNumber() {
        assertEquals(new RationalNumber(new BigInteger("680"), new BigInteger("3240")).toString(), "(17/81)");
    }
}