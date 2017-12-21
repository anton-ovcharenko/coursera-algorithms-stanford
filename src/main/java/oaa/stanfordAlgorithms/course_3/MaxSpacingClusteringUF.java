package oaa.stanfordAlgorithms.course_3;

import javafx.util.Pair;
import oaa.stanfordAlgorithms.Edge;
import oaa.stanfordAlgorithms.Heap;
import oaa.stanfordAlgorithms.UnionFind;

import java.util.Comparator;
import java.util.List;

import static oaa.stanfordAlgorithms.course_3.MaxSpacingClustering.edgesFromFile;

public class MaxSpacingClusteringUF {

    private static final int clusterAmount = 4;

    public static void main(String[] args) {
        Pair<Integer, List<Edge>> edgesFromFilePair = edgesFromFile("clustering1.txt");
        int nodeNumber = edgesFromFilePair.getKey();
        List<Edge> edgeList = edgesFromFilePair.getValue();

        UnionFind uf = new UnionFind(nodeNumber);

        Heap<Edge> heap = new Heap<>(Edge.class, edgeList.size(), Comparator.comparingInt(e -> e.cost));
        edgeList.forEach(heap::addElement);

        while (uf.count() > clusterAmount) {
            Edge edge = heap.removeRoot();
            if (!uf.isConnected(edge.v1, edge.v2)) {
                uf.union(edge.v1, edge.v2);
            }
        }

        while (heap.getSize() > 0) {
            Edge edge = heap.removeRoot();
            if (!uf.isConnected(edge.v1, edge.v2)) {
                System.out.println(edge.cost);
                break;
            }
        }
    }
}
