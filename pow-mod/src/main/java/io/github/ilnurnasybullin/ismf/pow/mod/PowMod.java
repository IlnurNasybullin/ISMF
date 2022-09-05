package io.github.ilnurnasybullin.ismf.pow.mod;

public class PowMod {

    public long powMod(long a, long b, long n) {
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
