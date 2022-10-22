package io.github.ilnurnasybullin.math.polynomial;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Класс, представляющий собой полином (точнее, коэффициенты полинома)
 * @author Насыбуллин Ильнур Анасович (гр. 09-275)
 */
public class IntPolynomialCoefficients {

    /**
     * Коэффициенты полинома в порядке little endian (от младших к старшим): c[0]x_0 + c[1]x_1 + ... c[i]x_i
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

    /**
     * Статический метод для создания единичного полинома с заданным старшим разрядом (x^maxDegree)
     */
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
        c[maxDegree] = 1;
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

    /**
     * Старшая степень полинома
     */
    public int leaderDegree() {
        return c.length - 1;
    }

    /**
     * Значение коэффициента полинома перед переменной с заданной степенью
     */
    public int c(int degree) {
        return c[degree];
    }

    public int[] c() {
        return Arrays.copyOf(c, c.length);
    }

    /**
     * Метод, возвращающий полином, у которого все элементы имеют противоположные знаки
     */
    public IntPolynomialCoefficients neg() {
        var negative = Arrays.stream(c)
                .map(Math::negateExact)
                .toArray();

        return withoutCopying(negative);
    }

    /**
     * Метод, возвращающий результат суммы полиномов - текущее + заданное
     */
    public IntPolynomialCoefficients sum(IntPolynomialCoefficients summand) {
        int[] lessSummand;
        int[] sum;

        if (summand.c.length > c.length) {
            sum = Arrays.copyOf(summand.c, summand.c.length);
            lessSummand = c;
        } else {
            sum = c();
            lessSummand = summand.c;
        }

        for (int i = 0; i < lessSummand.length; i++) {
            sum[i] += lessSummand[i];
        }

        return withoutCopying(sum);
    }

    /**
     * Метод, возвращающий результат разности полиномов - текущее - заданное
     */
    public IntPolynomialCoefficients subtract(IntPolynomialCoefficients subtrahend) {
        return sum(subtrahend.neg());
    }

    /**
     * Метод, возвращающий полином с умноженными на заданное число элементами
     */
    public IntPolynomialCoefficients multiply(int scalar) {
        var product = Arrays.stream(c)
                .map(value -> Math.multiplyExact(value, scalar))
                .toArray();

        return withoutCopying(product);
    }

    /**
     * Метод, возвращающий результат разности полиномов - текущее - заданное
     */
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

    /**
     * Метод, возвращающий объект-обёртку с результатами вычисления частного и остатка при делении полиномов - текущее / заданное
     */
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
        var remainder = c();
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

    /**
     * Класс-объёртка, содержащий 2 полинома - частное и остаток
     */
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
