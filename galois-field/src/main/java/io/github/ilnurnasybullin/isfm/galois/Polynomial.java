package io.github.ilnurnasybullin.isfm.galois;

public class Polynomial {

    // little endian order: c[0] is bound with x_0 etc.
    private final int[] coefficients;
    private final Field field;

    private Polynomial(int[] coefficients, Field field) {
        this.coefficients = coefficients;
        this.field = field;
    }

    public static Polynomial of(int[] coefficients, Field field) {
        return new Polynomial(coefficients, field);
    }

    public Polynomial pow(int degree) {
        return this;
    }

}
