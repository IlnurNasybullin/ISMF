package io.github.ilnurnasybullin.ismf.ext.gcd;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

public class ExtendedGcdAlgorithm {

    public ExtendedGcd<Integer> calculate(int a, int b) {
        var extendedGcd = calculate(BigInteger.valueOf(a), BigInteger.valueOf(b));
        return new ExtendedGcd<>(extendedGcd.gcd().intValueExact(), new BezoutCoefficients<>(
                extendedGcd.bezoutCoefficients().x().intValueExact(),
                extendedGcd.bezoutCoefficients().y().intValueExact()
        ));
    }

    public ExtendedGcd<Long> calculate(long a, long b) {
        var extendedGcd = calculate(BigInteger.valueOf(a), BigInteger.valueOf(b));
        return new ExtendedGcd<>(extendedGcd.gcd().longValueExact(), new BezoutCoefficients<>(
                extendedGcd.bezoutCoefficients().x().longValueExact(),
                extendedGcd.bezoutCoefficients().y().longValueExact()
        ));
    }

    private BezoutCoefficients<BigInteger> calculateBezoutCoefficients(Deque<BigInteger> divisions) {
        var x = BigInteger.ZERO;
        var y = BigInteger.ONE;

        while (!divisions.isEmpty()) {
            var oldX = x;
            x = y;
            y = oldX.subtract(y.multiply(divisions.removeLast()));
        }

        return new BezoutCoefficients<>(x, y);
    }

    private BigInteger gcdWithFillingDivisions(BigInteger a, BigInteger b, Deque<BigInteger> divisions) {
        BigInteger[] divideAndRemainder = a.divideAndRemainder(b);
        BigInteger mod = divideAndRemainder[1];

        while (!BigInteger.ZERO.equals(mod)) {
            var div = divideAndRemainder[0];
            divisions.add(div);

            a = b;
            b = mod;
            divideAndRemainder = a.divideAndRemainder(b);
            mod = divideAndRemainder[1];
        }

        return b;
    }

    public ExtendedGcd<BigInteger> calculate(BigInteger a, BigInteger b) {
        if (BigInteger.ZERO.equals(a)) {
            return new ExtendedGcd<>(b.abs(), new BezoutCoefficients<>(
                    BigInteger.ZERO, BigInteger.valueOf(b.signum())
            ));
        }

        if (BigInteger.ZERO.equals(b)) {
            return new ExtendedGcd<>(a.abs(), new BezoutCoefficients<>(
                    BigInteger.valueOf(a.signum()), BigInteger.ZERO
            ));
        }

        var divisions = new ArrayDeque<BigInteger>();

        var gcd = gcdWithFillingDivisions(a, b, divisions);
        var bezoutCoefficients = calculateBezoutCoefficients(divisions);

        // gcd is always positive
        if (gcd.compareTo(BigInteger.ZERO) < 0) {
            gcd = gcd.abs();
            bezoutCoefficients = new BezoutCoefficients<>(
                    bezoutCoefficients.x().negate(), bezoutCoefficients.y().negate()
            );
        }

        return new ExtendedGcd<>(gcd, bezoutCoefficients);
    }
}
