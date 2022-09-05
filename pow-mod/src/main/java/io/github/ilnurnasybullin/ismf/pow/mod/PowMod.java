package io.github.ilnurnasybullin.ismf.pow.mod;

import io.github.ilnurnasybullin.ismf.ext.gcd.ExtendedGcdAlgorithm;

public class PowMod {

    public long powMod(long a, long b, long n) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("Modulo n = %s is negative!", n));
        }

        if (b < 0) {
            var extendedGcd = new ExtendedGcdAlgorithm().calculate(a, n);
            if (extendedGcd.gcd() != 1L) {
                throw new IllegalArgumentException(
                        String.format("Value a = %d is not invertible for modulo n = %d", a, n)
                );
            }

            return powMod(extendedGcd.bezoutCoefficients().x(), Math.negateExact(b), n);
        }

        var c = 1L;
        while (b != 0) {
            if (isEven(b)){
                b >>>= 1;
                a = Math.multiplyExact(a, a) % n;
            } else {
                b--;
                c = Math.multiplyExact(c, a) % n;
            }
        }

        if (c < 0) {
            return Math.addExact(c, n);
        }

        return c;
    }

    private boolean isEven(long number) {
        return (number & 1) == 0;
    }

}
