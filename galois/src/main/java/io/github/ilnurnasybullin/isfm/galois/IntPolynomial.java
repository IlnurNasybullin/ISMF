package io.github.ilnurnasybullin.isfm.galois;

public interface IntPolynomial {
    int[] coefficients();

    default int getCi(int index) {
        return coefficients()[index];
    }
}
