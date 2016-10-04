package com.danilyanich;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String expr = "";
        while (true) {
            try {
                if (in.hasNextLine()) expr = in.nextLine();
                if (expr.equals("exit")) break;
                List<String> orderedTokens = ReverseNotation.convert(expr);
                System.out.println(orderedTokens.toString());
                double result = ReverseNotation.calculate(orderedTokens);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
