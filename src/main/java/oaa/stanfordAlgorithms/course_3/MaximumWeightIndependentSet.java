package oaa.stanfordAlgorithms.course_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class MaximumWeightIndependentSet {

    public static void main(String[] args) {
        long[] weights = weightsFromFile("mwis.txt");
        System.out.println(weights.length);

        long[] A = new long[weights.length + 1];
        int[] B = new int[weights.length + 1];
        A[0] = 0;
        A[1] = weights[0];

        for (int i = 2; i <= weights.length; i++) {
            A[i] = Math.max(A[i - 1], A[i - 2] + weights[i - 1]);
        }
        System.out.println(Arrays.toString(A));

        for (int i = A.length - 1; i >= 1; ) {
            if (A[i - 1] >= (i >= 2 ? A[i - 2] : 0) + weights[i - 1]) {
                i--;
            } else {
                B[i] = 1;
                i -= 2;
            }
        }

        System.out.println(Arrays.toString(B));
        Stream
                .of(1, 2, 3, 4, 17, 117, 517, 997)
                .map(i -> B[i])
                .forEach(System.out::print);
    }

    static long[] weightsFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int amount = scanner.nextInt();
            long[] weights = new long[amount];
            for (int i = 0; i < amount; i++) {
                weights[i] = scanner.nextLong();
            }
            return weights;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
