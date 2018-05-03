package io.github.stepheniskander.equationsolver;

import junit.framework.TestCase;

public class MatrixTest extends TestCase {
    public void testSquare(){
        Matrix A = Parser.parseMatrix("[[1,2] [3,4]]");
        Matrix B = Parser.parseMatrix("[[2,3] [4,5]]");
        Matrix C = Matrix.matrixMultiply(A, B);

        assertEquals(C, Parser.parseMatrix("[[10,13] [22,29]]"));
    }
    public void testNonSquare() {
        Matrix A = Parser.parseMatrix("[[1,2,3]]");
        Matrix B = Parser.parseMatrix("[[3] [4] [5]]");
        Matrix C = Matrix.matrixMultiply(A, B);
        assertEquals(C, Parser.parseMatrix("[[26]]"));
    }


}
