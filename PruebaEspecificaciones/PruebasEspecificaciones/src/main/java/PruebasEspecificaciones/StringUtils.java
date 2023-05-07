package PruebasEspecificaciones;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Busca un String para subcadenas delimitadas por una etiqueta start y end,
     * y devuelve todas las subcadenas coincidentes en un arreglo.
     *
     * @param str   el String que contiene las subcadenas, null devuelve null, empty devuelve empty
     * @param open  el String identifica el inicio de la subcadena, empty retorna null
     * @param close the String identifica el fin de la subcadena, empty retorna null
     * @return un arreglo String de subcadenas o {@code null} si no empareja
     */
    public static String[] substringsBetween(final String str, final String open, final String close) {
        // Completa
    }
}


