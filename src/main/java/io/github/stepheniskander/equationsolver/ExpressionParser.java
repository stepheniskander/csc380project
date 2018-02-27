package io.github.stepheniskander.equationsolver;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class ExpressionParser {
    public Expression parse(String ex){
        StringTokenizer tokenizer = new StringTokenizer(ex, "+-*/^()", true);
        ArrayDeque<String> outputQueue = new ArrayDeque<>();
        ArrayDeque<String> operatorStack = new ArrayDeque<>();
        String token;

        while(tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken().trim();

            if(isNumeric(token)) {
                outputQueue.add(token);
            } else if(token.length() == 1 && "+-*/^".contains(token.substring(0, 1))) { // token is an operator
                while(operatorStack.peek() != null && greaterPrecedence(operatorStack.peek().charAt(0), token.charAt(0))) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if(token.length() == 1 && token.charAt(0) == '(') {
                operatorStack.push("(");
            } else if(token.length() == 1 && token.charAt(0) == ')') {
                while(operatorStack.peek().charAt(0) != '(') {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.pop();
            }
        }

        while(!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        return new Expression(outputQueue);
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
