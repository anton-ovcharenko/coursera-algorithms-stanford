public class StrassenMatrixMultiplication {
    public static void main(String[] args) {
        int[][] A = new int[][]{
                {1, 3},
                {13, 56},
                {4, 5}};

        int[][] B = new int[][]{
                {3, 1, 66, 5},
                {5, 6, 34, -7}};

        multiplyMatrices(A, B);
    }

    private static void multiplyMatrices(int[][] a, int[][] b) {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException(
                    "Can not multiply matrices. Column amount of matrix a should be equal to string amount of matrix b.");
        }

        int maxDimention = Math.max(a.length, Math.max(b.length, b[0].length));
        int N = (int) Math.pow(2, Math.ceil(Math.log10(maxDimention) / Math.log10(2)));
        int[][] newA = supplementToSquareMatrix(a, N);
        int[][] newB = supplementToSquareMatrix(b, N);
        int[][] result = cutMatrix(multiplyStrassen(newA, newB), a.length, b[0].length);

        for (int[] aResult : result) {
            for (int anAResult : aResult) {
                System.out.printf("%6d", anAResult);
            }
            System.out.println();
        }
    }

    private static int[][] multiplyStrassen(int[][] left, int[][] right) {
        //left and right are square matrices with equals dimensions
        if (left.length < 2) {
            return new int[][]{{left[0][0] * right[0][0]}};
        }

        SplittedMatrices sl = splitMatrix(left);
        SplittedMatrices sr = splitMatrix(right);
        int[][] p1 = multiplyStrassen(sl.a, subMatrices(sr.b, sr.d));
        int[][] p2 = multiplyStrassen(addMatrices(sl.a, sl.b), sr.d);
        int[][] p3 = multiplyStrassen(addMatrices(sl.c, sl.d), sr.a);
        int[][] p4 = multiplyStrassen(sl.d, subMatrices(sr.c, sr.a));
        int[][] p5 = multiplyStrassen(addMatrices(sl.a, sl.d), addMatrices(sr.a, sr.d));
        int[][] p6 = multiplyStrassen(subMatrices(sl.b, sl.d), addMatrices(sr.c, sr.d));
        int[][] p7 = multiplyStrassen(subMatrices(sl.a, sl.c), addMatrices(sr.a, sr.b));

        return joinMatrices(new SplittedMatrices(
                addMatrices(addMatrices(p5, p6), subMatrices(p4, p2)),
                addMatrices(p1, p2),
                addMatrices(p3, p4),
                subMatrices(addMatrices(p1, p5), addMatrices(p3, p7))
        ));
    }

    private static int[][] addMatrices(int[][] left, int[][] right) {
        int n = left.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = left[i][j] + right[i][j];
            }
        }

        return result;
    }

    private static int[][] subMatrices(int[][] left, int[][] right) {
        int n = left.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = left[i][j] - right[i][j];
            }
        }

        return result;
    }

    private static int[][] supplementToSquareMatrix(int[][] inputMatrix, int n) {
        int[][] result = new int[n][n];
        for (int i = 0; i < inputMatrix.length; i++) {
            System.arraycopy(inputMatrix[i], 0, result[i], 0, inputMatrix[i].length);
        }
        return result;
    }

    private static int[][] cutMatrix(int[][] inputMatrix, int row, int col) {
        int[][] result = new int[row][col];
        for (int i = 0; i < row; i++) {
            System.arraycopy(inputMatrix[i], 0, result[i], 0, col);
        }
        return result;
    }

    private static SplittedMatrices splitMatrix(int[][] inputMatrix) {
        int n = inputMatrix.length / 2;
        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        int[][] c = new int[n][n];
        int[][] d = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(inputMatrix[i], 0, a[i], 0, n);
            System.arraycopy(inputMatrix[i], n, b[i], 0, n);
            System.arraycopy(inputMatrix[i + n], 0, c[i], 0, n);
            System.arraycopy(inputMatrix[i + n], n, d[i], 0, n);
        }

        return new SplittedMatrices(a, b, c, d);
    }

    private static int[][] joinMatrices(SplittedMatrices splittedMatrices) {
        int n = splittedMatrices.a.length;
        int[][] result = new int[n * 2][n * 2];

        for (int i = 0; i < n; i++) {
            System.arraycopy(splittedMatrices.a[i], 0, result[i], 0, n);
            System.arraycopy(splittedMatrices.b[i], 0, result[i], n, n);
            System.arraycopy(splittedMatrices.c[i], 0, result[i + n], 0, n);
            System.arraycopy(splittedMatrices.d[i], 0, result[i + n], n, n);
        }

        return result;
    }

    static class SplittedMatrices {
        int[][] a, b, c, d;

        SplittedMatrices(int[][] a, int[][] b, int[][] c, int[][] d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }
}
