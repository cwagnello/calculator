package com.cwagnello.calculator;

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
                try {
                    String userInput = input.nextLine();
                    if (userInput != null && !userInput.isEmpty()) {
                        LOG.info("Expression received: {}", userInput);
                        double expression = new ExpressionParser(userInput).evaluate();
                        System.out.println(expression);
                        LOG.info("Answer: " + expression);
                    }
                }
                catch (Exception e) {
                    System.out.println("Error processing input");
                    LOG.error("", e);
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
