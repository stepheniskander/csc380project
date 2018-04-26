package io.github.stepheniskander.equationsolver;
import junit.framework.TestCase;

import java.math.BigDecimal;

public class CalculusTest extends TestCase{
    public void testIntegral(){
        BigDecimal x = Calculus.integrate("4x^2+2x",2,3);
        assertTrue(new BigDecimal("30.3333333333333270").compareTo(x) == 0);

    }
    public void testDerive(){
        BigDecimal x = Calculus.derive("3x^2+2x",2);
        assertTrue(new BigDecimal("14.0").compareTo(x) == 0);
    }
}
