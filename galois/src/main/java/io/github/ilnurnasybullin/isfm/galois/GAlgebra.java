package io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;

public class GAlgebra {

    private final GSpace space;

    private GAlgebra(GSpace space) {
        this.space = space;
    }

    public static GAlgebra of(GSpace space) {
        return new GAlgebra(space);
    }

    public GIntPolynomialCoefficients normalization(IntPolynomialCoefficients polynomial) {
        var remainders = polynomial.divideAndRemainder(space.base()).remainder().c();
        int mod = space.mod();

        for (int i = 0; i < remainders.length; i++) {
            remainders[i] %= mod;
            if (remainders[i] < 0) {
                // for example: -3 % 2 = -1
                remainders[i] += mod;
            }
        }

        return new GIntPolynomialCoefficients(space, IntPolynomialCoefficients.of(remainders));
    }

}
