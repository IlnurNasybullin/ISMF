package test.io.github.ilnurnasybullin.ismf.eea;

import io.github.ilnurnasybullin.ismf.eea.ExtendedGcdAlgorithm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedGcdAlgorithmTest {

    @ParameterizedTest
    @MethodSource("testIntCalculateDataSource")
    public void testIntCalculate(int a, int b, int gcd) {
        var extendedGcd = new ExtendedGcdAlgorithm().calculate(a, b);
        assertThat(extendedGcd.gcd())
                .describedAs("check gcd value")
                .isEqualTo(gcd);
        var bezoutCoefficients = extendedGcd.bezoutCoefficients();

        assertThat(bezoutCoefficients.x() * a + bezoutCoefficients.y() * b)
                .describedAs("check Bezout's coefficients = %s", bezoutCoefficients)
                .isEqualTo(gcd);

    }

    public static Stream<Arguments> testIntCalculateDataSource() {
        return Stream.of(
                Arguments.of(72, 25, 1),
                Arguments.of(25, 1, 1),
                Arguments.of(1, 25, 1),
                Arguments.of(10, 0, 10),
                Arguments.of(0, -15, 15),
                Arguments.of(80, -25, 5),
                Arguments.of(-25, 4, 1),
                Arguments.of(-35, -60, 5),
                Arguments.of(19, 9, 1)
        );
    }

}
