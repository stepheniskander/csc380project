package io.github.stepheniskander.equationsolver;

public class Matrix {
    private double[][] numMatrix;
    public Matrix(double[][] constructorMatrix){
        numMatrix = constructorMatrix;
    }
    public void display(){

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
}
