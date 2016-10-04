package com.danilyanich;

import com.danilyanich.Tokens.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Dan on 09.09.16.
 */
public class ReverseNotation {
    private static String numbers = "0123456789.", operators = "/*+-", brackets = "()";

    private static List<String> split(String expression) {
        List<String> tokens = new ArrayList<>();
        String digits = "", word = "";
        int i = 0;
        while (i < expression.length()) {
            if (expression.charAt(i) == ' ') {
                i++;
                continue;
            }
            for (; i < expression.length() &&
                    numbers.indexOf(expression.charAt(i)) != -1; i++) {
                digits += expression.charAt(i);
            }
            if (!digits.isEmpty()) {
                tokens.add(digits);
                digits = "";
                continue;
            }
            for (; i < expression.length() &&
                    Character.isLetter(expression.charAt(i)); i++) {
                word += expression.charAt(i);
            }
            if (!word.isEmpty()) {
                tokens.add(word);
                word = "";
                continue;
            }
            tokens.add("" + expression.charAt(i));
            i++;
        }
        return tokens;
    }

    private static boolean containsAny(String string, String any) {
        for (char character : any.toCharArray())
            if (string.indexOf(character) == -1)
                return false;
        return true;
    }

    public static List<String> convert(String expression) throws Exception {
        List<String> converted = new ArrayList<>();
        List<String> tokens = split(expression);
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (containsAny(numbers, token)) {
                converted.add(token);
                continue;
            }
            if (containsAny(operators, token)) {
                while (!stack.isEmpty() &&
                        Operator.priority(token) < Operator.priority(stack.peek())) {
                    converted.add(stack.peek());
                    stack.pop();
                }
                stack.push(token);
                continue;
            }
            if (containsAny(brackets, token)) {
                if (token.equals("("))
                    stack.push(token);
                else try {
                    while (!stack.peek().equals("(")) {
                        converted.add(stack.peek());
                        stack.pop();
                    }
                    stack.pop();
                } catch (Exception e) {
                    throw new Exception("Brackets mismatch");
                }
                continue;
            }
            boolean consistsFromLetters = true;
            for (char c : token.toCharArray())
                if (!Character.isLetter(c))
                    consistsFromLetters = false;
            if (consistsFromLetters)
                converted.add(token);
        }
        while (!stack.isEmpty()) {
            converted.add(stack.peek());
            stack.pop();
        }
        return converted;
    }

    public static double calculate(List<String> tokens) throws Exception {
        Stack<Double> stack = new Stack<>();
        boolean operatorsCheck = false;
        for (String token : tokens) {
            if (containsAny(numbers, token)) {
                stack.push(Double.parseDouble(token));
                continue;
            }
            if (containsAny(operators, token)) {
                operatorsCheck = true;
                double arg1, arg2;
                try {
                    arg2 = stack.peek();
                    stack.pop();
                    arg1 = stack.peek();
                    stack.pop();
                } catch (Exception e) {
                    throw new Exception("insufficient values: operator " + token);
                }
                double result = Operator.apply(token, arg1, arg2);
                stack.push(result);
            }
        }
        if (!operatorsCheck)
            throw new Exception("no operators in expression");
        if (stack.size() != 1)
            throw new Exception("operators & operands mismatch");
        return stack.pop();
    }

    public static double calculate(String expression) throws Exception {
        return calculate(split(expression));
    }
}
