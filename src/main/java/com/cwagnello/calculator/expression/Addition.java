package com.cwagnello.calculator.expression;

public class Addition extends Expression {
    Expression operand1;
    Expression operand2;

    public Addition(Expression e1, Expression e2) {
        this.operand1 = e1;
        this.operand2 = e2;
    }

    @Override
    public double evaluate() {
        return this.operand1.evaluate() + this.operand2.evaluate();
    }
}
