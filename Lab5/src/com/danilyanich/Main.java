package com.danilyanich;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Scanner scanner;
    private static final String RE = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String BOLD = "\u001B[1m";
    private static final String BG = "\u001B[33m\u001B[1m";

    private static double bonus = 0;

    public static void main(String[] args) throws FileNotFoundException {
        scanner = new Scanner(new File("/home/danilyanich/Projects/Java/Projects/Labs/Lab5/input.txt"));
        point1();
        point2();
        point3();
        point4();
        point5();
        point6();
        point7();
        point8();
        point9();
        System.out.println("kek:" + bonus);
    }

    /**
     * Italic text
     */
    private static void point1() {
        System.out.println(BG + "1 Преобразовать текст, обрамленный в звездочки, в курсив. Не трогать текст в двойных звездочках (жирный)." + RE);
        String string, output = "";
        Pattern pattern = Pattern.compile("(?<!\\*)(\\*([^*]+)\\*)(?!\\*)");
        while (!(string = scanner.nextLine()).equals(">")) {
            Matcher matcher = pattern.matcher(string);
            int end = 0;
            while (matcher.find()) {
                output += string.substring(end, matcher.start());
                output += BOLD + "<em>" + matcher.group(2) + "</em>" + RE;
                end = matcher.end();
            }
            output += string.substring(end, string.length()) + "\n";
        }
        System.out.print(output);

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * Time
     */
    private static void point2() {
        System.out.println(BG + "2 Определите, что переданная строка является корректным временем вида '12:59', '23:41', '00:12', '00:00', '09:15'. " + RE);
        String string;
        Pattern pattern = Pattern.compile("(([01][0-9])|(2[0-3])):([0-5][0-9])");
        while (!(string = scanner.nextLine()).equals(">")) {
            System.out.println((string.matches(pattern.pattern()) ? BLUE + "+" + RE : RED + "-" + RE) + "\t" + string);
        }

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * GUID
     */
    private static void point3() {
        System.out.println(BG + "3 Написать регулярное выражение определяющее является ли данная строчка GUID с или без скобок. Где GUID это строчка, состоящая из 8, 4, 4, 4, 12 шестнадцатеричных цифр разделенных тире." + RE);
        String string;
        Pattern pattern = Pattern.compile("([\\dA-Fa-f]{8}-([\\dA-Fa-f]{4}-){3}[\\dA-Fa-f]{12})|(\\{[\\dA-Fa-f]{8}-([\\dA-Fa-f]{4}-){3}[\\dA-Fa-f]{12}\\})");
        while (!(string = scanner.nextLine()).equals(">")) {
            System.out.println((string.matches(pattern.pattern()) ? BLUE + "+" + RE : RED + "-" + RE) + "\t" + string);
        }

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * IPv4
     */
    private static void point4() {
        System.out.println(BG + "4 Выбрать IPv4 адреса во всех возможных, представлениях: десятичном, шестнадцатеричном и восьмеричном. С точками и без." + RE);
        String string;
        Pattern pattern = Pattern.compile("((((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|(\\d?\\d))\\.){3}((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|(\\d?\\d))|(\\d{1,10}))|((((0x[\\dA-Fa-f]{2})\\.){3}(0x[\\dA-Fa-f]{2}))|(0x[\\dA-Fa-f]{8}))|((((0[0-7]{3})\\.){3}(0[0-7]{3}))|(0[0-7]{11}))");
        while (!(string = scanner.nextLine()).equals(">")) {
            System.out.println((string.matches(pattern.pattern()) ? BLUE + "+" + RE : RED + "-" + RE) + "\t" + string);
        }

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * URL
     */
    private static void point5() {
        System.out.println(BG + "5 Написать регулярное выражение определяющее является ли данная строчка валидным URL адресом." + RE);
        String string;
        Pattern pattern = Pattern.compile("((?<scheme>https?)://)?(?<host>([\\w^_\\d]{2,}\\.)+[\\w^_\\d]{2,})(:(?<port>[\\d]+))?(/(?<path>(\\w+/)*\\w+\\.?\\w*))?(\\?(?<query>(\\w+=\\w+[&;])*\\w+=\\w+))?([#%](?<fragment>\\w+))?");
        while (!(string = scanner.nextLine()).equals(">")) {
            System.out.println((string.matches(pattern.pattern()) ? BLUE + "+" + RE : RED + "-" + RE) + "\t" + string);
            if (string.matches(pattern.pattern())) {
                Matcher matcher = pattern.matcher(string);
                while (matcher.find()) {
                    if (matcher.group("scheme") != null)
                        System.out.println("scheme: " + matcher.group("scheme"));
                    if (matcher.group("host") != null)
                        System.out.println("host: " + matcher.group("host"));
                    if (matcher.group("port") != null)
                        System.out.println("port: " + matcher.group("port"));
                    if (matcher.group("path") != null)
                        System.out.println("path: " + matcher.group("path"));
                    if (matcher.group("query") != null)
                        System.out.println("query: " + matcher.group("query"));
                    if (matcher.group("fragment") != null)
                        System.out.println("fragment: " + matcher.group("fragment"));
                }
            }
        }

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * DATE
     */
    private static void point6() {
        System.out.println(BG + "6 Написать регулярное выражение определяющее является ли данная строчка датой в формате dd/mm/yyyy. Начиная с 1600 года до 9999 года." + RE);
        String string;
        Pattern pattern = Pattern.compile("((((3[01]|[1-2]\\d|0[1-9])\\.(01|03|05|07|08|10|12))|(30|[1-2]\\d|0[1-9])\\.(04|06|09|11)|(2[0-8]|1\\d|0[1-9])\\.02)\\.(1[6-9]|[2-9]\\d)\\d\\d)|(29\\.02\\.((1[6-9]|[2-9]\\d)([2468][48]|[13579][26])|([02468][048]|[13579][26])00))");
        while (!(string = scanner.nextLine()).equals(">")) {
            System.out.println((string.matches(pattern.pattern()) ? BLUE + "+" + RE : RED + "-" + RE) + "\t" + string);
        }

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * HTML Color
     */
    private static void point7() {
        System.out.println(BG + "7 Написать регулярное выражение определяющее является ли данная строка шестнадцатеричным идентификатором цвета в HTML." + RE);
        String string;
        Pattern pattern = Pattern.compile("#[\\dA-Fa-f]{6}");
        while (!(string = scanner.nextLine()).equals(">")) {
            System.out.println((string.matches(pattern.pattern()) ? BLUE + "+" + RE : RED + "-" + RE) + "\t" + string);
        }

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * Tokenizer
     */
    private static List<String> tokenize(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group(3) != null)
                tokens.add(matcher.group(3));
            if (matcher.group(4) != null)
                tokens.add(matcher.group(4));
            if (matcher.group(5) != null)
                tokens.add(matcher.group(5));
        }
        return tokens;
    }

    private static void point8() throws FileNotFoundException {
        System.out.println(BG + "8 Разбить предложение на токены." + RE);
        String string;
        Pattern pattern = Pattern.compile("((?<!\")(\"([^\"]+)\")(?!\"))|(\\w+[-']\\w+)|(\\w+)");
        while (!(string = scanner.nextLine()).equals(">")) {
            if (string.startsWith("file:")) {
                System.out.println("\u001b[36m" + string + RE);
                Scanner fileScanner = new Scanner(new File(string.substring(5, string.length())));
                while (fileScanner.hasNextLine()) {
                    List<String> tokens = tokenize(fileScanner.nextLine(), pattern);
                    if (tokens.size() > 0) {
                        System.out.print("[");
                        for (int i = 0; i < tokens.size() - 1; ++i)
                            System.out.print(tokens.get(i) + ", ");
                        System.out.println(tokens.get(tokens.size() - 1) + "]");
                    }
                }
                System.out.println(RED + "EOF" + RE);
            } else {
                System.out.println(string);
                List<String> tokens = tokenize(string, pattern);
                if (tokens.size() > 0) {
                    System.out.print("[");
                    for (int i = 0; i < tokens.size() - 1; ++i)
                        System.out.print(tokens.get(i) + ", ");
                    System.out.println(tokens.get(tokens.size() - 1) + "]");
                }
            }
        }

        bonus += 1d/pattern.pattern().length();
    }

    /**
     * IRC
     */
    private static void point9() {
        System.out.println(BG + "9 Выбрать правильно сформированное IRC сообщение." + RE);
        String string;
        Pattern pattern = Pattern.compile("");
        while (!(string = scanner.nextLine()).equals(">")) {
            System.out.println((string.matches(pattern.pattern()) ? BLUE + "+" + RE : RED + "-" + RE) + "\t" + string);
        }

        //bonus += 1d/pattern.pattern().length();
    }
}
