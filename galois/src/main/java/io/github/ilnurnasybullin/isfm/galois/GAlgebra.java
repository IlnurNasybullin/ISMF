package io.github.ilnurnasybullin.isfm.galois;

public class GAlgebra {

    private final GSpace gSpace;

    private GAlgebra(GSpace gSpace) {
        this.gSpace = gSpace;
    }

    public static GAlgebra of(GSpace gSpace) {
        return new GAlgebra(gSpace);
    }

    public GPolynomial mod(IntPolynomial polynomial, int degree) {
        return null;
    }

}
