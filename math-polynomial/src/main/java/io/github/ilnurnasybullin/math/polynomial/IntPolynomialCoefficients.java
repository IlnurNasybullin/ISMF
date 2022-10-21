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

    public static IntPolynomialCoefficients build(int... c) {
        return byCopy(c);
    }

    public static IntPolynomialCoefficients of(int[] c) {
        return byCopy(c);
    }

    public static IntPolynomialCoefficients eye(int maxDegree) {
        if (maxDegree < 0) {
            throw new IllegalArgumentException(
                    String.format("Polynomials coefficients can't be created with maxDegree = %d", maxDegree)
            );
        }

        if (maxDegree == 0) {
            return ONE;
        }

        var c = new int[maxDegree + 1];
        return withoutCopying(c);
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

    public int leaderDegree() {
        return c.length - 1;
    }

    public int c(int degree) {
        return c[degree];
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

        int productSize = leaderDegree() + multiplier.leaderDegree();
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

        if (divisor.c[divisor.leaderDegree()] != 1) {
            throw new IllegalArgumentException(
                    String.format("Polynomial %s is can't be divisor in general case, because leader coefficient is not equal 1", divisor)
            );
        }

        if (divisor.c.length > c.length) {
            return new QuotientAndRemainder(ZERO, this);
        }

        int cursor = c.length - 1;
        int divisorSize = divisor.c.length;
        var remainder = Arrays.copyOf(c, c.length);
        var quotient = new int[leaderDegree() - divisor.leaderDegree() + 1];
        int quotientIndex = quotient.length - 1;
        while (cursor + 1 >= divisorSize) {
            int k = remainder[cursor];
            quotient[quotientIndex] = k;
            for (int i = 0; i < divisorSize; i++) {
                remainder[cursor - i] -= k * divisor.c[divisorSize - 1 - i];
            }
            cursor--;
            quotientIndex--;
        }

        return new QuotientAndRemainder(withoutCopying(quotient), withoutCopying(remainder));
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
            joiner.add(String.format("%dx_%d", c[i], i));
        }

        return joiner.toString();
    }
}
