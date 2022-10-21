package test.io.github.ilnurnasybullin.math.polynomial;

import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
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

    private static int[] ints(int... numbers) {
        return numbers;
    }

}
