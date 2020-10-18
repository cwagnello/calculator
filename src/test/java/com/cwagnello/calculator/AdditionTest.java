package com.cwagnello.calculator;

import com.cwagnello.calculator.expression.Addition;
import com.cwagnello.calculator.expression.Expression;
import com.cwagnello.calculator.expression.Value;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionTest {
    private static final Logger LOG = LoggerFactory.getLogger(AdditionTest.class);

    @Test
    public void testAddingTwoValueExpressions() throws Exception {
        Expression expression = new Addition(new Value(5), new Value(7));
        Assert.assertTrue(expression.evaluate() == 12);
        LOG.info ("Answer: {}", expression.evaluate());
    }

    @Test
    public void testAddingOneValueExpressionAndAnotherAddExpression() throws Exception {
        Expression expression = new Addition(new Value(3), new Addition(new Value(5), new Value(7)));
        Assert.assertTrue(expression.evaluate() == 15);
        LOG.info ("Answer: {}", expression.evaluate());
    }
}
