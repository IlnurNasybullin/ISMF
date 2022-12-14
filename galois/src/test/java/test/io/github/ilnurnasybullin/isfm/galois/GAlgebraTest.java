package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.GAlgebra;
import io.github.ilnurnasybullin.isfm.galois.GField;
import io.github.ilnurnasybullin.isfm.galois.GIntPolynomialCoefficients;
import io.github.ilnurnasybullin.isfm.galois.GSpace;
import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GAlgebraTest {

    @ParameterizedTest
    @MethodSource("_testNormalization_success_dataSet")
    public void testNormalization_success(GAlgebra algebra, IntPolynomialCoefficients coefficients,
                                  GIntPolynomialCoefficients normalized) {
        assertThat(algebra.normalization(coefficients))
                .isEqualTo(normalized);
    }

    public static Stream<Arguments> _testNormalization_success_dataSet() {
        var space_2_3 = space(2, 3, build(1, 1, 0, 1));
        var algebra_2_3 = GAlgebra.of(space_2_3);

        return Stream.of(
                Arguments.of(algebra_2_3, build(-1, -1), gPol(space_2_3, build(1, 1))),
                Arguments.of(algebra_2_3, eye(7), gPol(space_2_3, build(1))),
                Arguments.of(algebra_2_3, eye(6), gPol(space_2_3, build(1, 0, 1))),
                Arguments.of(algebra_2_3, eye(5), gPol(space_2_3, build(1, 1, 1))),
                Arguments.of(algebra_2_3, eye(4), gPol(space_2_3, build(0, 1, 1))),
                Arguments.of(algebra_2_3, eye(3), gPol(space_2_3, build(1, 1)))
        );
    }

    @ParameterizedTest
    @MethodSource("_testToDecimal_success_dataSet")
    public void testToDecimal_success(GAlgebra algebra, IntPolynomialCoefficients coefficients, int number) {
        assertThat(algebra.toDecimal(coefficients))
                .isEqualTo(number);
    }

    public static Stream<Arguments> _testToDecimal_success_dataSet() {
        var algebra_2_3 = GAlgebra.of(space(2, 3, build(1, 1, 0, 1)));

        return Stream.of(
                Arguments.of(algebra_2_3, eye(7), 1),
                Arguments.of(algebra_2_3, eye(6), 5),
                Arguments.of(algebra_2_3, eye(5), 7),
                Arguments.of(algebra_2_3, eye(4), 6),
                Arguments.of(algebra_2_3, eye(3), 3),
                Arguments.of(algebra_2_3, eye(2), 4),
                Arguments.of(algebra_2_3, eye(1), 2),
                Arguments.of(algebra_2_3, ONE, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("_testReverse_success_dataSet")
    public void testReverse_success(GAlgebra algebra, IntPolynomialCoefficients c, IntPolynomialCoefficients reverseC) {
        var assertions = new SoftAssertions();
        assertThat(algebra.reverse(c))
                .isEqualTo(algebra.normalization(reverseC).coefficients());
        assertThat(algebra.reverse(reverseC))
                .isEqualTo(algebra.normalization(c).coefficients());
        assertions.assertAll();
    }

    public static Stream<Arguments> _testReverse_success_dataSet() {
        var algebra_2_3 = GAlgebra.of(space(2, 3, build(1, 1, 0, 1)));

        return Stream.of(
                Arguments.of(algebra_2_3, ONE, ONE),
                Arguments.of(algebra_2_3, eye(7), eye(7)),
                Arguments.of(algebra_2_3, eye(1), eye(6)),
                Arguments.of(algebra_2_3, eye(2), eye(5)),
                Arguments.of(algebra_2_3, build(1, 0, 1), build(0, 1))
        );
    }

    @ParameterizedTest
    @MethodSource("_testDivide_success_dataSet")
    public void testDivide_success(GAlgebra algebra, IntPolynomialCoefficients dividend, IntPolynomialCoefficients divisor,
                                   IntPolynomialCoefficients quotient) {
        var assertions = new SoftAssertions();
        assertThat(algebra.divide(dividend, divisor))
                .isEqualTo(algebra.normalization(quotient).coefficients())
                .isEqualTo(algebra.multiply(dividend, algebra.reverse(divisor)));
        assertThat(algebra.multiply(quotient, divisor))
                .isEqualTo(algebra.normalization(dividend).coefficients());
        assertions.assertAll();
    }

    public static Stream<Arguments> _testDivide_success_dataSet() {
        var algebra_2_3 = GAlgebra.of(space(2, 3, build(1, 1, 0, 1)));

        return Stream.of(
                Arguments.of(algebra_2_3, build(1, 1), build(1, 0, 1), build(0, 1, 1)),
                Arguments.of(algebra_2_3, eye(4), eye(3), eye(1)),
                Arguments.of(algebra_2_3, eye(6), eye(3), build(1, 1)),
                Arguments.of(algebra_2_3, eye(4), eye(2), eye(2))
        );
    }

    private static GSpace space(int characteristic, int degree, IntPolynomialCoefficients base) {
        return GSpace.of(
                GField.of(characteristic, degree), base
        );
    }

    private static GIntPolynomialCoefficients gPol(GSpace space, IntPolynomialCoefficients coefficients) {
        return new GIntPolynomialCoefficients(space, coefficients);
    }

}
