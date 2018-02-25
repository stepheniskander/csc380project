package io.github.stepheniskander.equationsolver;

public class Expression {
    private String rpn;

    public Expression(String rpn) {
        this.rpn = rpn;
    }

    public String getRpn() {
        return rpn;
    }
}
