package io.github.stepheniskander.equationsolver;
import junit.framework.TestCase;

public class CalculusTest extends TestCase{
    public void testIntegral(){
        double x = Calculus.integrate("4x^2+2x",2,3);
        assertEquals(30.33333333333333,x);

    }
    public void testDerive(){
        double x = Calculus.derive("3x^2+2x",2);
        assertEquals(14.0,x);
    }
}
