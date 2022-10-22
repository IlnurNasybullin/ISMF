package io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;

import java.util.ArrayDeque;

/**
 * Класс, представляющий собой реализацию расширенного алгоритма Евклида для полиномов
 * @author Насыбуллин Ильнур Анасович
 */
public class PolynomialEGAlgorithm {

    private final GAlgebra algebra;

    public PolynomialEGAlgorithm(GAlgebra algebra) {
        this.algebra = algebra;
    }

    public ExtendedGcd extendedGcd(IntPolynomialCoefficients a, IntPolynomialCoefficients b) {
        var divs = new ArrayDeque<IntPolynomialCoefficients>();

        while (!IntPolynomialCoefficients.ZERO.equals(b)) {
            b = algebra.normalization(b).coefficients();
            var quotientAndRemainder = a.divideAndRemainder(b);
            var div = quotientAndRemainder.quotient();
            var remainder = quotientAndRemainder.remainder();

            divs.add(div);
            a = b;
            b = remainder;
        }

        var gcd = divs.removeLast();

        var x = IntPolynomialCoefficients.ZERO;
        var y = IntPolynomialCoefficients.ONE;

        while (!divs.isEmpty()) {
            var oldX = x;
            x = y;
            var div = divs.removeLast();
            y = oldX.subtract(div.neg().multiply(y));
        }

        return new ExtendedGcd(gcd, new BezoutCoefficients(x, y));
    }

    /**
     * Класс-обёртка, включающий в себя наибольший общий делитель-полином  (gcd) и коэффициенты Безу
     */
    public record ExtendedGcd(IntPolynomialCoefficients gcd, BezoutCoefficients bezout) {}

    /**
     * Класс-обёртка для коэффициентов Безу
     */
    public record BezoutCoefficients(IntPolynomialCoefficients x, IntPolynomialCoefficients y) {}

}
