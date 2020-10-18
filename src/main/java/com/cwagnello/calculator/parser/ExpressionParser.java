package com.cwagnello.calculator.parser;

import com.cwagnello.calculator.expression.Addition;
import com.cwagnello.calculator.expression.Division;
import com.cwagnello.calculator.expression.Expression;
import com.cwagnello.calculator.expression.Multiplication;
import com.cwagnello.calculator.expression.Subtraction;
import com.cwagnello.calculator.expression.Value;
import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {

    private String input;

    public ExpressionParser(String input) {
        this.input = input;
    }

    public Expression build() {
        List<Token> tokens = ExpressionTokenizer.tokenize(this.input);
/*        for (Token t : tokens) {
            System.out.println("Token: "  + t);
        }*/
        return buildRecursive(tokens);
    }

    private Expression buildRecursive(List<Token> tokens) {
        //System.out.println("TOKENS size: " + tokens.size());

        for (int i = 0; i < tokens.size(); i++) {
            //System.out.println("I'm inside the loop. Token: " + tokens.get(i));

            if (tokens.get(i).role() == Token.Role.BINARY) {
                return binaryExpression(tokens.get(i).type(), tokens, i);
            }
            else if (tokens.get(i).type() == Token.Type.INTEGER) {
                //System.out.println("Parsing value: " + tokens.get(i).value);
                return new Value(Double.parseDouble(tokens.get(i).value()));
            }
        }
        return null;
    }

    private Expression binaryExpression(Token.Type type, List<Token> tokens, int index) {
        List<Token> tokensForAdd = getTokensForExpression(tokens, index + 1);
        //System.out.println("Size of tokensForAddExpression: " + tokensForAdd.size());

        List<Token> list1 = getTokensForExpression(tokensForAdd, index + 1);
        List<Token> list2 = getTokensForExpression(tokensForAdd, index + 1 +list1.size());
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

    private List<Token> getTokensForExpression(List<Token> tokens, int index) {
        List<Token> tokenList = new ArrayList<>();
        int parenCount = 0;
        if (index >= tokens.size()) {
            return tokenList;
        }
        boolean isExpression = false;
        do {
            if (parenCount == 0 && isExpression) {
                isExpression = false;
            }
            //System.out.println("Token in expression: " + tokens.get(index).value);
            if (tokens.get(index).type() == Token.Type.L_PAREN) {
                //System.out.println("Found an open paren: " + parenCount);
                parenCount++;
            }
            else if (tokens.get(index).type() == Token.Type.R_PAREN) {
                //System.out.println("Found a close paren: " + parenCount);
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
            else {
                //System.out.println("Found another token: " + tokens.get(index));
            }
            tokenList.add(tokens.get(index));
            index++;
        }
        while (index < tokens.size() && (parenCount > 0 || isExpression));
        return tokenList;
    }
}
