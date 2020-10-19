package com.cwagnello.calculator.expression;

import java.util.Map;

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

    @Override
    public double evaluate(Map<String, Double> variables) {
        return this.operand1.evaluate(variables) - this.operand2.evaluate(variables);
    }
}
