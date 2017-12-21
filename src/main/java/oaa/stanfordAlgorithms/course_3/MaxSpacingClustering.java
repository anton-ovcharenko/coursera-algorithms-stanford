package oaa.stanfordAlgorithms.course_3;

import javafx.util.Pair;
import oaa.stanfordAlgorithms.Edge;
import oaa.stanfordAlgorithms.Heap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class MaxSpacingClustering {

    private static final int clusterAmount = 4;

    public static void main(String[] args) {
        Pair<Integer, List<Edge>> edgesFromFilePair = edgesFromFile("clustering1.txt");
        int nodeNumber = edgesFromFilePair.getKey();
        List<Edge> edgeList = edgesFromFilePair.getValue();

        List<Set<Integer>> setList = IntStream
                .range(1, nodeNumber + 1)
                .mapToObj(i -> new HashSet<>(Collections.singletonList(i)))
                .collect(Collectors.toList());

        Heap<Edge> heap = new Heap<>(Edge.class, edgeList.size(), Comparator.comparingInt(e -> e.cost));
        edgeList.forEach(heap::addElement);

        while (setList.size() > clusterAmount) {
            Edge edge = heap.removeRoot();

            Set<Integer> setV1 = getContainerOf(setList, edge.v1);
            Set<Integer> setV2 = getContainerOf(setList, edge.v2);

            if (!setV1.equals(setV2)) {
                setV1.addAll(setV2);
                setList.remove(setV2);
            }
        }

        while (heap.getSize() > 0) {
            Edge edge = heap.removeRoot();

            Set<Integer> setV1 = getContainerOf(setList, edge.v1);
            Set<Integer> setV2 = getContainerOf(setList, edge.v2);

            if (!setV1.equals(setV2)) {
                System.out.println(edge.cost);
                break;
            }
        }
    }

    private static Set<Integer> getContainerOf(List<Set<Integer>> setList, Integer node) {
        return setList
                .stream()
                .filter(set -> set.contains(node))
                .findFirst().orElseThrow(() -> new RuntimeException("Can not found container for: " + node));
    }

    static Pair<Integer, List<Edge>> edgesFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int nodeNumber = scanner.nextInt();
            List<Edge> edgeList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (!row.isEmpty()) {
                    String[] num = row.split(" ");
                    edgeList.add(new Edge(parseInt(num[0]), parseInt(num[1]), parseInt(num[2])));
                }
            }
            return new Pair<>(nodeNumber, edgeList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
