package com.danilyanich;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    /**
     * ﻿Задание. Родственные слова.
     * <p>
     * Назовем два слова родственными, если одно может быть получено из другого циклическим сдвигом на несколько позиций.
     * <p>
     * Например:  камыш – мышка
     * <p>
     * Во входном файле input.txt находится текст.
     * Разделители: пробел, точка, запятая, точка с запятой.
     * Исходный файл может быть пустым.
     * В файле может быть несколько строк.
     * Регистр не имеет значения.
     * <p>
     * В выходной файл output.txt вывести все группы родственных слов (слова одной группы – через пробел в одну строку, слова следующей группы – с новой строки).
     * Если слово не имеет родственных слов, то оно будет в своей группе одно.
     * <p>
     * При проходе по коллекции хотя бы один раз использовать итератор.
     */

    public static class Sequence implements Comparable {
        String word;

        public Sequence(String word) {
            this.word = word;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Sequence) {
                Sequence seq = (Sequence) o;
                char[] seqChars, thisChars;
                Arrays.sort(seqChars = seq.word.toCharArray());
                Arrays.sort(thisChars = this.word.toCharArray());
                int compare = new String(thisChars).compareTo(new String(seqChars) + " ");
                if (seq.word.length() != this.word.length() || seq.word.length() == 0)
                    return compare;
                int index1 = 0;
                int index2 = seq.word.indexOf(this.word.charAt(0));
                if (index2 == -1)
                    return compare;
                while (index1 < this.word.length()) {
                    Character c1 = Character.toLowerCase(this.word.charAt(index1));
                    Character c2 = Character.toLowerCase(seq.word.charAt(index2));
                    if (!c1.equals(c2))
                        return compare;
                    index1++;
                    index2++;
                    if (index2 >= this.word.length())
                        index2 = 0;
                }
                return 0;
            } else throw new ClassCastException();

        }
    }

    public static void main(String[] args) throws IOException {
        List<String> words = fileToList("input.txt");
        Map<Sequence, List<String>> groups = splitToGroups(words);
        pasteToFile(groups, "output.txt");
    }

    private static void pasteToFile(Map<Sequence, List<String>> groups, String path) throws IOException {
        Set<Sequence> keys = groups.keySet();
        FileWriter fileWriter = new FileWriter(path);
        for (Sequence key : keys) {
            fileWriter.write(groups.get(key).toString() + System.lineSeparator());
        }
        fileWriter.flush();
    }

    private static Map<Sequence, List<String>> splitToGroups(List<String> words) {
        Map<Sequence, List<String>> groups = new TreeMap<>();
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            Sequence sequence = new Sequence(word);
            if (groups.containsKey(sequence))
                groups.get(sequence).add(word);
            else {
                groups.put(sequence, new LinkedList<>());
                groups.get(sequence).add(word);
            }
        }
        return groups;
    }

    private static List<String> fileToList(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path)).useDelimiter("[\\s.,;]+");
        List<String> words = new LinkedList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        return words;
    }
}