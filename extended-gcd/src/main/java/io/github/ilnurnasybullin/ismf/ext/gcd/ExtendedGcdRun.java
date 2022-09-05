package io.github.ilnurnasybullin.ismf.ext.gcd;

import java.math.BigInteger;

public class ExtendedGcdRun {
    public static void main(String[] args) {
        var a = new BigInteger(args[0]);
        var b = new BigInteger(args[1]);

        var extendedGcd = new ExtendedGcdAlgorithm().calculate(a, b);
        var gcd = extendedGcd.gcd();
        var x = extendedGcd.bezoutCoefficients().x();
        var y = extendedGcd.bezoutCoefficients().y();

        System.out.printf("Gcd: %d%n", gcd);
        System.out.printf("a * %d + b * %d = %d", x, y, gcd);
    }
}
