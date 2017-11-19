package oaa.stanfordAlgorithms.course_1;

import java.math.BigInteger;
import java.util.Arrays;

public class KaratsubaMultiplication {
    public static void main(String[] args) {
        System.out.println(String.valueOf(multiply(
            "3141592653589793238462643383279502884197169399375105820974944592".toCharArray(),
            "2718281828459045235360287471352662497757247093699959574966967627".toCharArray())));
    }

    static char[] multiply(char[] left, char[] right) {
        if (left.length == 1 && right.length == 1) {
            return mul(left[0], right[0]);
        }

        int halfN = (int) Math.ceil(Integer.max(left.length, right.length) / 2.0);

        char[] newLeft = addZerosRightToSize(left, halfN * 2);
        char[] newRight = addZerosRightToSize(right, halfN * 2);

        char[] a = Arrays.copyOfRange(newLeft, 0, halfN);
        char[] b = Arrays.copyOfRange(newLeft, halfN, newLeft.length);
        char[] c = Arrays.copyOfRange(newRight, 0, halfN);
        char[] d = Arrays.copyOfRange(newRight, halfN, newRight.length);

        char[] ac = multiply(a, c);
        char[] bd = multiply(b, d);
        char[] adpbc = sub(sub(multiply(add(a, b), add(c, d)), ac), bd);

        return add(bd, add(addZerosLeft(ac, halfN * 2), addZerosLeft(adpbc, halfN)));
    }

    private static char[] addZerosRightToSize(char[] input, int size) {
        char[] result = new char[size];
        for (int i = 0; i < size; i++) {
            result[i] = i < size - input.length ? '0' : input[i + input.length - size];
        }
        return result;
    }

    private static char[] addZerosLeft(char[] input, int amount) {
        char[] result = new char[input.length + amount];
        for (int i = 0; i < result.length; i++) {
            result[i] = i < input.length ? input[i] : '0';
        }
        return result;
    }

    private static char[] add(char[] left, char[] right) {
        return toBigInteger(left).add(toBigInteger(right)).toString().toCharArray();
    }

    private static char[] mul(char left, char right) {
        return String.valueOf(((int) left - 48) * (((int) right - 48))).toCharArray();
    }

    private static char[] sub(char[] left, char[] right) {
        return toBigInteger(left).subtract(toBigInteger(right)).toString().toCharArray();
    }

    private static BigInteger toBigInteger(char[] value) {
        return new BigInteger(String.valueOf(value));
    }
}
