package io.github.stepheniskander.equationsolver;

import junit.framework.TestCase;

public class ExpressionParserTest extends TestCase {

    public void testParse() {
        ExpressionParser parser = new ExpressionParser();
        Expression ex = parser.parse("4 + 18 / (9 ^ 2 - 3)");
        assertTrue(ex.toString().equals("4 18 9 2 ^ 3 - / +"));
    }
}