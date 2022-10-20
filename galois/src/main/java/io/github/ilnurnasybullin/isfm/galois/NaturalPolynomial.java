package io.github.ilnurnasybullin.isfm.galois;

import java.util.Arrays;

public class NaturalPolynomial implements IntPolynomial {

    /**
     * little endian order: c[0] is bound with x0 etc.
      */
    private final int[] coefficients;

    private NaturalPolynomial(int[] coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * @param coefficients - little endian order - {@link #coefficients}
     */
    public static NaturalPolynomial of(int[] coefficients) {
        return new NaturalPolynomial(coefficients);
    }

    public static NaturalPolynomial eye(int maxDegree) {
        int[] coefficients = new int[maxDegree + 1];
        coefficients[maxDegree] = 1;
        return of(coefficients);
    }

    public NaturalPolynomial pow(int degree) {
        return this;
    }

    @Override
    public int[] coefficients() {
        return Arrays.copyOf(coefficients, coefficients.length);
    }

    @Override
    public int getCi(int index) {
        return coefficients[index];
    }
}
