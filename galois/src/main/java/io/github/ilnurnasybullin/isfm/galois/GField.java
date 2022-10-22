package io.github.ilnurnasybullin.isfm.galois;

/**
 * Поле Галуа (конечное поле)
 * @author Насыбуллин Ильнур Анасович (гр. 09-275)
 */
public class GField {

    /**
     * Характеристика поля
     */
    private final int characteristic;

    /**
     * Степень поля
     */
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

    public int characteristic() {
        return characteristic;
    }

}
