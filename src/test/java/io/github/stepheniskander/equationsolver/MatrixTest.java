package io.github.stepheniskander.equationsolver;

import junit.framework.TestCase;

public class MatrixTest extends TestCase {
    public void testSquare(){
        MatrixParser X = new MatrixParser();
        Matrix A = X.parse("[[1,2] [3,4]]");
        Matrix B = X.parse("[[2,3] [4,5]]");
        Matrix C = Matrix.matrixMultiply(A, B);

        assertEquals(C, X.parse("[[10,13] [22,29]]"));
    }
    public void testNonSquare() {
        MatrixParser x = new MatrixParser();
        Matrix A = x.parse("[[1,2,3]]");
        Matrix B = x.parse("[[3] [4] [5]]");
        Matrix C = Matrix.matrixMultiply(A, B);
        assertEquals(C, x.parse("[[26]]"));
    }


}
