package io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;

/**
 * Линейное пространство с полем Галуа и некоторым заданным неприводимым полиномом
 * @author Насыбуллин Ильнур Анасович (гр. 09-275)
 */
public class GSpace {

    /**
     * Поле Галуа
     */
    private final GField field;

    /**
     * Неприводимый полином
     */
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
