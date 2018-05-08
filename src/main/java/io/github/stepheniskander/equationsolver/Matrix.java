package io.github.stepheniskander.equationsolver;

public class Matrix {
    private RationalNumber[][] numMatrix;

    public Matrix(RationalNumber[][] constructorMatrix) {
        numMatrix = constructorMatrix;
    }

    public static Matrix matrixMultiply(Matrix m1, Matrix m2) {
        RationalNumber[][] result = new RationalNumber[m1.getColumns()][m2.getRows()];
        for (int i = 0; i < m1.getColumns(); i++) {
            for (int j = 0; j < m2.getRows(); j++) {
                result[i][j] = RationalNumber.ZERO;
                for (int k = 0; k < m1.getRows(); k++) {
                    result[i][j] = result[i][j].add(m1.getMatrix()[i][k].multiply(m2.getMatrix()[k][j]));
                }
            }
        }
        return new Matrix(result);

    }

    public int getColumns() {
        return numMatrix.length;
    }

    public int getRows() {
        return numMatrix[0].length;
    }

    public RationalNumber[][] getMatrix() {
        return numMatrix;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < getColumns(); i++) {
            sb.append("[");
            for (int j = 0; j < getRows(); j++)
                sb.append(numMatrix[i][j].toString() + ",");
            sb.replace(sb.length() - 1, sb.length(), "] ");
        }
        sb.replace(sb.length() - 1, sb.length(), "]");

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix))
            return false;

        Matrix m = (Matrix) obj;
        if (this.getRows() != m.getRows() || this.getColumns() != m.getColumns())
            return false;

        for (int i = 0; i < this.getColumns(); i++) {
            for (int j = 0; j < this.getColumns(); j++) {
                if (!this.getMatrix()[i][j].equals(m.getMatrix()[i][j]))
                    return false;
            }
        }

        return true;
    }
}
