package io.github.ilnurnasybullin.isfm.galois;

import java.math.BigInteger;
import java.util.Objects;

public class GField {

    private final int characteristic;
    private final int power;

    private GField(int characteristic, int power) {
        this.characteristic = characteristic;
        this.power = power;
    }

    public static GField of(int characteristic, int degree) {
        checkOnPrime(characteristic);
        checkOnPositive(degree);

        return new GField(characteristic, degree);
    }

    private static void checkOnPositive(int degree) {
        if (degree <= 0) {
            throw new IllegalArgumentException(String.format("Degree %d is cannot be non positive number!", degree));
        }
    }

    public int characteristic() {
        return characteristic;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GField gField = (GField) o;
        return characteristic == gField.characteristic && power == gField.power;
    }

    @Override
    public int hashCode() {
        return Objects.hash(characteristic, power);
    }
}
