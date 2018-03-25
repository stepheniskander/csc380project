package io.github.stepheniskander.equationsolver;

public class MatrixParser {
        public Matrix parse(String input){
            int x;
            int y;
            String[] splits = input.split(" ");
            String[] xlength = splits[0].split(",");
            y = splits.length;
            x = xlength.length;
            double[][] ex = new double[x][y];

            for(int i = 0; i<x; i++ ){
                String[] ysplits = splits[i].split(",");

                for(int j = 0; j<y; j++){
                    String valstring = ysplits[j].replaceAll("[^0-9]","");
                    double val = Double.parseDouble(valstring);
                    ex[i][j] = val;
                }
            }


            Matrix t = new Matrix(ex);
            return t;
        }
}
