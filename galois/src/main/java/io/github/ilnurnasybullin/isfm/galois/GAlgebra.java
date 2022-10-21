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

    public GIntPolynomialCoefficients normalization(IntPolynomialCoefficients coefficients) {
        return null;
    }

}
