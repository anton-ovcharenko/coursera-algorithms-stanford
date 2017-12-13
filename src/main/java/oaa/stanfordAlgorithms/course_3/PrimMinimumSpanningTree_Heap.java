package oaa.stanfordAlgorithms.course_3;

import javafx.util.Pair;
import oaa.stanfordAlgorithms.Heap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class PrimMinimumSpanningTree_Heap {

    public static void main(String[] args) {
        Pair<Integer, Edge[]> edgesFromFilePair = edgesFromFile("edges.txt");
        int nodeNumber = edgesFromFilePair.getKey();
        Edge[] array = edgesFromFilePair.getValue();

        Map<Integer, List<Edge>> adjacencyMap = new HashMap<>();
        for (Edge edge : array) {
            getAdjacencyList(adjacencyMap, edge.v1).add(edge);
            getAdjacencyList(adjacencyMap, edge.v2).add(edge);
        }

        Heap<Edge> heap = new Heap<>(Edge.class, array.length, Comparator.comparingInt(e -> e.cost));
        Set<Integer> X = new HashSet<>();

        int startVertex = array[0].v1;
        X.add(startVertex);
        adjacencyMap
                .get(startVertex)
                .forEach(heap::addElement);

        long T = 0;
        while (X.size() < nodeNumber) {
            Edge edge = heap.removeRoot();
            if (!X.contains(edge.v1) || !X.contains(edge.v2)) {
                int newVertex = X.contains(edge.v1) ? edge.v2 : edge.v1;
                X.add(newVertex);
                T += edge.cost;

                adjacencyMap
                        .get(newVertex)
                        .stream()
                        .filter(e -> !X.contains(e.v1) || !X.contains(e.v2))
                        .forEach(heap::addElement);
            }
        }

        System.out.println(T);
    }

    private static List<Edge> getAdjacencyList(Map<Integer, List<Edge>> adjacencyMap, int vertex) {
        List<Edge> list = adjacencyMap.getOrDefault(vertex, new ArrayList<>());
        adjacencyMap.put(vertex, list);
        return list;
    }

    static Pair<Integer, Edge[]> edgesFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            String[] firstRow = scanner.nextLine().split(" ");
            int nodeNumber = parseInt(firstRow[0]);
            int edgeNumber = parseInt(firstRow[1]);
            Edge[] array = new Edge[edgeNumber];
            int i = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (!row.isEmpty()) {
                    String[] num = row.split(" ");
                    array[i++] = new Edge(parseInt(num[0]), parseInt(num[1]), parseInt(num[2]));
                }
            }
            return new Pair<>(nodeNumber, array);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Edge {
        int v1;
        int v2;
        int cost;

        Edge(int v1, int v2, int cost) {
            this.v1 = v1;
            this.v2 = v2;
            this.cost = cost;
        }
    }
}
