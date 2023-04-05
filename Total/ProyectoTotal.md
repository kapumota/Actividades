## Escribiendo tu primer test

###  Actividad grupal

Descarga la actividad incompleta desde aquí:https://github.com/kapumota/Actividades/tree/main/TDD-2/TDD-2 

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada TDD-2 y coloca todas tus respuestas.


## Iniciando TDD: Arrange-Act-Assert

Siempre es útil tener plantillas para seguir cuando hacemos cosas y las pruebas unitarias no son una excepción. 

Con base en el trabajo comercial realizado en el Proyecto de Compensación Integral de Chrysler , el inventor de TDD, Kent Beck, descubrió que las pruebas unitarias tenían ciertas características en común. Esto se resumió como una estructura recomendada para el código de prueba, llamada `Arrange-Act-Assert` o `AAA`. 

La descripción original de `AAA` se puede encontrar aquí, en el wiki de C2: http://wiki.c2.com/?ArrangeActAssert. 

Para explicar lo que hace cada sección, analicemos una prueba unitaria completa para un fragmento de código en el que queremos asegurarnos de que un nombre de usuario se muestre en minúsculas: 


```
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
public class UsernameTest {
    @Test
    public void convertsToLowerCase() {
        var username = new Username("SirLara35179");
        String actual = username.asLowerCase();
        assertThat(actual).isEqualTo("sirLara35179");
    }
}
```

Lo primero que debes notar es el nombre de la clase para la prueba:  `UsernameTest`. ¿Qué comentario se puede hacer a partir de esto?  

La prueba unitaria en sí es el método `convertsToLowerCase()`. Este método tiene la anotación `@Test` del framework de prueba JUnit5. La anotación le dice a JUnit que esta es una prueba que puede ejecutar para nosotros.  Dentro del método `@Test`, podemos ver la estructura `Arrange-Act-Assert`. 

Primero el paso `arrange` es para que el código pueda ejecutarse. Esto implica la creación de los objetos necesarios, el suministro de la configuración necesaria y la conexión de los objetos y funciones dependientes. A veces, no necesitamos este paso, por ejemplo, si estamos probando una función independiente simple. 

En el código de ejemplo, ¿cuál es el paso `Arrange`? 

El paso `Act` sigue. Esta es la parte en la que hacemos que el código bajo prueba actúe: ejecutamos ese código. Esta es siempre una llamada al código bajo prueba, proporcionando los parámetros necesarios y organizando la captura de los resultados. 

En el código de ejemplo, ¿cuál es el paso `Act`? 


Completar nuestra prueba es el último paso `Assert`.  `AssertThat(actual).isEqualTo("sirLara35179");`  es la línea del paso `assert` aquí. Se utiliza el método `assertThat()` y el método `isEqualTo()` de la biblioteca de aserciones fluidas `AssertJ`. 

Tu trabajo es verificar si el resultado que obtuvimos del paso `Act` coincide con nuestras expectativas o no. Aquí, estamos probando si todas las letras mayúsculas del nombre original se han convertido en minúsculas. 


La librería  JUnit es el framework de prueba de unidad estándar de la industria para Java. Nos proporciona un medio para anotar los métodos de Java como pruebas unitarias, nos permite ejecutar todas nuestras pruebas y muestra visualmente los resultados.

Revisa: https://www.jetbrains.com/help/idea/tdd-with-intellij-idea.html 


¿Puedes ejecutar la prueba del código anterior?, ¿la prueba pasa, sino puedes usar TDD?.


Las pruebas más útiles también siguen el principios `FIRST` y usan una aserción por prueba.


### Aplicando FIRST

Estos son un conjunto de cinco principios que hacen que las pruebas sean más efectivas: 

- Rápidas 
- Aisladas 
- Repetibles 
- Autoverificables 
- Oportunas

### Usando una aserción por prueba 

Se centra en los mensajes de error que recibimos durante las pruebas fallidas y nos ayuda a controlar la complejidad del código. Nos obliga a desglosar las cosas un poco más. 

### Detectar errores comunes

Hay varios errores simples y comunes que podemos cometer y las pruebas unitarias son excelentes para detectarlos todos. Los errores más probables son los siguientes: 

- Errores de uno en uno (off-by-one)
- Lógica condicional invertida 
- Condiciones que faltan 
- Datos no inicializados 
- El algoritmo incorrecto 
- Comprobaciones de igualdad rotas 

Volviendo a la prueba anterior para un nombre de usuario en minúsculas, supongamos que decidimos no implementar esto usando el método `.toLowerCase()` incorporado de `String`, sino que intentamos generar nuestro propio código de bucle, así:

```
public class Username {
    private final String name;
    public Username(String username) {
        name = username;
    }
    public String asLowerCase() {
        var result = new StringBuilder();
        for (int i=1; i < name.length(); i++) {
            char current = name.charAt(i);
            if (current > 'A' && current < 'Z') {
                result.append(current + 'a' - 'A');
            } else {
                result.append( current );               
            }
        }
        return result.toString() ;
    }
}
```
¿Qué tipo de error produce que la prueba falle?

### Aserción de  excepciones 

Un área en la que las pruebas unitarias sobresalen es en la prueba del código de manejo de errores. Como ejemplo de cómo probar el lanzamiento de excepciones, agreguemos un requisito comercial de manera que los nombres de usuario deben tener al menos cuatro caracteres. Pensamos en el diseño que queremos y decidimos lanzar una excepción personalizada si el nombre es demasiado corto. Decidimos representar esta excepción personalizada como clase `InvalidNameException`.

Así es como se ve la prueba, usando AssertJ:

```
@Test
public void rejectsShortName() {
    assertThatExceptionOfType(InvalidNameException.class)
            .isThrownBy(()->new Username("Abc"));
}
```

Podemos considerar agregar otra prueba específicamente destinada a demostrar que se acepta un nombre de cuatro caracteres y no se lanza ninguna excepción: 

```
@Test
public void acceptsMinimumLengthName() {
    assertThatNoException()
            .isThrownBy(()->new Username("Abcd"));
}
```

Alternativamente, podemos simplemente decidir que esta prueba explícita no es necesaria. 

**Es una buena práctica agregar ambas pruebas para dejar claras nuestras intenciones**


### Solo probando métodos públicos 

TDD se trata de probar los comportamientos de los componentes, no de sus implementaciones. Dentro de una prueba, esto aparece como llamar a métodos o funciones públicas en clases y paquetes públicos. Los métodos públicos son los comportamientos que elegimos exponer a una aplicación más amplia. Cualquier dato privado o código de soporte en clases, métodos o funciones permanece oculto.

### Preservando la encapsulación 

Si sentimos que necesitamos agregar `getters` a todos nuestros datos privados para que la prueba pueda verificar que cada uno es como se esperaba, a menudo es mejor tratar esto como un objeto de valor. Un **objeto de valor**  es un objeto que carece de identidad. 

Dos objetos de valor que contengan los mismos datos se consideran iguales. Usando objetos de valor, podemos crear otro objeto que contenga los mismos datos privados y luego probar que los dos objetos son iguales. 

En Java, esto requiere que codifiquemos un método `equals()` personalizado para nuestra clase. Si hacemos esto, también deberíamos codificar un método `hashcode()`, ya que los dos van de la mano. Cualquier implementación que funcione servirá.


### Importante

Cuando nuestro diseño no es bueno, las secciones AAA de nuestra prueba revelarán esos problemas de diseño a medida que el código huele mal en la prueba. 

#### Arrange

Si el código en el paso `Arrange` está desordenado, el objeto puede ser difícil de crear y configurar. Puedes necesitar demasiados parámetros en un constructor o demasiados parámetros opcionales dejados como null en la prueba. 
Puede ser que el objeto necesite inyectar demasiadas dependencias, lo que indica que tiene demasiadas responsabilidades o puedes necesitar demasiados parámetros de datos primitivos para pasar muchos elementos de configuración.


### Act 

Llamar a la parte principal del código en el paso `Act` suele ser sencillo, pero puede revelar algunos errores básicos de diseño. Por ejemplo, es posible que tengamos parámetros poco claros que pasamos, firmas como una lista de objetos booleanos o de cadena.

### Assert

El paso `Assert` revelará si los resultados del código son difíciles de usar. Podemos rediseñar para usar construcciones más seguras en cualquier caso. 


## Aplicación Wordz.

En esta actividad vamos a comenzar con una clase que contendrá el core de la lógica de la aplicación, una que represente una palabra para adivinar y que pueda calcular el puntaje de una suposición. Comenzamos creando una clase de prueba unitaria e: ¿cómo deberíamos llamar a la prueba? 

Iremos con `WordTest`, ya que describe el área que queremos cubrir: la palabra que se debe adivinar. 

Las estructuras típicas de un proyecto Java se dividen en paquetes. El código de producción vive bajo `src/main/ java` y el código de prueba se encuentra en `src/test/java`. Esta estructura describe cómo el código de producción y de prueba son partes igualmente importantes del código fuente, al tiempo que nos brinda una forma de compilar e implementar solo el código de producción. 

Siempre enviamos el código de prueba con el código de producción cuando tratamos con el código fuente, pero para los ejecutables implementados, solo omitimos las pruebas. También seguiremos la convención básica del paquete Java de tener un nombre único para nuestra empresa o proyecto en el nivel superior. Esto ayuda a evitar conflictos con el código de la biblioteca. 

Llamaremos al nuestro `com.wordz`, el nombre de la aplicación.

El siguiente paso de diseño es decidir qué comportamiento eliminar y probar primero.

Para empezar, escribamos una prueba que arroje la puntuación de una sola letra que sea incorrecta: 

1 . Escribe el siguiente código para comenzar la prueba: 

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

3 . Usamos **autocomplete** en este punto para crear una nueva clase `Word` en tu propio archivo. Doble click en  src/main folder tree y no en src/test. 


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
Podemos usar atajos del IDE para hacer la mayor parte del trabajo al escribir ese código por nosotros. La prueba pasa. ¿Pero?


