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
        int leaderDegree = base.leaderDegree();
        if (leaderDegree < 2) {
            throw new IllegalArgumentException(
                    String.format("Galois space can't be created with polynomial base %s that leader degree equal %d", base, leaderDegree)
            );
        }

        int leaderDegreeValue = base.c(leaderDegree);
        if (leaderDegreeValue != 1) {
            throw new IllegalArgumentException(
                    String.format("Galois space can't be created with polynomial base %s that leader degree coefficient value %d not equal to 1", base, leaderDegreeValue)
            );
        }

        return new GSpace(gField, base);
    }

    public int mod() {
        return field.characteristic();
    }

    public IntPolynomialCoefficients base() {
        return base;
    }

}
