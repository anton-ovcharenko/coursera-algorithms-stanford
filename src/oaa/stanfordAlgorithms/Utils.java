package oaa.stanfordAlgorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    public static int[] arrayFromFile(String fileName, int size) {
        int[] array = new int[size];

        try (Scanner scanner = new Scanner(new File("./res/" + fileName))) {
            int i = 0;
            while (scanner.hasNextInt()) {
                array[i++] = scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return array;
    }
}
