package com.cwagnello.calculator.expression;

import java.util.Map;

public class Value extends Expression {
    private double value;

    public Value(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return this.value();
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return this.value();
    }

    public double value() {
        return this.value;
    }

    public void value(int value) {
        this.value = value;
    }
}
