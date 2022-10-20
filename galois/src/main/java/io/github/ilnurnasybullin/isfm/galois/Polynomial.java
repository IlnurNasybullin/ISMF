package io.github.ilnurnasybullin.isfm.galois;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class Polynomial {

    /**
     * little endian order: c[0] is bound with x0 etc.
      */
    private final int[] coefficients;

    private Polynomial(int[] coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * @param coefficients - little endian order - {@link #coefficients}
     */
    public static Polynomial of(int[] coefficients) {
        return byCopy(coefficients);
    }

    private static Polynomial byCopy(int[] coefficients) {
        return new Polynomial(Arrays.copyOf(coefficients, coefficients.length));
    }

    private static Polynomial withoutCopy(int[] coefficients) {
        return new Polynomial(coefficients);
    }

    public static Polynomial bigEndian(int[] reversed) {
        int[] coefficients = reversedArray(reversed);
        return withoutCopy(coefficients);
    }

    private static int[] reversedArray(int[] array) {
        int[] reversedArray = new int[array.length];
        for (int i = 0; i < reversedArray.length; i++) {
            reversedArray[i] = array[array.length - 1 - i];
        }

        return reversedArray;
    }

    public static Polynomial eye(int maxDegree) {
        int[] coefficients = new int[maxDegree + 1];
        coefficients[maxDegree] = 1;
        return of(coefficients);
    }

    public Polynomial pow(int degree) {
        return this;
    }

    public Polynomial add(Polynomial x) {
        return polynomialTermOperating(x, Math::addExact);
    }

    private Polynomial polynomialTermOperating(Polynomial x, IntBinaryOperator operator) {
        var resultPolynomialsLength = Math.max(coefficients.length, x.coefficients.length);
        var sumPolynomials = new int[resultPolynomialsLength];

        var summingPolynomialsCount = Math.min(coefficients.length, x.coefficients.length);
        for (int i = 0; i < summingPolynomialsCount; i++) {
            sumPolynomials[i] = operator.applyAsInt(coefficients[i], x.coefficients[i]);
        }

        if (summingPolynomialsCount < resultPolynomialsLength) {
            var copiedPolynomials = resultPolynomialsLength == coefficients.length ?
                    coefficients : x.coefficients;

            var copyLength = resultPolynomialsLength - summingPolynomialsCount;
            System.arraycopy(copiedPolynomials, summingPolynomialsCount, sumPolynomials, summingPolynomialsCount, copyLength);
        }

        return withoutCopy(sumPolynomials);
    }
    public Polynomial subtract(Polynomial x) {
        return polynomialTermOperating(x, Math::subtractExact);
    }

    public Polynomial multiply(Polynomial x) {
        return this;
    }

    public Polynomial divide(Polynomial x) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return Arrays.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coefficients);
    }
}
