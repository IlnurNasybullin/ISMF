package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.ilnurnasybullin.isfm.galois.SingleTermPolynomial.eye;
import static org.assertj.core.api.Assertions.assertThat;

public class GAlgebraTest {

    @ParameterizedTest
    @MethodSource("_testPow_dataSet_field_2_3")
    public void testPow(GAlgebra algebra, SingleTermPolynomial polynomial, int degree, GPolynomial expected) {
        assertThat(algebra.pow(polynomial, degree))
                .isEqualTo(expected);
    }

    public static Stream<Arguments> _testPow_dataSet_field_2_3() {
        var field = GField.of(2, 3);
        var base = SingleTermPolynomial.of(new int[]{1, 1, 0});

        var gSpace = new GSpace(field, base);
        var gAlgebra = GAlgebra.of(gSpace);

        SingleTermPolynomial x = eye(1);

        SingleTermPolynomial x4 = SingleTermPolynomial.of(new int[]{0, 1, 1});
        SingleTermPolynomial x5 = SingleTermPolynomial.of(new int[]{1, 1, 1});
        SingleTermPolynomial x6 = SingleTermPolynomial.of(new int[]{1, 0, 1});
        SingleTermPolynomial x7 = eye(0);

        return Stream.of(
                Arguments.of(gAlgebra, x, 1, new GPolynomial(gSpace, x)),
                Arguments.of(gAlgebra, x, 2, new GPolynomial(gSpace, eye(2))),
                Arguments.of(gAlgebra, x, 3, new GPolynomial(gSpace, base)),
                Arguments.of(gAlgebra, x, 4, new GPolynomial(gSpace, x4)),
                Arguments.of(gAlgebra, x, 5, new GPolynomial(gSpace, x5)),
                Arguments.of(gAlgebra, x, 6, new GPolynomial(gSpace, x6)),
                Arguments.of(gAlgebra, x, 7, new GPolynomial(gSpace, x7))
        );
    }

    @ParameterizedTest
    @MethodSource("_testSum_success_dataSet")
    public void testSum_success(GAlgebra algebra, SingleTermPolynomial x, SingleTermPolynomial y, GPolynomial sum) {
        assertThat(algebra.sum(x, y))
                .isEqualTo(sum);
    }

    public static Stream<Arguments> _testSum_success_dataSet() {
        var field_2_3 = GField.of(2, 3);
        var base_2_3 = pol(1, 0, 1, 1);
        var gSpace_2_3 = new GSpace(field_2_3, base_2_3);
        var gAlgebra_2_3 = GAlgebra.of(gSpace_2_3);

        var field_3_2 = GField.of(3, 2);
        var base_3_2 = pol(1, 0, 1);
        var gSpace_3_2 = new GSpace(field_3_2, base_3_2);
        var gAlgebra_3_2 = GAlgebra.of(gSpace_3_2);

        return Stream.of(
                Arguments.of(gAlgebra_2_3, pol(0, 0, 1), pol(0, 0, 1), gPol(gSpace_2_3, pol(0))),
                Arguments.of(gAlgebra_2_3, pol(0, 0, 1), pol(0, 1, 0), gPol(gSpace_2_3, pol(0, 1, 1))),
                Arguments.of(gAlgebra_2_3, pol(0, 0, 1), pol(1, 1, 1), gPol(gSpace_2_3, pol(1, 1, 0))),
                Arguments.of(gAlgebra_2_3, pol(0, 1, 0), pol(0, 1, 0), gPol(gSpace_2_3, pol(0))),
                Arguments.of(gAlgebra_2_3, pol(1, 1, 1), pol(0, 1, 0), gPol(gSpace_2_3, pol(1, 0, 1))),
                Arguments.of(gAlgebra_2_3, pol(1, 1, 1), pol(1, 1, 1), gPol(gSpace_2_3, pol(0))),
                Arguments.of(gAlgebra_2_3, pol(0, 1, 1), pol(1, 0, 1), gPol(gSpace_2_3, pol(1, 1, 0))),
                Arguments.of(gAlgebra_3_2, pol(2, 2), pol(2, 2), gPol(gSpace_3_2, pol(1, 1)))
        );
    }

    @ParameterizedTest
    @MethodSource("_testMultiply_success_dataSet")
    public void testMultiply_success(GAlgebra algebra, SingleTermPolynomial x, SingleTermPolynomial y, GPolynomial product) {
        assertThat(algebra.multiply(x, y))
                .isEqualTo(product);
    }

    public static Stream<Arguments> _testMultiply_success_dataSet() {
        var field_2_3 = GField.of(2, 3);
        var base_2_3 = pol(1, 0, 1, 1);
        var gSpace_2_3 = new GSpace(field_2_3, base_2_3);
        var gAlgebra_2_3 = GAlgebra.of(gSpace_2_3);

        return Stream.of(
                Arguments.of(gAlgebra_2_3, pol(), pol(), gPol(gSpace_2_3, pol(0))),
                Arguments.of(gAlgebra_2_3, pol(), pol(1, 3), gPol(gSpace_2_3, pol())),
                Arguments.of(gAlgebra_2_3, pol(1, 0, 1, 1), pol(), gPol(gSpace_2_3, pol())),
                Arguments.of(gAlgebra_2_3, pol(2, 1), pol(1, 5), gPol(gSpace_2_3, pol(1, 1))),
                Arguments.of(gAlgebra_2_3, eye(2), eye(3), gPol(gSpace_2_3, pol(1, 1, 1))),
                Arguments.of(gAlgebra_2_3, pol(0, 1, 1), pol(1, 0, 1), gPol(gSpace_2_3, pol(1, 0, 0)))
        );
    }

    @ParameterizedTest
    @MethodSource("_testNormalize_success_dataSet")
    public void testNormalize_success(GAlgebra algebra, SingleTermPolynomial polynomial, GPolynomial normalized) {
        assertThat(algebra.normalized(polynomial))
                .isEqualTo(normalized);
    }

    public static Stream<Arguments> _testNormalize_success_dataSet() {
        var field_2_3 = GField.of(2, 3);
        var base_2_3 = pol(1, 0, 1, 1);
        var gSpace_2_3 = new GSpace(field_2_3, base_2_3);
        var gAlgebra_2_3 = GAlgebra.of(gSpace_2_3);

        return Stream.of(
                Arguments.of(gAlgebra_2_3, eye(1), gPol(gSpace_2_3, pol(1, 0))),
                Arguments.of(gAlgebra_2_3, eye(2), gPol(gSpace_2_3, pol(1, 0, 0))),
                Arguments.of(gAlgebra_2_3, eye(3), gPol(gSpace_2_3, pol(0, 1, 1))),
                Arguments.of(gAlgebra_2_3, eye(4), gPol(gSpace_2_3, pol(1, 1, 0))),
                Arguments.of(gAlgebra_2_3, eye(5), gPol(gSpace_2_3, pol(1, 1, 1))),
                Arguments.of(gAlgebra_2_3, eye(6), gPol(gSpace_2_3, pol(1, 0, 1))),
                Arguments.of(gAlgebra_2_3, eye(7), gPol(gSpace_2_3, pol(0, 0, 1))),
                Arguments.of(gAlgebra_2_3, pol(2, 11, 5), gPol(gSpace_2_3, pol(1, 1)))
        );
    }

    private static SingleTermPolynomial pol(int... coefficients) {
        return SingleTermPolynomial.bigEndian(coefficients);
    }

    private static GPolynomial gPol(GSpace gSpace, SingleTermPolynomial polynomial) {
        return new GPolynomial(gSpace, polynomial);
    }
}
