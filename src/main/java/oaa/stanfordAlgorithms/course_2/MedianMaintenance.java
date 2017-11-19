package oaa.stanfordAlgorithms.course_2;

import oaa.stanfordAlgorithms.Utils;

public class MedianMaintenance {

    public static void main(String[] args) {
        final int size = 10_000;
        int[] array = Utils.arrayFromFile("Median.txt", size);
        Heap heapLow = new Heap(size, true);
        Heap heapHigh = new Heap(size, false);

        int result = 0;
        for (int element : array) {
            // add element according to heap roots
            if (heapHigh.isEmpty() || element < heapLow.getRoot()) {
                heapLow.addElement(element);
            } else {
                heapHigh.addElement(element);
            }

            // equal heaps size
            if (heapLow.size - heapHigh.size > 1) {
                heapHigh.addElement(heapLow.removeRoot());
            } else if (heapHigh.size - heapLow.size > 1) {
                heapLow.addElement(heapHigh.removeRoot());
            }

            // calculate median
            if (heapLow.size >= heapHigh.size) {
                result += heapLow.getRoot();
            } else if (heapLow.size < heapHigh.size) {
                result += heapHigh.getRoot();
            }
        }
        System.out.println(result % 10000);
    }

    static class Heap {
        int[] array;
        int size;
        boolean decreasing;

        Heap(int maxSize, boolean decreasing) {
            this.array = new int[maxSize + 1];
            this.size = 0;
            this.decreasing = decreasing;
        }

        boolean isEmpty() {
            return size == 0;
        }

        int getRoot() {
            if (size == 0) {
                throw new RuntimeException("Heap is empty");
            }
            return array[0];
        }

        void addElement(int value) {
            array[size] = value;
            size++;
            swimUp(size - 1);
        }

        int removeRoot() {
            int result = array[0];
            array[0] = array[size - 1];
            array[size - 1] = 0;
            size--;
            sinkDown(0);
            return result;
        }

        private int getParentIndex(int index) {
            return (int) Math.ceil(index / 2.0) - 1;
        }

        private int getLeftChildIndex(int index) {
            return index * 2 + 1;
        }

        private int getRightChildIndex(int index) {
            return index * 2 + 2;
        }

        private void swap(int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        private void swimUp(int index) {
            while (index > 0 &&
                ((decreasing && array[getParentIndex(index)] < array[index])
                    || (!decreasing && array[getParentIndex(index)] > array[index]))) {
                swap(getParentIndex(index), index);
                index = getParentIndex(index);
            }
        }

        private void sinkDown(int index) {
            while (getLeftChildIndex(index) < size) {
                int selectedChildIndex = getLeftChildIndex(index);
                int rightChildIndex = getRightChildIndex(index);
                if ((rightChildIndex < size) &&
                    ((decreasing && array[rightChildIndex] > array[selectedChildIndex])
                        || (!decreasing && array[rightChildIndex] < array[selectedChildIndex]))) {
                    selectedChildIndex = rightChildIndex;
                }
                if ((decreasing && array[index] > array[selectedChildIndex]) || (!decreasing && array[index] < array[selectedChildIndex])) {
                    break;
                }
                swap(index, selectedChildIndex);
                index = selectedChildIndex;
            }
        }
    }
}
