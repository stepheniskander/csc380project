package io.github.stepheniskander.equationsolver;

public class Matrix {
    private double[][] numMatrix;
    public Matrix(double[][] constructorMatrix){
        numMatrix = constructorMatrix;
    }
    public void display(){

    }
    public int getRows(){
        return numMatrix.length;
    }
    public int getColumns(){
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
//    public static double[][] matrixMultiply(Matrix m1, Matrix m2){
//        double[][] result;
//    }
}
