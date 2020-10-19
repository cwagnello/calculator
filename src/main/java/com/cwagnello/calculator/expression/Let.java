package com.cwagnello.calculator.expression;

import java.util.HashMap;
import java.util.Map;

public class Let extends Expression {
    private Variable operand1;
    private Expression operand2;
    private Expression operand3;
    private Map<String, Double> variables = new HashMap<>();

    public Let(Variable e1, Expression e2, Expression e3) {
        this.operand1 = e1;
        this.operand2 = e2;
        this.operand3 = e3;
    }

    @Override
    public double evaluate() {
        //System.out.println("Adding to map: " + operand1.name() + "=>" + operand2.evaluate(variables));
        this.variables.put(operand1.name(), operand2.evaluate(variables));
        //System.out.println("operand3.evaluate with variables: " + operand3.evaluate(variables));
        return operand3.evaluate(variables);
    }

    @Override
    public double evaluate(Map<String, Double> variables) {
        return operand3.evaluate(variables);
    }

}
