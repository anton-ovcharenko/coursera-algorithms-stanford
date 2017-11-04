package oaa.stanfordAlgorithms.course_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class KosarajuSCC {
    private static int t, s;
    private static int[] finishingTimeMirroring;
    private static Map<Integer, Integer> leaders;
    private static long startTs;
    private static Graph graph;

    public static void main(String[] args) {
        startTs = System.currentTimeMillis();
        int verticesAmount = 875714;
        final String fileName = "SCC.txt";

        graph = graphFromFile(fileName, verticesAmount, true);
        log("Initialization finished");

        int[] trailArray = IntStream
            .range(0, verticesAmount + 1)
            .toArray();
        initGlobal(verticesAmount);
        DFSloop(trailArray);
        log("Pass 1 finished");

        graph = graphFromFile(fileName, verticesAmount, false);
        trailArray = Arrays.copyOf(finishingTimeMirroring, finishingTimeMirroring.length);
        initGlobal(verticesAmount);
        DFSloop(trailArray);
        log("Pass 2 finished");

        Object[] result = leaders
            .values()
            .stream()
            .sorted(Comparator.reverseOrder())
            .limit(5)
            .toArray();
        System.out.println(Arrays.toString(result));
        log("Finished");
    }

    private static void initGlobal(int size) {
        finishingTimeMirroring = new int[size + 1];
        leaders = new HashMap<>();
    }

    static void DFSloop(int[] trailArray) {
        t = 0;
        for (int i = trailArray.length - 1; i > 0; i--) {
            int v = trailArray[i];
            if (!graph.vertices[v]) {
                s = v;
                DFS(v);
            }
        }
    }

    static void DFS(int i) {
        graph.vertices[i] = true;
        leaders.put(s, leaders.getOrDefault(s, 0) + 1);
        for (int j : graph.edgeArray[i]) {
            if (!graph.vertices[j]) {
                DFS(j);
            }
        }
        t++;
        finishingTimeMirroring[t] = i;
    }

    static Graph graphFromFile(String fileName, int size, boolean isReverted) {
        try (Scanner rowReader = new Scanner(new File("./src/main/resources/" + fileName))) {
            Graph graph = new Graph(size);
            while (rowReader.hasNextLine()) {
                String[] num = rowReader.nextLine().split(" ");
                int from = Integer.parseInt(num[0]);
                int to = Integer.parseInt(num[1]);
                if (isReverted) {
                    graph.addEdge(to, from);
                } else {
                    graph.addEdge(from, to);
                }
            }
            return graph;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Graph {
        boolean[] vertices;
        LinkedListInteger[] edgeArray;

        Graph(int verticesAmount) {
            int arraySize = verticesAmount + 1;
            this.vertices = new boolean[arraySize];
            this.edgeArray = initListArray(arraySize);
        }

        private LinkedListInteger[] initListArray(int arraySize) {
            LinkedListInteger[] array = new LinkedListInteger[arraySize];
            for (int i = 1; i < arraySize; i++) {
                array[i] = new LinkedListInteger();
            }
            return array;
        }

        void addEdge(int from, int to) {
            this.edgeArray[from].add(to);
        }
    }

    static void log(String msg) {
        System.out.println(msg + " (" + (System.currentTimeMillis() - startTs) + " ms." + ")");
    }

    static class LinkedListInteger extends LinkedList<Integer> {
    }
}
