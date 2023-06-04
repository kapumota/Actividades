## Curso de desarrollo de software

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada `DoblePruebas` y coloca todas tus respuestas.

Esta actividad es individual.

## Dobles de pruebas, - mocks y stubs: 

En esta actividad, vamos a resolver un desafío de prueba común. ¿Cómo se prueba un objeto que depende de otro objeto? ¿Qué hacemos si ese colaborador es difícil de configurar con datos de prueba? 
Hay varias técnicas disponibles para ayudarnos con esto y se basan en los principios SOLID que aprendimos anteriormente. 
Podemos usar la idea de la inyección de dependencia para permitirnos reemplazar los objetos de colaboración con otros especialmente escritos para ayudarnos a escribir una prueba.

Estos nuevos objetos se denominan dobles de pruebas y aprenderemos sobre dos tipos importantes de dobles de pruebas. 

Aprenderemos cuándo aplicar cada tipo de doble de prueba y luego aprenderemos dos formas de crearlos en Java, escribiendo el código nosotros mismos y usando la popular librería `Mockito`. 

### Los problemas que presentan los colaboradores para las pruebas 

Repasemos estos desafíos con algunos ejemplos breves. 

#### Los desafíos de probar el comportamiento irrepetible 

Hemos aprendido que los pasos básicos de una prueba TDD son Organizar, Actuar y Afirmar (Arrange, Act, Assert). Le pedimos al objeto que actúe y luego aseveramos que ocurre un resultado esperado. 
Pero, ¿qué sucede cuando ese resultado es impredecible? 

``` 
import java.util.random.RandomGenerator;
public class LanzamientoDados{
    private final int NUMERO_DE_LADOS = 6;
	private final RandomGenerator rnd =
                   	RandomGenerator.getDefault();
	public String asText() {
    	int lanzado = rnd.nextInt(NUMERO_DE_LADOS) + 1;
    	return String.format("Tu lanzamiento a %d", lanzado);
	}
}
``` 

Este es un código bastante simple, con solo un puñado de líneas ejecutables. Lamentablemente, simple de escribir no siempre es simple de probar. 

**Pregunta:** ¿Cómo escribiríamos una prueba para esto? Específicamente, ¿cómo escribiríamos la aseveración? 
 

Los desafíos de probar el manejo de errores

Probar el código que maneja las condiciones de error es otro desafío. A modo ilustrativo, imaginemos un código que nos avise cuando la batería de el dispositivo portátil se esté agotando:

```
public class BatteryMonitor {
   public void warnWhenBatteryPowerLow() {
       if (DeviceApi.getBatteryPercentage() < 10) {
        	System.out.println("Advertencia - Bateria Baja");
    	}
	}
}

```
**Pregunta:** Hay un problema al escribir esta prueba: ¿cómo podemos obligar al método `getBatteryPercentage()` a que devuelva un número inferior a 10 como parte de el paso Arrange? ¿Descargaríamos la batería de alguna manera? ¿Cómo haríamos esto? .

### El propósito del double de prueba 

Un doble de prueba de software es un objeto que hemos escrito específicamente para que sea fácil de usar en una prueba unitaria. 
En la prueba, inyectamos el doble de prueba en el SUT en el paso Arrange. En el código de producción, inyectamos en el objeto de producción que el doble de prueba había reemplazado. 

Reconsideremos el  ejemplo anterior de `LanzamientoDados`. ¿Cómo refactorizaríamos ese código para que sea más fácil de probar? 

1. Crea una interfaz que abstraiga la fuente de números aleatorios: 

```
interface NumerosAleatorios {
	int nextInt(int upperBoundExclusive);
}
```

2. Aplica el principio de inversión de dependencia a la clase `LanzamientoDados` para hacer uso de esta abstracción: 

```
import java.util.random.RandomGenerator;
public class LanzamientoDados{
            private final int NUMERO_DE_LADOS = 6;
	private final NumerosAleatorios rnd ;
             
public LanzamientoDados(NumerosAleatorios r){
  this.rnd = r; 
	}
	public String asText() {
    	int lanzado = rnd.nextInt(NUMERO_DE_LADOS) + 1;
    	return String.format("Tu lanzamiento a %d", lanzado);
	}
}
```

3. Escribe una prueba, usando un doble de prueba para reemplazar la fuente NumerosAleatorios: 

```
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class LanzamientoDadosTest {
	@Test
	void producesMensage() {
    	var stub = new StubNumeroAleatorio();
    	var lanzado = new LanzamientoDados(stub);
    	var actual = lanzado.asText();
    			assertThat(actual).isEqualTo("Sacastes un	5");
   }
}
```   

En esta prueba, vemos las secciones habituales `Arrange`, `Act` y `Assert`. La nueva idea aquí es la clase `StubNumeroAleatorio`. Veamos el código del stub: 

```
public class StubNumeroAleatorio implement NumerosAleatorios {
	@Override
	public int nextInt(int upperBoundExclusive) {
    	return 4; 
	}
}
``` 


#### Haciendo la versión del código de producción

Para hacer que la clase `LanzamientoDados` funcione correctamente en producción, necesitamos inyectar una fuente de números aleatorios. Una clase adecuada sería la siguiente: 

```
public class NumerosGeneradosAleatoriamente implements NumerosAleatorios
{
    private final RandomGenerator rnd =
                   	RandomGenerator.getDefault();
	@Override
	public int nextInt(int upperBoundExclusive) {
    return rnd.nextInt(upperBoundExclusive);
	}
}

```

Ahora podemos usar esto para crear la versión de producción del código. Ya cambiamos la clase LanzamientoDados para permitirnos inyectar cualquier implementación adecuada de la interfaz `NumerosAleatorios`. Para el código de prueba, inyectamos un doble de prueba: una instancia de la clase `StubNumerosAleatorios`. 

Para el código de producción, inyectamos una instancia de la clase `NumerosGeneradosAleatoriamente`. El código de producción usará ese objeto para crear números aleatorios reales y no habrá cambios de código dentro de la clase `LanzamientoDados`. 


### Inversión de dependencia, inyección de dependencia e inversión de control 

El ejemplo anterior muestra estas tres ideas en acción. 

La **inversión de dependencia** es la técnica de diseño donde creamos una abstracción en el código. 

La **inyección de dependencia** es la técnica de tiempo de ejecución en la que proporcionamos una implementación de esa abstracción al código que depende de ella. 

Juntas, estas ideas a menudo se denominan **inversión de control (IoC)**. En Los frameworks  como Spring a veces se denominan contenedores IoC porque brindan herramientas para ayudar a administrar la creación e inyección de dependencias en una aplicación. 

El siguiente código es un ejemplo de cómo usamos `LanzamientoDados` y `NumerosGeneradosAleatoriamente` en producción:

```
public class appLanzamientoDados {
     public static void main(String[] args) {
        new appLanzamientoDados().run();
	}
	private void run() {
    	var rnd = new NumerosGeneradosAleatoriamente();
    	var lanzado = new LanzamientoDados(rnd);
    	System.out.println(lanzado.asText());
	}
}
``` 


Puedes ver en el código anterior que inyectamos una instancia de la clase `NumerosGeneradosAleatoriamente` de la versión de producción en la clase `LanzamientoDados`. 

Frameworks como Spring (https://spring. io/), Google Guice (https://github.com/google/guice) y el CDI de Java integrado (https://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html) proporcionar formas de minimizar el repetitivo de crear dependencias y conectarlas, usando anotaciones. 

Usar DIP para intercambiar un objeto de producción por un doble de prueba es una técnica muy poderosa. Este doble de prueba es un ejemplo de un tipo de doble conocido como **stub**. 



