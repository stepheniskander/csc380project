package io.github.stepheniskander.equationsolver;
import junit.framework.TestCase;

public class CalculusTest extends TestCase{
    public void testIntegral(){
        double x = Calculus.integrate("3x^2+2x",0,4);
        assertEquals(80.0,x);

    }
    public void testDerive(){
        double x = Calculus.derive("3x^2+2x",2);
        assertEquals(14.0,x);
    }
}
