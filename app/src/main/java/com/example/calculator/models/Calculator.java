package com.example.calculator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
// Clase creada para llevar a cabo los cálculos de las expresiones que introduce el usuario.
public class Calculator {
    // Realiza los calculos a partir de una expresión y devuelve un número decimal.
    public static double calculate(String expression) throws ArithmeticException {
        return evaluatePostfix(convertExpression(expression));
    }
    // Realiza los calculos a partir de una expresión y devuelve el resultado en forma de String.
    public static String calculateAsString(String expression) throws ArithmeticException {
        double doubleResult = calculate(expression);
        return doubleResult % 1 == 0 ? String.valueOf((int) doubleResult) : Double.toString(doubleResult);
    }
    // Convierte una expresión normal a una de tipo postfix.
    private static List<String> convertExpression(String expression) {
        Map<String, Integer> priorities = new HashMap<String, Integer>();
        priorities.put("+", 2);
        priorities.put("-", 2);
        priorities.put("*", 1);
        priorities.put("/", 1);

        String[] expressionItems = expression.split(" ");
        List<String> postfixItems = new ArrayList<>(expressionItems.length);
        Stack<String> operators = new Stack<>();
        // Se itera cada operando y operador que conforman la expresión.
        for (int i = 0; i < expressionItems.length; i++) {
            // Si es un número, significa que no es un operador.
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
    // Evalua una expresión postfix mediante Stacks y devuelve el resultado.
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
    // Verifica si la String es un número.
    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}