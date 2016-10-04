package com.danilyanich;

public class PascalTriangle {
    private long[] previousArray;
    public PascalTriangle(){
        previousArray = new long[1];
        previousArray[0] = 1;
    }

    public long[] next() throws Exception {
        long[] array = new long[previousArray.length + 1];
        array[0] = 1;
        array[array.length - 1] = 1;
        for (int i = 0; i < previousArray.length - 1; i++) {
            array[i + 1] = previousArray[i] + previousArray[i + 1];
            if (array[i + 1] < 0) throw new Exception("long overflow");
        }
        long[] link = previousArray;
        previousArray = array;
        return link;
    }
}
