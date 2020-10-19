package com.cwagnello.calculator.expression;

import java.util.Map;

public abstract class Expression {

    public abstract double evaluate();
    public abstract double evaluate(Map<String, Double> variables);
}
