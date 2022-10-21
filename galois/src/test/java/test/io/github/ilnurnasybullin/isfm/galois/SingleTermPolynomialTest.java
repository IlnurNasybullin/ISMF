package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.SingleTermPolynomial;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.ilnurnasybullin.isfm.galois.SingleTermPolynomial.eye;
import static org.assertj.core.api.Assertions.assertThat;

public class SingleTermPolynomialTest {

    @ParameterizedTest
    @MethodSource("_testAdd_success_dataSet")
    public void testAdd_success(SingleTermPolynomial x, SingleTermPolynomial y, SingleTermPolynomial sum) {
        assertThat(x.add(y))
                .isEqualTo(sum);
    }

    public static Stream<Arguments> _testAdd_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(1, 2, 0), pol(3, 5, 1), pol(4, 7, 1)),
                Arguments.of(pol(-2, 3, 8, 0), pol(2, -5, 3), pol(-2, 5, 3, 3)),
                Arguments.of(pol(5, 6), pol(-5, -6), pol(0, 0)),
                Arguments.of(pol(), pol(), pol()),
                Arguments.of(pol(1, 2, 9), pol(), pol(1, 2, 9))
        );
    }

    @ParameterizedTest
    @MethodSource("_testSubtract_success_dataSet")
    public void testSubtract_success(SingleTermPolynomial x, SingleTermPolynomial y, SingleTermPolynomial difference) {
        assertThat(x.subtract(y))
                .isEqualTo(difference);
    }

    public static Stream<Arguments> _testSubtract_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(1, 2, 0), pol(3, 5, 1), pol(-2, -3, -1)),
                Arguments.of(pol(-2, 3, 8, 0), pol(2, -5, 3), pol(-2, 1, 13, -3)),
                Arguments.of(pol(5, 6), pol(-5, -6), pol(10, 12)),
                Arguments.of(pol(), pol(), pol()),
                Arguments.of(pol(1, 2, 9), pol(), pol(1, 2, 9))
        );
    }

    @ParameterizedTest
    @MethodSource("_testMultiply_success_dataSet")
    public void testMultiply_success(SingleTermPolynomial x, SingleTermPolynomial y, SingleTermPolynomial product) {
        assertThat(x.multiply(y))
                .isEqualTo(product);
    }

    public static Stream<Arguments> _testMultiply_success_dataSet() {
        return Stream.of(
                Arguments.of(pol(1, 2, 0), pol(3, 5, 1), pol(3, 11, 11, 2, 0)),
                Arguments.of(pol(-2, 3, 8, 0), pol(2, -5, 3), pol(-4, 16, -5, -31, 24, 0)),
                Arguments.of(pol(5, 6), pol(-5, -6), pol(-25, -60, -36)),
                Arguments.of(pol(1, 1), pol(1, 1), pol(1, 2, 1)),
                Arguments.of(pol(-1, 0), pol(1, 0), pol(-1, 0, 0)),
                Arguments.of(pol(-1), pol(1), pol(-1)),
                Arguments.of(pol(-4), pol(), pol()),
                Arguments.of(pol(), pol(), pol()),
                Arguments.of(eye(0), pol(1, 0, 24), pol(1, 0, 24))
        );
    }

    private static SingleTermPolynomial pol(int... coefficients) {
        return SingleTermPolynomial.bigEndian(coefficients);
    }


    @ParameterizedTest
    @MethodSource("_testEquals_success_dataSet")
    public void testEquals_success(int[] x, int[] y) {
        assertThat(SingleTermPolynomial.of(x))
                .isEqualTo(SingleTermPolynomial.of(y));
    }

    public static Stream<Arguments> _testEquals_success_dataSet() {
        return Stream.of(
                Arguments.of(new int[]{1}, new int[]{1}),
                Arguments.of(new int[]{}, new int[]{0, 0, 0}),
                Arguments.of(new int[]{1, 1, 0}, new int[]{1, 1})
        );
    }

}
