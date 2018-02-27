package io.github.stepheniskander.equationsolver;

import java.util.ArrayDeque;
import java.math.*;

public class Expression {
    private ArrayDeque<String> rpnStack;

    public Expression(ArrayDeque<String> rpnStack) {
        this.rpnStack = rpnStack;
    }

    public ArrayDeque<String> getRpnStack() {
        return rpnStack;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for(String s : rpnStack) {
            sb.append(s);
            sb.append(" ");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    public double evaluateRpn(){
        ArrayDeque<String> resultStack = new ArrayDeque<>();
        for(String s : rpnStack){
            if(!ExpressionParser.isNumeric(s)){
                double op_1 =Double.parseDouble(resultStack.pop());
                double op_2 =Double.parseDouble(resultStack.pop());
                String res = Double.toString(evaluateExpression(op_1,op_2,s));

            }else if(ExpressionParser.isNumeric(s)){
                resultStack.push(s);
            }
        }
        return Double.parseDouble(resultStack.pop());


    }
    private double evaluateExpression(double op_1, double op_2, String operand){
        switch (operand){
            case "+": return op_1 + op_2;
            case "-": return op_1 - op_2;
            case "^": return Math.pow(op_1,op_2);
            case "*": return op_1 * op_2;
            case "/": return op_1 / op_2;
            default: return 0;
        }
    }

}
