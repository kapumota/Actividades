## Escribiendo tu primer test

###  Actividad grupal

Descarga la actividad incompleta desde aquí:https://github.com/kapumota/Actividades/tree/main/TDD-2/TDD-2 

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada TDD-1 y coloca todas tus respuestas.


## Aplicación Wordz.

En esta actividad vamos a comenzar con una clase que contendrá el core de la lógica de la aplicación, una que represente una palabra para adivinar y que pueda calcular el puntaje de una suposición. Comenzamos creando una clase de prueba unitaria y esto nos pone inmediatamente en modo de diseño de software: ¿cómo deberíamos llamar la prueba? 

Iremos con WordTest, ya que describe el área que queremos cubrir: la palabra que se debe adivinar. 

Las estructuras típicas de un proyecto Java se dividen en paquetes. El código de producción vive bajo src/main/ java y el código de prueba se encuentra en src/test/java. Esta estructura describe cómo el código de producción y de prueba son partes igualmente importantes del código fuente, al tiempo que nos brinda una forma de compilar e implementar solo el código de producción. 

Siempre enviamos el código de prueba con el código de producción cuando tratamos con el código fuente, pero para los ejecutables implementados, solo omitimos las pruebas. También seguiremos la convención básica del paquete Java de tener un nombre único para nuestra empresa o proyecto en el nivel superior. Esto ayuda a evitar conflictos con el código de la biblioteca. 

Llamaremos al nuestro `com.wordz`, el nombre de la aplicación. Llamaremos al nuestro com.wordz, el nombre de la aplicación. 

El siguiente paso de diseño es decidir qué comportamiento eliminar y probar primero.

Para empezar, escribamos una prueba que arroje la puntuación de una sola letra que sea incorrecta: 

1 . Escribe el siguiente código para comenzar nuestra prueba: 

```
public class WordTest {
    @Test
    public void oneIncorrectLetter() {
    }
}
``` 


El nombre de la prueba nos da una visión general de lo que está haciendo la prueba. 

2 . Para comenzar el diseño, decidimos usar una clase llamada `Word` para representar nuestra palabra. También decidimos proporcionar la palabra a adivinar como un parámetro de construcción para nuestra instancia de objeto de la clase `Word` que queremos crear. Codificamos estas decisiones de diseño en la prueba: 


```
@Test
public void oneIncorrectLetter () {
    new Word("A");
}
```

3 . Usamos **autocomplet**e en este punto para crear una nueva clase `Word` en tu propio archivo. Doble click en  src/main folder tree y no en src/test. 


4 . Haz clic en **OK** para crear el archivo en el árbol fuente dentro del paquete correcto. 

5 . Ahora, renombramos el parámetro del constructor de `Word`: 

```
public class Word {
    public Word(String correctWord) {
  // No Implementado
    }
}
```

6 . A continuación, volvemos a la prueba. Capturamos el nuevo objeto como una variable local para que podamos probarlo: 


```
@Test
public void oneIncorrectLetter () {
    var word = new Word("A");
}
```

El siguiente paso de diseño es pensar en una forma de pasar una suposición a la clase de `Word` y devolver una puntuación. 

7 . Pasar la adivinación es una decisión fácil: usaremos un método que llamaremos `guess()`. Podemos codificar estas decisiones en la prueba: 

```
@Test
public void oneIncorrectLetter () {
    var word = new Word("A");
    word.guess("Z");
}
```

8 . Usa autocomplete para agregar el método `guess()` a la clase de `Word`: 

9 . Haz clic en Enter para agregar el método, luego cambia el nombre del parámetro a un nombre descriptivo: 


```
public void guess(String attempt) {
}
```

10 . A continuación, agreguemos una forma de obtener la puntuación resultante de esa suposición. Comienza con la prueba: 

```
@Test
public void oneIncorrectLetter () {
    var word = new Word("A");
    var score = word.guess("Z");
}
```
Luego, necesitamos pensar un poco sobre qué devolver del código de producción.

TDD nos brinda retroalimentación rápida sobre nuestras decisiones de diseño y eso nos obliga a hacer un entrenamiento de diseño ahora mismo. 

Después de unos 15 minutos de reflexionar sobre qué hacer, estas son las tres decisiones de diseño que usaremos en este código: 

- Admitir un número variable de letras en una palabra 
- Representar la puntuación mediante una enumeración simple `INCORRECT`, `PART_CORRECT`, o `CORRECT` 
- Acceder a cada puntaje por su posición en la palabra, basado en cero 


Pasemos al diseño: 

1 . Captura estas decisiones en la prueba

```
@Test
public void oneIncorrectLetter() {
    var word = new Word("A");
    var score = word.guess("Z");
    var result = score.letter(0);
    assertThat(result).isEqualTo(Letter.INCORRECT);
}
```

2 . Ahora, ejecuta esta prueba. Falla. Este es un paso sorprendentemente importante. 

3 . Hagamos que la prueba pase agregando código a la clase `Word`: 

```
public class Word {
    public Word(String correctWord) {
        // Not Implemented
    }
    public Score guess(String attempt) {
        var score = new Score();
        return score;
    }
}

``` 
4 . A continuación, crea la clase `score`: 


```
public class Score {
    public Letter letter(int position) {
        return Letter.INCORRECT;
    }
}
```
Podemos usar atajos del IDE para hacer la mayor parte del trabajo al escribir ese código por nosotros. La prueba pasa. Pero?


