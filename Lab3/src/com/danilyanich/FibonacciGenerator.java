package com.danilyanich;

public class FibonacciGenerator {
    private long x0, x1;

    public FibonacciGenerator(long x0, long x1) {
        this.x0 = x0;
        this.x1 = x1;
    }

    public long next() {
        long next = x1 + x0;
        x0 = x1;
        x1 = next;
        return next;
    }

    public long[] asArray(int size) throws Exception {
        if (size <= 2)
            throw new Exception("no data generation required");
        long[] array = new long[size];
        array[0] = x0;
        array[1] = x1;
//        FibonacciGenerator f = new FibonacciGenerator(x0, x1);
        for (int i = 2; i < size; i++) {
            array[i] = next();
            if (array[i] < 0) throw new Exception("long overflow");
        }
        return array;
    }
}
