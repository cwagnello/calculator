package com.cwagnello.calculator.expression;

public class Value extends Expression {
    private double value;

    public Value(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return this.getValue();
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
