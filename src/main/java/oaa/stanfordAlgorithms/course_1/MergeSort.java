package oaa.stanfordAlgorithms.course_1;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sort(new int[] {1, 3, 2, 8, 3, 6, 9, 3, 6, 7, 1, 3, 6})));
    }

    private static int[] sort(int[] array) {
        if (array.length < 2) {
            return array;
        }

        int middleIndex = array.length / 2;
        return merge(
            sort(Arrays.copyOfRange(array, 0, middleIndex)),
            sort(Arrays.copyOfRange(array, middleIndex, array.length)));
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int leftIndex = 0, rightIndex = 0;

        for (int k = 0; k < result.length; k++) {
            if (rightIndex >= right.length ||
                (leftIndex < left.length && left[leftIndex] < right[rightIndex])) {
                result[k] = left[leftIndex++];
            } else {
                result[k] = right[rightIndex++];
            }
        }

        return result;
    }
}
