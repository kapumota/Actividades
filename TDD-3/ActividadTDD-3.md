## Curso de desarrollo de software

###  Actividad grupal

Descarga la actividad desde aquí:https://github.com/kapumota/Actividades/tree/main/TDD-3/TDD-3 

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada TDD-3 y coloca todas tus respuestas e implementaciones.

## El ciclo RGR

Vimos en la actividad anterior cómo una prueba de una sola unidad se divide en tres partes, conocidas  `Arrange`, `Act` y `Assert`. Esto forma de trabajo simple que nos guía a través de la escritura de cada prueba. Nos fuerza a diseñar cómo se utilizará el código: el exterior del código. Si pensamos en un objeto como un límite de encapsulación, tiene sentido hablar de lo que está dentro y fuera de ese límite. **Los métodos públicos forman el exterior del objeto**.


Una vez que hemos escrito la prueba, pasamos a crear el código que está dentro del objeto: los campos y métodos privados. Para ello, hacemos uso de otra técnica llamada `RGR`. Este es un proceso de tres pasos que nos ayuda a generar confianza en la prueba, crear una implementación básica del código y luego refinarlo de manera segura. 

### Comenzando en rojo

![](https://github.com/kapumota/Actividades/blob/main/TDD-3/Imagenes/RGB1.png)

El objetivo de esta fase es utilizar la plantilla `Arrange`, `Act` y `Assert` para poner en marcha la prueba y prepararla para probar el código que escribiremos a continuación. La parte más importante de esta fase es asegurarse de que la prueba no pase. A esto llamamos prueba fallida, o prueba roja, debido al color que la mayoría de las herramientas gráficas de prueba usan para indicar una prueba fallida. 

Queremos que la prueba falle en esta etapa para tener la confianza de que funciona correctamente. Si la prueba pasa en este punto, es una preocupación. ¿Por qué pasa? Sabemos que aún no hemos escrito nada del código que estamos probando. Si la prueba pasa ahora, eso significa que no necesitamos escribir ningún código nuevo o que cometimos un error en la prueba. 

Aquí se tiene un enlace https://medium.com/pragmatic-programmers/3-5-getting-green-on-red-d189240b1c87  con ocho razones por las que una prueba podría no funcionar correctamente. 

### Pásate al verde 

![](https://github.com/kapumota/Actividades/blob/main/TDD-3/Imagenes/RGB2.png)

Una vez que tenemos una prueba fallida, somos libres de escribir el código que la hará pasar. A esto llamamos el **código de producción**, el código que formará parte del sistema de producción. 

Tratamos el código de producción como un componente de caja negra. El componente tiene un interior y un exterior. El interior es donde escribimos el código de producción. Es donde ocultamos los datos y los algoritmos de la implementación. Podemos hacer esto utilizando cualquier enfoque que elijamos: orientado a objetos, funcional, declarativo o procedimental, etc. 

El exterior es la interfaz de programación de aplicaciones (API). Esta es la parte que usamos para conectarnos al componente y usarlo para crear piezas de software más grandes. 

Si elegimos un enfoque orientado a objetos, esta API estará compuesta por métodos públicos en un objeto. 

Con TDD, la primera pieza a la que nos conectamos es la prueba, y eso nos da una respuesta rápida sobre qué tan fácil es usar la conexión. 

El siguiente diagrama muestra las diferentes piezas: el interior, el exterior, el código de prueba y otros usuarios del componente: 


![](https://github.com/kapumota/Actividades/blob/main/TDD-3/Imagenes/Componentes.png)


## Refactorización para limpiar el código

![](https://github.com/kapumota/Actividades/blob/main/TDD-3/RGB3.png)

Esta es la fase en la que entramos en modo de ingeniería de software. 

Tenemos un código simple y funcional con una prueba que pasa. 

Ahora es el momento de refinar eso en un código limpio, es decir, un código que será fácil de leer más adelante. 

Algunos ejemplos de técnicas de refactorización que podemos usar durante esta fase incluyen los siguientes: 

- Extracción de un método para eliminar el código duplicado 
- Cambiar el nombre de un método para expresar lo que hace mejor 
- Cambiar el nombre de una variable para expresar mejor lo que contiene 
- Dividir un método largo en varios más pequeños 
- Extraer una clase más pequeña
- Combinar una larga lista de parámetros en su propia clase 

Todas estas técnicas tienen un objetivo: hacer que el código sea más fácil de entender. Esto hará que sea más fácil de mantener. 

Al final de esta fase, tendremos una prueba unitaria que cubrirá un fragmento de código de producción que hemos diseñado para que sea fácil trabajar con él en el futuro. 

### Escribiendo las pruebas para Wordz 

Para la próxima prueba, una buena opción es ir a lo seguro y dar un pequeño paso más. Agregaremos una prueba para una sola letra correcta. Esto eliminará la primera pieza de lógica de aplicación genuina: 

1. Comencemos en rojo. Escribe una prueba fallida para una sola letra correcta:

```
@Test
public void oneCorrectLetter() {
   var word = new Word("A");
   var score = word.guess("A");
   assertThat(score.letter(0))
  	.isEqualTo(Letter.CORRECT);
}
``` 
Esta prueba  comprueba si una letra es correcta, en lugar de incorrecta 

Realizamos los cambios en la clase `Word`. 

2. Ahora pasemos a verde agregando el código de producción para que pase la prueba: 

```
public class Word {
	private final String word;
	public Word(String correctWord) {
	     this.word = correctWord;
	}
	
  public Score guess(String attempt) {
    	  var score = new Score(word);
    	  score.assess( 0, attempt );
    	  return score;
	}
}
``` 
El objetivo aquí es lograr que se apruebe la nueva prueba mientras se mantiene la aprobación de la prueba existente. No queremos romper ningún código existente. 

Usamos el IDE para ayudarnos a escribir class `Score`: 

```
public class Score {
      private final String correct;
      private Letter resultado = Letter.INCORRECT ;
	
      public Score(String correct) {
     	  this.correct = correct;
	}
      public Letter letter(int position) {
    	   return resultado;
	}
   
   public void assess(int position, String attempt) {
    	if ( correct.charAt(position) == attempt.
        	   charAt(position)){
        	   resultado = Letter.CORRECT;
    	}
   }
}
```
**Pregunta** Ejecuta todas las pruebas para ver cómo lo estamos haciendo. Explica tus resultados.


Ahora entramos en la fase de refactorización del ciclo RGR. Una vez más, TDD nos devuelve el control. ¿Queremos refactorizar? ¿Qué cosas debemos refactorizar? ¿Por qué? ¿Vale la pena hacer esto ahora o podemos posponerlo hasta un paso posterior? 

Revisemos el código y busquemos olores de código. Un olor de código es una indicación de que la implementación puede necesitar mejoras. 

Lectura: la definición de olor de código se puede encontrar en [https://wiki.c2.com/?CodeSmell](https://wiki.c2.com/?CodeSmell).


3. Vamos a refactorizar después de analizar el método `assess()`. Extrae un método `isCorrectLetter()` para mayor claridad:

``` 
public void assess(int position, String attempt) {
	if (isCorrectLetter(position, attempt)){
    	    resultado = Letter.CORRECT;
	}
}
private boolean isCorrectLetter(int position,
                            	String attempt) {
	return correct.charAt(position) ==
       		attempt.charAt(position);
}
```


Hay dos áreas más que refactorizar en esta etapa. El primero es un método simple para mejorar la legibilidad de la prueba. 

Refactoricemos el código de prueba para mejorar su claridad. Agrega un método `assert` personalizado:

```
@Test
public void oneCorrectLetter() {
	var word = new Word("A");
	var score = word.guess("A");
	assertScoreForLetter(score, 0, Letter.CORRECT);
}
private void assertScoreForLetter(Score score,
              	int position, Letter expected) {
	assertThat(score.letter(position))
      	.isEqualTo(expected);
}
```

**Problema:** Utiliza `AssertJ custom matcher` de manera que en lugar de escribir un comentario al código fuente, uses un nombre de método para capturar la intención del código `assertThat()`.  Referencia: https://github.com/assertj/assertj-examples/tree/main/assertions-examples/src/test/java/org/assertj/examples/custom.


4. Cambiemos cómo especificamos la posición de la letra para verificar en el método `assess()`:

```
public class Score {
  private final String correct;
  private Letter resultado = Letter.INCORRECT ;
  private int position;
	
  public Score(String correct) {
    this.correct = correct;
  }
     
  public Letter letter(int position) {
      return resultado;
  }
     
  public void assess(String attempt) {
     if (isCorrectLetter(attempt)){
        resultado = Letter.CORRECT;
       }
   }
    private boolean isCorrectLetter(String attempt) {
    	return correct.charAt(position) == attempt.
    	charAt(position);
	}
}
``` 

**Problema:** Analiza y explica el código anterior. ¿Se requiere que cambiemos el código de prueba para reflejar ese cambio en la firma del método?. ¿hay algún riesgo en esto?.

### Diseño con combinaciones de dos letras 

Podemos proceder a agregar pruebas destinadas a lograr que el código maneje combinaciones de dos letras. 

Para hacer esto, necesitaremos introducir un nuevo concepto en el código: una letra puede estar presente en la palabra, pero no en la posición que adivinamos: 

1. Comencemos escribiendo una prueba para una segunda letra que está en la posición incorrecta:

```
@Test
void secondLetterWrongPosition() {
   var word = new Word("AR");
   var score = word.guess("ZA");
    assertScoreForLetter(score, 1,
                     	Letter.PART_CORRECT);
}

``` 
Cambiemos el código dentro del método `assess()`  para que esto pase y mantengamos las pruebas existentes. 

2. Agreguemos el código inicial para verificar todas las letras en nuestra adivinación:

```
public void assess(String attempt) {
     for (char current: attempt.toCharArray()) {
         if (isCorrectLetter(current)) {
        	resultado = Letter.CORRECT;
    	}
   }
}
private boolean isCorrectLetter(char currentLetter) {
	return correct.charAt(position) == currentLetter;
}
``` 
El cambio principal aquí es evaluar todas las letras en `attempt` y no asumir que solo tiene una letra. Ese, por supuesto, era el propósito de esta prueba: eliminar este comportamiento. 

Al elegir convertir la cadena `attempt` en un arreglo `char`, el código parece leerse bastante bien. Este algoritmo simple itera sobre cada `char`, utilizando la variable `current` para representar la letra actual que se evaluará. Esto requiere que el método `isCorrectLetter()` sea refactorizado para que acepte y funcione con la entrada `char` o convertir `char` a `String`. 

**Triangulación:**  Hacemos que el código tenga un propósito más general a medida que agregamos pruebas más específicas. 

3. Agreguemos código para detectar cuándo una letra correcta está en la posición incorrecta: 

```
public void assess(String attempt) {
   for (char current: attempt.toCharArray()) {
      if (isCorrectLetter(current)) {
        resultado = Letter.CORRECT;
      } else if (occursInWord(current)) {
        resultado = Letter.PART_CORRECT;
    	}
   }
}
    private boolean occursInWord(char current) {
    	return
         correct.contains(String.valueOf(current));
	}
``` 

Agreguemos otra prueba para ejercitar completamente el comportamiento en torno a la segunda letra en la posición incorrecta. 

4. Agrega una nueva prueba ejercitando las tres posibilidades de puntuación: 

```
@Test
void allScoreCombinations() {
  var word = new Word("ARI");
  var score = word.guess("ZAI");
  assertScoreForLetter(score, 0, Letter.INCORRECT);
  assertScoreForLetter(score, 1,
                     	Letter.PART_CORRECT);
  assertScoreForLetter(score, 2, Letter.CORRECT);
}
```

**Pregunta** ¿Esta prueba falla?. Explica.

5. Agrega `List` de resultados para almacenar el resultado de cada posición de una letra por separado: 

```
public class Score {
  private final String correct;
  private final List<Letter> results =
                         	new ArrayList<>();
  private int position;
  public Score(String correct) {
     this.correct = correct;
  }
  public Letter letter(int position) {
      return results.get(position);
  }
  public void assess(String attempt) {
     for (char current: attempt.toCharArray()) {
       if (isCorrectLetter(current)) {
            results.add(Letter.CORRECT);
       } else if (occursInWord(current)) {
            results.add(Letter.PART_CORRECT);
       } else {
	             results.add(Letter.INCORRECT);
        	}
        	position++;
      }
    }
    private boolean occursInWord(char current) {
       return
     	   correct.contains(String.valueOf(current));
   }
    
    private boolean isCorrectLetter(char
      currentLetter) {
    	  return correct.charAt(position) ==
             	currentLetter;
    }
}
``` 
**Pregunta:** Explica los resultados dados y analiza esto con los resultados anteriores.

En el código class `Score`, es el cuerpo del bucle del método `assess()` el que parece difícil de manejar. Tiene un cuerpo de bucle largo con lógica y un conjunto de bloques `if-else-if`. 

Podemos extraer el cuerpo del bucle en un método. 

El nombre del método nos da un lugar para describir lo que le sucede a cada cosa. El  bucle se vuelve entonces más corto y más fácil de manejar. También podemos reemplazar las escaleras `if-else-if` con una construcción más simple.

 6. Extraigamos la lógica dentro del cuerpo del ciclo en un método `scoreFor()`: 

```
public void assess(String attempt) {
  for (char current: attempt.toCharArray()) {
    results.add( scoreFor(current) );
    position++;
  }
}

private Letter scoreFor(char current) {
   if (isCorrectLetter(current)) {
     return Letter.CORRECT;
   }
   if (occursInWord(current)) {
      return Letter.PART_CORRECT;
   }
	return Letter.INCORRECT;
}
``` 

**Pregunta:** ¿Esto se lee mucho más claramente?. 


El siguiente trabajo es limpiar el código de prueba. En TDD, el código de prueba tiene la misma prioridad que el código de producción. Forma parte de la documentación sobre el sistema. 

Debe mantenerse y ampliarse junto con el código de producción. Tratamos la legibilidad del código de prueba con la misma importancia que el código de producción. 

El olor del código con el código de prueba está alrededor de las aserciones. Se podrían mejorar dos cosas. 

Hay una duplicación obvia en el código que podríamos eliminar y  hay una pregunta sobre cuántas aserciones se deben hacer en una prueba.

7. Eliminemos el código de aserción duplicado extrayendo un método: 

```
@Test
void allScoreCombinations() {
   var word = new Word("ARI");
   var score = word.guess("ZAI");
   assertScoreForGuess(score, INCORRECT, PART_CORRECT,
                           	CORRECT);
}
private void assertScoreForGuess(Score score, Letter...
   for (int position=0;
         	position < expectedScores.length;
         	  position++){
        Letter expected = expectedScores[position];
    	
        assertThat(score.letter(position))
        	    .isEqualTo(expected);
	}
}
``` 

8. Ahora, echemos un vistazo al conjunto final de pruebas después de la refactorización: 

```
package com.wordz.domain;
import org.junit.jupiter.api.Test;
import static com.wordz.domain.Letter.*;
import static org.assertj.core.api.Assertions.assertThat;

public class WordTest {
	
  @Test
   public void oneIncorrectLetter() {
     var word = new Word("A");
     var score = word.guess("Z");
     assertScoreForGuess(score, INCORRECT);
    }
   
   @Test
   public void oneCorrectLetter() {
     var word = new Word("A");
     var score = word.guess("A");
     assertScoreForGuess(score, CORRECT);
	 }
    @Test
    public void secondLetterWrongPosition() {
      var word = new Word("AR");
    	var score = word.guess("ZA");
    	assertScoreForGuess(score,  INCORRECT,
                                	PART_CORRECT);
	 }
    @Test
    public void allScoreCombinations() {
    	var word = new Word("ARI");
    	var score = word.guess("ZAI");
    	assertScoreForGuess(score,  INCORRECT,
                                	PART_CORRECT,
                                	CORRECT);
	   }
	
     private void assertScoreForGuess(Score score,
    	   Letter... expectedScores) {
    	    for (int position = 0;
          	          position < expectedScores.length;
          	                   position++) {
                   Letter expected =
                	expectedScores[position];
        	       assertThat(score.letter(position))
                	.isEqualTo(expected);
    	}
	}
}

```
Esto parece ser un conjunto completo de casos de prueba. 
