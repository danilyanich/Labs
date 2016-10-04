package com.danilyanich;

/*
Лабораторная работа 3.

        Реализовать генератор чисел Фибоначчи:
        Пользователь вводит x0, x1 и N кол-во чисел. Числа записать в массив, размерностью N.

        Рассчитать треугольник Паскаля.
        Треугольник Паскаля – бесконечная таблица биномиальных коэффициентов, имеющая треугольную форму. В этом треугольнике на вершине и по бокам стоят единицы. Каждое число равно сумме двух расположенных над ним чисел.
        1
        11
        121
        1331
        14641
*/

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        fibonacci(input);
        triangle(input);
    }

    private static int nextExplicitInt(Scanner input) {
        while (true) {
            if (input.hasNextInt())
                return input.nextInt();
            input.next();
        }
    }

    private static void fibonacci(Scanner input) {
        System.out.println("Fibonacci series:");
        int size;
        long x0;
        long x1;
        System.out.print("size: ");
        size = nextExplicitInt(input);
        System.out.print("x0: ");
        x0 = nextExplicitInt(input);
        System.out.print("x1: ");
        x1 = nextExplicitInt(input);
        try {
            FibonacciGenerator g = new FibonacciGenerator(x0, x1);
            g.next();
            g.asArray(size);
            //System.out.println(Arrays.toString(fibonacci));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void triangle(Scanner input) {
        System.out.println("Triangle of Pascal:");
        System.out.print("size of triangle: ");
        int size = nextExplicitInt(input);
        PascalTriangle triangle = new PascalTriangle();
        try {
            for (int i = 0; i < size; ++i) {
                System.out.println(Arrays.toString(triangle.next()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
