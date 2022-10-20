package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GAlgebraTest {

    @ParameterizedTest
    @MethodSource("_testPow_dataSet_field_2_3")
    public void testPow(GAlgebra algebra, IntPolynomial polynomial, int degree, GPolynomial expected) {
        assertThat(algebra.mod(polynomial, degree))
                .isEqualTo(expected);
    }

    public static Stream<Arguments> _testPow_dataSet_field_2_3() {
        var field = GField.of(2, 3);
        var base = NaturalPolynomial.of(new int[]{1, 1, 0});

        var gSpace = new GSpace(field, base);
        var gAlgebra = GAlgebra.of(gSpace);

        IntPolynomial x = NaturalPolynomial.eye(1);

        IntPolynomial x4 = NaturalPolynomial.of(new int[]{0, 1, 1});
        IntPolynomial x5 = NaturalPolynomial.of(new int[]{1, 1, 1});
        IntPolynomial x6 = NaturalPolynomial.of(new int[]{1, 0, 1});
        IntPolynomial x7 = NaturalPolynomial.eye(0);

        return Stream.of(
                Arguments.of(gAlgebra, x, 1, new GPolynomial(gSpace, x)),
                Arguments.of(gAlgebra, x, 2, new GPolynomial(gSpace, NaturalPolynomial.eye(2))),
                Arguments.of(gAlgebra, x, 3, new GPolynomial(gSpace, base)),
                Arguments.of(gAlgebra, x, 4, new GPolynomial(gSpace, x4)),
                Arguments.of(gAlgebra, x, 5, new GPolynomial(gSpace, x5)),
                Arguments.of(gAlgebra, x, 6, new GPolynomial(gSpace, x6)),
                Arguments.of(gAlgebra, x, 7, new GPolynomial(gSpace, x7))
        );
    }

}
