package io.github.stepheniskander.equationsolver;

import java.math.BigInteger;

public class MatrixParser {
        public Matrix parse(String input){
            int x;
            int y;
            String[] splits = input.split(" ");
            String[] ylength = splits[0].split(",");
            x = splits.length;
            y = ylength.length;
            RationalNumber[][] ex = new RationalNumber[x][y];

            for(int i = 0; i<x; i++ ){
                String[] ysplits = splits[i].split(",");

                for(int j = 0; j<y; j++){
                    String valstring = ysplits[j].replaceAll("[^0-9]","");
                    RationalNumber val;
                    if(valstring.contains(".")) {
                        BigInteger denominator = new BigInteger("10").multiply(BigInteger.valueOf(valstring.length() - valstring.indexOf(".") - 1));
                        val = new RationalNumber(new BigInteger(valstring.replace(".", "")), denominator);
                    } else {
                        val = new RationalNumber(valstring);
                    }

                    ex[i][j] = val;
                }
            }


            Matrix t = new Matrix(ex);
            return t;
        }
}
