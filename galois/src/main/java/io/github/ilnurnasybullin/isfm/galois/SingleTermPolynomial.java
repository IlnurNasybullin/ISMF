package io.github.ilnurnasybullin.isfm.galois;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class SingleTermPolynomial {

    /**
     * little endian order: c[0] is bound with x0 etc.
      */
    private final int[] coefficients;

    public final static SingleTermPolynomial ZERO = new SingleTermPolynomial(new int[]{});

    private SingleTermPolynomial(int[] coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * @param coefficients - little endian order - {@link #coefficients}
     */
    public static SingleTermPolynomial of(int[] coefficients) {
        return byCopy(coefficients);
    }

    private static SingleTermPolynomial byCopy(int[] coefficients) {
        return withoutCopy(Arrays.copyOf(coefficients, coefficients.length));
    }

    private static int[] normalize(int[] array) {
        int largestDegree = array.length;
        while (largestDegree > 0 && array[largestDegree - 1] == 0) {
            largestDegree--;
        }

        if (largestDegree == array.length) {
            return array;
        }

        return Arrays.copyOf(array, largestDegree);
    }

    static SingleTermPolynomial withoutCopy(int[] coefficients) {
        int[] normalized = normalize(coefficients);
        if (normalized.length == 0) {
            return ZERO;
        }

        return new SingleTermPolynomial(normalized);
    }

    public static SingleTermPolynomial bigEndian(int[] reversed) {
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

    public static SingleTermPolynomial eye(int maxDegree) {
        int[] coefficients = new int[maxDegree + 1];
        coefficients[maxDegree] = 1;
        return of(coefficients);
    }

    public SingleTermPolynomial pow(int degree) {
        return this;
    }

    public SingleTermPolynomial add(SingleTermPolynomial x) {
        return polynomialTermOperating(x, Math::addExact);
    }

    private SingleTermPolynomial polynomialTermOperating(SingleTermPolynomial x, IntBinaryOperator operator) {
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
    public SingleTermPolynomial subtract(SingleTermPolynomial x) {
        return polynomialTermOperating(x, Math::subtractExact);
    }

    public SingleTermPolynomial abs() {
        int[] absPolynomial = Arrays.stream(coefficients)
                .map(Math::absExact)
                .toArray();

        return SingleTermPolynomial.withoutCopy(absPolynomial);
    }

    public SingleTermPolynomial multiply(SingleTermPolynomial x) {
        var maxDegree = (coefficients.length - 1) + (x.coefficients.length - 1);

        // if their bath empty - equal to zero
        if (maxDegree < 0) {
            return ZERO;
        }

        int[] productPolymers = new int[maxDegree + 1];

        for (int i = 0; i < x.coefficients.length; i++) {
            for (int j = 0; j < coefficients.length; j++) {
                productPolymers[i + j] += x.coefficients[i] * coefficients[j];
            }
        }

        return withoutCopy(productPolymers);
    }

    public SingleTermPolynomial divide(SingleTermPolynomial x) {
        return this;
    }

    public int[] coefficients() {
        return Arrays.copyOf(coefficients, coefficients.length);
    }

    int[] coefficientsRef() {
        return coefficients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleTermPolynomial that = (SingleTermPolynomial) o;
        return Arrays.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coefficients);
    }

    @Override
    public String toString() {
        return "SingleTermPolynomial{" +
                "coefficients=" + Arrays.toString(coefficients) +
                '}';
    }
}
