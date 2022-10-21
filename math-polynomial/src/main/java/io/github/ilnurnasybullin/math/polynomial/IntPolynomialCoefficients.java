package io.github.ilnurnasybullin.math.polynomial;

import java.util.Arrays;

/**
 * Immutable class
 */
public class IntPolynomialCoefficients {

    /**
     * little endian order: c[0]x_0 + c[1]x_1 + ... c[i]x_i
     */
    private final int[] c;

    public final static IntPolynomialCoefficients ZERO = new IntPolynomialCoefficients(new int[]{});

    private IntPolynomialCoefficients(int[] c) {
        this.c = c;
    }

    public static IntPolynomialCoefficients of(int[] c) {
        int[] normalized = skipZeroLeaders(c);
        if (normalized.length == 0) {
            return ZERO;
        }

        return new IntPolynomialCoefficients(normalized);
    }

    private static int[] skipZeroLeaders(int[] array) {
        int nonZeroLeader = array.length - 1;
        while (nonZeroLeader >= 0 && array[nonZeroLeader] == 0) {
            nonZeroLeader--;
        }

        if (nonZeroLeader == array.length - 1) {
            return array;
        }

        return Arrays.copyOf(array, nonZeroLeader + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntPolynomialCoefficients that = (IntPolynomialCoefficients) o;
        return Arrays.equals(c, that.c);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(c);
    }
}
