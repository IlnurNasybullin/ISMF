package io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;

/**
 * Класс-обёртка, включает в себя линейное пространство и некоторый полином
 * @author Насыбуллин Ильнур Анасович (гр. 09-275)
 */
public record GIntPolynomialCoefficients(GSpace space, IntPolynomialCoefficients coefficients) {
}
