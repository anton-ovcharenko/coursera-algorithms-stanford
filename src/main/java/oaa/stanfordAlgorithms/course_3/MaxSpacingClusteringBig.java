package oaa.stanfordAlgorithms.course_3;

import javafx.util.Pair;
import oaa.stanfordAlgorithms.UnionFind;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MaxSpacingClusteringBig {

    public static void main(String[] args) {
        Pair<Integer, List<String>> pair = nodesFromFile("clustering_big.txt");
        List<String> nodes = pair.getValue();
        System.out.println("Nodes amount = " + nodes.size());

        Map<String, Integer> distinctNodeMap = new HashMap<>();
        int i = 1;
        for (String node : nodes) {
            if (!distinctNodeMap.containsKey(node)) {
                distinctNodeMap.put(node, i++);
            }
        }
        System.out.println("Distinct nodes amount = " + distinctNodeMap.size());

        UnionFind uf = new UnionFind(distinctNodeMap.size());

        for (String node : distinctNodeMap.keySet()) {
            for (String neighbour : buildNeighbours(node)) {
                if (distinctNodeMap.containsKey(neighbour)) {
                    uf.union(distinctNodeMap.get(node), distinctNodeMap.get(neighbour));
                }
            }
        }

        System.out.println(uf.count());
    }

    private static List<String> buildNeighbours(String a) {
        List<String> neighbours = new ArrayList<>();

        char[] chars;
        String oneAwayString;
        for (int i = 0; i < a.length(); i++) {
            //add one away
            chars = a.toCharArray();
            chars[i] = chars[i] == '1' ? '0' : '1';
            oneAwayString = new String(chars);
            neighbours.add(oneAwayString);

            //add two aways
            for (int j = 0; j < a.length(); j++) {
                if (i != j) {
                    chars = oneAwayString.toCharArray();
                    chars[j] = chars[j] == '1' ? '0' : '1';
                    neighbours.add(new String(chars));
                }
            }
        }
        return neighbours;
    }

    private static Pair<Integer, List<String>> nodesFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            String[] firstLineArray = scanner.nextLine().split(" ");
            int bitNumber = Integer.parseInt(firstLineArray[1]);
            List<String> nodes = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                if (!row.isEmpty()) {
                    String bitsString = row.replaceAll(" ", "");
                    nodes.add(bitsString);
                }
            }
            return new Pair<>(bitNumber, nodes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
