package io.github.ilnurnasybullin.isfm.galois;

import java.util.Arrays;

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
        return new Polynomial(coefficients);
    }

    public static Polynomial eye(int maxDegree) {
        int[] coefficients = new int[maxDegree + 1];
        coefficients[maxDegree] = 1;
        return of(coefficients);
    }

    public Polynomial pow(int degree) {
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
