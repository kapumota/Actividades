package PruebasEspecificaciones;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;

public class NumberUtilsTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void shouldReturnCorrectResult(List<Integer> left, List<Integer> right, List<Integer> expected) {
        assertThat(new NumberUtils().add(left, right))
                .isEqualTo(expected);
    }

    static Stream<Arguments> testCases() {

        /*
         * Casos de prueba:
         *
         * T1 = left null
         * T2 = left vacio
         * T3 = right null
         * T4 = right vacio
         *
         * unico digito:
         * T5 =  un solo dígito, sin aumento
         * T6 = un solo digito, con aumento
         *
         * multiples digitos:
         * T7 = sin aumento
         * T8 = aumento en el dígito menos significativo
         * T9 = aumento en el medio
         * T10 = muchos aumentos
         * T11 = muchos aumentos, no seguidos
         * T12 = muchos aumentos propagados
         *
         * multiples digitos, diferentes longitudes:
         * T13 = sin aumento
         * T14 = aumento en el dígito menos significativo
         * T15 = aumento en el medio
         * T16 = muchos aumentos
         * T17 =  muchos aumentos, no seguidos
         * T18 = : aumento propagado a un dígito nuevo (ahora el más significativo)

         *
         * ceros en left:
         * T19 = sin aumento
         * T20 = con aumento
         *
         * limite:
         * T21 =  aumento de  un nuevo dígito más significativo, por uno (e.g., 99+1).
         */

        return Stream.of(
                // null y vacíos deben devolver null
                of(null, numbers(7,2), null), // T1
                of(numbers(), numbers(7,2), numbers(7,2)), // T2
                of(numbers(9,8), null, null), // T3
                of(numbers(9,8), numbers(), numbers(9,8)), // T4

                // unicos digitos
                of(numbers(1), numbers(2), numbers(3)), // T5
                of(numbers(9), numbers(2), numbers(1,1)), // T6

                // multiples digitos
                of(numbers(2,2), numbers(3,3), numbers(5,5)), // T7
                of(numbers(2,9), numbers(2,3), numbers(5,2)), // T8
                of(numbers(2,9,3), numbers(1,8,3), numbers(4,7,6)), // T9
                of(numbers(1,7,9), numbers(2,6,8), numbers(4,4,7)), // T10
                of(numbers(1,9,1,7,1), numbers(1,8,1,6,1), numbers(3,7,3,3,2)), // T11
                of(numbers(9,9,8), numbers(1,7,2), numbers(1,1,7,0)), // T12

                // multiples digitos, diferentes longitudes con o sin aumento
                // (ambos lados)
                of(numbers(2,2), numbers(3), numbers(2,5)), // T13.1
                of(numbers(3), numbers(2,2), numbers(2,5)), // T13.2

                of(numbers(2,2), numbers(9), numbers(3,1)), // T14.1
                of(numbers(9), numbers(2,2), numbers(3,1)), // T14.2

                of(numbers(1,7,3), numbers(9,2), numbers(2,6,5)), // T15.1
                of(numbers(9,2), numbers(1,7,3), numbers(2,6,5)), // T15.2

                of(numbers(3,1,7,9), numbers(2,6,8), numbers(3,4,4,7)), // T16.1
                of(numbers(2,6,8), numbers(3,1,7,9), numbers(3,4,4,7)), // T16.2

                of(numbers(1,9,1,7,1), numbers(2,1,8,1,6,1), numbers(2,3,7,3,3,2)), // T17.1
                of(numbers(2,1,8,1,6,1), numbers(1,9,1,7,1), numbers(2,3,7,3,3,2)), // T17.2

                of(numbers(9,9,8), numbers(9,1,7,2), numbers(1,0,1,7,0)), // T18.1
                of(numbers(9,1,7,2), numbers(9,9,8), numbers(1,0,1,7,0)), // T18.2

                // ceros a la izquierda
                of(numbers(0,0,0,1,2), numbers(0,2,3), numbers(3,5)), // T19
                of(numbers(0,0,0,1,2), numbers(0,2,9), numbers(4,1)), // T20,

                // limites
                of(numbers(9,9), numbers(1), numbers(1,0,0)) // T21
        );
    }

    @ParameterizedTest
    @MethodSource("digitsOutOfRange")
    void shouldThrowExceptionWhenDigitsAreOutOfRange(List<Integer> left, List<Integer> right) {
        assertThatThrownBy(() -> new NumberUtils().add(left, right))
                .isInstanceOf(IllegalArgumentException.class);

    }

    static Stream<Arguments> digitsOutOfRange() {
        return Stream.of(
               // completa
        );
    }

    // devuelve una lista mutable de enteros
    private static List<Integer> numbers(int... nums) {
       // completa
    }

}
