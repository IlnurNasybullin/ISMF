package test.io.github.ilnurnasybullin.math.polynomial;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class IntPolynomialCoefficientsTest {

    @ParameterizedTest
    @MethodSource("_testEquals_success_dataSet")
    public void testEquals_success(int[] p1, int[] p2) {
        assertThat(IntPolynomialCoefficients.of(p1))
                .isEqualTo(IntPolynomialCoefficients.of(p2));
    }

    public static Stream<Arguments> _testEquals_success_dataSet() {
        return Stream.of(
                Arguments.of(ints(), ints()),
                Arguments.of(ints(12), ints(12)),
                Arguments.of(ints(-5), ints(-5)),
                Arguments.of(ints(-123, 2), ints(-123, 2, 0)),
                Arguments.of(ints(0), ints(0, 0)),
                Arguments.of(ints(0, 0), ints())
        );
    }

    @ParameterizedTest
    @MethodSource("_testNeg_success_dataSet")
    public void testNeg_success(IntPolynomialCoefficients c, IntPolynomialCoefficients negC) {
        var softly = new SoftAssertions();
        softly.assertThat(c.neg())
                .isEqualTo(negC);

        softly.assertThat(negC.neg())
                .isEqualTo(c);

        softly.assertAll();
    }

    public static Stream<Arguments> _testNeg_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(), IntPolynomialCoefficients.ZERO),
                Arguments.of(pol(1, 2, 0, 5), pol(-1, -2, 0, -5)),
                Arguments.of(IntPolynomialCoefficients.ONE, pol(-1)),
                Arguments.of(pol(0, 0, 2), pol(0, 0, -2)),
                Arguments.of(pol(-3, 6), pol(3, -6)),
                Arguments.of(pol(5), pol(-5))
        );
    }

    @ParameterizedTest
    @MethodSource("_testSum_success_dataSet")
    public void testSum_success(IntPolynomialCoefficients c1, IntPolynomialCoefficients c2,
                                IntPolynomialCoefficients summand) {
        assertThat(c1.sum(c2))
                .isEqualTo(c2.sum(c1))
                .isEqualTo(summand);
    }

    public static Stream<Arguments> _testSum_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(), pol(), IntPolynomialCoefficients.ZERO),
                Arguments.of(pol(1, 2, 0, 5), pol(), pol(1, 2, 0, 5)),
                Arguments.of(pol(0, 0, -4, 7), pol(0, 0, -4, 2), pol(0, 0, -8, 9)),
                Arguments.of(pol(0, 12, -4, -32, 8), pol(0, -2, 9, -3, 7), pol(0, 10, 5, -35, 15)),
                Arguments.of(pol(2, 3, 2, 4), pol(-1, -5, 3), pol(1, -2, 5, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("_testSubtract_success_dataSet")
    public void testSubtract_success(IntPolynomialCoefficients c1, IntPolynomialCoefficients c2,
                                     IntPolynomialCoefficients subtrahend) {
        assertThat(c1.subtract(c2))
                .isEqualTo(c1.sum(c2.neg()))
                .isEqualTo(subtrahend);
    }

    public static Stream<Arguments> _testSubtract_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(), pol(), IntPolynomialCoefficients.ZERO),
                Arguments.of(pol(), pol(1, 2, 0, 5), pol(-1, -2, 0, -5)),
                Arguments.of(pol(0, 0, -4, 7), pol(0, 0, -4, 2), pol(0, 0, 0, 5)),
                Arguments.of(pol(0, 12, -4, -32, 8), pol(0, -2, 9, -3, 7), pol(0, 14, -13, -29, 1)),
                Arguments.of(pol(2, 3, 2), pol(-1, -5, 3, 4), pol(3, 8, -1, -4))
        );
    }

    @ParameterizedTest
    @MethodSource("_testMultiplyScalar_success_dataSet")
    public void testMultiplyScalar_success(IntPolynomialCoefficients c, int scalar, IntPolynomialCoefficients product) {
        assertThat(c.multiply(scalar))
                .isEqualTo(product);
    }

    public static Stream<Arguments> _testMultiplyScalar_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(), 5, IntPolynomialCoefficients.ZERO),
                Arguments.of(IntPolynomialCoefficients.ONE, -1, pol(-1)),
                Arguments.of(pol(-10, 5, -3, 2), 2, pol(-20, 10, -6, 4)),
                Arguments.of(pol(5, -6, 3), -3, pol(-15, 18, -9))
        );
    }

    @ParameterizedTest
    @MethodSource("_testMultiply_success_dataSet")
    public void testMultiply_success(IntPolynomialCoefficients m1, IntPolynomialCoefficients m2,
                                     IntPolynomialCoefficients product) {
        assertThat(m1.multiply(m2))
                .isEqualTo(m2.multiply(m1))
                .isEqualTo(product);
    }

    public static Stream<Arguments> _testMultiply_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(), pol(), IntPolynomialCoefficients.ZERO),
                Arguments.of(pol(1, 2, 0, 5), pol(), IntPolynomialCoefficients.ZERO),
                Arguments.of(pol(1, 2, 0, 5), IntPolynomialCoefficients.ONE, pol(1, 2, 0, 5)),
                Arguments.of(pol(0, 0, 2), pol(0, 3), pol(0, 0, 0, 6)),
                Arguments.of(pol(-3, 6), pol(5, 2), pol(-15, 24, 12)),
                Arguments.of(pol(5), pol(7), pol(35)),
                Arguments.of(pol(3, 5), pol(-15, 7), pol(-45, -54, 35))
        );
    }

    @ParameterizedTest
    @MethodSource("_testDivideAndRemainder_exception_dataSet")
    public <X extends Exception> void testDivideAndRemainder_exception(IntPolynomialCoefficients dividend,
                                                                       IntPolynomialCoefficients divisor,
                                                                       Class<X> exceptionClass) {
        assertThatThrownBy(() -> dividend.divideAndRemainder(divisor))
                .isInstanceOf(exceptionClass);
    }

    public static Stream<Arguments> _testDivideAndRemainder_exception_dataSet() {
        return Stream.of(
                Arguments.of(pol(1, 2, 0, 5), pol(), ArithmeticException.class),
                Arguments.of(pol(0, 0, 2), pol(0, 3), IllegalArgumentException.class)
        );
    }

    private static int[] ints(int... numbers) {
        return numbers;
    }

    private static IntPolynomialCoefficients pol(int... numbers) {
        return IntPolynomialCoefficients.of(numbers);
    }

}
