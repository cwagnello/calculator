package com.cwagnello.calculator;

import com.cwagnello.calculator.parser.ExpressionTokenizer;
import com.cwagnello.calculator.parser.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ExpressionTokenizerTest {
    @Test
    public void testValidAddExpression() throws Exception {
        String test = "add( 1, 2)";
        List<Token> tokenizer = ExpressionTokenizer.tokenize(test);
        Assert.assertTrue(tokenizer.size() == 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidExpression() throws Exception {
        String test = "adddd( 1, 2)";
        List<Token> tokenizer = ExpressionTokenizer.tokenize(test);
    }
}
