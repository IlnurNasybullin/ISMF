package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.GField;
import io.github.ilnurnasybullin.isfm.galois.GSpace;
import io.github.ilnurnasybullin.math.polynomial.IntPolynomialCoefficients;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class GSpaceTest {

    @ParameterizedTest
    @MethodSource("_testConstructClass_exception_dataSet")
    public <X extends Exception> void testConstructClass_exception(GField field, IntPolynomialCoefficients base,
                                                                   Class<X> exceptionClass) {
        assertThatThrownBy(() -> GSpace.of(field, base))
                .isInstanceOf(exceptionClass);
    }

    public static Stream<Arguments> _testConstructClass_exception_dataSet() {
        return Stream.of(
                Arguments.of(GField.of(2, 3), pol(1, 1), IllegalArgumentException.class),
                Arguments.of(GField.of(3, 4), pol(1, 0, 1, 2), IllegalArgumentException.class)
        );
    }

    private static IntPolynomialCoefficients pol(int... c) {
        return IntPolynomialCoefficients.of(c);
    }

}
