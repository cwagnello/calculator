package com.cwagnello.calculator.expression;

public class Subtraction extends Expression {
    Expression operand1;
    Expression operand2;

    public Subtraction(Expression e1, Expression e2) {
        this.operand1 = e1;
        this.operand2 = e2;
    }

    @Override
    public double evaluate() {
        return this.operand1.evaluate() - this.operand2.evaluate();
    }
}