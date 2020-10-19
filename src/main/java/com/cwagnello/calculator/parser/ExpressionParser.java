package com.cwagnello.calculator.parser;

import com.cwagnello.calculator.expression.Addition;
import com.cwagnello.calculator.expression.Division;
import com.cwagnello.calculator.expression.Expression;
import com.cwagnello.calculator.expression.Let;
import com.cwagnello.calculator.expression.Multiplication;
import com.cwagnello.calculator.expression.Subtraction;
import com.cwagnello.calculator.expression.Value;
import com.cwagnello.calculator.expression.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private static final Logger LOG = LoggerFactory.getLogger(ExpressionParser.class);
    private String input;

    public ExpressionParser(String input) {
        this.input = input;
    }

    public Expression build() {
        List<Token> tokens = ExpressionTokenizer.tokenize(this.input);
        return buildRecursive(tokens);
    }

    private Expression buildRecursive(List<Token> tokens) {
        LOG.debug("Build recursive: {}" + tokens);
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).role() == Token.Role.BINARY) {
                return binaryExpression(tokens.get(i).type(), tokens, i);
            }
            else if (tokens.get(i).role() == Token.Role.TRINARY) {
                return trinaryExpression(tokens.get(i).type(), tokens, i);
            }
            else if (tokens.get(i).type() == Token.Type.INTEGER) {
                return new Value(Double.parseDouble(tokens.get(i).value()));
            }
            else if (tokens.get(i).type() == Token.Type.VARIABLE) {
                return new Variable(tokens.get(i).value());
            }
        }
        return null;
    }

    private Expression binaryExpression(Token.Type type, List<Token> tokens, int index) {
        List<Token> tokensForBinaryExpression = getTokensForExpression(tokens, index + 1);
        LOG.debug("Size of tokensForBinaryExpression: {}", tokensForBinaryExpression.size());
        LOG.debug("Binary tokens: {}", tokensForBinaryExpression);
        List<Token> list1 = getTokensForExpression(tokensForBinaryExpression, index + 1);
        index += 1 + list1.size();
        List<Token> list2 = getTokensForExpression(tokensForBinaryExpression, index);
        Expression op1 = buildRecursive(list1);
        Expression op2 = buildRecursive(list2);

        if (type == Token.Type.ADD) {
            return new Addition(op1, op2);
        }
        else if (type == Token.Type.SUB) {
            return new Subtraction(op1, op2);
        }
        else if (type == Token.Type.MULT) {
            return new Multiplication(op1, op2);
        }
        else if (type == Token.Type.DIV) {
            return new Division(op1, op2);
        }

        return null;
    }

    private Expression trinaryExpression(Token.Type type, List<Token> tokens, int index) {
        List<Token> tokensForTrinayExpression = getTokensForExpression(tokens, index + 1);
        LOG.debug("All tokens: {}" + tokens);
        List<Token> list1 = getTokensForExpression(tokensForTrinayExpression, index + 1);
        index += 1 + list1.size();
        List<Token> list2 = getTokensForExpression(tokensForTrinayExpression, index);
        index += list2.size();
        List<Token> list3 = getTokensForExpression(tokensForTrinayExpression, index);
        LOG.debug("List1 tokens: {}", list1);
        LOG.debug("List2 tokens: {}", list2);
        LOG.debug("List3 tokens: {}", list3);
        if (type == Token.Type.LET) {
            if (list1.size() != 1) {
                throw new IllegalArgumentException("" + list1);
            }
            Expression op1 = buildRecursive(list1);
            Expression op2 = buildRecursive(list2);
            Expression op3 = buildRecursive(list3);
            return new Let((Variable)op1, op2, op3);
        }
        return null;
    }

    private List<Token> getTokensForExpression(List<Token> tokens, int index) {
        List<Token> tokenList = new ArrayList<>();
        int parenCount = 0;
        if (index >= tokens.size()) {
            return tokenList;
        }
        boolean isExpression = false;
        do {
            if (parenCount == 0 && isExpression && tokenList.size() > 2) {
                isExpression = false;
                continue;
            }
            else if (tokens.get(index).type() == Token.Type.L_PAREN) {
                parenCount++;
            }
            else if (tokens.get(index).type() == Token.Type.R_PAREN) {
                parenCount--;
            }
            else if (tokens.get(index).type() == Token.Type.INTEGER){
                if (tokenList.isEmpty()) {
                    tokenList.add(tokens.get(index));
                    return tokenList;
                }
            }
            else if (tokens.get(index).role() == Token.Role.BINARY || tokens.get(index).role() == Token.Role.TRINARY) {
                isExpression = true;
            }
            //System.out.println("Adding token to list: " + tokens.get(index));
            tokenList.add(tokens.get(index));
            index++;
        }
        while (index < tokens.size() && (parenCount > 0 || isExpression));
        return tokenList;
    }
}
