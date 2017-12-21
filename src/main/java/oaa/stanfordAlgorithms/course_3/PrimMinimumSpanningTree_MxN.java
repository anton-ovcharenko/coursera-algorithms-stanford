package oaa.stanfordAlgorithms.course_3;

import javafx.util.Pair;
import oaa.stanfordAlgorithms.Edge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class PrimMinimumSpanningTree_MxN {

    public static void main(String[] args) {
        Pair<Integer, Edge[]> edgesFromFilePair = edgesFromFile("edges.txt");
        int nodeNumber = edgesFromFilePair.getKey();
        Edge[] array = edgesFromFilePair.getValue();

        long T = 0;
        Set<Integer> X = new HashSet<>();
        X.add(array[0].v1);

        while (X.size() < nodeNumber) {
            Edge e = Arrays
                    .stream(array)
                    .filter(edge -> (X.contains(edge.v1) && !X.contains(edge.v2))
                            || (X.contains(edge.v2) && !X.contains(edge.v1)))
                    .sorted(Comparator.comparingInt(edg -> edg.cost))
                    .findFirst()
                    .orElseGet(() -> null);
            Objects.nonNull(e);
            T += e.cost;
            X.add(X.contains(e.v2) ? e.v1 : e.v2);
        }

        System.out.println(T);
    }

    public static Pair<Integer, Edge[]> edgesFromFile(String fileName) {
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
}
