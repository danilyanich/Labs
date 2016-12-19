package com.danilyanich;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Predicate;

public class Printer implements Runnable {

    public static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter("Lab9/out.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final long SHUT_DELAY = 10000L;
    private static final long DELAY = 5000L;
    public String name;
    private String task = null;
    public Predicate<Printer> onIdle;

    public Printer(String name) {
        this.name = name;
    }

    public synchronized boolean setTask(String task) {
        if (this.task == null) {
            System.out.println("Printer " + name + " got task \"" + task + "\".");
            this.task = task;
            this.notifyAll();
            return true;
        }
        System.out.println("Printer " + name + " got task \"" + task + "\", but already has one.");
        return false;
    }

    @Override
    public void run() {
        System.out.println("Printer " + name + " started.");
        while (true) {
            if (task != null) {
                synchronized (this) {
                    System.out.println("Printer " + name + ": " + task);
                    try {
                        fileWriter.write(name);
                        fileWriter.write(":");
                        fileWriter.write(task);
                        fileWriter.write(System.lineSeparator());
                        fileWriter.flush();
                        Thread.sleep((long) ((Math.random() / 2 + 0.5) * DELAY));
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    task = null;
                }
            } else if (task == null) {
                try {
                    if (onIdle.test(this))
                        synchronized (this) {
                            this.wait(SHUT_DELAY);
                        }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task == null)
                    break;
            }
        }
        System.out.println("Printer " + name + " shut.");
    }
}