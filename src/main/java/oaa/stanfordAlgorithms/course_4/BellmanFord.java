package oaa.stanfordAlgorithms.course_4;

import oaa.stanfordAlgorithms.Edge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.MAX_VALUE;

public class BellmanFord {

    public static void main(String[] args) {
        processGraph("bf_g1.txt");
        processGraph("bf_g2.txt");
        processGraph("bf_g3.txt");
        //processGraph("bf_large.txt");
    }

    private static void processGraph(String fileName) {
        long startTs = System.currentTimeMillis();
        InputData inputData = inputDataFromFile(fileName);

        Integer min = MAX_VALUE;
        for (int i = 1; i <= inputData.vertexAmount; i++) {
            Integer mp = getMinPathBySourceOrNull(inputData, i);
            System.out.println(String.format("%s done at %s ...", i, (System.currentTimeMillis() - startTs)));
            if (mp == null) {
                min = null;
                break;
            }
            if (min > mp) {
                min = mp;
            }
        }
        System.out.println(fileName + " min: " + min + " duration: " + (System.currentTimeMillis() - startTs));
    }

    private static Integer getMinPathBySourceOrNull(InputData inputData, int startVertex) {
        int[] A = new int[inputData.vertexAmount + 1];
        Arrays.fill(A, MAX_VALUE);
        A[startVertex] = 0;

        boolean hasNegativeCycle = false;
        for (int i = 1; i <= inputData.vertexAmount; i++) {
            boolean isChanged = false;
            for (Edge edge : inputData.edges) {
                if (A[edge.from] < MAX_VALUE) {
                    if (A[edge.to] > A[edge.from] + edge.weight) {
                        A[edge.to] = A[edge.from] + edge.weight;
                        isChanged = true;
                    }
                }
            }
            hasNegativeCycle = (i == inputData.vertexAmount && isChanged);
            if (!isChanged) break;
        }

        return hasNegativeCycle ? null : minValue(A, startVertex);
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
