package io.github.ilnurnasybullin.ismf.pow.mod;

public class PowModRun {
    public static void main(String[] args) {
        var a = Long.parseLong(args[0]);
        var b = Long.parseLong(args[1]);
        var n = Long.parseLong(args[2]);

        var powMod = new PowMod().powMod(a, b, n);
        System.out.printf("(%d ^ %d) mod %d = %d", a, b, n, powMod);
    }
}
