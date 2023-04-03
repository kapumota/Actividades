### Creación de una aplicación mediante TDD 

Vamos a aprender el lado práctico de TDD creando primero la prueba de la aplicación. En esta actividad tiene objetivo tener todo listo para las siguientes actividades.

TDD es un enfoque excelente para proporcionar retroalimentación rápida sobre piezas de software independientes. Es el complemento perfecto para el desarrollo ágil. 

### Requerimientos técnicos 

Para este proyecto utilizaremos las siguientes herramientas: 

- IntelliJ IDEA IDE 2022.1.3 (Community Edition) o superior 
- Amazon Corretto Java 17 JDK 
- El framework  de pruebas de unidad JUnit 5 
- El framework  de aserciones fluidas AssertJ 
- El sistema de gestión de dependencias de Gradle 


Una vez que se instala IntelliJ, podemos importar el proyecto de inicio proporcionado en el repositorio de GitHub adjunto. Esto configura un proyecto de Java que utiliza el kit de desarrollo de Java (JDK) de Amazon Corretto 17, el ejecutor de pruebas de unidades JUnit 5, el sistema de gestión de compilación Gradle y la biblioteca de aserciones fluidas AssertJ. Para ello, consulta los siguientes pasos: 

1. En tu navegador web, vaya a  https://github.com/kapumota/Actividades y selecciona TDD-1

2. Usa la herramienta git preferida para clonar todo el repositorio en tu computadora. 

3. Inicia IntelliJ. Debería ver la pantalla de bienvenida: Haga clic en **Open** y luego navega a la carpeta de la actividad del repositorio que acabamos de clonar. 

4. Espera a que IntelliJ importe los archivos. Deberías ver este espacio de trabajo abierto. Ahora tenemos el IDE configurado con un proyecto básico que contiene todo lo que necesitamos para comenzar. 

![](https://github.com/kapumota/Actividades/blob/main/TDD-1/Imagenes/IDE.png)

### Describiendo las reglas de Wordz 

Para jugar Wordz, un jugador tendrá hasta seis intentos para adivinar una palabra de cinco letras. Después de cada intento, las letras de la palabra se resaltan de la siguiente manera: 

- La letra correcta en la posición correcta tiene un fondo negro 

- La letra correcta en la posición incorrecta tiene un fondo gris

- Las letras incorrectas que no están presentes en la palabra tienen un fondo blanco

El jugador puede usar esta retroalimentación para hacer una próxima suposición mejor. Una vez que un jugador adivina la palabra correctamente, obtiene algunos puntos. Obtienen seis puntos por una suposición correcta en el primer intento, cinco puntos por una suposición correcta en el segundo intento y un punto por una suposición correcta en el sexto y último intento. Los jugadores compiten entre sí en varias rondas para obtener la puntuación más alta. Wordz es un juego divertido, así como un entrenamiento cerebral suave. 

Si bien la construcción de una interfaz de usuario está fuera del alcance del curso, es muy útil ver un posible ejemplo: 


![](https://github.com/kapumota/Actividades/blob/main/TDD-1/Imagenes/Wordz.png)


Técnicamente, vamos a crear el componente de servicio web backend para este juego. 


### Explorando métodos ágiles

A medida que construimos Wordz, vamos a utilizar un enfoque iterativo, donde construimos la aplicación como una serie de funciones con las que los usuarios pueden trabajar. Esto se conoce como **desarrollo ágil**. 

Los procesos de desarrollo ágil profesional se basan en mantener una única base de código que siempre se prueba y representa la mejor versión hasta la fecha del software. 
Este código siempre está listo para implementarse en los usuarios. Desarrollamos esta base de código una característica a la vez, mejorando continuamente su diseño a medida que avanzamos.

Las técnicas como TDD juegan un papel importante en esto, al garantizar que nuestro código esté bien diseñado y probado exhaustivamente.  

Para respaldar mejor el desarrollo iterativo, elegimos una técnica iterativa para capturar los requisitos. Esta técnica se llama **historias de usuario**. 

### Lectura de historias de usuarios

Con el desarrollo ágil, capturamos los requirimientos siguiendo dos principios clave: 

- Los requisitos se presentan uno a la vez de forma aislada.
- Hacemos hincapié en el valor para el usuario, no en el impacto técnico en el sistema. 

La técnica para hacer esto se llama **historia de usuario**. 

El formato de una historia de usuario es siempre el mismo: consta de tres secciones:

```
Como [persona o máquina que utiliza el software],... 
Quiero [un resultado específico de ese software]... 
… para que [se logre una tarea que es importante]. 
``` 

Es en las historias de usuario donde comenzamos el desarrollo. 

### Combinando el desarrollo ágil con TDD 

TDD es un complemento perfecto para el desarrollo ágil. TDD nos ayuda a mejorar nuestro diseño y probar que nuestra lógica es correcta. 

El flujo de trabajo que usaremos es típico para un proyecto TDD ágil: 

- Escoge una historia de usuario priorizada por impacto. 
- Piensa un poco en el diseño al que aspiras. 
- Usa TDD para escribir la lógica de la aplicación en el core. 
- Usa TDD para escribir código para conectar el core a una base de datos. 
- Usa TDD para escribir código para conectarse a un punto final de la API. 

Este proceso se repite. 

