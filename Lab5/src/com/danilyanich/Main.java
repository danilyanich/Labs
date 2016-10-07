package com.danilyanich;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            point1(reader);
            point2(reader);
            point3(reader);
            point4(reader);
            point5(reader);
            point6(reader);
            point7(reader);
            point8(reader);
            point9(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void point1(BufferedReader reader) throws IOException {
        System.out.println("1 Преобразовать текст, обрамленный в звездочки, в курсив. Не трогать текст в двойных звездочках (жирный).");
        String string, output = "";
        Pattern pattern = Pattern.compile("((\\A)|(\\G)|([^*]))(\\*[^*]+\\*)(([^*])|(\\Z))");
        while (!(string = reader.readLine()).equals("next")) {
            Matcher matcher = pattern.matcher(string);
            int lastend = 0;
            while (matcher.find()) {
                int begin = string.indexOf('*',matcher.start());
                int end = string.indexOf('*', begin + 1);
                output += string.substring(lastend, begin);
                output += "<em>";
                output += string.substring(begin+1, end);
                output += "</em>";
                lastend = end +1;
            }
            if(lastend < string.length())
                output += string.substring(lastend, string.length());
        }
        System.out.println(output);
    }

    static void point2(BufferedReader reader) throws IOException {
        System.out.println("2 Определите, что переданная строка является корректным временем вида '12:59', '23:41', '00:12', '00:00', '09:15'. ");
        String string;
        Pattern pattern = Pattern.compile("(([01][0-9])|(2[0-3])):([0-5][0-9])");
        while (!(string = reader.readLine()).equals("next")) {
            System.out.println(string.matches(pattern.pattern())?"Matches":"Nope");
        }
    }

    static void point3(BufferedReader reader) throws IOException {
        System.out.println("3 Написать регулярное выражение определяющее является ли данная строчка GUID с или без скобок. Где GUID это строчка, состоящая из 8, 4, 4, 4, 12 шестнадцатеричных цифр разделенных тире.");
        String string;

        /////////////////////////////////backreferences

        Pattern pattern = Pattern.compile("(([\\dA-Fa-f]{4}){2}-(([\\dA-Fa-f]{4})-){3}([\\dA-Fa-f]{4}){3})|(\\{(([\\dA-Fa-f]{4}){2}-(([\\dA-Fa-f]{4})-){3}([\\dA-Fa-f]{4}){3})\\})");
        while (!(string = reader.readLine()).equals("next")) {
            System.out.println(string.matches(pattern.pattern())?"Matches":"Nope");
        }
    }

    static void point4(BufferedReader reader) throws IOException {
        System.out.println("4 Выбрать IPv4 адреса во всех возможных, представлениях: десятичном, шестнадцатеричном и восьмеричном. С точками и без.");
        String string;
        while (!(string = reader.readLine()).equals("next")) {

        }
    }

    static void point5(BufferedReader reader) throws IOException {
        System.out.println("5 Написать регулярное выражение определяющее является ли данная строчка валидным URL адресом.");
        String string;
        while (!(string = reader.readLine()).equals("next")) {

        }
    }

    static void point6(BufferedReader reader) throws IOException {
        System.out.println("6 Написать регулярное выражение определяющее является ли данная строчка датой в формате dd/mm/yyyy. Начиная с 1600 года до 9999 года.");
        String string;
        while (!(string = reader.readLine()).equals("next")) {

        }
    }

    static void point7(BufferedReader reader) throws IOException {
        System.out.println("7 Написать регулярное выражение определяющее является ли данная строка шестнадцатеричным идентификатором цвета в HTML.");
        String string;
        while (!(string = reader.readLine()).equals("next")) {

        }
    }

    static void point8(BufferedReader reader) throws IOException {
        System.out.println("8 Разбить предложение на токены.");
        String string;
        while (!(string = reader.readLine()).equals("next")) {

        }
    }

    static void point9(BufferedReader reader) throws IOException {
        System.out.println("9 Выбрать правильно сформированное IRC сообщение.");
        String string;
        while (!(string = reader.readLine()).equals("next")) {

        }
    }
}
