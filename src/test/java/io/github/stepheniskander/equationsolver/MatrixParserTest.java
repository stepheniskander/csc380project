package io.github.stepheniskander.equationsolver;

import junit.framework.TestCase;

public class MatrixParserTest extends TestCase {
    public void testMatrixParse() {
        MatrixParser test = new MatrixParser();
        Matrix ex = test.parse("[[1,2,3] [3,4,5]]");
        // System.out.println(ex.toString());
        assertEquals(ex.toString(),"1 2 3 \n" +
                "3 4 5");
    }
}