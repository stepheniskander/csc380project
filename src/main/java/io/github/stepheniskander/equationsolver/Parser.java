package io.github.stepheniskander.equationsolver;

import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Parser {
    public static  Matrix parseMatrix(String input) {
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
                String valstring = ysplits[j].replaceAll("[^0-9.]","");

                ex[i][j] = RationalNumber.fromDecimalString(valstring);
            }
        }


        Matrix t = new Matrix(ex);
        return t;
    }

    public static Expression parseExpression(String ex){
        if(ex.length()!=0) {
            while(ex.contains("int(")) {
                int start = ex.indexOf("int(");
                int end = start + "int(".length();
                int lParenCount = 1;
                int rParenCount = 0;
                for(; end < ex.length() && lParenCount != rParenCount; end++) {
                    if(ex.charAt(end) == '(')
                        lParenCount++;
                    else if(ex.charAt(end) == ')')
                        rParenCount++;
                }
                String intExpr = ex.substring(start, end);
                ex = ex.substring(0, start) + Calculus.integrateFromString(intExpr) + ex.substring(end);
            }

            while(ex.contains("der(")) {
                int start = ex.indexOf("der(");
                int end = start + "der(".length();
                int lParenCount = 1;
                int rParenCount = 0;
                for(; end < ex.length() && lParenCount != rParenCount; end++) {
                    if(ex.charAt(end) == '(')
                        lParenCount++;
                    else if(ex.charAt(end) == ')')
                        rParenCount++;
                }
                String derExpr = ex.substring(start, end);
                ex = ex.substring(0, start) + Calculus.deriveFromString(derExpr) + ex.substring(end);
            }

            if (ex.startsWith("-")) { //This handles when the string starts with a negative number
                ex = ex.replaceFirst("-([0-9]+\\.?[0-9]*)", "(0-$1)");
            }
            //ReplaceAll doesn't work correctly, so I do it an arbitrary amount of times
            for (int i = 0; i<16;i++) {
                ex = ex.replaceAll("([*+/\\-^])\\-(.*)", "$1(0-$2)");
                ex = ex.replaceAll("\\(\\-(.*)", "(0-$1");
            }

            StringTokenizer tokenizer = new StringTokenizer(ex, "+-*/^()", true);
            ArrayDeque<String> outputQueue = new ArrayDeque<>();
            ArrayDeque<String> operatorStack = new ArrayDeque<>();
            String token;

            while (tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken().trim();

                if (isNumeric(token)) {
                    outputQueue.add(token);
                } else if (token.length() == 1 && "+-*/^".contains(token.substring(0, 1))) { // token is an operator
                    while (operatorStack.peek() != null && greaterPrecedence(operatorStack.peek().charAt(0), token.charAt(0))) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.push(token);
                } else if (token.length() == 1 && token.charAt(0) == '(') {
                    operatorStack.push("(");
                } else if (token.length() == 1 && token.charAt(0) == ')') {
                    while (operatorStack.peek().charAt(0) != '(') {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                }
            }

            while (!operatorStack.isEmpty()) {
                outputQueue.add(operatorStack.pop());
            }

            return new Expression(outputQueue);
        }else{
            ArrayDeque<String> zero = new ArrayDeque<>();
            zero.add("0");
            return new Expression(zero);
        }

    }

    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean greaterPrecedence(char first, char second) {
        switch(first) {
            case '^':
                return second != '^' && second != '(' && second != ')';
            case '*': case '/':
                return second == '+' || second == '-';
            case '+': case '-':
                return false;
        }

        return false;
    }
}
