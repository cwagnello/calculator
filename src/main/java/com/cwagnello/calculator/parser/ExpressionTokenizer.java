package com.cwagnello.calculator.parser;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTokenizer {

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        //Remove all whitespace
        char[] c = input.replaceAll("\\s+", "").toCharArray();
        int n = c.length;
        int i = 0;


        while (i < n) {
            if (c[i] == '(') {
                tokens.add(new Token(Token.Type.L_PAREN, Character.toString(c[i])));
                i++;
            }
            else if (c[i] == ')') {
                tokens.add(new Token(Token.Type.R_PAREN, Character.toString(c[i])));
                i++;
            }
            else if (Character.isAlphabetic(c[i])) {
                if (c[i] == 'l' && c[i+1] == 'e' && c[i+2] == 't' && c[i+3] == '(') {
                    tokens.add(new Token(Token.Type.LET, "let"));
                    i += 3;
                }
                else if (c[i] == 'a' && c[i+1] == 'd' && c[i+2] == 'd' && c[i+3] == '(') {
                    tokens.add(new Token(Token.Type.ADD, "add"));
                    i += 3;
                }
                else if (c[i] == 's' && c[i+1] == 'u' && c[i+2] == 'b' && c[i+3] == '(') {
                    tokens.add(new Token(Token.Type.SUB, "sub"));
                    i += 3;
                }
                else if (c[i] == 'm' && c[i+1] == 'u' && c[i+2] == 'l' && c[i+3] == 't' && c[i+4] == '(') {
                    tokens.add(new Token(Token.Type.MULT, "mult"));
                    i += 4;
                }
                else if (c[i] == 'd' && c[i+1] == 'i' && c[i+2] == 'v' && c[i+3] == '(') {
                    tokens.add(new Token(Token.Type.DIV, "div"));
                    i += 3;
                }
                else {
                    throw new UnsupportedOperationException("invalid operation " + input.substring(i));
                }
            }
            else if (Character.isDigit(c[i])) {
                String number = "";
                while (Character.isDigit(c[i])) {
                    number += Character.toString(c[i]);
                    i++;
                }
                tokens.add(new Token(Token.Type.INTEGER, number));
            }
            else {
                i++;
            }
        }
        return tokens;
    }
}
