package oaa.stanfordAlgorithms.course_3;

import oaa.stanfordAlgorithms.Heap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class HuffmanCoding {

    public static void main(String[] args) {
        List<Symbol> symbols = symbolsFromFile("huffman.txt");
        System.out.println(symbols.size());

        Heap<Holder> heap = new Heap<>(Holder.class, symbols.size(), Comparator.comparingLong(h -> h.totalWeight));
        symbols.forEach(symbol -> heap.addElement(new Holder(symbol.weight, symbol)));

        while (heap.getSize() > 1) {
            Holder smallest1 = heap.removeRoot();
            Holder smallest2 = heap.removeRoot();

            smallest1.totalWeight += smallest2.totalWeight;
            smallest1.symbols.forEach(symbol -> symbol.code = "0" + symbol.code);
            smallest2.symbols.forEach(symbol -> symbol.code = "1" + symbol.code);
            smallest1.symbols.addAll(smallest2.symbols);
            heap.addElement(smallest1);
        }

        heap
                .getRoot()
                .symbols
                .stream()
                .sorted(Comparator.comparingLong(h -> h.code.length()))
                .forEach(System.out::println);
    }

    static class Symbol {
        String symbol;
        long weight;
        String code;

        public Symbol(String symbol, long weight, String code) {
            this.symbol = symbol;
            this.weight = weight;
            this.code = code;
        }

        @Override
        public String toString() {
            return "Symbol{" +
                    "symbol='" + symbol + '\'' +
                    ", weight=" + weight +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    static class Holder {
        long totalWeight;
        List<Symbol> symbols = new ArrayList<>();

        public Holder(long totalWeight, Symbol symbol) {
            this.totalWeight = totalWeight;
            this.symbols.add(symbol);
        }
    }

    static List<Symbol> symbolsFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File("./src/main/resources/" + fileName))) {
            int amount = scanner.nextInt();
            List<Symbol> symbols = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                long weight = scanner.nextLong();
                symbols.add(new Symbol("#" + (i + 1), weight, ""));
            }
            return symbols;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
