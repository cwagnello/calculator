package com.cwagnello.calculator;

import com.cwagnello.calculator.expression.Expression;
import com.cwagnello.calculator.parser.ExpressionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) {
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            while (true) {
                String userInput = input.nextLine();
                if (userInput != null && !userInput.isEmpty()) {
                    Expression expression = new ExpressionParser(userInput).build();
                    System.out.println(expression.evaluate());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Expression received: {}", userInput);
                        LOG.debug("Answer: " + expression.evaluate());
                    }
                }
            }
        }
        finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
