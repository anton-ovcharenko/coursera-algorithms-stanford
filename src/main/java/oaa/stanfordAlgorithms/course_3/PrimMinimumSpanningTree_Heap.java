package oaa.stanfordAlgorithms.course_3;

import javafx.util.Pair;
import oaa.stanfordAlgorithms.Edge;
import oaa.stanfordAlgorithms.Heap;

import java.util.*;

import static oaa.stanfordAlgorithms.course_3.PrimMinimumSpanningTree_MxN.edgesFromFile;

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
}
