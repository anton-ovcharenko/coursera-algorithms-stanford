package oaa.stanfordAlgorithms.course_3;

import java.util.Arrays;

import static oaa.stanfordAlgorithms.course_3.Knapsack.inputDataFromFile;

public class KnapsackBig {

    public static void main(String[] args) {
        Knapsack.InputData inputData = inputDataFromFile("knapsack_big.txt");
        System.out.println(inputData.capacity);
        System.out.println(Arrays.toString(inputData.items));

        long[] aPrev = new long[inputData.capacity + 1];
        long[] aCurr = new long[inputData.capacity + 1];
        for (int i = 1; i <= inputData.items.length; i++) {
            Knapsack.Item item = inputData.items[i - 1];
            for (int x = 0; x < aCurr.length; x++) {
                aCurr[x] = Math.max(
                        getA(aPrev, x),
                        item.weight > x ? -1 : getA(aPrev, x - item.weight) + item.value);
            }
            aPrev = Arrays.copyOf(aCurr, aCurr.length);
        }

        System.out.println(aCurr[inputData.capacity]);
    }

    static long getA(long[] a, int x) {
        return x < 0 ? 0 : a[x];
    }
}
