package com.jashburn.junit5.dynamictests;

import java.util.Iterator;

class EvenNumbersGenerator {

    private static final int EXCL_LIMIT = 10;

    static Iterator<Integer> generator() {
        Iterator<Integer> iterator = new Iterator<>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < EXCL_LIMIT;
            }

            @Override
            public Integer next() {
                int currentIndex = index;
                index += 2;
                return currentIndex;
            }
        };

        return iterator;
    }
}
