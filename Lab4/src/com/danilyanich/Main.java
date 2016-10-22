package com.danilyanich;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {
        pureviewOperators();
        testParser();
    }

    private static void pureviewOperators() {
        System.out.println("see how operators work:");

        Complex a = Complex.rectangular(4, 2);
        Complex b = Complex.polar(1, Math.PI / 4);
        Complex c = Complex.rectangular(0, 1);

        System.out.println("a:");
        System.out.println(a.toString(Complex.Form.RECTANGULAR));
        System.out.println(a.toString(Complex.Form.EXPONENTIAL));
        System.out.println(a.toString(Complex.Form.POLAR));
        System.out.println();

        System.out.println("b:");
        System.out.println(b.toString(Complex.Form.RECTANGULAR));
        System.out.println(b.toString(Complex.Form.EXPONENTIAL));
        System.out.println(b.toString(Complex.Form.POLAR));
        System.out.println();

        System.out.println("c:");
        System.out.println(c.toString(Complex.Form.RECTANGULAR));
        System.out.println(c.toString(Complex.Form.EXPONENTIAL));
        System.out.println(c.toString(Complex.Form.POLAR));
        System.out.println();

        System.out.println("a + b = " + Complex.add(a, b));
        System.out.println("b - c = " + Complex.sub(b, c));
        System.out.println("a * b = " + Complex.mul(a, b));
        System.out.println("c / b = " + Complex.div(c, b));
        System.out.println("c^2 = " + c.pow(2));
        System.out.println();
    }

    private static void testParser() {
        System.out.println("test complex parser:");
        System.out.println(Complex.PATTERN.pattern());
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        List<Complex> list = new ArrayList<>();
        String string;
        try {
            while (!(string = input.readLine()).equals("")) {
                Matcher matcher = Complex.PATTERN.matcher(string);
                while (matcher.find())
                    try {
                        list.add(Complex.parseComplex(matcher.group()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }

        System.out.println();
        for (Complex c : list) {
            System.out.println(c.toString(Complex.Form.RECTANGULAR));
            System.out.println(c.toString(Complex.Form.EXPONENTIAL));
            System.out.println(c.toString(Complex.Form.POLAR));
            System.out.println();
        }
        System.out.println();
    }
}
