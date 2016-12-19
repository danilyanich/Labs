package com.danilyanich;

public class Main {
    private static final long MAX_TIMING = 5000L;

    public static void main(String[] args) {
        TaskDispenser taskDispenser = new TaskDispenser(
                new Printer("Петя"),
                new Printer("Алёша")
        );
        taskDispenser.start();
        for (int i = 0; i < 10; i++) {
            taskDispenser.addTask(String.valueOf(i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            taskDispenser.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
