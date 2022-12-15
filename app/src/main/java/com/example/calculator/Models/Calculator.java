package com.example.calculator.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Calculator {

    public static double calculate(String expression) throws ArithmeticException {
        return evaluatePostfix(convertExpression(expression));
    }

    public static String calculateAsString(String expression) throws ArithmeticException {
        double doubleResult = calculate(expression);
        return doubleResult % 1 == 0 ? String.valueOf((int) doubleResult) : Double.toString(doubleResult);
    }

    private static List<String> convertExpression(String expression) {
        Map<String, Integer> priorities = new HashMap<String, Integer>();
        priorities.put("+", 2);
        priorities.put("-", 2);
        priorities.put("*", 1);
        priorities.put("/", 1);

        String[] expressionItems = expression.split(" ");
        List<String> postfixItems = new ArrayList<>(expressionItems.length);
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < expressionItems.length; i++) {
            if (isNumeric(expressionItems[i])) {
                postfixItems.add(expressionItems[i]);
            } else {

                while (!operators.isEmpty() && (priorities.get(operators.peek()) < priorities.get(expressionItems[i]))) {
                    postfixItems.add(operators.pop());
                }
                operators.push(expressionItems[i]);
            }
        }
        while (!operators.isEmpty()) {
            postfixItems.add(operators.pop());
        }
        return postfixItems;
    }

    private static Double evaluatePostfix(List<String> postfix) throws ArithmeticException {
        Stack<Double> operands = new Stack<>();

        for (int i = 0; i < postfix.size(); i++) {
            if (isNumeric(postfix.get(i))) {
                operands.push(Double.parseDouble(postfix.get(i)));
                continue;
            }
            double rightOperand = operands.pop();
            double leftOperand = operands.pop();
            String operator = postfix.get(i);

            switch (operator) {
                case "+":
                    operands.push(leftOperand + rightOperand);
                    break;
                case "-":
                    operands.push(leftOperand - rightOperand);
                    break;
                case "*":
                    operands.push(leftOperand * rightOperand);
                    break;
                case "/":
                    if (rightOperand == 0) {
                        throw new ArithmeticException();
                    }
                    operands.push(leftOperand / rightOperand);
                    break;
            }
        }
        return operands.pop();
    }

    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}