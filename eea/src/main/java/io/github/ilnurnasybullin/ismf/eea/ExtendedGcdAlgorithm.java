package io.github.ilnurnasybullin.ismf.eea;

import java.math.BigInteger;
import java.util.ArrayDeque;

public class ExtendedGcdAlgorithm {

    public ExtendedGcd<Integer> calculate(int a, int b) {
        if (a == 0) {
            return new ExtendedGcd<>(Math.absExact(b), new BezoutCoefficients<>(0, sign(b)));
        }

        if (b == 0) {
            return new ExtendedGcd<>(Math.absExact(a), new BezoutCoefficients<>(sign(a), 0));
        }

        var divisions = new ArrayDeque<Integer>();

        var gcd = gcdWithFillingDivisions(a, b, divisions);
        var bezoutsIdentity = calculateBezoutsIdentity(divisions);

        // gcd is always positive
        if (gcd < 0) {
            gcd = Math.absExact(gcd);
            bezoutsIdentity = new BezoutCoefficients<>(
                    Math.negateExact(bezoutsIdentity.x()), Math.negateExact(bezoutsIdentity.y())
            );
        }

        return new ExtendedGcd<>(gcd, bezoutsIdentity);
    }

    private int sign(int b) {
        return b < 0 ? -1 : 1;
    }

    private BezoutCoefficients<Integer> calculateBezoutsIdentity(ArrayDeque<Integer> divisions) {
        var x = 0;
        var y = 1;
        while (!divisions.isEmpty()) {
            var oldX = x;
            x = y;
            y = oldX - y * divisions.removeLast();
        }

        return new BezoutCoefficients<>(x, y);
    }

    private int gcdWithFillingDivisions(int a, int b, ArrayDeque<Integer> divisions) {
        int mod;
        while ((mod = Math.ceilMod(a, b)) != 0) {
            var div = Math.ceilDiv(a, b);
            divisions.add(div);

            a = b;
            b = mod;
        }
        return b;
    }

    public ExtendedGcd<Long> calculate(long a, long b) {
        return null;
    }

    public ExtendedGcd<BigInteger> calculate(BigInteger a, BigInteger b) {
        return null;
    }
}
