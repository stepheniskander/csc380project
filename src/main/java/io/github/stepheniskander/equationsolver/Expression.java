package io.github.stepheniskander.equationsolver;

import java.util.ArrayDeque;

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
}
