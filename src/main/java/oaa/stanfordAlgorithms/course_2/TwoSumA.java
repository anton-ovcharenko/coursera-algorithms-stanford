package oaa.stanfordAlgorithms.course_2;

import oaa.stanfordAlgorithms.Utils;

import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TwoSumA {
    static int c;

    public static void main(String[] args) {
        int size = 1_000_000;
        int leftBorder = -10_000;
        int rightBorder = 10_000;

        long[] array = Utils.longArrayFromFile("2sum.txt", size);
        HashSet<Long> hashSet = new HashSet<>(10_000, 0.1f);
        for (int i = 0; i < size; i++) {
            hashSet.add(array[i]);
        }

        System.out.println("Start...");
        long ts = System.currentTimeMillis();
        int counter = new ForkJoinPool().invoke(new Count2Sum(hashSet, leftBorder, rightBorder));
        System.out.println("Finish in: " + (System.currentTimeMillis() - ts) + " ms.");

        System.out.println(counter);
    }

    static class Count2Sum extends RecursiveTask<Integer> {
        private HashSet<Long> hashSet;
        private int leftBorder;
        private int rightBorder;

        Count2Sum(HashSet<Long> hashSet, int leftBorder, int rightBorder) {
            this.hashSet = hashSet;
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }

        @Override
        protected Integer compute() {
            int sum = 0;

            if (rightBorder - leftBorder > 100) {
                int mid = (int) (leftBorder + ((rightBorder - leftBorder) / 2.0));
                Count2Sum leftTask = new Count2Sum(hashSet, leftBorder, mid);
                Count2Sum rightTask = new Count2Sum(hashSet, mid + 1, rightBorder);

                leftTask.fork();
                rightTask.fork();

                sum += rightTask.join();
                sum += leftTask.join();
            } else {
                for (int t = leftBorder; t <= rightBorder; t++) {
                    for (Long x : hashSet) {
                        if (x != t - x && hashSet.contains(t - x)) {
                            sum++;
                            break;
                        }
                    }
                }
                System.out.println((++c) * 75 + " done.");
            }
            return sum;
        }
    }
}
