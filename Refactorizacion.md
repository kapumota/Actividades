## Curso de desarrollo de software

**Indicaciones**

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada Refactorizacion y coloca todas tus respuestas.

Esta actividad es individual.

### Introducción a la refactorización 

La refactorización de código es uno de los términos clave en el desarrollo de software. La refactorización de código es el proceso de cambiar el código fuente de un programa sin modificar su comportamiento funcional externo, con el fin de mejorar algunos de los atributos no funcionales del software. 

En otras palabras, la refactorización de código es el proceso de aclarar y simplificar el diseño del código existente, sin cambiar su comportamiento. 

Hoy en día, el desarrollo de software ágil es literalmente imprescindible y los equipos ágiles mantienen y amplían mucho su código de iteración en iteración, y sin una refactorización continua, esto es difícil de lograr. 

Esto se debe a que el código no refactorizado tiende a degradarse con dependencias poco saludables entre clases o paquetes, mala asignación de responsabilidades de clase, demasiadas responsabilidades por método o clase, código duplicado y muchas otras variedades de confusión y desorden. 

Por lo tanto, las ventajas incluyen una mejor legibilidad del código y una menor complejidad,  capacidades que pueden mejorar el mantenimiento del código fuente y crear una arquitectura interna más expresiva. 


#### Algunos consejos para hacer bien las técnicas de refactorización de código
La refactorización del código se debe realizar como una serie de pequeños cambios, cada uno de los cuales mejora un poco el código existente y deja el programa en condiciones de funcionamiento. No mezcles un montón de refactorizaciones en un gran cambio. 


Cuando refactorices, definitivamente debe hacerlo usando TDD y CI. Si no puedes ejecutar esas pruebas después de cada pequeño paso en una refactorización, creas el riesgo de introducir errores.  El código debería volverse más limpio.

No se debe crear una nueva funcionalidad durante la refactorización.  No mezcles la refactorización y el desarrollo directo de nuevas características. 

Intenta separar estos procesos al menos dentro de los límites de las confirmaciones individuales.

### Beneficios de la refactorización de código 

1 . Ver la imágen completa 

Si tienes un método principal que maneja toda la funcionalidad, lo más probable es que sea demasiado largo e increíblemente complejo. Pero si se divide en partes, es fácil ver lo que realmente se está haciendo. 

2 . Legibilidad para tu equipo 

Haz que sea fácil de entender para tus compañeros, no lo escribas para ti mismo, piensa a largo plazo. 

3 . Mantenibilidad 

La integración de actualizaciones y actualizaciones es un proceso continuo que es inevitable y debe ser bienvenido. Cuando el código base no está organizado y se basa en una base débil, los desarrolladores a menudo dudan en realizar cambios. Pero con la refactorización de código, el código organizado, el producto se construirá sobre una base limpia y estará listo para futuras actualizaciones. 

4 . Eficiencia 

La refactorización de código puede considerarse una inversión, pero obtienes buenos resultados. Reduce el esfuerzo requerido para futuros cambios en el código, ya sea por ti o por otros desarrolladores, mejorando así la eficiencia. 

5 . Reducir la complejidad 

Haz que sea más fácil para ti y tu equipo trabajar en el proyecto. 

### Ejercicio

Hay muchas técnicas de refactorización de código y presentamos algunas de las más comunes y útiles vista en una actividad anterior. Tu trabajo es encontrar las soluciones y como se pueden refactorizar.

**Rojo-verde-refactorización**

Comencemos hablando brevemente sobre la muy popular técnica código rojo-verde-refactorización. Este es el patrón de ingeniería ágil que sustenta el desarrollo basado en pruebas. Caracterizado por un enfoque de `prueba primero` para el diseño y la implementación. 
Esto sienta las bases para todas las formas de refactorización. 

Tu incorporas la refactorización en el ciclo de desarrollo basado en pruebas comenzando con una prueba `roja` fallida, escribiendo el código más simple posible para que la prueba pase a `verde` y finalmente trabajes para mejorar y mejorar tu código mientras mantienes la prueba `verde`. 

Este enfoque trata de cómo se puede integrar sin problemas la refactorización en tu proceso de desarrollo general y trabajar para mantener limpio el código. Hay dos partes distintas en esto: escribir código que agrega una nueva función a tu sistema y mejorar el código que hace esta función. Lo importante es recordar no hacer  ambas cosas al mismo tiempo durante el flujo de trabajo.

Realiza un proyecto en IntelliJ  siguiendo las instrucciones siguientes:

**Producto:** Una clase de calculadora simple. 

**Requisito 1:** El estado de la calculadora es verdadero cuando se inicializa correctamente. 

Escribe la primera prueba en la clase `CalculatorTest`.

```
@Test
public void whenCalculatorInitializedThenReturnTrue() {
	Calculadora calculadora = new Calculadora();
   	 
	assertTrue(calculadora.getStatus());
}
``` 
En la primera línea, puedes ver la anotación que le dice al compilador: `Este es un método de prueba`. El nombre del método debe tener suficiente información para saber qué está probando este código. 
Usa palabras: `when`, `then`. Para verificar si el método devuelve `True`, usaremos el método `assertTrue`. 

Ahora escribamos un código que hará que la prueba sea verdadera.

```
private boolean status;

public Calculadora() {
	this.status = true;
}

public boolean getStatus() {
	return status;
}
```

Ejecuta la clase de prueba para verificar si todo está bien. ¡Debes ver una línea verde!

**Requisito 2:** La calculadora debe tener funciones de suma y resta. 

**Prueba:**

```
@Test
public void whenAdditionTwoNumberThenReturnCorrectAnswer() {
	Calculadora calculadora = new Calculadora();
   	 
	assertEquals( 5, calculadora.addition(2,3));
}
```

El siguiente método de uso frecuente es `assertEquals`. Este método verifica si el primer argumento es igual a un segundo argumento. 

La implementación a escribir parece que:

``` 
public int addition(int a, int b) {
	return a + b;
}
``` 
Tal vez notes que inicializamos la clase  calculadora` en cada método, en este caso, no es una buena idea. 

Hagamos la primera refactorización. Usaremos la anotación `@BeforeAll` para escribir un método que se ejecutará primero antes del método de prueba.

``` 
private static Calculadora calculadora;

@BeforeAll
public static void init(){
	calculadora = new Calculadora();
}
``` 
Ahora puedes eliminar todo el código de inicialización de la calculadora en los métodos de prueba. 

Realiza la implementación de la función de resta por tu cuenta. 

**Requisito 3:** La calculadora debe tener función de división.

 Cuando alguien divide por cero, se debe lanzar una excepción. Escribamos la primera prueba para este requisito. 

```
@Test
public void whenDivisionThenReturnCorrectAnswer() {
	assertEquals(2, calculadora.division(8, 4));
}
``` 

**Implementación**

```
public int division(int a, int b) {
	return a / b;
}
```

Debemos comprobar que se lanza una excepción cuando alguien divide por cero.

```
@Test
public void whenDivisionByZeroThenThrowException() {
	Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
    	calculadora.division(5, 0);
	});
	assertEquals("No se puede divisor por  cero", exception.getMessage());
}
``` 
Usamos `assertThrows` con Lambda de Java 8 para usar la función de división en condiciones controladas y esperar `IllegalArgumentException`. 

Al final, verificamos el mensaje de excepción en el método `assertEquals`. 

Ahora podemos cambiar el método `división` en la clase `Calculadora`.

```
public int division(int a, int b) {
	if (b == 0) {
    	throw new IllegalArgumentException("No se puede dividir por cero");
	} else {
    	return a / b;
	}
}
``` 
Si ejecutas la prueba, puedes verificar de inmediato si los cambios que realizó no interrumpen otras funciones del método. 
Es por eso que debes escribir una prueba unitaria. Cuando cambia el código, puede verificar si todavía funciona como antes. 

**Ejercicio:** Agrega más funciones a la clase `Calculadora` utilizando los principios de TDD.

