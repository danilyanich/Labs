package com.danilyanich;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Лабораторная работа 6. Работа с файловыми и потоками.
 * <p>
 * Создать иерархию файлов и директорий.<br>
 * Входные данные.<br>
 * root.txt<br>
 * Путь к рутовой директории<br>
 * Имя директории<br>
 * Пример файла:<br>
 * C:\\temp\\last_name <br>
 * Root_dir <br>
 * <p>
 * 2. files.txt <br>
 * Список файлов и директорий который необходимо создать от корневой папки <br>
 * Пример файла: <br>
 * /temp.txt <br>
 * /layer/temp_1.txt <br>
 * /layer_0 <br>
 * /temp-dir/test/file.txt <br>
 * /layer/filecsv.txt <br>
 * /layer/temp.txt/test_file.txt <br>
 * <p>
 * Записать во все созданные файлы: <br>
 * 1. Путь от диска <br>
 * 2. Уровень вложенности от корневой папки из файла root.txf <br>
 * 3. Записать строку из стихотворения любимого автора в соответствии с уровнем вложенности.
 */

public class Main {

    private final static String poem[] = {
            "What you got going on?",
            "High notes, eyes closed, holding on",
            "And I don't want another day to break",
            "Take our, steal our night away",
            "",
            "Warm shadow",
            "Warm shadow",
            "Won't you cast yourself on me?",
            "",
            "What you got in store for me?",
            "Keep those, eyes closed next to me",
            "And I don't want another day to break",
            "Take our, steal our night away",
            "",
            "Warm shadow",
            "Warm shadow",
            "Won't you cast yourself on me?",
            "",
            "Won't you stop breaking in?",
            "Red sky, red light, awakening",
            "And I don't want another day to break",
            "Take our, steal our night away"
    };

    private static String ROOT = "Lab6" + File.separator;

    public static void main(String[] args) throws Exception {
        File file = new File(ROOT + "root.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String dir = reader.readLine() + File.separator + reader.readLine();
        File root = new File(dir);
        root.mkdirs();
        check(root.isDirectory());

        file = new File(ROOT + "files.txt");
        final Object[] capture = {root, dir, nestingLevel(root)};
        new BufferedReader(new FileReader(file)).lines().forEach(line -> {
            File path = new File("" + capture[1] + File.separator + line);
            try {
                path.getParentFile().mkdirs();
                path.createNewFile();
                check(path.isFile());
                Integer level = nestingLevel(path) - (Integer) capture[2];
                FileWriter writer = new FileWriter(path);
                String endl = System.lineSeparator();
                writer.write(path.getAbsolutePath() + endl);
                writer.write(level + endl);
                writer.write(poem[level - 1] + endl);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static int nestingLevel(File file) {
        if (file != null) return 1 + nestingLevel(file.getParentFile());
        else return 0;
    }

    private static void check(boolean value) throws Exception {
        if (!value) throw new Exception("check failed.");
    }

}
