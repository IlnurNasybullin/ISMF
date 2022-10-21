package io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;

public class GSpace {

    private final GField field;
    private final IntPolynomialCoefficients base;

    private GSpace(GField field, IntPolynomialCoefficients base) {
        this.field = field;
        this.base = base;
    }

    public static GSpace of(GField gField, IntPolynomialCoefficients base) {
        return new GSpace(gField, base);
    }

}
