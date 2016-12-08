package com.danilyanich;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) throws IOException {
        List<LinkedList<String>> juices = parseFile("Lab8/juice.in");
        List<String> components = getComponents(juices);

        StringBuilder[] builder = {new StringBuilder()};
        Consumer<String> toBuilder = component -> {
            builder[0].append(component);
            builder[0].append(System.lineSeparator());
        };

        Thread thread1 = new Thread(() -> {
            synchronized (components) {
                components.forEach(toBuilder);
                toFile(builder[0].toString(), "Lab8/juice1.out");
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (components) {
                builder[0] = new StringBuilder();
                components.parallelStream().sorted().forEach(toBuilder);
                toFile(builder[0].toString(), "Lab8/juice2.out");
            }
        });

        Thread thread3 = new Thread(() -> {
            juices.forEach(Collections::sort);
            int count = 1;
            List<List<String>> list = new ArrayList<>(juices);
            Collections.sort(list, (l1, l2) -> l1.size() - l2.size());
            while (!list.isEmpty()) {
                List<String> first = list.get(0);
                list.remove(0);

                System.out.print(first);
                System.out.print(" -> ");

                List<String> superList;
                while ((superList = findSuperList(first, list)) != null)
                {

                    System.out.print(superList);
                    System.out.print(" -> ");

                    first = superList;
                    list.remove(superList);
                }
                System.out.println(" x");
                count++;
            }
            toFile(String.valueOf(count), "Lab8/juice3.out");
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static List<String> findSuperList(List<String> list, List<List<String>> lists) {
        for (List<String> list1 : lists)
            if (isSuperList(list1, list))
                return list1;
        return null;
    }

    private static boolean containsFrom(List<String> l1, List<String> l2) {
        return l1.stream().filter(l2::contains).count() > 0;
    }

    private static boolean isSuperList(List<String> l1, List<String> l2) {
        return l1.stream().filter(l2::contains).count() == l2.size();
    }

    private static List<String> getComponents(List<LinkedList<String>> juices) {
        List<String> components = new LinkedList<>();
        juices.forEach(juice ->
                juice.stream().filter(fruit ->
                        !components.contains(fruit)).forEach(components::add));
        return components;
    }

    private static void toFile(String string, String filepath) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filepath);
            fileWriter.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<LinkedList<String>> parseFile(String s) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(s));
        LinkedList<LinkedList<String>> list = new LinkedList<>();
        while (scanner.hasNextLine()) {
            list.addLast(new LinkedList<>());
            Arrays.asList(scanner.nextLine()
                    .split(scanner.delimiter().pattern()))
                    .forEach(list.getLast()::add);
        }
        return list;
    }

}
