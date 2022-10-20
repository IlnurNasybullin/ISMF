package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.Polynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PolynomialTest {

    @ParameterizedTest
    @MethodSource("_testAdd_success_dataSet")
    public void testAdd_success(Polynomial x, Polynomial y, Polynomial sum) {
        assertThat(x.add(y))
                .isEqualTo(sum);
    }

    public static Stream<Arguments> _testAdd_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(1, 2, 0), pol(3, 5, 1), pol(4, 7, 1)),
                Arguments.of(pol(-2, 3, 8, 0), pol(2, -5, 3), pol(-2, 5, 3, 3)),
                Arguments.of(pol(5, 6), pol(-5, -6), pol(0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("_testSubtract_success_dataSet")
    public void testSubtract_success(Polynomial x, Polynomial y, Polynomial difference) {
        assertThat(x.subtract(y))
                .isEqualTo(difference);
    }

    public static Stream<Arguments> _testSubtract_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(1, 2, 0), pol(3, 5, 1), pol(-2, -3, -1)),
                Arguments.of(pol(-2, 3, 8, 0), pol(2, -5, 3), pol(-2, 1, 13, -3)),
                Arguments.of(pol(5, 6), pol(-5, -6), pol(10, 12))
        );
    }

    private static Polynomial pol(int... coefficients) {
        return Polynomial.bigEndian(coefficients);
    }

}
