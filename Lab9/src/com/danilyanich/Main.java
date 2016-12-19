package com.danilyanich;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        TaskDispenser taskDispenser = new TaskDispenser(
                new Printer("Петя"),
                new Printer("Алёша"),
                new Printer("Катя")
        );
        taskDispenser.start();
        try {
            long lastRead = readInitial(taskDispenser, "Lab9/in.txt");

            Path monitoredFile = Paths.get("Lab9");
            WatchService watcher = FileSystems.getDefault().newWatchService();
            monitoredFile.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

            while (taskDispenser.isAlive()) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW)
                        continue;
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        @SuppressWarnings("unchecked")
                        Path path = ((WatchEvent<Path>) event).context();
                        if (path.endsWith("in.txt")) try {
                            RandomAccessFile file = new RandomAccessFile(
                                    monitoredFile.toString() + File.separator + path.toString(),
                                    "r");
                            if (lastRead > file.length())
                                lastRead = file.length();
                            file.seek(lastRead);
                            String line;
                            while ((line = file.readLine()) != null)
                                if (!line.equals(""))
                                    taskDispenser.addTask(line);
                            lastRead = file.length();
                            file.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!key.reset())
                    break;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        try {
            taskDispenser.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static long readInitial(TaskDispenser taskDispenser, String path) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(new File(path), "r");
        String line;
        while ((line = raf.readLine()) != null)
            taskDispenser.addTask(line);
        long filePointer = raf.getFilePointer();
        raf.close();
        return filePointer;
    }
}
