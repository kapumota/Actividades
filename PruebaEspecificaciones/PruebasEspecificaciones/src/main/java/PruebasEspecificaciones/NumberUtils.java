package PruebasEspecificaciones;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NumberUtils {

    /**
     * Este método recibe dos números, 'left' y 'right', ambos representados como una lista de dígitos.
     * Agrega estos números y devuelve el resultado también como una lista de dígitos.
     *
     * Por ejemplo, si deseas agregar los números 23 y 42, debes crear
     * una lista (left) con dos elementos [2,3] y una lista (right) con dos elementos [4,2].
     * 23+42 = 65, por lo que el programa produce otra lista con dos elementos [6,5]
     * [2,3] + [4,2] = [6,5]
     *
     * Cada elemento en las listas left y right debe ser un número de [0-9].
     * Se lanza una IllegalArgumentException en caso de que esta pre-condición  no se cumpla.
     *
     * @param left  una lista que contiene el número left. Null devuelve null, empty significa 0.
     * @param right una lista que contiene el número right. Null devuelve null, empty significa 0.
     * @return la suma de left y right, como una lista.
     */
    public static List<Integer> add(List<Integer> left, List<Integer> right) {
        // Si alguno es null, devuelve null


        // invierta los números para que el dígito menos significativo vaya a la izquierda.


        // mientras haya un dígito, sigue sumando si hay desplazamiento,



        // si hay algún desplazamiento sobrante, agréguelo al número final


        // elimina los ceros iniciales del resultado

    }
}
