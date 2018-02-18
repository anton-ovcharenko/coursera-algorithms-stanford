package oaa.stanfordAlgorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Scanner;

public class Utils {
    public static int[] intArrayFromFile(String fileName, int size) {
        int[] array = new int[size];

        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int i = 0;
            while (scanner.hasNextInt()) {
                array[i++] = scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static long[] longArrayFromFile(String fileName, int size) {
        long[] array = new long[size];

        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int i = 0;
            while (scanner.hasNextLong()) {
                array[i++] = scanner.nextLong();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static <T> T[] concatArray(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }
}
