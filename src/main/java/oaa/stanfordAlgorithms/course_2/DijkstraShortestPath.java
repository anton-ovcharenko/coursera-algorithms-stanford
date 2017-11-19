package oaa.stanfordAlgorithms.course_2;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class DijkstraShortestPath {

    public static void main(String[] args) {
        int verticesAmount = 200;
        final String fileName = "dijkstraData.txt";
        Graph graph = graphFromFile(fileName, verticesAmount);

        int[] distances = new int[verticesAmount + 1];
        Arrays.fill(distances, 1000000);

        Set<Integer> X = new HashSet<>();
        distances[1] = 0;
        X.add(1);

        while (X.size() < verticesAmount) {
            int minValue = Integer.MAX_VALUE;
            int minW = -1;
            for (int v : X) {
                for (Graph.Edge w : graph.edgeArray[v]) {
                    if (!X.contains(w.to) && distances[v] + w.length < minValue) {
                        minValue = distances[v] + w.length;
                        minW = w.to;
                    }
                }
            }
            X.add(minW);
            distances[minW] = minValue;
        }

        Arrays.stream(new int[] {7, 37, 59, 82, 99, 115, 133, 165, 188, 197})
              .map(i -> distances[i])
              .forEach(i -> System.out.print(i + ","));
    }

    static Graph graphFromFile(String fileName, int size) {
        try (Scanner rowReader = new Scanner(new File("./src/main/resources/" + fileName))) {
            Graph graph = new Graph(size);
            while (rowReader.hasNextLine()) {
                String[] tuples = rowReader.nextLine().split("\t");
                int from = Integer.parseInt(tuples[0]);
                for (int i = 1; i < tuples.length; i++) {
                    String[] tuple = tuples[i].split(",");
                    int to = Integer.parseInt(tuple[0]);
                    int length = Integer.parseInt(tuple[1]);
                    graph.addEdge(from, to, length);
                }
            }
            return graph;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    static class Graph {
        boolean[] vertices;
        LinkedListEdge[] edgeArray;

        Graph(int verticesAmount) {
            int arraySize = verticesAmount + 1;
            this.vertices = new boolean[arraySize];
            this.edgeArray = initListArray(arraySize);
        }

        private LinkedListEdge[] initListArray(int arraySize) {
            LinkedListEdge[] array = new LinkedListEdge[arraySize];
            for (int i = 1; i < arraySize; i++) {
                array[i] = new LinkedListEdge();
            }
            return array;
        }

        void addEdge(int from, int to, int length) {
            this.edgeArray[from].add(new Edge(to, length));
        }

        static class Edge {
            int to;
            int length;

            Edge(int to, int length) {
                this.length = length;
                this.to = to;
            }
        }

        static class LinkedListEdge extends LinkedList<Edge> {
        }
    }
}
