package test.io.github.ilnurnasybullin.math.polynomial;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    private static int[] ints(int... numbers) {
        return numbers;
    }

    private static IntPolynomialCoefficients pol(int... numbers) {
        return IntPolynomialCoefficients.of(numbers);
    }

}
