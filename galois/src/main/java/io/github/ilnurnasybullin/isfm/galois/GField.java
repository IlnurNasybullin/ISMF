package io.github.ilnurnasybullin.isfm.galois;

public class GField {

    private final int characteristic;
    private final int degree;

    private GField(int characteristic, int degree) {
        this.characteristic = characteristic;
        this.degree = degree;
    }

    public static GField of(int characteristic, int degree) {
        return new GField(characteristic, degree);
    }

}
