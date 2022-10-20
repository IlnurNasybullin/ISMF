package io.github.ilnurnasybullin.isfm.galois;

public record GPolynomial(GSpace gSpace, IntPolynomial polynomial) implements IntPolynomial {
    @Override
    public int[] coefficients() {
        return polynomial.coefficients();
    }

    @Override
    public int getCi(int index) {
        return polynomial().getCi(index);
    }
}
