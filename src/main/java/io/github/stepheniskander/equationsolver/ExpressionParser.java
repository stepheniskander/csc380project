package io.github.stepheniskander.equationsolver;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class ExpressionParser {
    public Expression parse(String ex){
        StringTokenizer tokenizer = new StringTokenizer(ex, "+-*/^", true);
        ArrayDeque<String> outputQueue = new ArrayDeque<>();
        ArrayDeque<String> operatorStack = new ArrayDeque<>();
        String token;

        while(tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken().trim();

            if(isNumeric(token)) {
                outputQueue.add(token);
            } else if(token.length() == 1 && "+-*/^".contains(token.substring(0, 1))) { // token is an operator
                while(operatorStack.peek() != null && greaterPrecendence(operatorStack.peek().charAt(0), token.charAt(0))) {
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

        StringBuilder rpn = new StringBuilder();
        for(String s : outputQueue)
            rpn.append(s);

        return new Expression(rpn.toString());
    }

    private boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean greaterPrecendence(char first, char second) {
        switch(first) {
            case '(': case ')':
                if(second != '(' && second != ')')
                    return true;
                else
                    return false;
            case '^':
                if(second != '^' && second != '(' && second != ')')
                    return true;
                else
                    return false;
            case '*': case '/':
                if(second == '+' || second == '-')
                    return true;
                else
                    return false;
            case '+': case '-':
                return false;
        }

        return false;
    }

}
