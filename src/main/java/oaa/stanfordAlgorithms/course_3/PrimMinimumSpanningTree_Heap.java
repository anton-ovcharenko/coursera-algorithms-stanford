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
            getAdjacencyList(adjacencyMap, edge.from).add(edge);
            getAdjacencyList(adjacencyMap, edge.to).add(edge);
        }

        Heap<Edge> heap = new Heap<>(Edge.class, array.length, Comparator.comparingInt(e -> e.weight));
        Set<Integer> X = new HashSet<>();

        int startVertex = array[0].from;
        X.add(startVertex);
        adjacencyMap
                .get(startVertex)
                .forEach(heap::addElement);

        long T = 0;
        while (X.size() < nodeNumber) {
            Edge edge = heap.removeRoot();
            if (!X.contains(edge.from) || !X.contains(edge.to)) {
                int newVertex = X.contains(edge.from) ? edge.to : edge.from;
                X.add(newVertex);
                T += edge.weight;

                adjacencyMap
                        .get(newVertex)
                        .stream()
                        .filter(e -> !X.contains(e.from) || !X.contains(e.to))
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
