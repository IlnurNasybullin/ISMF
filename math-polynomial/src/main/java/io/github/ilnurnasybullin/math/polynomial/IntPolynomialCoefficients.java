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
        return byCopy(c);
    }

    private static IntPolynomialCoefficients byCopy(int[] array) {
        return normalized(Arrays.copyOf(array, array.length));
    }

    private static IntPolynomialCoefficients normalized(int[] array) {
        int[] normalized = skipZeroLeaders(array);
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

    private static IntPolynomialCoefficients withoutCopying(int[] array) {
        return normalized(array);
    }

    public IntPolynomialCoefficients neg() {
        var negative = Arrays.stream(c)
                .map(Math::negateExact)
                .toArray();

        return withoutCopying(negative);
    }

    public IntPolynomialCoefficients sum(IntPolynomialCoefficients summand) {
        int[] lessSummand;
        int[] sum;

        if (summand.c.length > c.length) {
            sum = Arrays.copyOf(summand.c, summand.c.length);
            lessSummand = c;
        } else {
            sum = Arrays.copyOf(c, c.length);
            lessSummand = summand.c;
        }

        for (int i = 0; i < lessSummand.length; i++) {
            sum[i] += lessSummand[i];
        }

        return withoutCopying(sum);
    }

    public IntPolynomialCoefficients subtract(IntPolynomialCoefficients subtrahend) {
        return sum(subtrahend.neg());
    }

    public IntPolynomialCoefficients multiply(int scalar) {
        var product = Arrays.stream(c)
                .map(value -> Math.multiplyExact(value, scalar))
                .toArray();

        return withoutCopying(product);
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

        return withoutCopying(product);
    }

    public QuotientAndRemainder divideAndRemainder(IntPolynomialCoefficients divisor) {
        if (ZERO.equals(divisor)) {
            throw new ArithmeticException("Division on zero polynomial!");
        }

        if (divisor.c[divisor.c.length - 1] != 1) {
            throw new IllegalArgumentException(
                    String.format("Polynomial %s is can't be divisor in general case, because leader coefficient is not equal 1", divisor)
            );
        }

        return null;
    }

    public record QuotientAndRemainder(IntPolynomialCoefficients quotient, IntPolynomialCoefficients remainder) { }

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
        if (ZERO.equals(this)) {
            return "0";
        }

        var joiner = new StringJoiner(" + ");
        for (int i = c.length - 1; i >= 0; i--) {
            joiner.add(String.format("%dx_%d", c[i], i + 1));
        }

        return joiner.toString();
    }
}
