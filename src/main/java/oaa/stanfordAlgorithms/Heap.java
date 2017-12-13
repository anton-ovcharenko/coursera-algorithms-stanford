package oaa.stanfordAlgorithms;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Objects;

public class Heap<E> {
    private E[] array;
    private int size;
    private Comparator<E> comparator;

    @SuppressWarnings("unchecked")
    public Heap(Class<E> e, int maxSize, Comparator<E> comparator) {
        Objects.nonNull(comparator);
        this.array = (E[]) Array.newInstance(e, maxSize + 1);
        this.size = 0;
        this.comparator = comparator;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E getRoot() {
        if (size == 0) {
            throw new RuntimeException("Heap is empty");
        }
        return array[0];
    }

    public void addElement(E element) {
        array[size] = element;
        size++;
        swimUp(size - 1);
    }

    public E removeRoot() {
        E result = array[0];
        array[0] = array[size - 1];
        array[size - 1] = null;
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
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void swimUp(int index) {
        while (index > 0 && comparator.compare(array[index], array[getParentIndex(index)]) < 0) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    private void sinkDown(int index) {
        while (getLeftChildIndex(index) < size) {
            int selectedChildIndex = getLeftChildIndex(index);
            int rightChildIndex = getRightChildIndex(index);
            if (rightChildIndex < size && comparator.compare(array[rightChildIndex], array[selectedChildIndex]) < 0) {
                selectedChildIndex = rightChildIndex;
            }
            if (comparator.compare(array[index], array[selectedChildIndex]) < 0) {
                break;
            }
            swap(index, selectedChildIndex);
            index = selectedChildIndex;
        }
    }
}
