package oaa.stanfordAlgorithms.course_2;

import oaa.stanfordAlgorithms.Heap;
import oaa.stanfordAlgorithms.Utils;

import java.util.Comparator;

public class MedianMaintenance {

    public static void main(String[] args) {
        final int size = 10_000;
        int[] array = Utils.intArrayFromFile("Median.txt", size);
        Heap<Integer> heapLow = new Heap<>(Integer.class, size, Comparator.<Integer>naturalOrder().reversed());
        Heap<Integer> heapHigh = new Heap<>(Integer.class, size, Comparator.naturalOrder());

        int result = 0;
        for (int element : array) {
            // add element according to heap roots
            if (heapHigh.isEmpty() || element < heapLow.getRoot()) {
                heapLow.addElement(element);
            } else {
                heapHigh.addElement(element);
            }

            // equal heaps size
            if (heapLow.getSize() - heapHigh.getSize() > 1) {
                heapHigh.addElement(heapLow.removeRoot());
            } else if (heapHigh.getSize() - heapLow.getSize() > 1) {
                heapLow.addElement(heapHigh.removeRoot());
            }

            // calculate median
            if (heapLow.getSize() >= heapHigh.getSize()) {
                result += heapLow.getRoot();
            } else if (heapLow.getSize() < heapHigh.getSize()) {
                result += heapHigh.getRoot();
            }
        }
        System.out.println(result % 10000);
    }
}
