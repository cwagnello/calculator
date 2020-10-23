package com.cwagnello.calculator;

import com.cwagnello.calculator.parser.ExpressionParser;
import org.junit.Assert;
import org.junit.Test;

public class ExpressionParserTest {
    @Test
    public void addTwoValues() throws Exception {
        String test = "add(1, 2)";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(3, ep.evaluate()) == 0);
    }

    @Test
    public void subtractTwoValues() throws Exception {
        String test = "sub(4, 8)";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(-4, ep.evaluate()) == 0);
    }

    @Test
    public void multiplyTwoValues() throws Exception {
        String test = "mult(4, 8)";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(32, ep.evaluate()) == 0);
    }

    @Test
    public void divideTwoValues() throws Exception {
        String test = "div(4, 8)";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(0.5, ep.evaluate()) == 0);
    }

    @Test
    public void nestedArithmeticAddMult() throws Exception {
        String test = "add(1, mult(2, 3))";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(7, ep.evaluate()) == 0);

    }

    @Test
    public void nestedArithmeticMultAddDiv() throws Exception {
        String test = "mult(add(2, 2), div(9, 3))";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(12, ep.evaluate()) == 0);
    }

    @Test
    public void nestedArithmeticLetAdd() throws Exception {
        String test = "let(a, 5, add(a, a))";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(10, ep.evaluate()) == 0);
    }

    @Test
    public void twoNestedLets() throws Exception {
        String test = "let(a, 5, let(b, mult(a, 10), add(b, a)))";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(55, ep.evaluate()) == 0);
    }

    @Test
    public void twoNestedLetsWithMultipleLetsInSameExpression() throws Exception {
        String test = "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(40, ep.evaluate()) == 0);
    }

    @Test
    public void threeNestedLetsWithArithmetic() throws Exception {
        String test = "let(a, 5, let(b, mult(a, let(c, 4, add(c,a))), add(c, b)))";
        ExpressionParser ep = new ExpressionParser(test);
        Assert.assertTrue(Double.compare(49, ep.evaluate()) == 0);
    }
}

