package oaa.stanfordAlgorithms.course_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.MAX_VALUE;

public class BellmanFord {
    static long startTs = System.currentTimeMillis();

    public static void main(String[] args) {
        System.out.println("Starting...");
        processGraph("bf_g1.txt");
        processGraph("bf_g2.txt");
        processGraph("bf_g3.txt");
    }

    private static void processGraph(String fileName) {
        System.out.println(String.format("Start processing %s at %s", fileName, tsFromStarting()));
        InputData inputData = inputDataFromFile(fileName);

        int min = MAX_VALUE;
        for (int i = 1; i <= inputData.vertexAmount; i++) {
            Integer mp = getMinPathBySourceOrNull(inputData, i);
            System.out.println(String.format("%s done at %s ...", i, (tsFromStarting())));
            if (mp == null) {
                System.out.println("NO");
                break;
            }
            if (min > mp) {
                min = mp;
            }
        }
        System.out.println(min);
    }

    private static long tsFromStarting() {
        return System.currentTimeMillis() - startTs;
    }

    private static Integer getMinPathBySourceOrNull(InputData inputData, int startVertex) {
        int[] aCurr = new int[inputData.vertexAmount + 1];
        int[] aPrev = new int[inputData.vertexAmount + 1];
        Arrays.fill(aCurr, MAX_VALUE);
        aCurr[startVertex] = 0;

        boolean hasNegativeCycle = false;
        for (int i = 1; i <= inputData.vertexAmount; i++) {
            System.arraycopy(aCurr, 0, aPrev, 0, aCurr.length);

            boolean isChanged = false;
            for (int v = 1; v <= inputData.vertexAmount; v++) {
                int minWV = MAX_VALUE;
                for (Edge edge : inputData.edges) {
                    if (edge.to == v && aPrev[edge.from] != MAX_VALUE) {
                        int val = aPrev[edge.from] + edge.weight;
                        if (val < minWV) {
                            minWV = val;
                        }
                    }
                }

                aCurr[v] = Math.min(aPrev[v], minWV);
                isChanged = isChanged || aCurr[v] < aPrev[v];
            }

            hasNegativeCycle = (i == inputData.vertexAmount && isChanged);
            if (!isChanged) break;
        }

        return hasNegativeCycle ? null : minValue(aCurr, startVertex);
    }

    private static int minValue(int[] array, int excludeIndex) {
        int min = MAX_VALUE;
        for (int i = 1; i < array.length; i++) {
            if (i != excludeIndex && array[i] < min)
                min = array[i];
        }
        return min;
    }

    static InputData inputDataFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            String[] firstLineArray = scanner.nextLine().split(" ");
            int vertexAmount = Integer.parseInt(firstLineArray[0]);
            int edgeAmount = Integer.parseInt(firstLineArray[1]);

            Edge[] edges = new Edge[edgeAmount];
            for (int i = 0; i < edgeAmount; i++) {
                String[] lineArray = scanner.nextLine().split(" ");
                int from = Integer.parseInt(lineArray[0]);
                int to = Integer.parseInt(lineArray[1]);
                int weight = Integer.parseInt(lineArray[2]);
                edges[i] = new Edge(from, to, weight);
            }
            return new InputData(vertexAmount, edgeAmount, edges);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

    static class InputData {
        int vertexAmount;
        int edgeAmount;
        Edge[] edges;

        InputData(int vertexAmount, int edgeAmount, Edge[] edges) {
            this.vertexAmount = vertexAmount;
            this.edgeAmount = edgeAmount;
            this.edges = edges;
        }
    }
}
