package io.github.ilnurnasybullin.isfm.galois;

import java.util.Arrays;

public class GAlgebra {

    private final GSpace gSpace;

    private GAlgebra(GSpace gSpace) {
        this.gSpace = gSpace;
    }

    public static GAlgebra of(GSpace gSpace) {
        return new GAlgebra(gSpace);
    }

    public GPolynomial pow(SingleTermPolynomial polynomial, int degree) {
        if (degree < 0) {
            var powPolynomial = pow(polynomial, Math.absExact(degree));
            return reverse(powPolynomial.polynomial());
        }

        return powMod(polynomial, degree);
    }

    private GPolynomial powMod(SingleTermPolynomial polynomial, int degree) {
        var result = new GPolynomial(gSpace, SingleTermPolynomial.eye(0));
        while (degree != 0) {
            if (isEven(degree)) {
                degree >>>= 1;
                polynomial = normalized(polynomial.multiply(polynomial)).polynomial();
            } else {
                degree--;
                result = normalized(result.polynomial().multiply(polynomial));
            }
        }

        return result;
    }

    private boolean isEven(int degree) {
        return (degree & 1) == 0;
    }

    public GPolynomial sum(SingleTermPolynomial x, SingleTermPolynomial y) {
        var sum = x.add(y);
        return normalized(sum);
    }

    public GPolynomial multiply(SingleTermPolynomial x, SingleTermPolynomial y) {
        var product = x.multiply(y);
        return normalized(product);
    }

    public GPolynomial normalized(SingleTermPolynomial polynomial) {
        int[] normalized = polynomial.coefficients();

        int handleDegree = normalized.length;
        int baseDegree = baseDegree();
        var base = gSpace.base();
        while (handleDegree >= baseDegree) {
            var px = SingleTermPolynomial.withoutCopy(slice(normalized, handleDegree - baseDegree, handleDegree));
            var subtractPx = px.subtract(base).coefficientsRef();

            int i = 0;
            for (; i < subtractPx.length; i++) {
                normalized[handleDegree - baseDegree + i] = subtractPx[i];
            }

            for (; i < baseDegree; i++) {
                normalized[handleDegree - baseDegree + i] = 0;
            }

            normalizeByField(normalized, handleDegree - baseDegree, handleDegree);

            // next handleDegree
            int j = handleDegree - 1;
            while (j >= 0 && normalized[j] == 0) {
                handleDegree--;
                j--;
            }
        }

        normalizeByField(normalized, 0, handleDegree);

        return new GPolynomial(gSpace, SingleTermPolynomial.withoutCopy(normalized));
    }

    private void normalizeByField(int[] array, int from, int to) {
        int mod = gSpace.field().characteristic();

        for (int i = from; i < to; i++) {
            int remains = array[i] % mod;

            if (remains < 0) {
                remains += mod;
            }
            array[i] = remains;
        }
    }

    private static int[] slice(int[] array, int from, int to) {
        return Arrays.copyOfRange(array, from, to);
    }

    private int baseDegree() {
        return gSpace.base().coefficientsRef().length;
    }

    public GPolynomial reverse(SingleTermPolynomial polynomial) {
        return null;
    }

}
