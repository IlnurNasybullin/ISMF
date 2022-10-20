package io.github.ilnurnasybullin.isfm.galois;

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

        return null;
    }

    public GPolynomial reverse(SingleTermPolynomial polynomial) {
        return null;
    }

}
