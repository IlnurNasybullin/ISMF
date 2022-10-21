package io.github.ilnurnasybullin.isfm.galois;

public class GField {

    private final int characteristic;
    private final int degree;

    private GField(int characteristic, int degree) {
        this.characteristic = characteristic;
        this.degree = degree;
    }

    public static GField of(int characteristic, int degree) {
        if (characteristic < 2) {
            throw new IllegalArgumentException(
                    String.format("Galois field can't be created with characteristic = %d < 2!", characteristic)
            );
        }

        return new GField(characteristic, degree);
    }

}
