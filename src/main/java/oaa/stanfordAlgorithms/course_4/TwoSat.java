package oaa.stanfordAlgorithms.course_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TwoSat {

    private static Map<Integer, List<Integer>> gM;
    private static Map<Integer, List<Integer>> gtM;
    private static Set<Integer> used;
    private static Map<Integer, Integer> component;
    private static List<Integer> order;

    public static void main(String[] args) {
        ceckSatisfiability("2sat1.txt");
        ceckSatisfiability("2sat2.txt");
        ceckSatisfiability("2sat3.txt");
        ceckSatisfiability("2sat4.txt");
        ceckSatisfiability("2sat5.txt");
        ceckSatisfiability("2sat6.txt");
    }

    private static void ceckSatisfiability(String fileName) {
        InputData data = inputDataFromFile(fileName);

        int n = data.amount;
        gM = new HashMap<>();
        gtM = new HashMap<>();
        Arrays
                .stream(data.clauses)
                .forEach(clause -> {
                    append(gM, not(clause.left), clause.right);
                    append(gM, not(clause.right), clause.left);

                    append(gtM, clause.right, not(clause.left));
                    append(gtM, clause.left, not(clause.right));
                });

        used = new HashSet<>();
        order = new ArrayList<>();
        for (int i = -n; i <= n; ++i) {
            if (gM.containsKey(i) && !used.contains(i)) {
                dfs1(i);
            }
        }

        component = new HashMap<>();
        int j = 0;
        while (order.size() > 0) {
            int v = order.get(order.size() - 1);
            order.remove(order.size() - 1);
            if (!component.containsKey(v)) {
                dfs2(v, j++);
            }
        }

        boolean isSatisfiable = true;
        for (int i = 1; i <= n; ++i) {
            if (component.containsKey(i) && component.containsKey(not(i))
                    && Objects.equals(component.get(i), component.get(not(i)))) {
                isSatisfiable = false;
                break;
            }
        }

        System.out.println(isSatisfiable ? "satisfiable" : "not satisfiable");
    }

    static void append(Map<Integer, List<Integer>> map, int key, int value) {
        List<Integer> list = map.getOrDefault(key, new ArrayList<>());
        list.add(value);
        map.put(key, list);
    }

    static int not(int value) {
        return value * -1;
    }

    static void dfs1(int v) {
        used.add(v);
        for (int i = 0; i < gM.get(v).size(); ++i) {
            int to = gM.get(v).get(i);
            if (gM.containsKey(to) && !used.contains(to)) {
                dfs1(to);
            }
        }
        order.add(v);
    }

    static void dfs2(int v, int cl) {
        component.put(v, cl);
        for (int i = 0; gtM.containsKey(v) && i < gtM.get(v).size(); ++i) {
            int to = gtM.get(v).get(i);
            if (!component.containsKey(to)) {
                dfs2(to, cl);
            }
        }
    }

    static InputData inputDataFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int amount = Integer.parseInt(scanner.nextLine());
            Clause[] clauses = new Clause[amount];
            clauses[0] = null;
            for (int i = 0; i < amount; i++) {
                String s = scanner.nextLine();
                String[] lineArray = s.split(" ");
                int left = Integer.parseInt(lineArray[0]);
                int right = Integer.parseInt(lineArray[1]);
                clauses[i] = new Clause(left, right);
            }
            return new InputData(amount, clauses);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Clause {
        int left;
        int right;

        public Clause(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Clause{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    static class InputData {
        int amount;
        Clause[] clauses;

        public InputData(int amount, Clause[] clauses) {
            this.amount = amount;
            this.clauses = clauses;
        }
    }
}
