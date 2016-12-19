package com.danilyanich;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class TaskDispenser extends Thread {

    private final static long DELAY = 10000L;

    private ExecutorService executorService;
    private List<Printer> printers;
    private LinkedList<String> tasks;
    private boolean requests[];

    public TaskDispenser(Printer... printers) {
        this.printers = Arrays.asList(printers);
        this.executorService = Executors.newFixedThreadPool(this.printers.size());
        this.tasks = new LinkedList<>();
        this.requests = new boolean[printers.length];
        Predicate<Printer> onIdle = this::requestTask;
        for (Printer p : this.printers) {
            p.onIdle = onIdle;
        }
    }

    public synchronized void addTask(String task) {
        tasks.add(task);
        System.out.println("task:" + task + " added.");
        this.notifyAll();
    }

    private synchronized boolean requestTask(Printer printer) {
        System.out.println("Printer " + printer.name + " requested a task.");
        if (tasks.isEmpty()) {
            requests[printers.indexOf(printer)] = true;
            System.out.println("Queue has no available tasks.");
            return true;
        } else {
            printer.setTask(tasks.getFirst());
            tasks.removeFirst();
            return false;
        }
    }

    @Override
    public void run() {
        for (Printer p : this.printers) {
            executorService.execute(p);
        }
        while (true) {
            if (tasks.isEmpty()) {
                try {
                    synchronized (this) {
                        System.out.println("task queue is empty!");
                        this.wait(DELAY);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tasks.isEmpty()) {
                    System.out.println("Queue empty for 10sec. Dispenser shut.");
                    break;
                }
            } else {
                synchronized (this) {
                    for (int i = 0; i < requests.length && !tasks.isEmpty(); i++) {
                        if (requests[i]) {
                            printers.get(i).setTask(tasks.getFirst());
                            tasks.removeFirst();
                            requests[i] = false;
                        }
                    }
                }
            }
        }
        executorService.shutdown();
    }
}
