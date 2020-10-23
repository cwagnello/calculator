package com.cwagnello.calculator.parser;

import com.cwagnello.calculator.expression.Addition;
import com.cwagnello.calculator.expression.Division;
import com.cwagnello.calculator.expression.Expression;
import com.cwagnello.calculator.expression.Multiplication;
import com.cwagnello.calculator.expression.Subtraction;
import com.cwagnello.calculator.expression.Value;
import com.cwagnello.calculator.expression.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ExpressionParser {
    private static final Logger LOG = LoggerFactory.getLogger(ExpressionParser.class);
    private String input;
    private Map<String, Double> variables = new HashMap<>();

    public ExpressionParser(String input) {
        this.input = input;
    }

    public double evaluate() {
        List<Token> tokens = ExpressionTokenizer.tokenize(this.input);
        LOG.debug("Parsing expression: {}", this.input);
        return evaluate(tokens);
    }

    private double evaluate(List<Token> tokens) {
        Stack<Token> operations = new Stack<>();
        Stack<Double> values = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (t.role() == Token.Role.BINARY) {
                operations.push(t);
            }
            else if (t.role() == Token.Role.TRINARY) {
                if (t.type() == Token.Type.LET) {
                    List<Token> tokensForTrinaryExpression = getTokensForExpression(tokens, i + 1);
                    LOG.debug("All tokens: {}", tokens);
                    List<Token> list1 = getTokensForExpression(tokensForTrinaryExpression, i + 1);
                    i += 1 + list1.size();
                    List<Token> list2 = getTokensForExpression(tokensForTrinaryExpression, i);
                    i += list2.size();
                    List<Token> list3 = getTokensForExpression(tokensForTrinaryExpression, i);
                    LOG.debug("List1 tokens: {}", list1);
                    LOG.debug("List2 tokens: {}", list2);
                    LOG.debug("List3 tokens: {}", list3);
                    if (!list1.isEmpty() && !list2.isEmpty() && !list3.isEmpty()) {
                        if (list1.get(0).type() == Token.Type.VARIABLE) {
                            variables.put(list1.get(0).value(), getExpression(list2).evaluate(variables));
                        }
                        LOG.debug("VARIABLES: " + variables);
                        values.push(getExpression(list3).evaluate(variables));
                    }
                }
            }
            else if (t.role() == Token.Role.STRUCTURE) {
                if(t.type() == Token.Type.R_PAREN && values.size() > 1) {
                    if (!operations.isEmpty()) {
                        double op2 = values.pop();
                        double op1 = values.pop();
                        if (operations.peek().type() == Token.Type.ADD) {
                            values.push(new Addition(new Value(op1), new Value(op2)).evaluate());
                        } else if (operations.peek().type() == Token.Type.SUB) {
                            values.push(new Subtraction(new Value(op1), new Value(op2)).evaluate());
                        } else if (operations.peek().type() == Token.Type.MULT) {
                            values.push(new Multiplication(new Value(op1), new Value(op2)).evaluate());
                        } else if (operations.peek().type() == Token.Type.DIV) {
                            values.push(new Division(new Value(op1), new Value(op2)).evaluate());
                        }
                        operations.pop();
                    }
                }
            }
            else if (t.role() == Token.Role.IDENTIFIER) {
                if (t.type() == Token.Type.INTEGER) {
                    values.push(Double.parseDouble(t.value()));
                }
                else if (t.type() == Token.Type.VARIABLE) {
                    values.push(variables.get(t.value()));
                }
            }
        }

        return values.pop();
    }

    private Expression getExpression(List<Token> tokens) {
        int index = 0;
        Token current = tokens.get(index);
        if (current.role() == Token.Role.BINARY) {
            return binaryExpression(current.type(), tokens, index);
        }
        else if (current.role() == Token.Role.TRINARY) {
            return new Value(evaluate(tokens));
        }
        else if (current.type() == Token.Type.INTEGER) {
            return new Value(Double.parseDouble(current.value()));
        }
        else if (current.type() == Token.Type.VARIABLE) {
            return new Variable(current.value());
        }
        else {
            throw new IllegalArgumentException("Error parsing expression");
        }
    }

    private Expression binaryExpression(Token.Type type, List<Token> tokens, int index) {
        List<Token> tokensForBinaryExpression = getTokensForExpression(tokens, index + 1);
        LOG.debug("Size of tokensForBinaryExpression: {}", tokensForBinaryExpression.size());
        LOG.debug("Binary tokens: {}", tokensForBinaryExpression);
        List<Token> list1 = getTokensForExpression(tokensForBinaryExpression, index + 1);
        index += 1 + list1.size();
        List<Token> list2 = getTokensForExpression(tokensForBinaryExpression, index);
        Expression op1 = getExpression(list1);
        Expression op2 = getExpression(list2);

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
            tokenList.add(tokens.get(index));
            index++;
        }
        while (index < tokens.size() && (parenCount > 0 || isExpression));
        return tokenList;
    }

}
