package io.github.stepheniskander.equationsolver;

public class Matrix {
    private double[][] numMatrix;
    public Matrix(double[][] constructorMatrix){
        numMatrix = constructorMatrix;
    }
    public void display(){
        for(int i = 0; i < numMatrix.length; i++){
            for (int j = 0; j < numMatrix[0].length; j++){
                System.out.print(numMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
