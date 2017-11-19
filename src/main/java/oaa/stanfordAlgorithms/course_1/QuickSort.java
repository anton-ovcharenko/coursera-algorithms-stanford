package oaa.stanfordAlgorithms.course_1;

import java.util.Arrays;

import oaa.stanfordAlgorithms.Utils;

public class QuickSort {

    public static void main(String[] args) {
        //int[] array = {1, 3, 2, 8, 3, -1, 32, 0, -123, 6, 9, 3, 6, 7, 1, 3, 6};
        //sort(array, 0 , array.length - 1);
        //System.out.println(Arrays.toString(array));

        String fileName = "QuickSort.txt";
        int size = 10_000;
        int[] array = Utils.arrayFromFile(fileName, size);
        System.out.println(sort(array, 0, array.length - 1, PivotStrategy.FIRST));
        array = Utils.arrayFromFile(fileName, size);
        System.out.println(sort(array, 0, array.length - 1, PivotStrategy.FINAL));
        array = Utils.arrayFromFile(fileName, size);
        System.out.println(sort(array, 0, array.length - 1, PivotStrategy.MEDIAN_OF_THREE));
        System.out.println(Arrays.toString(array));
    }

    private static long sort(int[] array, int l, int r, PivotStrategy pivotStrategy) {
        if (r - l < 1) {
            return 0;
        }

        int pIndex = getPivotIndex(array, l, r, pivotStrategy);
        int npIndex = partition(array, l, r, pIndex);

        long c1 = sort(array, l, npIndex - 1, pivotStrategy);
        long c2 = sort(array, npIndex + 1, r, pivotStrategy);
        return r - l + c1 + c2;
    }

    private static int partition(int[] array, int l, int r, int pIndex) {
        swap(array, l, pIndex);
        pIndex = l;

        int i = l + 1;
        for (int j = l + 1; j <= r; j++) {
            if (array[j] < array[pIndex]) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i - 1, pIndex);
        return i - 1;
    }

    private static int getPivotIndex(int[] array, int l, int r, PivotStrategy pivotStrategy) {
        switch (pivotStrategy) {
            case FIRST:
                return l;
            case FINAL:
                return r;
            case MEDIAN_OF_THREE:
                int m = l + (int) Math.ceil((r - l + 1) / 2.0) - 1;
                return (array[l] > array[r])
                    ? ((array[r] > array[m]) ? r : ((array[l] > array[m]) ? m : l))
                    : ((array[l] > array[m]) ? l : ((array[r] > array[m]) ? m : r));
            default:
                throw new RuntimeException("Unknown pivot strategy");
        }
    }

    private static void swap(int[] array, int i1, int i2) {
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    enum PivotStrategy {
        FIRST, FINAL, MEDIAN_OF_THREE
    }
}
