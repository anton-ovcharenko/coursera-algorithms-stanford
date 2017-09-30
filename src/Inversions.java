import java.util.Arrays;

public class Inversions {

    public static void main(String[] args) {
        System.out.println(inversions(new int[]{1,3,5,2,4,6}).inversionCount);
        System.out.println(inversions(new int[]{6,5,4,3,2,1}).inversionCount);
    }

    private static InversionResult inversions(int[] array) {
        if (array.length < 2) {
            return new InversionResult(0, array);
        }

        int middleIndex = array.length / 2;
        InversionResult a = inversions(Arrays.copyOfRange(array, 0, middleIndex));
        InversionResult b = inversions(Arrays.copyOfRange(array, middleIndex, array.length));
        InversionResult d = merge(a.sortedArray, b.sortedArray);

        return new InversionResult(a.inversionCount + b.inversionCount + d.inversionCount,
                d.sortedArray);
    }

    private static InversionResult merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int leftIndex = 0, rightIndex = 0, inversionCount = 0;

        for (int k = 0; k < result.length; k++) {
            if (rightIndex >= right.length ||
                    (leftIndex < left.length && left[leftIndex] < right[rightIndex])) {
                result[k] = left[leftIndex++];
            } else {
                result[k] = right[rightIndex++];
                inversionCount += left.length - leftIndex;
            }
        }

        return new InversionResult(inversionCount, result);
    }

    static class InversionResult {
        int inversionCount;
        int[] sortedArray;

        InversionResult(int inversionCount, int[] soretdArray) {
            this.inversionCount = inversionCount;
            this.sortedArray = soretdArray;
        }
    }
}
