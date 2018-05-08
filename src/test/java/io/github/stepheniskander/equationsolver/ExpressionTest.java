package io.github.stepheniskander.equationsolver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {
    private static final double EPSILON = 10E-6;

    @Test
    public void testEvaluateRpn() {
        Expression ex = Parser.parseExpression("(2+3)^5/16-(9*1.5)");

        double result = ex.evaluateRpn().doubleValue();

        assertEquals(result, 181.8125, EPSILON);
    }

    @Test
    public void testNegative() {
        Expression neg = Parser.parseExpression("2*-1");

        double result = neg.evaluateRpn().doubleValue();
        assertEquals(result, -2.0, EPSILON);
    }

    @Test
    public void testIntegral() {
        Expression ex = Parser.parseExpression("(2+3)^5/int(6x^2, 4, 10)+16-(9*1.5)");
        double result = ex.evaluateRpn().doubleValue();
        assertEquals(4.1693376068376068376068376068, result, EPSILON);
    }

    @Test
    public void testDerivative() {
        Expression ex = Parser.parseExpression("(2+3)^5/der(6x^2, 4)+16-(9*1.5)");
        double result = ex.evaluateRpn().doubleValue();
        assertEquals(67.6041666666666666666, result, EPSILON);
    }
}