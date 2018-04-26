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
    public BigDecimal evaluateRpn(){
        ArrayDeque<String> resultStack = new ArrayDeque<>();
        for(String s : rpnStack){
            if(!ExpressionParser.isNumeric(s)){
                BigDecimal op_2 = new BigDecimal(resultStack.pop());
                BigDecimal op_1 = new BigDecimal(resultStack.pop());
                String res = evaluateExpression(op_1,op_2,s).toString();
                resultStack.push(res);

            }else {
                resultStack.push(s);
            }
        }
        return new BigDecimal(resultStack.pop());


    }
    private BigDecimal evaluateExpression(BigDecimal op_1, BigDecimal op_2, String operand){ //Evaluates basic arithmetic
        switch (operand){
            case "+": return op_1.add(op_2);
            case "-": return op_1.subtract(op_2);
            case "^": return BigDecimal.valueOf(Math.pow(op_1.doubleValue(), op_2.doubleValue()));
            case "*": return op_1.multiply(op_2);
            case "/": return op_1.divide(op_2, MathContext.DECIMAL64); //I'm not sure why these are reversed, I must have done something wrong in the evaluation
            default: return BigDecimal.ZERO;
        }
    }

}
