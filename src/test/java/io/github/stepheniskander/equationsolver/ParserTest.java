package io.github.stepheniskander.equationsolver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {
    @Test
    public void testMatrixParse() {
        Matrix ex = Parser.parseMatrix("[[1,2,3] [3,4,5]]");
        assertEquals(ex.toString(), "[[1,2,3] [3,4,5]]");
    }

    @Test
    public void testExpressionParse() {
        Expression ex = Parser.parseExpression("4 + 18 / (9 ^ 2 - 3)");
        assertEquals("4 18 9 2 ^ 3 - / +", ex.toString());
    }
}
