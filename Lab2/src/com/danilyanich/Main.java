package com.danilyanich;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Pattern ALL_IN_BOUNDS_OR_NOT = Pattern.compile("(\\[[0-1]+\\])|(\\[?[-]?\\d+\\.?\\d*\\]?)");

    public static void main(String[] args) {
        int[] numbers = getNumbers(3);
        System.out.println("input: " + Arrays.toString(numbers));
        Arrays.sort(numbers);
        System.out.println("min: " + numbers[0]);
        System.out.println("max: " + numbers[2]);
        System.out.println("average: " + getAverage(numbers));

        Integer[] array = getRandomArray(100, numbers[0]);
        System.out.println(Arrays.toString(array));
        printSpecial(array);
        sort(array);
    }

    static String deleteBrackets(String string) {
        if (string.indexOf(']') != -1)
            string = string.substring(0, string.indexOf(']'));
        if (string.lastIndexOf('[') != -1)
            string = string.substring(string.lastIndexOf('[') + 1, string.length());
        return string;
    }

    static int[] getNumbers(int count) {
        int numbers[] = new int[count];
        int total = 0;
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = input.readLine()) != null) {
                Matcher all = ALL_IN_BOUNDS_OR_NOT.matcher(line);
                while (all.find()) {
                    String token = all.group();
                    if (token.matches("\\[[0-1]+\\]")) {
                        token = deleteBrackets(token);
                        numbers[total] = Integer.parseInt(token, 2);
                        total++;
                        if (total >= 3) return numbers;
                    } else if (token.matches("\\[?[0-9]+\\]?")) {
                        token = deleteBrackets(token);
                        numbers[total] = Integer.parseInt(token);
                        total++;
                        if (total >= 3) return numbers;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    static double getAverage(int[] array) {
        float average = 0f;
        for (int number : array) {
            average += number;
        }
        average /= 3f;
        return average;
    }

    static Integer[] getRandomArray(int size, int bound) {
        Random generator = new Random();
        Integer array[] = new Integer[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = generator.nextInt(bound + 1);
        }
        return array;
    }

    static void printSpecial(Integer[] array) {
        int cPrime = 0;
        int c3m = 0;
        for (Integer element : array) {
            if (isPrime(element))
                cPrime++;
            if (element % 3 == 0)
                c3m++;
        }
        System.out.println("prime: " + cPrime);
        System.out.println("3*m: " + c3m);
    }

    static void sort(Integer[] array) {
        System.out.println("which sort u prefer? (quick, merge)");
        String answer = new Scanner(System.in).next();
        Long time = System.currentTimeMillis();
        if (answer.equals("merge"))
            Sort.merge(array);
        else if (answer.equals("quick"))
            Sort.quick(array);
        System.out.println("sorted: " + Arrays.toString(array));
        System.out.println((System.currentTimeMillis() - time) + "ms");
    }

    static boolean isPrime(int value) {
        if (value <= 1) return false;
        int sqtr = Math.toIntExact(Math.round(Math.sqrt(value)));
        for (int divider = 2; divider <= sqtr; ++divider) {
            if (value % divider == 0) {
                return false;
            }
        }
        return true;
    }


}
