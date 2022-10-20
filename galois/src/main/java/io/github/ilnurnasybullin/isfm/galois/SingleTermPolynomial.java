package io.github.ilnurnasybullin.isfm.galois;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class SingleTermPolynomial {

    /**
     * little endian order: c[0] is bound with x0 etc.
      */
    private final int[] coefficients;

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
        return new SingleTermPolynomial(Arrays.copyOf(coefficients, coefficients.length));
    }

    private static SingleTermPolynomial withoutCopy(int[] coefficients) {
        return new SingleTermPolynomial(coefficients);
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

    public SingleTermPolynomial multiply(SingleTermPolynomial x) {
        return this;
    }

    public SingleTermPolynomial divide(SingleTermPolynomial x) {
        return this;
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
}
