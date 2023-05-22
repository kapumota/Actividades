## Curso de Desarrollo de Software

En esta actividad debes crear un proyecto llamado `PruebasEspecificaciones` y configurar el archivo `pom.xml` para utilizar JUnit 5.

Sube el proyecto a un repositorio con el nombre de la actividad de manera grupal con todos tus resultados.


### Pruebas basadas en especificaciones 

Los requisitos de software son, sin duda, el artefacto más valioso de las pruebas de software. En esta actividad, exploramos las pruebas basadas en especificaciones. 

Estas técnicas utilizan los requisitos del programa, como historias de usuarios ágiles o casos de uso de UML, como entrada de prueba.

#### Los requisitos lo dicen todo 

Implementa: un método que busca subcadenas entre dos etiquetas en una cadena determinada y devuelve todas las subcadenas coincidentes. Llamemos a este método `substringsBetween()`.

Requisitos para el método substringsBetween(): 

`Método: substringsBetween()`

Busca en una cadena subcadenas delimitadas por una etiqueta de `start` y `end` y devuelve todas las subcadenas coincidentes en un arreglo. 

- `str`: la cadena que contiene las subcadenas. `Null` devuelve `null`, una cadena vacía devuelve otra cadena vacía. 
- `open`: la cadena que identifica el inicio de la subcadena. Una cadena vacía devuelve `null`. 
- `close`: la cadena que identifica el final de la subcadena. Una cadena vacía devuelve `null`. 

**Ejemplo:** si `str = "axcaycazc"`, `open = "a"` y `close = "c"`, la salida será un arreglo que contiene `["x", "y", "z"]`. Este es el caso  ya que la subcadena`"a< algo>c"`  aparece tres veces en la cadena original: la primera contiene `"x"` en el medio, la segunda `"y"` y la última `"z"`. 

Con estos requisitos en mente, escribimos la implementación que se muestra en el listado. Utiliza el nombre de `stringUtils.java`.

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

**Ejercicio**:  Escribe el código prueba y considera las entradas `str = "axcaycazc"`, `open = "a"` y `close = "c"` y explica lo que hace el código anterior. 

```
package PruebasEspecificacion;
import java.util.ArrayList;
import java.util.List;
``` 

**Ejercicio :**  Mira los requisitos una vez más y escribe todos los casos de prueba que se te ocurran. El formato no importa, puede ser algo así como "todos los parámetros son nulos". 

Cuando hayas terminado con esta parte, compara tu conjunto de pruebas inicial con el que estamos a punto de derivar. 


#### Paso 1: Comprensión de los requisitos, entradas y salidas 

Para el método `substringsBetween()`:  

1. El objetivo de este método es recopilar todas las subcadenas en una cadena que están delimitadas por una etiqueta `open` y una etiqueta `close` (el usuario las proporciona). 

2. El programa recibe tres parámetros: 
  
  - `str`, que representa la cadena de la que el programa extraerá las subcadenas 
  - La etiqueta `open`, que indica el inicio de una subcadena 
  - La etiqueta `close`, que indica el final de la subcadena 

El programa devuelve un arreglo compuesto por todas las subcadenas encontradas por el programa. 

#### Paso 2: Explora lo que hace el programa para varias entradas

El proceso es el siguiente (escribe el código JUnit en el listado siguiente): 

Pasa la cadena `"abcd"` con la etiqueta `open "a"` y la etiqueta `close "d"`. Se espera que devuelva un arreglo con un solo elemento: `["bc"]`. 

Intenta eso (en una prueba unitaria) y que el programa devuelve lo que se esperaba. 

A continuación, observa qué sucede si hay varias subcadenas en la cadena principal. Pasa la cadena `"abcdabcdab"` con las mismas etiquetas de `open` y `close`. Se  espera que devuelva un arreglo con dos cadenas: `["bc", "bc"]`. 

Se espera que el programa se comporte de la misma manera con etiquetas `open` y `close` más grandes que un solo carácter. Repite la segunda prueba usando `"a"` y `"d"` en todos los parámetros. También cambia uno de los `"bc"` a `"bf"`, para que sea más fácil comprobar que el método devuelve dos subcadenas diferentes: `["bc", "bf"]`. 

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

**Ejercicio:** Escribe código de prueba  en un archivo llamado `stringUtilsExploracionTest.java` que alberge el código anterior.


#### Paso 3: Explora las posibles entradas y salidas e identifica las particiones

Deberíamos encontrar una manera de priorizar y seleccionar un subconjunto de entradas y salidas que nos brinde suficiente certeza sobre la corrección del programa.

 En el caso del ejemplo, para fines de prueba, la entrada  `"abcd"` con la etiqueta `open "a"` y  la etiqueta `close "d"`, que hace que el programa devuelve `"bc"`, es lo mismo que la entrada `"xyzw"` con la etiqueta `open "x"` y la etiqueta `close "w"`. Cambia las letras, pero se espera que el programa haga lo mismo para ambas entradas. 
 
Dadas las limitaciones de recursos, debes probar solo una de estas entradas (no importa cuál) y confiar en que este caso único representa toda la clase de entradas. Al probar la terminología, decimos que estas dos entradas son equivalentes. 

Una vez que hayas identificado esta clase (o partición), repite el proceso y buscas otra clase que haga que el programa se comporte de una forma diferente a la que aún no has probado. 

Si continúas dividiendo el dominio, eventualmente identificará todas las diferentes clases posibles (o particiones) de entradas. 

Una forma sistemática de hacer tal exploración es pensar en lo siguiente: 

1. Cada entrada individualmente: ¿Cuáles son las posibles clases de entradas que puedo proporcionar?.

2. Cada entrada en combinación con otras entradas: ¿Qué combinaciones puedo probar entre las etiquetas `open` y `close`?

3. Las diferentes clases de resultados que se esperan de este programa: ¿Devuelve arreglos?. ¿Puede devolver un arreglo vacío?. ¿Puede devolver valores nulos? 

Empezamos con entradas individuales: 

* Parámetro `str`: la cadena puede ser cualquier cadena.

  a. Cadena `null` 
  
  b. Cadena vacía 
  
  c. Longitud de cadena 1
  
  d. Longitud de cadena > 1 (alguna cadena)

* Parámetro `open`: esto también puede ser cualquier cosa. 

  a. Cadena `null` 
  
  b. Cadena vacía 
  
  c. Longitud de cadena 1
  
  d. Longitud de cadena > 1 

* Parámetro `close`: Este parámetro es como el anterior: 

  a. Cadena `null` 
  
  b. Cadena vacía 
  
  c. Longitud de cadena 1
  
  d. Longitud de cadena > 1 (alguna cadena)

Exploramos posibles combinaciones de variables.

* parámetros `(str, open, close)` - `open` y `close` pueden o no estar en la cadena. Además, `open` puede estar allí  pero no `close` (y viceversa). 

  a. `str` no contiene ni la etiqueta de `open` ni la de `close`.
  
  b. `str` contiene la etiqueta `open` pero no la etiqueta `close`. 
  
  c. `str` contiene la etiqueta de `close` pero no la etiqueta de `open`. 
  
  d. `str` contiene las etiquetas de `open` y `close`. 
  
  e. `str` contiene las etiquetas de `open` y `close` varias veces. 

Reflexionamos sobre los posibles resultados. El método devuelve un arreglo de subcadenas. Hay un conjunto de posibles salidas diferentes, tanto para el arreglo como para las cadenas dentro del arreglo: 

* Arreglo de cadenas (salida) 
  
  a. Arreglo `null` 
  
  b. Arreglo vacío

  c. Único item 

  d. Múltiples items

* Cada cadena individual (salida) 

  a. Vacío
 
  b. Único caracter
 
  c. Múltiples caracteres

#### Paso 4: Analiza los límites

Cuando diseñamos particiones, tienen límites cercanos con las otras particiones. 

Imagina un programa simple que imprime `"hiphip"` si la entrada dada es un número menor que 10 o `"hurra"` si la entrada dada es mayor o igual a 10. 

Un evaluador puede dividir el dominio de entrada en dos particiones: (1) el conjunto de entradas que hacen que el programa imprima `"hiphip"` y (2) el conjunto de entradas que hacen que el programa imprima `"hurra"`. 

Ten en cuenta que el valor de entrada 9 pertenece a la partición `"hiphip"`, mientras que el valor de entrada 10 pertenece a la partición `"hurra"`.

Las probabilidades de que un programador escriba un error cerca del límite (en este caso, cerca de los valores de entrada 9 y 10) son mayores que para otros valores de entrada. 

De esto se trata la prueba de límites: hacer que el programa se comporte correctamente cuando las entradas están cerca de un límite. Y de esto se trata este cuarto paso: prueba de límites.

Siempre que se identifique un límite, se sugiere que pruebes lo que sucede con el programa cuando las entradas van de un límite a otro.

El truco para explorar los límites es observar todas las particiones y pensar en las entradas entre ellas. Cada vez que encuentres uno que valga la pena probar, prueba. 

En el ejemplo, se produce un límite cuando la cadena pasa de vacía a no vacía, ya que se sabe que el programa deja de devolver vacío y (posiblemente) comenzará a devolver algo. 

Ya cubriste este límite, ya que tienes particiones para ambos casos. A medida que examinas cada partición y cómo se establecen los límites con otras, analiza las particiones en la categoría `(str, open, close)`. 

El programa no puede tener subcadenas, una subcadena o varias subcadenas. Y las etiquetas de `open` y `close` pueden no estar en la cadena; o, lo que es más importante, pueden estar en la cadena, pero sin una subcadena entre ellos. ¡Este es un límite que debes ejercitar!.

Cada vez que identificamos un límite, ideamos dos pruebas, uno para cada lado del límite. Para el límite `'sin subcadena'/'una subcadena'`, las dos pruebas son las siguientes: 

* `str` contiene etiquetas `open` como `close`, sin caracteres entre ellas. 
* `str` contiene etiquetas `open` como `close`, con caracteres entre ellas. 

La segunda prueba no es necesaria en este caso, ya que otras pruebas ya ejercen esta situación. Por lo tanto, podemos descartar. 

#### Paso 5: Idea casos de prueba 

Con las entradas, salidas y límites correctamente diseccionados, podemos generar casos de prueba concretos.  

**Ejercicio:**  ¿En el ejemplo cuál es el número de pruebas? 

Puede haber otras particiones que no necesiten combinarse por completo. En este problema hay dos: 

- Para el caso de `cadena de longitud 1`, dado que la cadena tiene longitud 1, dos pruebas pueden ser suficientes: una en la que el carácter único de la cadena coincida con  `open` y `close` y otra en la que no. 

- A menos que tengamos una buena razón para creer que el programa maneja etiquetas de `open` y `close` de diferentes longitudes de diferentes maneras, no necesitamos las cuatro combinaciones de `(longitud open = 1, longitud close = 1)`, `(longitud open > 1, longitud close = 1)`, `(longitud open = 1, longitud close > 1)` y `(longitud open > 1, longitud close > 1)`. 

Solo `(longitud open = 1, longitud close = 1)` y `(longitud open > 1, longitud close > 1)`  son suficientes.

**Ejercicio:**  ¿Hay más casos donde se pueda simplificar el número de pruebas? 

Con una comprensión clara de qué particiones deben probarse exhaustivamente y cuáles no, podemos derivar los casos de prueba realizando la combinaciones. 

`Casos excepcionales`

* T1: `str` es `null`. 
* T2: `str` está vacío.
* T3: `open` es null. 
* T4: `open` está vacío. 
* T5: `close` es nulo. 
* T6: `close` está vacío. 

`longitud str = 1`: 

* T7: el carácter único en `str` coincide con la etiqueta `open`. 
* T8: El carácter único en `str` coincide con la etiqueta `close`. 
* T9: El carácter único en `str` no coincide ni con la etiqueta  `open` ni `close`. 
* T10: el carácter único en `str` coincide con las etiquetas `open` y `close`. 

`longitud str  > 1`, `longitud open = 1`, `close = 1`: 

* T11: `str` no contiene ni la etiqueta `open` ni  `close`. 
* T12: `str` contiene la etiqueta `open` pero no contiene la etiqueta `close`. 
* T13: `str` contiene la etiqueta `close` pero no contiene la etiqueta `open`. 
* T14: `str` contiene las etiquetas `open` y `close`. 
* T15: `str` contiene las etiquetas `open` y close` varias veces. 

A continuación, `longitud str > 1`, `longitud open > 1`, `close > 1`: 

* T16: `str` no contiene ni la etiqueta `open` ni `close`. 
* T17: `str` contiene la etiqueta `open` pero no contiene la etiqueta `close`. 
* T18: `str` contiene la etiqueta `close` pero no contiene la etiqueta `open`. 
* T19: `str` contiene las etiquetas `open` y `close`. 
* T20: `str` contiene las etiquetas `open` y `close` varias veces. 

Finalmente, aquí está la prueba para el límite: 

* T21: `str` contiene las etiquetas `open` y `close` sin caracteres entre ellas. 

Terminamos con 21 pruebas.

#### Paso 6: Automatiza los casos de prueba 

Ahora es el momento de transformar los casos de prueba en pruebas JUnit automatizadas. Escribir esas pruebas es principalmente una tarea mecánica. 

Cada llamada al método `substringsBetween` es uno de los casos de prueba. Las 21 llamadas se distribuyen entre los métodos de prueba, cada una de las cuales coincide con los casos de prueba que ideamos anteriormente.

Primero están las pruebas relacionadas con que la cadena sea nula o vacía. 

```
@Test
   void strlsNullOrEmpty(){
       assertThat(substringsBetween(null, "a", "b"))
               .isEqualTo(null);
       assertThat(substringsBetween("", "a", "b"))
               .isEqualTo(new String[]{});
   }

```

A continuación están todas las pruebas relacionadas con `open` o `close` sean nulas o vacías.

```
@Test
void openIsNullOrEmpty(){
   assertThat(substringsBetween("abc", null, "b"))
           .isEqualTo(null);
   assertThat(substringsBetween("abc", "a", ""))
           .isEqualTo(null);
}
@Test
void closeIsNullOrEmpty() {
     assertThat(substringsBetween("abc", "a", null))
.isEqualTo(null);
assertThat(substringsBetween("abc", "a", ""))
.isEqualTo(null);
}

```
Ahora vienen todas las pruebas relacionadas con cadenas y etiquetas `open` y `close` con longitud 1.


```
@Test
void strOfLegth1(){
   assertThat(substringsBetween("a", "a", "b")).isEqualTo(null);
   assertThat(substringsBetween("a", "b", "a")).isEqualTo(null);
   assertThat(substringsBetween("a", "b", "b")).isEqualTo(null);
   assertThat(substringsBetween("a", "a", "a")).isEqualTo(null);

}

void openAndCloseOfLenght1(){
   assertThat(substringsBetween("abc", "x", "y")).isEqualTo(null);
   assertThat(substringsBetween("abc", "a", "y")).isEqualTo(null);
   assertThat(substringsBetween("abc", "x", "c")).isEqualTo(null);
   assertThat(substringsBetween("abc", "a", "c")).isEqualTo(new String[] { "b", "b"});
}

```

Luego tenemos las pruebas para las etiquetas `open` y `close` de diferentes tamaños.

```
@Test
void openAndCloseTagsOfDifferentSizes() {
   assertThat(substringsBetween("aabcc", "xx", "yy")).isEqualTo(null);
   assertThat(substringsBetween("aabcc", "aa", "yy")).isEqualTo(null);
   assertThat(substringsBetween("aabcc", "xx", "cc")).isEqualTo(null);
   assertThat(substringsBetween("aabbcc", "aa", "cc"))
           .isEqualTo(new String[] {"bb"});
   assertThat(substringsBetween("aabbccaaeecc", "aa", "cc"))
           .isEqualTo(new String[] {"bb", "ee"});
}

```

Finalmente, aquí está la prueba para cuando no hay una subcadena entre las etiquetas `open` y `close`.

```
 @Test
   void notSubstringBetweenOpenAndCloseTags(){
       assertThat(substringsBetween("aabb", "aa", "bb"))
               .isEqualTo(new String[] { ""});
   }
}
```

Algunos desarrolladores garantizarían un solo método por caso de prueba, lo que significaría 21 métodos de prueba, cada uno con una llamada de método y una aserción. 

La ventaja sería que el nombre del método de prueba describiría claramente el caso de prueba. 

JUnit también ofrece la función de prueba parametrizada: 

https://junit.org/junit5/docs/5.3.0/user-guide/#writing-tests-parameterized-tests que podría usarse en este caso. 

**Ver:** [Writing A Parameterized Test In JUnit With Examples](https://coderpad.io/blog/development/writing-a-parameterized-test-in-junit-with-examples/).

#### Paso 7: Aumenta el conjunto de pruebas con creatividad y experiencia

Siempre es bueno tener variación en las pruebas. 

Por ejemplo, cuando revisas las pruebas, nota que nunca probamos cadenas con espacios. Entonces se diseñaron dos pruebas adicionales basadas en T15 y T20  una para etiquetas `open`  y `close` con longitudes 1, otra para etiquetas `open` y `close` con longitudes más grandes. 

Estos verifican si la implementación funciona si hay espacios en blanco en la cadena.

```
@Test
void openAndCloseOfLength1() {
// ... aseveraciones previas
    That(substringsBetween("abcabyt byrc", "a", "c"))
.isEqualTo(new String[ ] {"b", "byt byr"});
   }
@Test
void openAndCloseTagsOfDifferentSizes() {
// ...aseveraciones previas
   assertThat(substringsBetween("a abb ddc ca abbcc", "a a", "c c"))
. isEqualTo(new String[ ]      {"bb dd"});
}

``` 
**Ejercicio:** Escribe un archivo `stringUtilsTest.java` y completa el código anterior.  

Terminamos con 23 casos de prueba. Revisa todos los pasos que hemos trabajado y luego considera esta pregunta: ¿hemos terminado?.

**Ejercicio:** Escribe el siguiente  archivo de código `NumberUtils.java`

```
public class NumberUtils {
   public static List<Integer> add(List<Integer> left, List<Integer> right) {

       if (left == null || right == null)
           return null;

       Collections.reverse(left);
       Collections.reverse(right);

       LinkedList<Integer> result = new LinkedList<>();

       int aumento = 0;

       for (int i = 0; i < Math.max(left.size(), right.size()); i++) {

           int leftDigit = left.size() > i ? left.get(i) : 0;
           int rightDigit = right.size() > i ? right.get(i) : 0;

           if (leftDigit < 0 || leftDigit > 9 || rightDigit < 0 ||  rightDigit > 9)
               throw new IllegalArgumentException();

           int sum = leftDigit + rightDigit + aumento;

           result.addFirst(sum % 10);
           aumento = sum / 10;
       }

       if (aumento > 0)
           result.addFirst(aumento);

       while (result.size() > 1 && result.get(0) == 0)
           result.remove(0);

       return result;
   }

}
``` 

El método recibe dos números `left`  y `right` (cada uno representado como una lista de dígitos), los suma y devuelve el resultado como una lista de dígitos. Cada elemento de las listas de dígitos `left` y `right` debe ser un número del `[0 al 9]`. Se lanza una `IllegalArgumentException` si esta pre-condición  no se cumple. 

- `left`- una lista que contiene el número de la izquierda. `Null` devuelve `null`, vacío significa 0. 

- `right`- una lista que contiene el número correcto. `Null` devuelve `null`, vacío significa 0. 

El programa devuelve la suma de `left` y `right` como una lista de dígitos. 

Por ejemplo, agregar los números 23 y 42 significa una lista (left) con dos elementos `[2,3]`, una lista (right) con dos elementos `[4,2]` y como salida, una lista con dos elementos `[6 ,5]` (ya que 23 + 42 = 65). 

**Ejercicio:** Explica el funcionamiento del algoritmo anterior.

**Ejercicio:** Escribe un programa llamado `NumberUtilsNonSystematicTest.java` de la siguiente manera:

```
public class NumberUtilsNonSystematicTest {
   @Test
   void t1() {
       assertThat(new NumberUtils().add(numbers(1), numbers(1)))
               .isEqualTo(numbers(2));

       assertThat(new NumberUtils().add(numbers(1,5), numbers(1,0)))
               .isEqualTo(numbers(2, 5));

       assertThat(new NumberUtils().add(numbers(1,5), numbers(1,5)))
               .isEqualTo(numbers(3,0));

       assertThat(new NumberUtils().add(numbers(5,0,0), numbers(2,5,0)))
               .isEqualTo(numbers(7,5,0));
   }

   private static List<Integer> numbers(int... nums) {
       List<Integer> list = new ArrayList<>();
       for(int n : nums)
           list.add(n);
       return list;
   }

```

**Ejercicio:** ¿Funciona el código anterior para las siguientes expresiones?

```
T1 = [1] + [1] = [2] 
T2 = [1,5] + [1,0] = [2,5] 
T3 = [1,5] + [1,5] = [3,0] 
T4 = [5,0,0] + [2,5,0] = [7,5,0]
```


* `Parámetro left:` es una lista, por lo que primero debemos ejercitar las entradas básicas como nulo, vacío, un solo dígito y varios dígitos. Dado que esta lista representa un número, también deberíamos probar con un número con muchos ceros a la izquierda. Dichos ceros son inútiles, pero es bueno ver si la implementación puede manejarlos. 

  Así tenemos las siguientes particiones:

  - Vacío
  - `Null`
  - Un solo dígito
  - Múltiples dígitos
  - Ceros a la izquierda
 
* `parámetro right:` tenemos la misma lista de particiones que para el parámetro `left`: 

  - Vacío
  - `Null`
  - Un solo dígito
  - Múltiples dígitos
  - Ceros a la izquierda

`left` y `right` tienen una relación. Exploremos eso: 

* `Parámetros  (left, right)`: pueden tener diferentes tamaños y el programa debería poder manejarlos:

  - `length( lista left ) > length( lista right )`
  - `length( lista left ) < length( lista right )`
  - `length( lista left ) = length( lista right )`

Algunas sumas requieren aumento. El **aumento** es un concepto importante en este programa que merece ser probado.  Por ejemplo, supongamos que estamos sumando `18 + 15: 8 + 5 = 13` lo que significa que tenemos un 3 y llevamos +1 al dígito siguiente. Luego sumamos `1 + 1 + 1`: el primer 1 del número de la izquierda, el segundo 1 del número de la derecha y el tercero de la suma anterior. El resultado final es 33. 

* `Aumento:` probemos sumas que requieren aumentos de muchas maneras diferentes. Estos son buenos lugares para comenzar:

  - Suma sin `aumento`
  - Suma con `aumento`: un `aumento` al principio
  - Suma con `aumento`: un `aumento` en el medio 
  - Suma con `aumento` : muchos `aumentos` 
  - Suma con `aumento`: muchos `aumentos`, no seguidos 
  - Suma con `aumento`: `aumento` propagado a un dígito nuevo (el más significativo) 


El único límite que vale la pena probar es el siguiente: garantizar que casos como `99 + 1` (donde el número final se lleva a un nuevo dígito más significativo) están cubiertos. Esto viene de la última partición derivada al analizar el aumento: `"Suma con un aumento: aumento propagado a un nuevo dígito (el más significativo)"`.` 

Con todas las entradas y salidas analizadas, es hora de derivar casos de prueba concretos. 

Apliquemos la siguiente estrategia: 

1. Prueba `null` y vacíos solo una vez. 

2. Prueba con un solo dígito solo una vez. 

3. Prueba con números con múltiples dígitos, con `left` y `right` con longitudes iguales y diferentes. Tendremos el mismo conjunto de pruebas para longitudes iguales y diferentes, y duplicaremos el conjunto de pruebas para garantizar que todo funcione si `left` es más larga que `right` o viceversa. 

4. Ejercitaremos los ceros de la izquierda, pero unos pocos casos de prueba son suficientes. 

5. Prueba el límite. 

Veamos los casos de prueba específicos: 

* `Null` y vacíos

  - T1: `left`  `null` 
  - T2: `left` vacío 
  - T3: `right` null 
  - T4: `right` vacío

* Dígitos individuales
 
  - T5: un solo dígito, sin `aumento`
  - T6: un solo dígito, `aumento`

* Múltiples dígitos
 
  - T7: sin `aumento`
  - T8: `aumento` en el dígito menos significativo
  - T9: `aumento` en el medio 
  - T10: muchos `aumentos`
  - T11: muchos `aumentos` no seguidos
  - T12: `aumento` propagado a un dígito nuevo (ahora el más significativo) 

* Múltiples dígitos con diferentes longitudes (uno para `left` más largo que `right` y otro para `right` más larga a `left`) 

  - T13: sin `aumento`
  - T14: `aumento` en el dígito menos significativo 
  - T15: `aumento` en el medio
  - T16: muchos `aumentos` 
  - T17: muchos `aumentos`, no seguidos 
  - T18: `aumento` propagado a un dígito nuevo (ahora el más significativo) 

* Ceros a la izquierda 

  - T19: sin `aumento`
  - T20: con `aumento`
 
* Límites
  
  - T21: `aumento` a un nuevo dígito más significativo, por uno (como 99 +1).

**Ejercicio:** Transforma los casos de prueba automatizados, creando una prueba parametrizada llamada `NumberUtilsTest.java`. ¿Qué sucede ahora con los resultados? Por ejemplo `left = [9]` y `right = [2]`,  qué valor genera  el programa y cuando  `left = [9,9,8]` y `right = [1,7,2]` ? . ¿El programa maneja ceros a la izquierda?. Agrega código al programa realizado. 


**Ejercicio:** Escribe un caso de prueba de que se cumple la precondición de que cada dígito sea un número entre 0 y 9. Realiza la prueba en JUnit de la siguiente manera.

```
@ParameterizedTest
@MethodSource("digitsOutOfRange")
void shouldThrowExceptionWhenDigitsAreOutOfRange(List<Integer> left, List<Integer> right) {
   assertThatThrownBy(() -> new NumberUtils().add(left, right))
           .isInstanceOf(IllegalArgumentException.class);

}

static Stream<Arguments> digitsOutOfRange() {
   return Stream.of(
           of(numbers(1,-1,1), numbers(1)),
           of(numbers(1), numbers(1,-1,1)),
           of(numbers(1,11,1), numbers(1)),
           of(numbers(1), numbers(1,11,1))
   );
}
```

**Ejercicio:** Analiza los códigos de cobertura antes y después de los cambios. 

