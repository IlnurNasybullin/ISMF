package io.github.ilnurnasybullin.math.polynomial;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Immutable class
 */
public class IntPolynomialCoefficients {

    /**
     * little endian order: c[0]x_0 + c[1]x_1 + ... c[i]x_i
     */
    private final int[] c;

    public final static IntPolynomialCoefficients ZERO = new IntPolynomialCoefficients(new int[]{});

    public final static IntPolynomialCoefficients ONE = new IntPolynomialCoefficients(new int[]{1});

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

    public IntPolynomialCoefficients multiply(IntPolynomialCoefficients multiplier) {
        if (c.length == 0 || multiplier.c.length == 0) {
            return ZERO;
        }

        int productSize = (c.length - 1) + (multiplier.c.length - 1);
        var product = new int[productSize + 1];

        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < multiplier.c.length; j++) {
                product[i + j] += c[i] * multiplier.c[j];
            }
        }

        return new IntPolynomialCoefficients(product);
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

    @Override
    public String toString() {
        var joiner = new StringJoiner(" + ");
        for (int i = c.length - 1; i >= 0; i--) {
            joiner.add(String.format("%dx_%d", c[i], i + 1));
        }

        return joiner.toString();
    }
}
