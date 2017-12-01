package oaa.stanfordAlgorithms.course_2;

import oaa.stanfordAlgorithms.Utils;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TwoSumB {
    static int c;
    static long[] array;

    public static void main(String[] args) {
        int size = 1_000_000;
        int leftBorder = -10_000;
        int rightBorder = 10_000;

        array = Arrays
                .stream(Utils.longArrayFromFile("2sum.txt", size))
                .distinct()
                .sorted()
                .toArray();

        System.out.println("Start...");
        long ts = System.currentTimeMillis();
        int counter = new ForkJoinPool().invoke(new Count2Sum(leftBorder, rightBorder));
        System.out.println("Finish in: " + (System.currentTimeMillis() - ts) + " ms.");

        System.out.println(counter);
    }

    static class Count2Sum extends RecursiveTask<Integer> {
        private int leftBorder;
        private int rightBorder;

        Count2Sum(int leftBorder, int rightBorder) {
            this.leftBorder = leftBorder;
            this.rightBorder = rightBorder;
        }

        @Override
        protected Integer compute() {
            int sum = 0;

            if (rightBorder - leftBorder > 100) {
                int mid = (int) (leftBorder + ((rightBorder - leftBorder) / 2.0));
                Count2Sum leftTask = new Count2Sum(leftBorder, mid);
                Count2Sum rightTask = new Count2Sum(mid + 1, rightBorder);

                leftTask.fork();
                rightTask.fork();

                sum += rightTask.join();
                sum += leftTask.join();
            } else {
                for (int t = leftBorder; t <= rightBorder; t++) {
                    int lp = 0;
                    int rp = array.length - 1;
                    while (lp < rp && lp >= 0 && rp <= array.length - 1) {
                        if (array[lp] + array[rp] > t) {
                            rp--;
                        } else if (array[lp] + array[rp] < t) {
                            lp++;
                        } else {
                            sum++;
                            break;
                        }
                    }
                }
                System.out.println("~" + ((++c) * 75) + " done.");
            }
            return sum;
        }
    }
}
