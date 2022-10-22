package io.github.ilnurnasybullin.ismf;

import io.github.ilnurnasybullin.isfm.galois.GAlgebra;
import io.github.ilnurnasybullin.isfm.galois.GField;
import io.github.ilnurnasybullin.isfm.galois.GSpace;
import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.*;
import java.util.function.Function;

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
        System.out.println();
        printTableOfAddition(algebra);
        System.out.println();
        printTableOfMultiplication(algebra);
    }

    /**
     * Метод, распечатывающий таблицу сложения примитивных полиномов с заданной алгеброй над полем
     */
    private void printTableOfAddition(GAlgebra algebra) {
        var primitivePolynomials = primitivePolynomials(algebra);
        var additionMatrix = new IntPolynomialCoefficients[primitivePolynomials.length][primitivePolynomials.length];

        for (int i = 0; i < additionMatrix.length; i++) {
            for (int j = i; j < additionMatrix.length; j++) {
                additionMatrix[i][j] = algebra.sum(primitivePolynomials[i], primitivePolynomials[j]);
                additionMatrix[j][i] = additionMatrix[i][j];
            }
        }

        System.out.println("Table of addition");
        System.out.println("-----------------");
        printPolynomialMatrix(algebra, primitivePolynomials, additionMatrix);
    }

    /**
     * Метод, распечатывающий таблицу умножения примитивных полиномов с заданной алгеброй над полем
     */
    private void printTableOfMultiplication(GAlgebra algebra) {
        var primitivePolynomials = primitivePolynomials(algebra);
        var additionMatrix = new IntPolynomialCoefficients[primitivePolynomials.length][primitivePolynomials.length];

        for (int i = 0; i < additionMatrix.length; i++) {
            for (int j = i; j < additionMatrix.length; j++) {
                additionMatrix[i][j] = algebra.multiply(primitivePolynomials[i], primitivePolynomials[j]);
                additionMatrix[j][i] = additionMatrix[i][j];
            }
        }

        System.out.println("Table of multiplication");
        System.out.println("-----------------");
        printPolynomialMatrix(algebra, primitivePolynomials, additionMatrix);
    }

    private void printPolynomialMatrix(GAlgebra algebra, IntPolynomialCoefficients[] header, IntPolynomialCoefficients[][] matrix) {
        String linePattern = "%-4s";

        Function<IntPolynomialCoefficients[], String[]> toFormattedArgs = args ->
                Arrays.stream(args)
                        .map(algebra::toDecimal)
                        .map(Object::toString)
                        .toArray(String[]::new);

        var decimals = toFormattedArgs.apply(header);

        printArgs(linePattern, "");
        printArgs(linePattern, decimals);
        System.out.println();

        for (int i = 0; i < header.length; i++) {
            printArgs(linePattern, Integer.toString(algebra.toDecimal(header[i])));
            printArgs(linePattern, toFormattedArgs.apply(matrix[i]));
            System.out.println();
        }
    }

    private void printArgs(String pattern, String... args) {
        for (var arg: args) {
            System.out.printf(pattern, arg);
        }
    }

    private IntPolynomialCoefficients[] primitivePolynomials(GAlgebra algebra) {
        var comparator = Comparator.comparingInt(algebra::toDecimal);

        var primitives = new TreeSet<>(comparator);
        var c = IntPolynomialCoefficients.ONE;
        var x = IntPolynomialCoefficients.eye(1);

        do {
            primitives.add(c);
            c = algebra.normalization(c.multiply(x)).coefficients();
        } while (!primitives.contains(c));

        return primitives.toArray(IntPolynomialCoefficients[]::new);
    }

    /**
     * Метод, распечатывающий таблицу степеней примитивных полиномов с заданной алгеброй над полем
     */
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