package com.cwagnello.calculator;

import com.cwagnello.calculator.expression.Addition;
import com.cwagnello.calculator.expression.Division;
import com.cwagnello.calculator.expression.Expression;
import com.cwagnello.calculator.expression.Multiplication;
import com.cwagnello.calculator.expression.Subtraction;
import com.cwagnello.calculator.expression.Value;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionTest {
    private static final Logger LOG = LoggerFactory.getLogger(ExpressionTest.class);

    @Test
    public void testAddingTwoValueExpressions() throws Exception {
        Expression expression = new Addition(new Value(5), new Value(7));
        Assert.assertTrue(expression.evaluate() == 12);
    }

    @Test
    public void testAddingOneValueExpressionAndAnotherAddExpression() throws Exception {
        Expression expression = new Addition(new Value(3), new Addition(new Value(5), new Value(7)));
        Assert.assertTrue(expression.evaluate() == 15);
    }

    @Test
    public void testSubtractingTwoValueExpressions() throws Exception {
        Expression expression = new Subtraction(new Value(5), new Value(2));
        Assert.assertTrue(expression.evaluate() == 3);
    }

    @Test
    public void testAllBinaryOperationsChainedTogether() throws Exception {
        Expression expression = new Addition(new Value(233), new Subtraction(new Value(5), new Multiplication(new Value(8), new Division(new Value(21), new Value(7)))));
        Assert.assertTrue(expression.evaluate() == 214);
    }
}
