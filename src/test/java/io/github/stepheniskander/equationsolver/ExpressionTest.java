package io.github.stepheniskander.equationsolver;

import junit.framework.TestCase;

public class ExpressionTest extends TestCase {

    public void testEvaluateRpn() {
        final double EPSILON = 10E-6;

        ExpressionParser parser = new ExpressionParser();
        Expression ex = parser.parse("(2+3)^5/16-(9*1.5)");

        double result = ex.evaluateRpn();

        assertEquals(result, 181.8125, EPSILON);
    }
    public void testNegative(){
        ExpressionParser parse = new ExpressionParser();
        Expression neg = parse.parse("(-1)*2");

        double result = neg.evaluateRpn();
        assertEquals(result,  -2);
    }
}