package oaa.stanfordAlgorithms.course_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TSP_DP {
    static final float INF = Float.MAX_VALUE;
    static final long START_TS = System.currentTimeMillis();
    static float[][] DISTANCES, A;
    static int N, N_POW;

    public static void main(String[] args) {
        InputData inputData = inputDataFromFile("tsp.txt");
        initDistances(inputData);

        N = inputData.amount;
        N_POW = (int) Math.pow(2, N);
        A = new float[N][N_POW];
        int i, j;
        for (i = 0; i < N; i++) {
            for (j = 0; j < N_POW; j++) {
                A[i][j] = -1;
            }
        }
        for (i = 0; i < N; i++) {
            A[i][0] = DISTANCES[i][0];
        }

        log("Started...");
        float result = tsp(0, N_POW - 2);
        System.out.println(result);

        log("Finished");
    }

    static float tsp(int start, int set) {
        int masked, mask;
        float temp, result = -1;
        if (A[start][set] != -1) {
            return A[start][set];
        }

        for (int i = 0; i < N; i++) {
            mask = N_POW - 1 - (int) Math.pow(2, i);
            masked = set & mask;
            if (masked != set) {
                temp = DISTANCES[start][i] + tsp(i, masked);
                if (result == -1 || result > temp) {
                    result = temp;
                }
            }
        }
        A[start][set] = result;
        return result;
    }

    static void initDistances(InputData inputData) {
        DISTANCES = new float[inputData.amount][inputData.amount];
        for (int i = 0; i < inputData.amount; i++) {
            for (int j = 0; j < inputData.amount; j++) {
                DISTANCES[i][j] = DISTANCES[j][i] =
                        i == j ? INF : distance(inputData.edges[i], inputData.edges[j]);
            }
        }
        System.out.println("Distances:");
        for (int i = 0; i < inputData.amount; i++) {
            System.out.println(Arrays.toString(DISTANCES[i]));
        }
        System.out.println();
    }

    private static float distance(City c1, City c2) {
        return (float)Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
    }

    private static void log(String msg) {
        System.out.println(String.format("%s [%d ms.]", msg, System.currentTimeMillis() - START_TS));
    }

    static InputData inputDataFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int amount = Integer.parseInt(scanner.nextLine());
            City[] cities = new City[amount];
            cities[0] = null;
            for (int i = 0; i < amount; i++) {
                String s = scanner.nextLine();
                String[] lineArray = s.split(" ");
                float x = Float.parseFloat(lineArray[0]);
                float y = Float.parseFloat(lineArray[1]);
                cities[i] = new City("#" + (i + 1), x, y);
            }
            return new InputData(amount, cities);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class City {
        String name;
        float x;
        float y;

        City(String name, float x, float y) {
            this.name = name;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "City{" +
                    "name='" + name + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    static class InputData {
        int amount;
        City[] edges;

        public InputData(int amount, City[] edges) {
            this.amount = amount;
            this.edges = edges;
        }
    }
}
