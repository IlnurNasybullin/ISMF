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

    private IntPolynomialCoefficients(int[] c) {
        this.c = c;
    }

    public static IntPolynomialCoefficients of(int[] c) {
        return new IntPolynomialCoefficients(c);
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
