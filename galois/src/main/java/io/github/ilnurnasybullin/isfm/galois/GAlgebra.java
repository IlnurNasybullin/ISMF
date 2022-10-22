package io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;

/**
 * Класс алгебры над заданным линейным пространством
 * @author Насыбуллин Ильнур Анасович (гр. 09-275)
 */
public class GAlgebra {

    /**
     * Линейное пространство
     */
    private final GSpace space;

    private GAlgebra(GSpace space) {
        this.space = space;
    }

    public static GAlgebra of(GSpace space) {
        return new GAlgebra(space);
    }

    /**
     * Нормализация по модулю коэффициентов полинома
     */
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

    /**
     * Представление полинома в десятичной системе счисления
     */
    public int toDecimal(IntPolynomialCoefficients polynomial) {
        var normalized = normalization(polynomial).coefficients();

        int accumulator = 0;
        int digit = 1;
        for (int i = 0; i < normalized.leaderDegree() + 1; i++) {
            accumulator += digit * normalized.c(i);
            digit *= space.mod();
        }

        return accumulator;
    }

    /**
     * Суммирование полиномов в текущем линейном пространстве
     */
    public IntPolynomialCoefficients sum(IntPolynomialCoefficients c1, IntPolynomialCoefficients c2) {
        return normalization(c1.sum(c2)).coefficients();
    }

    /**
     * Перемножением полиномов в текущем линейном пространстве
     */
    public IntPolynomialCoefficients multiply(IntPolynomialCoefficients c1, IntPolynomialCoefficients c2) {
        return normalization(c1.multiply(c2)).coefficients();
    }

    /**
     * Нахождение обратного полинома в текущем линейном пространстве
     */
    public IntPolynomialCoefficients reverse(IntPolynomialCoefficients coefficients) {
        var reverse = new PolynomialEGAlgorithm(this)
                .extendedGcd(space.base(), coefficients)
                .bezout()
                .y();

        return normalization(reverse).coefficients();
    }

    /**
     * Деление полиномов в текущем линейном пространстве
     * @implNote реализовано через нахождение обратного элемента для делителя-полинома
     */
    public IntPolynomialCoefficients divide(IntPolynomialCoefficients dividend, IntPolynomialCoefficients divisor) {
//        return normalization(a.multiply(reverse(b))).coefficients();
        return null;
    }

}
