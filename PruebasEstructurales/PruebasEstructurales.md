## Curso de Desarrollo de Software

En esta actividad debes crear un proyecto llamado `PruebasEstructurales` y configurar el archivo `pom.xml` para utilizar JUnit 5.

Sube el proyecto a un repositorio con el nombre de la actividad de manera individual con todos tus resultados.


### Pruebas estructurales 

En esta actividad, aprendemos cómo utilizar sistemáticamente el código fuente, ver como se está ejerciendo el conjunto de pruebas que derivamos con la ayuda de las especificaciones y qué nos queda probar. 

El uso de la estructura del código fuente para guiar las pruebas también se conoce como pruebas estructurales. Comprender las técnicas de prueba estructural significa comprender los criterios de cobertura. 

#### Cobertura de código

Considera el siguiente requisito para un pequeño programa que cuenta la cantidad de palabras en una cadena que terminan con "r" o "s" : 

Dada una oración, el programa debe contar la cantidad de palabras que terminan con "s" o "r". 
Una palabra termina cuando aparece una no letra. El programa devuelve el número de palabras. 

Un desarrollador implementa este requisito como se muestra en la siguiente lista. Archivo `CountWords.java``.

```
public class CountWords {
   public int count(String str) {
     int words = 0;
          char last = ' ';
   
    for (int i = 0; i < str.length(); i++) {  // 1

        if (!isLetter(str.charAt(i)) &&    // 2
                (last == 's' || last == 'r')) {
               words++;
                }
        last = str.charAt(i);     //3
    }
   if (last == 'r' || last == 's') {
        words; 
          }
       return words;
   }

```

**Pregunta:** explica qué hacen las líneas 1, 2, 3 en el código. 

Ahora, considera a un desarrollador que no sabe mucho sobre técnicas de prueba basadas en especificaciones y escribe las siguientes dos pruebas JUnit para la implementación. 
Archivo `CountWordTest.java`.


```
@Test
void twoWordsEndingWithS() {   // 1
    int words = new CountLetters().count("dogs cats");
         assertThat(words).isEqualTo(2);
}
@Test
void noWordsAtAll() {  // 2
   int words = new CountLetters().count("dog cat");
        assertThat(words).isEqualTo(0);
}
```

**Pregunta:** explica qué hacen las líneas 1, 2 del código. Presenta un informe generado por JaCoCo (www.jacoco.org/jacoco) o otra herramienta de código de tu preferencia en el ide del curso.  


Continuando con el ejemplo, escribimos un caso de prueba que ejercita la partición "palabras que terminan en 'r'" de la siguiente manera. 

```
@Test
void wordsThatEndInR() {  // 1
   int words = new CountWords().count("car bar");
    assertThat(words).isEqualTo(2);
}
``` 

**Pregunta:** Explica la línea 1 y con el caso de prueba vuelve  a ejecutar la herramienta de cobertura. Explica los cambios obtenidos.

Si hay partes del código que aún no están cubiertas, repetiremos el proceso: identificamos las partes descubiertas, comprendemos por qué no están cubiertas y escribimos una prueba que ejerza esa parte del código. 

### Pruebas estructurales

En base a lo que acabamos de hacer, definamos un enfoque simple que cualquier desarrollador puede seguir: 

1.Realiza pruebas basadas en especificaciones 
2. Lee la implementación y comprenda las principales decisiones de codificación tomadas por el desarrollador
3. Ejecuta el conjunto de pruebas diseñado con una herramienta de cobertura de código.
4. Para cada pieza de código que no está cubierta: 
   - Comprender por qué no se probó ese fragmento de código. ¿Por qué no vio este caso de prueba durante las pruebas basadas en especificaciones? 
   - Decide si la pieza de código merece una prueba. Probar o no probar ese fragmento de código ahora es una decisión consciente de tu parte. 
   - Si se necesita una prueba, implementa un caso de prueba automatizado que cubra la pieza faltante. 
5. Vuelve al código fuente y busca otras pruebas interesantes ques pueda diseñar basándose en el código. Para cada pieza identificada del código, realiza los subpasos del paso 4.

Lo más importante de este enfoque es que las pruebas estructurales complementan el conjunto de pruebas previamente diseñado a través de pruebas basadas en especificaciones. 

La herramienta de cobertura de código es una forma automatizada de identificar partes que no están cubiertas. Al igual que el enfoque anterior, este pretende ser iterativo y no limitarse a una única forma de trabajar.

### Criterios de cobertura del código

 Cada vez que identificamos una línea de código que no está cubierta, tenemos que decidir qué tan minuciosos (o rigurosos) queremos ser al cubrir esa línea. Revisemos una declaración `if` del programa `CountWords`.
 
```
if (!Character.isLetter(str.charAt(i)) && (last == 's' || last == 'r'))
```

Un desarrollador puede decidir cubrir solo la línea; en otras palabras, si una prueba pasa a través de esa línea, el desarrollador la considerará cubierta. Un solo caso de prueba puede hacer esto. 

Un segundo desarrollador  puede cubrir el `if`  se evalúa como `true` y `false` hacerlo requiere dos casos de prueba. 

Un tercer desarrollador puede explorar cada condición en la instrucción `if`. Este particular `if` tiene tres condiciones que requieren al menos dos pruebas cada una, para un total de seis pruebas. Finalmente, un evaluador  muy minucioso puede decidir cubrir todas las rutas de ejecución posibles de esta declaración. Dado que tiene tres condiciones diferentes, hacerlo requiere 2 × 2 × 2 = 8 casos de prueba. 

#### Cobertura de línea 

Un desarrollador que tiene como objetivo lograr la cobertura de línea necesita al menos un caso de prueba que cubra la línea bajo prueba. No importa si esa línea contiene una declaración `if` compleja. Si una prueba toca esa línea de alguna manera, el desarrollador puede contar la línea como cubierta. 

#### Cobertura de ramas

La cobertura de ramas tiene en cuenta el hecho de que las instrucciones de ramas (`ifs`, `fors`, `whiles` etc.) hacen que el programa se comporte de diferentes maneras, dependiendo de cómo se evalúe la instrucción. Para una declaración `if(a && b)` simple, tener un caso de prueba T1 que haga que la declaración `if` sea verdadera y otro caso de prueba T2 que haga que la declaración sea falsa es suficiente para considerar la rama cubierta. 

La figura siguiente ilustra un gráfico de flujo de control (CFG) del programa `CountWords`.

![](https://github.com/kapumota/Actividades/blob/main/PruebasEstructurales/Imagenes/cobertura1.png)

Cubrir todos los bordes del grafo significa lograr una cobertura de rama del 100%. 

#### Condición + cobertura de rama 

`Condición + cobertura de rama` considera no solo las posibles ramas sino también cada condición de cada declaración de rama. 

Por ejemplo, la primera instrucción `if` del programa `CountWords` contiene tres condiciones: `!Character.isLetter(str.charAt(i))`, `last == 's'` y `last == 'r'`. 

Por lo tanto, un desarrollador que busca la `condición + cobertura de rama` debe crear un conjunto de pruebas que ejerza cada una de esas condiciones individuales que se evalúan como `true` y `false` al menos una vez y que la declaración de rama completa sea `true` y `false` al menos una vez. 

Ten en cuenta que mirar a ciegas solo las condiciones (e ignorar cómo se combinan) puede dar como resultado conjuntos de pruebas que no cubren todo. 

Imagina un simple `if(A || B)`. Un conjunto de pruebas compuesto por dos pruebas (T1 que hace que A sea `True` y B `false` y T2 que haga que A sea `false` y B sea `true`) cubre las dos condiciones, ya que cada condición se ejerce como `true` y `false`. Sin embargo, el conjunto de pruebas no cubre completamente la rama, ya que en ambas pruebas, la evaluación de toda la instrucción `if` siempre es `true`. 

Es por eso que usamos la  condición + cobertura de  rama, y no solo la cobertura de condición (básica). 

En el CFG extendido de la figura siguiente, los nodos de rama contienen una sola condición. 

El complicado `if` se divide en tres nodos. Cada condición está en su propio nodo. Cubrir todos los bordes del grafo significa lograr el 100% de `condición +  cobertura de rama`.

![](https://github.com/kapumota/Actividades/blob/main/PruebasEstructurales/Imagenes/Cobertura2.png)


#### Cobertura de ruta 

Un desarrollador que apunta a la cobertura de rutas cubre todas las rutas posibles de ejecución del programa. Si bien idealmente este es el criterio más fuerte, a menudo es imposible o demasiado costoso de lograr. En un solo programa con tres condiciones, donde cada condición podría evaluarse independientemente como `true` o `false`, tendríamos 8 caminos para cubrir. 

En un programa con 10 condiciones, el número total de combinaciones serían 1024. En otras palabras, ¡necesitaríamos idear más de mil pruebas! 

La cobertura de ruta también se vuelve más complicada para programas con bucles. 
En un programa con un bucle ilimitado, el ciclo puede repetirse cientos de veces. Un evaluador  riguroso que busca la cobertura de la ruta tendría que probar el programa con el bucle ejecutándose una vez, dos veces, tres veces, y así sucesivamente. 

#### Manejo de bucles y construcciones similares 

Quizás te preguntes qué hacer en el caso de bucles, como `for` y `while`. El bloque de código dentro del bucle puede ejecutarse un número diferente de veces, lo que complica las pruebas. 

Piensa en un bucle `while (True)`, que puede no terminar. Para ser rigurosos, tendríamos que probar el programa con el bloque de bucle ejecutado una vez, dos veces, tres veces, y así sucesivamente. 

Imagina un bucle `for(i = 0; i < 10; i++)` con un `break` dentro del cuerpo. Tendríamos que probar qué sucede si el cuerpo del bucle se ejecuta hasta 10 veces. ¿Cómo podemos manejar un bucle de larga duración (que se ejecuta durante muchas iteraciones) o un bucle ilimitado (que se ejecuta un número desconocido de veces)? 

Dado que las pruebas exhaustivas son imposibles, los evaluadores a menudo confían en el criterio de adecuación de los límites del bucle para decidir cuándo dejar de probar un bucle. 

Un conjunto de pruebas satisface este criterio si y sólo si para cada bucle:

- Hay un caso de prueba que ejercita el bucle cero veces. 
- Hay un caso de prueba que ejercita el bucle una vez. 
- Hay un caso de prueba que ejercita el bucle varias veces. 

Con todo esto, ¿debería el caso de prueba obligar al bucle a iterar 2, 5 o 10 veces? Esta decisión requiere una buena comprensión del programa y sus requisitos. Con una comprensión óptima de las especificaciones, deberías poder idear buenas pruebas para el bucle. No tengas miedo de crear dos o más pruebas para el caso de "varias veces". Haz lo que tengas que hacer para asegurarte de que el bucle funcione como se esperaba. 

### Pruebas estructurales y basadas en especificaciones 

Probemos las pruebas basadas en especificaciones y las pruebas estructurales juntas en un ejemplo del mundo real: la función `leftPad()` de Apache Commons Lang): 

Completa a la izquierda una cadena con una cadena especificada Completa a un tamaño `size`. 

- `str`: la cadena para completar. Puede ser `null`
- `size`: el tamaño a completar
- `padStr`: la cadena con la que completar. `Null` o vacío se trata como un solo espacio. 

El método devuelve una cadena completada a la izquierda, la cadena original si no se necesita completar o `null` si se ingresa una cadena nula. 

Por ejemplo, si damos "abc" como entrada de cadena, un guión "-" como cadena a completar  y 5 como tamaño, el programa generará "--abc". 

A un desarrollador de tu equipo se le ocurre la siguiente implementación.  Archivo `LeftPadUtils.java`


```
public class LeftPadUtils {

    private static final String SPACE = " ";
         private static boolean isEmpty(final CharSequence cs) {
    	return cs == null || cs.length() == 0;
     }

   /**
   
       * @param size  
       * @param padStr  
       * @return left 
       *  {@code null} 
    */
public static String leftPad(final String str, final int size, String padStr) {
     if (str == null) {  // 1
        	return null;
    }
    if (isEmpty(padStr)) {
        	padStr = SPACE;   //2
    }
             final int padLen = padStr.length();
    	final int strLen = str.length();
    	final int pads = size - strLen;
    	if (pads <= 0) {  // 3
                    return str; 
    	}

    	if (pads == padLen) {  // 4
        	     return padStr.concat(str);
    	} else if (pads < padLen) {  // 5
        	      return padStr.substring(0, pads).concat(str);
    	} else {   // 6
        	       final char[] padding = new char[pads];
        	      final char[] padChars = padStr.toCharArray();
        	     for (int i = 0; i < pads; i++) {
            	 	padding[i] = padChars[i % padLen];
        	     }
        	return new String(padding).concat(str);
    }
   }

}

```

**Pregunta**: explica los comentarios 1, 2, 3, 4 y 5  del código anterior.

Ahora es el momento de algunas pruebas sistemáticas. Como sabemos, el primer paso es aplicar pruebas basadas en especificaciones.
 
**1 Leemos los requisitos**. Entendemos que el programa agrega un carácter/cadena dado al principio (izquierda) de la cadena, hasta un tamaño específico. El programa tiene tres parámetros de entrada: `str`, que representa la cadena original que se va a completar; `size`, que representa el tamaño deseado de la cadena devuelta y `padStr` que representa la cadena utilizada para completar. El programa devuelve un `String`. El programa tiene un comportamiento específico si alguna de las entradas es nula.

2 Con base en todas las observaciones del paso 1, derivamos la siguiente lista de particiones: – parámetro `str` 

– `str` parameter
  - Null
  - Empty string
  - Non-empty string
  -
– `size` parameter
  - Negative number
  - Positive number
 
– `padStr` parameter
   - Null
   - Empty
   - Non-empty

– `str`, `size` parameters
   - `size < len( str )`
   - `size > len( str )`

**3 Hay varios límites:**

- `size` es precisamente 0
- `str` con longitud 1 
- `padStr` con longitud 1 
- `size` es precisamente la longitud de `str`

4 Podemos idear pruebas individuales para casos excepcionales como tamaño nulo, vacío y negativo. También tenemos un límite relacionado con `padStr`: podemos ejercitar `padStr` con un solo carácter solo una vez y hacer que todas las demás pruebas usen un `pad` con un solo carácter (de lo contrario, la cantidad de combinaciones sería demasiado grande). 

Obtenemos las siguientes pruebas: 

- T1: `str` es nulo
- T2: `str` está vacío
- T3:  negativo `size`
- T4: `padStr` es nulo
- T5: `padStr` está vacío.
- T6: `padStr` tiene un solo carácter
- T7: `size` es igual a la longitud de `str`
- T8: `size` es igual a 0
- T9: `size es más pequeño que la longitud de `str`. 


