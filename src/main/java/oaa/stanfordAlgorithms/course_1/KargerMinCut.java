package oaa.stanfordAlgorithms.course_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KargerMinCut {
    public static void main(String[] args) {
        String fileName = "kargerMinCut.txt";
        int vertexAmount = 200;
        Graph graph = graphFromFile(fileName, vertexAmount);


        long startTime = System.currentTimeMillis();
        int minResult = Integer.MAX_VALUE;
        for (int i = 0; i < 2000; i++) {
            minResult = Math.min(minResult, findMinCut(graph));
        }
        long finishTime = System.currentTimeMillis();
        System.out.println((finishTime - startTime) + "ms: " + minResult);
    }

    static int findMinCut(Graph graph) {
        Random random = new Random();
        int verticesAmount = graph.getVertices().size();

        List<Graph.Edge> edges = new ArrayList<>();
        for (Graph.Edge edge : graph.getEdges()) {
            edges.add(edge.clone());
        }

        while (verticesAmount > 2) {
            //choose rand edge
            int randomIndex = random.nextInt(edges.size());

            Graph.Edge removed = edges.remove(randomIndex);
            List<Graph.Edge> toRemove = new ArrayList<>();
            for (Graph.Edge edge : edges) {
                edge.merge(removed.getFrom(), removed.getTo());
                if (edge.isSelfLoop()) {
                    toRemove.add(edge);
                }
            }
            edges.removeAll(toRemove);

            verticesAmount--;
        }

        return edges.size();
    }

    static class Graph {
        Set<Integer> vertices = new HashSet<>();
        List<Edge> edges = new ArrayList<>();

        void addVertex(Integer label) {
            if (vertices.contains(label)) {
                throw new RuntimeException("Vertex with label " + label + " already exists");
            }
            vertices.add(label);
        }

        void addEdge(int from, int to, boolean isParallelAllowed) {
            if (!vertices.contains(from)) {
                throw new RuntimeException("Vertex 'from' with label " + from + " does not exist");
            }
            if (!vertices.contains(to)) {
                throw new RuntimeException("Vertex 'to' with label " + to + " does not exist");
            }

            boolean isExist = false;
            for (Edge edge : edges) {
                if (edge.from == from && edge.to == to
                        || edge.from == to && edge.to == from) {
                    isExist = true;
                    break;
                }

            }

            if (isParallelAllowed || !isExist) {
                edges.add(new Edge(from, to));
            }
        }

        Set<Integer> getVertices() {
            return vertices;
        }

        List<Edge> getEdges() {
            return edges;
        }

        static class Edge implements Cloneable {
            int from;
            int to;

            Edge(int from, int to) {
                this.from = from;
                this.to = to;
            }

            int getFrom() {
                return from;
            }

            int getTo() {
                return to;
            }

            boolean isSelfLoop() {
                return from == to;
            }

            void merge(int major, int minor) {
                if (from == minor) {
                    from = major;
                }
                if (to == minor) {
                    to = major;
                }
            }

            public Edge clone() {
                return new Edge(from, to);
            }
        }
    }

    static KargerMinCut.Graph graphFromFile(String fileName, int vertexAmount) {
        KargerMinCut.Graph graph = new KargerMinCut.Graph();

        for (int i = 1; i <= vertexAmount; i++) {
            graph.addVertex(i);
        }

        try (Scanner rowReader = new Scanner(new File("./src/main/resources/" + fileName))) {
            while (rowReader.hasNextLine()) {
                Scanner colReader = new Scanner(rowReader.nextLine());
                int colIndex = 0;
                int from = -1;
                while (colReader.hasNextInt()) {
                    if (colIndex > 0) {
                        graph.addEdge(from, colReader.nextInt(), false);
                    } else {
                        from = colReader.nextInt();
                    }
                    colIndex++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return graph;
    }
}
