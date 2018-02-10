package oaa.stanfordAlgorithms.course_4;

import oaa.stanfordAlgorithms.course_4.BellmanFord.Edge;

import static java.lang.Integer.MAX_VALUE;
import static oaa.stanfordAlgorithms.course_4.BellmanFord.inputDataFromFile;

public class FloydWarshall {

    public static void main(String[] args) {
        findShortestShortestPath("bf_g1.txt");
        findShortestShortestPath("bf_g2.txt");
        findShortestShortestPath("bf_g3.txt");
        //findShortestShortestPath("bf_large.txt");
    }

    private static void findShortestShortestPath(String fileName) {
        long startTs = System.currentTimeMillis();
        BellmanFord.InputData inputData = inputDataFromFile(fileName);

        int dimension = inputData.vertexAmount + 1;
        int[][] A = new int[dimension][dimension];
        for (int i = 1; i <= inputData.vertexAmount; i++) {
            for (int j = 1; j <= inputData.vertexAmount; j++) {
                A[i][j] = i == j ? 0 : MAX_VALUE;
            }
        }
        for (Edge edge : inputData.edges) {
            A[edge.from][edge.to] = edge.weight;
        }

        for (int k = 1; k <= inputData.vertexAmount; k++) {
            for (int i = 1; i <= inputData.vertexAmount; i++) {
                for (int j = 1; j <= inputData.vertexAmount; j++) {
                    if (A[i][k] < MAX_VALUE && A[k][j] < MAX_VALUE) {
                        A[i][j] = Math.min(A[i][j], A[i][k] + A[k][j]);
                    }
                }
            }
        }

        Integer min = MAX_VALUE;
        result_analise:
        for (int i = 1; i <= inputData.vertexAmount; i++) {
            for (int j = 1; j <= inputData.vertexAmount; j++) {
                if (i == j && A[i][j] < 0) {
                    min = null;
                    break result_analise;
                }
                if (i != j && A[i][j] < min) {
                    min = A[i][j];
                }
            }
        }
        System.out.println(fileName + " min: " + min + " duration: " + (System.currentTimeMillis() - startTs));
    }
}
