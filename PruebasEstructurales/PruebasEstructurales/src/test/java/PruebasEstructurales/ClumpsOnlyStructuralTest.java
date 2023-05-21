package PruebasEstructurales;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class ClumpsOnlyStructuralTest {
    @ParameterizedTest
    @MethodSource("generator")
    void testClumps(int[] nums, int expectedNoOfClumps) {
        assertThat(Clumps.countClumps(nums))
                .isEqualTo(expectedNoOfClumps);
    }

    static Stream<Arguments> generator() {
        return Stream.of(
                of(new int[]{}, 0),
                of(null, 0), // null
                of(new int[]{1,2,2,2,1}, 1), 
                of(new int[]{1}, 0), 

                // ¿las pruebas estructurales son suficientes?
                of(new int[]{2,2}, 1)
        );
    }

}
