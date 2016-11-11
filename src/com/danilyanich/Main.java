package com.danilyanich;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main implements Serializable {

    public static void main(String args[]) throws Exception {
        List<Double> c = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            c.add(-i * 4 * Math.cos(Math.PI * 2 * i));
        }
        Collections.sort(c);
        System.out.println(c.toString());
    }
}