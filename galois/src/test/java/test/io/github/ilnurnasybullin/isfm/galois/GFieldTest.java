package test.io.github.ilnurnasybullin.isfm.galois;

import io.github.ilnurnasybullin.isfm.galois.GField;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class GFieldTest {

    @ParameterizedTest
    @MethodSource("_testConstructClass_exception_dataSet")
    public <X extends Exception> void testConstructClass_exception(int characteristic, int degree,
                                                                   Class<X> exceptionClass) {
        assertThatThrownBy(() -> GField.of(characteristic, degree))
                .isInstanceOf(exceptionClass);
    }

    public static Stream<Arguments> _testConstructClass_exception_dataSet() {
        return Stream.of(
                Arguments.of(1, 5, IllegalArgumentException.class)
        );
    }

}
