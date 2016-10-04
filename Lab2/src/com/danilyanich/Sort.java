package com.danilyanich;

public class Sort {

    private static class Index {
        int left;
        int right;

        int size() {
            return right - left;
        }

        Index(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    static <T extends Comparable<T>> void print(T array[], Index current) {
        for (int i = 0; i < current.left; i++) {
            System.out.print("|\t");
        }
        for (int i = current.left; i <= current.right; i++) {
            System.out.print(array[i] + "\t");
        }
        for (int i = current.right + 1; i < array.length; i++) {
            System.out.print("|\t");
        }
        System.out.println();
    }

    private static <T extends Comparable<T>>
    void mergeSortedParts(T array[], Index left, Index right) {
        int i = left.left;
        int j = right.left;
        while (i <= left.right && j <= right.right) {
            while (array[i].compareTo(array[j]) < 0)
                i++;
            T temp = array[j];
            for (int k = j; k > i; k--)
                array[k] = array[k - 1];
            array[i] = temp;
            j++;
            i++;
            left.right++;
        }
    }

    private static <T extends Comparable<T>>
    void mergeSplit(T array[], Index current, Index previous) {
        if (current.right - current.left >= 1) {
            int middle = (current.right + current.left) / 2;
            mergeSplit(array, new Index(current.left, middle), current);
            mergeSplit(array, new Index(middle + 1, current.right), current);
        }
        if (previous != null && current.right == previous.right) {
            int middle = (previous.right + previous.left) / 2;
            mergeSortedParts(array, new Index(previous.left, middle),
                    new Index(middle + 1, previous.right));
        }
    }

    public static <T extends Comparable<T>> void merge(T array[]) {
        mergeSplit(array, new Index(0, array.length - 1), null);
    }

    private static <T extends Comparable<T>> void swap(T array[], int i1, int i2) {
        T temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    private static <T extends Comparable<T>>
    Index quickSplit(T array[], Index bounds) {
        Index position = new Index(bounds.left, bounds.right);
        T pivot = array[(int) (bounds.left + bounds.size() * Math.random())];
        while (position.size() >= 0) {
            while (array[position.left].compareTo(pivot) < 0 && position.left < bounds.right)
                position.left++;
            while (array[position.right].compareTo(pivot) > 0 && position.right > bounds.left)
                position.right--;
            if (position.size() >= 0) {
                swap(array, position.left, position.right);
                position.left++;
                position.right--;
            }
        }
        return new Index(position.right, position.left);
    }

    private static <T extends Comparable<T>>
    void quickFall(T array[], Index bounds) {
        if (bounds.size() > 0) {
            Index middle = quickSplit(array, bounds);
            quickFall(array, new Index(bounds.left, middle.left));
            quickFall(array, new Index(middle.right, bounds.right));
        }
    }

    public static <T extends Comparable<T>> void quick(T array[]) {
        quickFall(array, new Index(0, array.length - 1));
    }

}
