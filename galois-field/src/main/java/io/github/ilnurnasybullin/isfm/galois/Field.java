package io.github.ilnurnasybullin.isfm.galois;

import java.math.BigInteger;

public class Field {

    private final int characteristic;
    private final int power;

    private Field(int characteristic, int power) {
        this.characteristic = characteristic;
        this.power = power;
    }

    public static Field of(int characteristic, int power) {
        checkOnPrime(characteristic);
        checkOnPositive(power);

        return new Field(characteristic, power);
    }

    private static void checkOnPositive(int power) {

    }

    // used BigInteger's inner testing on probability
    // TODO: replace on small number prime test
    private static void checkOnPrime(int characteristic) {
        BigInteger value = BigInteger.valueOf(characteristic);

        // min probability of right answer is 1 - (1/2)^3 = 1 - 1/8 = 0.875 = 87.5%
        if (!value.isProbablePrime(3)) {
            throw new IllegalArgumentException(
                    String.format("Characteristic %d is not prime number!", characteristic)
            );
        }
    }

}
