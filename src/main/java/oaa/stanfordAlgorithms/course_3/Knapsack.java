package oaa.stanfordAlgorithms.course_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Knapsack {

    public static void main(String[] args) {
        InputData inputData = inputDataFromFile("knapsack1.txt");
        System.out.println(inputData.capacity);
        System.out.println(Arrays.toString(inputData.items));

        long[][] A = new long[inputData.items.length + 1][inputData.capacity + 1];
        for (int i = 1; i < A.length; i++) {
            Item item = inputData.items[i - 1];
            for (int x = 0; x < A[i].length; x++) {
                A[i][x] = Math.max(
                        getA(A, i - 1, x),
                        item.weight > x ? -1 : getA(A, i - 1, x - item.weight) + item.value);
            }
        }

        System.out.println(A[inputData.items.length][inputData.capacity]);
    }

    static long getA(long[][] a, int i, int x) {
        return i < 0 || x < 0 ? 0 : a[i][x];
    }

    static InputData inputDataFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            String[] firstLineArray = scanner.nextLine().split(" ");
            int capacity = Integer.parseInt(firstLineArray[0]);
            int itemsAmount = Integer.parseInt(firstLineArray[1]);

            Item[] items = new Item[itemsAmount];
            for (int i = 0; i < itemsAmount; i++) {
                String[] lineArray = scanner.nextLine().split(" ");
                int value = Integer.parseInt(lineArray[0]);
                int weight = Integer.parseInt(lineArray[1]);
                items[i] = new Item(value, weight);
            }
            return new InputData(capacity, items);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Item {
        int value;
        int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "value=" + value +
                    ", weight=" + weight +
                    '}';
        }
    }

    static class InputData {
        int capacity;
        Item[] items;

        public InputData(int capacity, Item[] items) {
            this.capacity = capacity;
            this.items = items;
        }
    }
}
