package test.io.github.ilnurnasybullin.ismf.ext.gcd;

import io.github.ilnurnasybullin.ismf.ext.gcd.ExtendedGcdAlgorithm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedGcdAlgorithmTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            # a,    b,      gcd
            72,     25,     1,
            25,     1,      1,
            1,      25,     1,
            10,     0,      10,
            0,      -15,    15,
            80,     -25,    5,
            -25,    4,      1,
            -35,    -60,    5,
            19,     9,      1
            """)
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
}
