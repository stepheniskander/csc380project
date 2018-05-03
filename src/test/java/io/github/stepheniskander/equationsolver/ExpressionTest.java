package io.github.stepheniskander.equationsolver;

import junit.framework.TestCase;

public class ExpressionTest extends TestCase {

    public void testEvaluateRpn() {
        final double EPSILON = 10E-6;

        Expression ex = Parser.parseExpression("(2+3)^5/16-(9*1.5)");

        double result = ex.evaluateRpn().doubleValue();

        assertEquals(result, 181.8125, EPSILON);
    }
    public void testNegative(){
        Expression neg = Parser.parseExpression("2*-1");

        double result = neg.evaluateRpn().doubleValue();
        assertEquals(result,  -2.0);
    }

}