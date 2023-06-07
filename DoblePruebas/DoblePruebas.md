## Curso de desarrollo de software

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada `DoblePruebas` y coloca todas tus respuestas.

Esta actividad es individual. 

Configurar la versión de gradle en esta actividad a una versión compatible del SDK.

## Dobles de pruebas - mocks y stubs 

En esta actividad, vamos a resolver un desafío de prueba común. ¿Cómo se prueba un objeto que depende de otro objeto? ¿Qué hacemos si ese colaborador es difícil de configurar con datos de prueba? 

Hay varias técnicas disponibles para ayudarnos con esto y se basan en los principios SOLID que aprendimos anteriormente. 

Podemos usar la idea de la inyección de dependencia para permitirnos reemplazar los objetos de colaboración con otros especialmente escritos para ayudarnos a escribir una prueba.

Estos nuevos objetos se denominan dobles de pruebas y aprenderemos sobre dos tipos importantes de dobles de pruebas. 

Aprenderemos cuándo aplicar cada tipo de doble de prueba y luego aprenderemos dos formas de crearlos en Java, escribiendo el código nosotros mismos y usando la popular librería `Mockito`. 

### Los problemas que presentan los colaboradores para las pruebas 

Repasemos estos desafíos con algunos ejemplos breves. 

#### Los desafíos de probar el comportamiento irrepetible 

Hemos aprendido que los pasos básicos de una prueba TDD son Organizar, Actuar y Afirmar (Arrange, Act, Assert). 

Le pedimos al objeto que actúe y luego aseveramos que ocurre un resultado esperado.  Pero, ¿qué sucede cuando ese resultado es impredecible? 

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
 
#### Los desafíos de probar el manejo de errores

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

3. Escribe una prueba, usando un doble de prueba para reemplazar la fuente `NumerosAleatorios`: 

```
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class LanzamientoDadosTest {
	@Test
	void producesMensage() {
    	var stub = new StubNumeroAleatorio();
    	var lanzado = new LanzamientoDados(stub);
    	var actual = lanzado.asText();
    			assertThat(actual).isEqualTo("Sacastes un  5");
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
    private final RandomGenerator rnd = RandomGenerator.getDefault();
	@Override
	public int nextInt(int upperBoundExclusive) {
    return rnd.nextInt(upperBoundExclusive);
	}
}

```

Ahora podemos usar esto para crear la versión de producción del código. Ya cambiamos la clase `LanzamientoDados` para permitirnos inyectar cualquier implementación adecuada de la interfaz `NumerosAleatorios`. 

Para el código de prueba, inyectamos un doble de prueba: una instancia de la clase `StubNumerosAleatorios`. 

Para el código de producción, inyectamos una instancia de la clase `NumerosGeneradosAleatoriamente`. El código de producción usará ese objeto para crear números aleatorios reales y no habrá cambios de código dentro de la clase `LanzamientoDados`. 


### Inversión de dependencia, inyección de dependencia e inversión de control 

El ejemplo anterior muestra estas tres ideas en acción. 

La **inversión de dependencia** es la técnica de diseño donde creamos una abstracción en el código. 

La **inyección de dependencia** es la técnica de tiempo de ejecución en la que proporcionamos una implementación de esa abstracción al código que depende de ella. 

Juntas, estas ideas a menudo se denominan **inversión de control (IoC)**. En Los frameworks  como **Spring** a veces se denominan contenedores IoC porque brindan herramientas para ayudar a administrar la creación e inyección de dependencias en una aplicación. 

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

Frameworks como Spring, [Google Guice](https://github.com/google/guice) y el [CDI de Java integrado](https://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html) proporcionan formas de minimizar el repetitivo de crear dependencias y conectarlas usando anotaciones. 

Usar DIP para intercambiar un objeto de producción por un doble de prueba es una técnica muy poderosa. Este doble de prueba es un ejemplo de un tipo de doble conocido como **stub**. 

####  Uso de stubs

Los stubs siempre reemplazan un objeto que no podemos controlar con una versión solo de prueba que podemos controlar. Siempre producen valores de datos conocidos para que el código bajo prueba los consuma.
Admiten un `pull models` para obtener objetos de otros lugares. 

Pero ese no es el único mecanismo por el cual los objetos pueden colaborar. Algunos objetos utilizan los `push models`. 

En este caso, cuando llamamos a un método en el SUT, esperamos que llame a otro  método en algún otro objeto. 

La prueba debe confirmar que esta llamada de método realmente tuvo lugar.  Esto es algo con lo que los stubs no pueden ayudar y necesita un enfoque diferente.

Referencia: [Differences Between Push and Pull](https://www.tencentcloud.com/document/product/406/4791).

### Uso de mocks para verificar interacciones 

Los mocks son una especie de dobles de prueba que registran las interacciones. A diferencia de los stubs, que proporcionan objetos conocidos al SUT, un mock simplemente registrará las interacciones que tiene el SUT con el mock. Es la herramienta perfecta para responder a la pregunta: 

`¿El SUT llamó al método correctamente? `

Esto resuelve el problema de las interacciones entre el SUT y su colaborador. El SUT ordena al colaborador que haga algo en lugar de solicitarle algo. 
Un mock proporciona una forma de verificar que se emitió ese comando junto con cualquier comando necesario. 

Vemos el código de prueba conectando un objeto mock al SUT. El paso `Act` hará que el SUT ejecute código que esperamos que interactúe con su colaborador. 
Hemos cambiado a ese colaborador por un `mock`, que registrará el hecho de que se invocó un determinado método. 

#### Ejemplo

Supongamos que se espera que el SUT envíe un correo electrónico a un usuario. Una vez más, usaremos el Principio de Inversión de Dependencia para crear una abstracción del servidor de correo como interfaz: 

``` 
public interface MailServer {
         void sendEmail(String recipiente, String tema,
               	String texto);
}

```

El código anterior muestra una interfaz simplificada solo adecuada para enviar un correo electrónico de texto corto. 

Para probar el SUT que llamó al método `sendEmail()` en esta interfaz, escribiríamos una clase `MockMailServer`:

``` 
public class MockMailServer implements MailServer {
     boolean fueLlamado;
     String actualRecipiente;
     String actualTema;
    String actualTexto;
    @Override
    public void sendEmail(String recipiente, String tema,
                      	String texto) {
        fueLlamado= true;
        actualRecipiente = recipiente;
        actualTema = tema;
        actualTexto = texto;
	}
}
``` 

**Pregunta:** El código de prueba puede usar los campos dados anteriormente para formar la aserción. 

La prueba simplemente tiene que conectar este objeto mock  al SUT, hacer que el SUT ejecute el código que esperamos llamar al método `sendEmail()` y luego verificar que lo hizo.  

Explica el resultado conseguido.

```
@Test
public void sendsWelcomeEmail() {
   var mailServer = new MockMailServer();
    var notificaciones = new UserNotifications(mailServer);
   notificaciones.welcomeNewUser();
   assertThat(mailServer.fueLlamado).isTrue();
   assertThat(mailServer.actualRecipiente)
     	.isEqualTo("test@example.com");
    assertThat(mailServer.actualTema)
     	.isEqualTo(“Bienvenido!");
     assertThat(mailServer.actualTexto)
     	.contains("Bienvenido a tu cuenta");
}
``` 
Los dobles de prueba no siempre son apropiados para su uso. Veamos algunos problemas a tener en cuenta al usar dobles de prueba.

Los objetos mocks son un tipo útil de dobles de prueba, como hemos visto. Pero no siempre son el enfoque correcto. Hay algunas situaciones en las que debemos evitar activamente el uso de mocks.

#### Caso 1

Si usamos un objeto mock para representar una abstracción, entonces estamos cumpliendo con eso. El problema potencial ocurre porque es demasiado fácil crear un objeto mock para un detalle de implementación, no una abstracción. Si hacemos esto, terminamos encerrando el código en una implementación y estructura específicas. 

Una vez que una prueba se acopla a un detalle de implementación específico, cambiar esa implementación requiere un cambio en la prueba. 

Si la nueva implementación tiene los mismos resultados que la anterior, la prueba aún debería pasar. Las pruebas que dependen de detalles de implementación específicos o estructuras de código impiden activamente la refactorización y la adición de nuevas características. 

#### Caso 2

No se deben usar mocks  como un sustituto de una clase concreta escrita fuera de tu equipo. 

#### Caso 3 

Un objeto de valor es un objeto que no tiene una identidad específica, se define solo por los datos que contiene. Algunos ejemplos incluirían un número entero o un objeto de cadena. 

Estos objetos pueden tener algunos comportamientos complejos dentro de sus métodos pero, en principio, los objetos de valor deberían ser fáciles de crear. No hay ningún beneficio en crear un objeto mock para reemplazar uno de estos objetos de valor. En su lugar, crea el objeto de valor y utilízalo en tus pruebas.

#### Caso 4

Los dobles de prueba solo se pueden usar donde podemos inyectarlos. Esto no siempre es posible. Si el código que queremos probar crea una clase concreta usando la  palabra clave `new` entonces no podemos reemplazarlo con un doble: 

``` 
public class UserGreeting {
     private final UserProfiles profiles
    	= new UserProfilesPostgres();
	
public String formatGreeting(UserId id) {
    	return String.format("Hola y bienvenido %s",
            	profiles.fetchNicknameFor(id));
	}
}
``` 
**Pregunta:** ¿Hay una forma directa de inyectar un doble de prueba con este diseño?.
 
#### No pruebes el mock

Probar el mock es una frase que se usa para describir una prueba con demasiadas suposiciones integradas en un doble de prueba. Supongamos que escribimos un stub que representa algún acceso a la base de datos, pero ese stub contiene cientos de líneas de código para emular consultas específicas detalladas a esa base de datos. 

Cuando escribimos las aserciones de prueba, todas se basarán en las consultas detalladas que emulamos en el mock. 

Ese enfoque probará que la lógica del SUT responde a esas consultas. Pero el stub ahora asume mucho sobre cómo funcionará el código de acceso a datos real. 

El stub y el código de acceso a datos reales pueden desfasarse rápidamente. Esto da como resultado una prueba unitaria inválida que pasa pero con respuestas cortadas que ya no pueden suceder en la realidad. 

### Cuándo usar objetos mocks 

Los mocks son útiles cuando el SUT usa un `push models` y solicita una acción de algún otro componente, donde no hay una respuesta obvia, como cuando : 

- Se solicita una acción desde un servicio remoto, como enviar un correo electrónico a un servidor de correo 
- Se inserta o elimina datos de una base de datos 
- Se envía un comando a través de un socket TCP o una interfaz serial 
- Se invalida un caché 
- Se escribe información en un archivo de registro.

