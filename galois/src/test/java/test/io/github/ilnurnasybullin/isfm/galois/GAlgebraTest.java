package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.GAlgebra;
import io.github.ilnurnasybullin.isfm.galois.GField;
import io.github.ilnurnasybullin.isfm.galois.GIntPolynomialCoefficients;
import io.github.ilnurnasybullin.isfm.galois.GSpace;
import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients.build;
import static io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients.eye;
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

    private static GSpace space(int characteristic, int degree, IntPolynomialCoefficients base) {
        return GSpace.of(
                GField.of(characteristic, degree), base
        );
    }

    private static GIntPolynomialCoefficients gPol(GSpace space, IntPolynomialCoefficients coefficients) {
        return new GIntPolynomialCoefficients(space, coefficients);
    }

}
