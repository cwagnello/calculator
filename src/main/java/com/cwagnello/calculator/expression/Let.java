package com.cwagnello.calculator.expression;

public class Let extends Expression {
    private Variable operand1;
    private Expression operand2;
    private Expression operand3;

    public Let(Variable e1, Expression e2, Expression e3) {
        this.operand1 = e1;
        this.operand2 = e2;
        this.operand3 = e3;
    }

    @Override
    public double evaluate() {
        return 0;
    }
}
