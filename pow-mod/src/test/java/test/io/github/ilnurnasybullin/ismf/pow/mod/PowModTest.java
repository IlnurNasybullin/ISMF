package test.io.github.ilnurnasybullin.ismf.pow.mod;

import io.github.ilnurnasybullin.ismf.pow.mod.PowMod;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

public class PowModTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            # a,    b,      n,      mod
            2,      199,    1003,   247,
            5,      -1,     6,      5,
            8,      -2,     5,      4,
            0,      4,      2,      0,
            64,     0,      7,      1,
            -4,     5,      6,      4,
            -12,    -4,     25,     1
            """)
    public void testLongPowMod(long a, long b, long n, long mod) {
        var actualMod = new PowMod().powMod(a, b, n);
        assertThat(actualMod)
                .isEqualTo(mod);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            # a,    b,      n
            0,      -4,     2,
            8,      2,      -2
            """)
    public void testLongPowMod_Exception(long a, long b, long n) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PowMod().powMod(a, b, n));
    }

}
