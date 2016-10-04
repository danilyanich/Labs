package com.danilyanich.Tokens;

/**
 * Created by Dan on 09.09.16.
 */
public class Operator {
    public static int priority(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    public static double apply(String operator, double arg1, double arg2) throws Exception {
        switch (operator) {
            case "*":
                return arg1 * arg2;
            case "/":
                if (arg2 == 0) throw new Exception("division by zero: " + arg1 + "/0");
                return arg1 / arg2;
            case "+":
                return arg1 + arg2;
            case "-":
                return arg1 - arg2;
        }
        throw new Exception("undefined behavior");
    }
}
