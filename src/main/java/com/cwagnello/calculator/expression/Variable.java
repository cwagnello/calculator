package com.cwagnello.calculator.expression;

public class Variable extends Expression {
    private String name;
    private int value;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double evaluate() {
        return getValue();
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
