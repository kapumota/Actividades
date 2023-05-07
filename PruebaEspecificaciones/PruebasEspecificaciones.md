## Curso de Desarrollo de Software

En esta actividad debes crear un proyecto llamado `PruebasEspecificaciones` y configurar el archivo pom.xml para utilizar JUnit 5.

Sube el proyecto a un repositorio con el nombre de la actividad de manera grupal con todos tus resultados.


### Pruebas basadas en especificaciones 

Los requisitos de software son, sin duda, el artefacto más valioso de las pruebas de software. En esta actividad, exploramos las pruebas basadas en especificaciones. 

Estas técnicas utilizan los requisitos del programa, como historias de usuarios ágiles o casos de uso de UML, como entrada de prueba.

#### Los requisitos lo dicen todo 

Implementa: un método que busca subcadenas entre dos etiquetas en una cadena determinada y devuelve todas las subcadenas coincidentes. Llamemos a este método `substringsBetween()`.

Requisitos para el método substringsBetween(): 

`Método: substringsBetween()`

Busca en una cadena subcadenas delimitadas por una etiqueta de `start` y `end` y devuelve todas las subcadenas coincidentes en un arreglo. 

- `str`: la cadena que contiene las subcadenas. Null devuelve null; una cadena vacía devuelve otra cadena vacía. 
- `open`: la cadena que identifica el inicio de la subcadena. Una cadena vacía devuelve null. 
- `close`: la cadena que identifica el final de la subcadena. Una cadena vacía devuelve null. 

**Ejemplo:** si `str = "axcaycazc"`, `open = "a"` y `close = "c"`, la salida será un arreglo que contiene `["x", "y", "z"]". Este es el caso  ya que la subcadena`"a< algo>c"`  aparece tres veces en la cadena original: la primera contiene `"x"` en el medio, la segunda `"y"` y la última `"z"`. Con estos requisitos en mente, escribimos la implementación que se muestra en el listado.

```
public static String[] substringsBetween(final String str, final String open, final String close) {
  if (str == null || isEmpty(open) || isEmpty(close)) {
           return null;
       }
       
     final int strLen = str.length();
       if (strLen == 0) {
           return EMPTY_STRING_ARRAY;
       }
     final int closeLen = close.length();
     final int openLen = open.length();
     
     final List<String> list = new ArrayList<>();
     int pos = 0;
      while (pos < strLen - closeLen) {
         int start = str.indexOf(open, pos);
           
           if (start < 0) {
               break;
           }
           start += openLen;
           final int end = str.indexOf(close, start);
           if (end < 0) {
               break;
           }
           
           list.add(str.substring(start, end));
           pos = end + closeLen;
       }
       if (list.isEmpty()) {
           return null;
       }
       return list.toArray(EMPTY_STRING_ARRAY);
   }
}


```

**Ejercicio**:  Escribe el código prueba y considera las entradas `str = "axcaycazc"`, `open = "a"` y `close = "c"` y explica lo que hace el código anterior. Utiliza el nombre de `stringUtils.java`

```
package PruebasEspecificacion;
import java.util.ArrayList;
import java.util.List;
``` 

**Ejercicio :**  Mira los requisitos una vez más y escribe todos los casos de prueba que se te ocurran. El formato no importa, puede ser algo así como "todos los parámetros son nulos". Cuando hayas terminado con esta nota, compara tu conjunto de pruebas inicial con el que estamos a punto de derivar. 


### Paso 1: Comprensión de los requisitos, entradas y salidas 

Para el método `substringsBetween()`:  

1. El objetivo de este método es recopilar todas las subcadenas en una cadena que están delimitadas por una etiqueta `open` y una etiqueta `close` (el usuario las proporciona). 

2. El programa recibe tres parámetros: 
  
  - `str`, que representa la cadena de la que el programa extraerá las subcadenas 
  - La etiqueta `open`, que indica el inicio de una subcadena 
  - La etiqueta `close`, que indica el final de la subcadena 

El programa devuelve un arreglo compuesto por todas las subcadenas encontradas por el programa. 

### Paso 2: Explora lo que hace el programa para varias entradas

El proceso es el siguiente (escribe el código JUnit en el listado siguiente): 

Pasa la cadena `"abcd"` con la etiqueta `open "a"` y la etiqueta `close "d"`. Se espera que devuelva un arreglo con un solo elemento: `["bc"]`. Intenta eso (en una prueba unitaria) y que el programa devuelve lo que se esperaba. 

A continuación, vemos qué sucede si hay varias subcadenas en la cadena principal. Pasa la cadena `"abcdabcdab"` con las mismas etiquetas de `open` y `close`. Se  espera que devuelva un arreglo con dos cadenas: `["bc", "bc"]`. El programa devuelve lo que se esperaba. 

Se espera que el programa se comporte de la misma manera con etiquetas `open` y `close` más grandes que un solo carácter. Repite la segunda prueba, doblando las `"a"` y las `"d"` en todos los parámetros. También cambia uno de los `"bc"` a `"bf"`, para que sea más fácil comprobar que el método devuelve dos subcadenas diferentes: `["bc", "bf"]`. 


```
@Test
void simpleCase(){
   assertThat(
           stringUtils.substringsBetween("abcd", "a", "d")
   ).isEqualTo(new String[] { "bc" });
}

@Test
void manyStrings(){
   assertThat(
           stringUtils.substringsBetween("abcdabcdab", "a", "d")
   ).isEqualTo(new String[] { "bc", "bc" });
}
@Test
void openAndCloseTagsThatAreLongerThan1Char(){
   assertThat(
           stringUtils.substringsBetween("aabcddaabfddaab", "aa", "dd")
   ).isEqualTo(new String[] { "bc", "bf" });
   }
}

```

