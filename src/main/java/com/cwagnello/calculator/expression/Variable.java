package com.cwagnello.calculator.expression;

import java.util.Map;

public class Variable extends Expression {
    private String name;
    private String value;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double evaluate() {
        return Double.parseDouble(this.value);
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        //System.out.println("Map: " + variables);
        //System.out.println("Getting variable name: " + name());
        return variables.get(name());
    }

    public String name() {
        return this.name;
    }

    public String value() {
        return value;
    }

    public void value(String value) {
        this.value = value;
    }
}
