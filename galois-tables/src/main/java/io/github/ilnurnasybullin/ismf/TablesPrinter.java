package io.github.ilnurnasybullin.ismf;

import io.github.ilnurnasybullin.isfm.galois.GAlgebra;
import io.github.ilnurnasybullin.isfm.galois.GField;
import io.github.ilnurnasybullin.isfm.galois.GSpace;
import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Collections;
import java.util.List;

@Command
public class TablesPrinter implements Runnable {

    @Option(names = "-p", required = true)
    private int p;

    @Option(names = "-k", required = true)
    private int k;

    @Option(names = {"-pol", "--polynomial"}, required = true, split = ",")
    private List<Integer> polynomial;

    @Option(names = {"-o", "--order"})
    private Order order = Order.be;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new TablesPrinter())
                .execute(args);

        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (order == Order.le) {
            Collections.reverse(polynomial);
        }

        var coefficients = polynomial.stream()
                .mapToInt(value -> value)
                .toArray();

        var field = GField.of(p, k);
        var base = IntPolynomialCoefficients.of(coefficients);
        var algebra = GAlgebra.of(GSpace.of(field, base));

        printTableOfDegrees(algebra);
    }

    private void printTableOfDegrees(GAlgebra algebra) {
        var linePattern = "%-5s|%-20s\n";

        System.out.println("Table of degrees");
        System.out.println("----------------");
        System.out.printf(linePattern, "x", "decompose");

        var c = IntPolynomialCoefficients.ONE;
        var x = IntPolynomialCoefficients.eye(1);
        int degree = 0;
        do {
            System.out.printf(linePattern, String.format("x^%d", degree), String.format("%s (%d)", c, algebra.toDecimal(c)));
            c = algebra.normalization(c.multiply(x)).coefficients();
            degree++;
        } while (!IntPolynomialCoefficients.ONE.equals(c));
        System.out.printf(linePattern, String.format("x^%d", degree), String.format("%s (%d)", c, algebra.toDecimal(c)));
    }
}