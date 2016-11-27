package com.danilyanich;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    @FunctionalInterface
    interface Action<T> {
        void apply(T o);
    }

    public static void main(String[] args) throws IOException {
        List<String> tokens = parseFile("Lab7/input.txt");
        final FileWriter[] fw = {new FileWriter("Lab7/output1.txt")};
        final Integer[] count = {0};
        Action<String> action = (String s) -> {
            try {
                fw[0].write(s);
                fw[0].write(' ');
            } catch (Exception e) {
                e.printStackTrace();
            }
            count[0]++;
        };
        tokens.stream()
                .filter((s) -> s.matches("\\d+"))
                .sorted(String::compareToIgnoreCase)
                .forEach(action::apply);
        fw[0].flush();
        System.out.println("output1.txt:" + count[0]);

        count[0] = 0;
        fw[0] = new FileWriter("Lab7/output2.txt");
        tokens.stream()
                .filter((s) -> s.matches("\\D+"))
                .sorted(String::compareToIgnoreCase)
                .forEach(action::apply);
        fw[0].flush();
        System.out.println("output2.txt:" + count[0]);
    }

    private static List<String> parseFile(String s) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(s))
                .useDelimiter("[\\s.,]+");
        List<String> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.next());
        }
        return list;
    }
}
