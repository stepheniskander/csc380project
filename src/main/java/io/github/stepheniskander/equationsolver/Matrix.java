package io.github.stepheniskander.equationsolver;

public class Matrix {
    private double[][] numMatrix;
    public Matrix(double[][] constructorMatrix){
        numMatrix = constructorMatrix;
    }

    public int getColumns(){
        return numMatrix.length;
    }
    public int getRows(){
        return numMatrix[0].length;
    }
    public double[][] getMatrix(){
        return numMatrix;
    }
    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < numMatrix.length; i++){
            for (int j = 0; j < numMatrix[0].length; j++){
                s = s + numMatrix[i][j] + " ";

            }
            s = s + "\n";
        }
        return s.trim();
    }
    public static Matrix matrixMultiply(Matrix m1, Matrix m2){
        double [][] result = new double[m1.getRows()][m2.getColumns()];
        for (int i = 0; i<m1.getRows(); i++){
            for (int j = 0; j < m2.getColumns(); j++){
                double sum = 0;
                for(int k = 0; k < m1.getRows(); k++){
                    sum = sum + m1.getMatrix()[i][k] * m2.getMatrix()[k][j];
                }
                result[i][j] = sum;
            }
        }
        return new Matrix(result);

    }
}
