## Curso de desarrollo de software

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada `Hexagonal` y coloca todas tus respuestas.Esta actividad es individual. 

Configurar la versión de gradle en esta actividad a una versión compatible del SDK.

En esta actividad, vamos a combinar todas esas técnicas en un poderoso enfoque de diseño conocido como arquitectura hexagonal. 

Con este enfoque, nos beneficiaremos al obtener más de la lógica de aplicación bajo pruebas unitarias y reducir la cantidad de pruebas de integración y de extremo a extremo requeridas. Construiremos una resistencia natural a los cambios fuera de la aplicación. 
Las tareas de desarrollo, como cambiar el proveedor de una base de datos, se simplificarán al tener menos lugares donde el código deba cambiarse. 

También podremos realizar pruebas unitarias en unidades más grandes, trayendo algunas pruebas que requieren pruebas de extremo a extremo en otros enfoques bajo pruebas unitarias.  

#### Los sistemas externos son difíciles  

Las dependencias de sistemas externos causan problemas en el desarrollo. La solución conduce a un buen enfoque de diseño. 

Veamos una forma sencilla de manejar sistemas externos. La tarea del usuario es extraer un informe de las ventas de este mes de una base de datos. 

<img src="Imagenes/Ejemplo1.png" width="520px" height="180px">

#### Los problemas de entorno traen problemas  

El entorno en el que se ejecuta el software a menudo genera desafíos. 


**Problema:** Indica los problemas que puedan ocurrir debido al entorno.  


Una base de datos almacena datos, causando problemas para las pruebas. Supongamos que escribimos una prueba contra una base de datos de prueba, que comienza escribiendo un nombre de usuario de prueba. Si hemos ejecutado esta prueba antes, el nombre de usuario de la prueba ya estará almacenado en la base de datos. 

Por lo general, la base de datos informará un error de elemento duplicado y la prueba fallará.  

Las pruebas contra bases de datos necesitan limpieza. Todos los datos de prueba almacenados deben eliminarse después de que se hayan completado las pruebas. Si intentamos eliminar datos después de que la prueba haya tenido éxito, es posible que el código de eliminación nunca se ejecute si la prueba falla. 

Podríamos evitar esto eliminando siempre los datos antes de que se ejecute la prueba. Dichas pruebas serán lentas de ejecutar.  

Una tarea común en el software comercial es aceptar el pago de un cliente. Para eso, inevitablemente usamos un procesador de pago de terceros como PayPal o Stripe, como dos ejemplos. Además de los desafíos de la conectividad de la red, las API de terceros nos presentan desafíos adicionales:  

**Problema:** Enuncia algunos desafios que se presentan en este caso.

Existen muchos desafíos cuando mezclamos servicios externos y una sola pieza de código monolítica, lo que complica tanto el mantenimiento como las pruebas. 

**Pregunta:** ¿Qué podemos hacer al respecto para resolver o hacer que los sistemas externos sean más fáciles de manejar?.


#### Inversión de dependencia al rescate  

Aprendimos sobre el principio de inversión de dependencia anteriormente. Vimos que nos ayuda a aislar algún código que queríamos probar de los detalles de sus colaboradores. Notamos que era útil para probar cosas que se conectaban a sistemas externos que estaban fuera de el control. 

Vimos cómo el principio de responsabilidad única nos guió a dividir el software en tareas más pequeñas y más enfocadas.  

Aplicando estas ideas a el ejemplo anterior de informe de ventas, llegaríamos a un diseño mejorado, como se muestra en el siguiente diagrama:  

<img src="Imagenes/Ejemplo2.png" width="500px" height="180px">

El diagrama anterior muestra cómo hemos aplicado los principios SOLID para dividir el código de informe de ventas. Hemos utilizado el principio de responsabilidad única para dividir la tarea general en tres tareas separadas:  

- Dar formato al informe
- Cálculo del total de ventas
- Lectura de los datos de ventas de la base de datos  

Esto ya hace que sea un poco más fácil trabajar con la aplicación.

Aquí también podemos aplicar el principio de inversión de dependencia. ¿Cómo?.

El mayor beneficio es que podemos intercambiar cualquier pieza de código que pueda acceder a cualquier base de datos, sin cambiar el código de cálculo. Por ejemplo, podríamos cambiar de una base de datos Postgres SQL a una base de datos Mongo NoSQL sin cambiar el código de cálculo. Podemos usar un doble de prueba para la base de datos para que podamos probar el código de cálculo como una prueba unitaria FIRST. 

Estas son ventajas muy significativas, no solo en términos de TDD y pruebas, sino también en términos de cómo se organiza el código. 

#### Generalizando este enfoque a la arquitectura hexagonal  

¿Podríamos extender este enfoque a toda la aplicación y obtener los mismos beneficios? ¿Podríamos encontrar una manera de separar toda la lógica de la aplicación y las representaciones de datos de las restricciones de la influencia externa? 

Ciertamente podemos y la forma general de este diseño se muestra en el siguiente diagrama:  

 <img src="Imagenes/Ejemplo3.png" width="520px" height="300px">

El diagrama anterior muestra lo que sucede cuando generalizamos el uso de la inversión de dependencia y la responsabilidad única para una aplicación completa. 

Se llama arquitectura hexagonal, también conocida como puertos y adaptadores por el término original utilizado por Alastair Cockburn, quien describió por primera vez este enfoque. 


#### Resumen de los componentes de la arquitectura hexagonal 

Para proporcionarnos este aislamiento de la lógica de aplicación central, la arquitectura hexagonal divide todo el programa en cuatro espacios:  

- Sistemas externos, incluidos navegadores web, bases de datos y otros servicios informáticos
- Los adaptadores implementan las API específicas requeridas por los sistemas externos
- Los puertos son la abstracción de lo que la aplicación necesita del sistema externo
- El modelo de dominio contiene la lógica de aplicación, libre de detalles de sistemas externos. 

El núcleo central de la aplicación es el modelo de dominio, rodeado por el soporte que necesita de sistemas externos.   

**Sistemas externos conectan los adaptadores**

Los sistemas externos son todas las cosas que viven fuera de la base de código. Incluyen cosas con las que el usuario interactúa directamente, como el navegador web y la aplicación de consola en el diagrama anterior. También incluyen almacenes de datos, como la base de datos SQL y la base de datos NoSQL. 

Otros ejemplos de sistemas externos comunes incluyen interfaces gráficas de usuario de escritorio, sistemas de archivos, API de servicios web descendentes y controladores de dispositivos de hardware. La mayoría de las aplicaciones necesitarán interactuar con sistemas como estos.  

En la arquitectura hexagonal, el núcleo del código de la aplicación no conoce ningún detalle sobre cómo se interactúa con los sistemas externos. La responsabilidad de comunicarse con sistemas externos se le da a una pieza de código conocida como **adaptador**.  

Los adaptadores encapsulan todo el conocimiento que el sistema necesita para interactuar con un sistema externo  y nada más. Los adaptadores tienen la única responsabilidad de saber cómo interactuar con un sistema externo. Si ese sistema externo cambia su interfaz pública, solo el adaptador deberá cambiar.  

**Los adaptadores se conectan a los puertos**

Los puertos son parte del modelo de dominio. Abstraen los detalles del intrincado conocimiento del adaptador de su sistema externo. Los puertos responden a una pregunta ligeramente diferente: ¿para qué necesitamos ese sistema externo? 

Los puertos usan el principio de Inversión de Dependencia para aislar el código de dominio de conocer cualquier detalle sobre los adaptadores. Están escritos puramente en términos de el modelo de dominio.

Dado el ejemplo de informe de ventas anterior, el puerto de comandos incluiría una forma de solicitar un informe de ventas. En el código, podría verse tan simple como esto: 

```
package com.sales.domain; 
import java.time.LocalDate; 
public interface Commands { 
    SalesReport calculateForPeriod(LocalDate start, 
        LocalDate end); 
} 
```

Este fragmento de código presenta lo siguiente:  

- Sin referencias a `HttpServletRequest` ni nada que ver con HTTP
- Sin referencias a formatos JSON
- Referencias a el modelo de dominio: `SalesReport` y `java.time.LocalDate`.
- El modificador de acceso `públic`, por lo que se puede llamar desde el adaptador REST. 

Esta interfaz es un puerto. Nos brinda una forma general de obtener un informe de ventas de la aplicación. 

**Los puertos se conectan a el modelo de dominio**

El modelo de dominio representa las cosas que los usuarios quieren hacer, en código. Cada historia de usuario se describe mediante un código aquí. 
Idealmente, el código de esta capa utiliza el lenguaje del problema que estamos resolviendo, en lugar de detalles tecnológicos. Cuando hacemos esto bien, este código describe acciones que preocupan a los usuarios en los términos que nos han contado.

En el centro de toda la aplicación se encuentra el modelo de dominio. Contiene la lógica que da vida a las historias de los usuarios.  

Contiene código que describe cómo se resuelve el problema del usuario. Esta es la lógica esencial de la aplicación que crea valor empresarial.  

**La regla de oro** : el dominio nunca se conecta directamente a los adaptadores.

A partir de esto podemos tomar dos decisiones estructurales de alto nivel:  

- El modelo de dominio vive en un paquete de dominio (y subpaquetes)
- Los adaptadores viven en un paquete de adaptadores (y subpaquetes)  

Las herramientas de análisis estático como [SonarQube](https://docs.sonarqube.org/latest/) pueden automatizar las comprobaciones de importación como parte del proceso de construcción.  

#### Las reglas de oro de la arquitectura hexagonal  

- El modelo de dominio nunca se conecta directamente a nada en la capa del adaptador para que la lógica de la aplicación no dependa de los detalles de los sistemas externos.
- Los adaptadores se conectan a los puertos para aislar el código que se conecta a los sistemas externos.
- Los puertos son parte del modelo de dominio para crear abstracciones de sistemas externos.
- El modelo de dominio y los adaptadores dependen únicamente de los puertos. Esta es la inversión de dependencia en el trabajo.  

Estas reglas simples mantienen el diseño en línea y preservan el aislamiento del modelo de dominio.  

### Abstracción del sistema externo  

Tomaremos un enfoque paso a paso para manejar sistemas externos, donde primero decidiremos qué necesita el modelo de dominio, luego resolveremos las abstracciones correctas que ocultan sus detalles técnicos. 

Consideraremos dos sistemas externos comunes: solicitudes web y acceso a bases de datos.  

#### Decidir qué necesita el modelo de dominio

 El lugar para comenzar el diseño es con el modelo de dominio. Necesitamos diseñar un puerto adecuado para que el modelo de dominio interactúe. Este puerto debe estar libre de cualquier detalle de el sistema externo y al mismo tiempo, debe responder a la pregunta de para qué la aplicación necesita este sistema. Estamos creando una abstracción. 

Pero hay varios tipos comunes de abstracciones que usaremos. Esto se debe a que se utilizan tipos comunes de sistemas externos al crear una aplicación web típica. La primera y más obvia es la conexión a la propia web. En la mayoría de las aplicaciones, encontraremos algún tipo de almacén de datos, normalmente un sistema de base de datos de terceros. 

Para muchas aplicaciones, también llamaremos a otro servicio web. A su vez, este servicio podrá llamar a otros en una flota de servicios, todos internos de la empresa. Otra llamada de servicio web típica es a un proveedor de servicios web de terceros, como un procesador de pagos con tarjeta de crédito, por ejemplo.  

Veamos formas de abstraer estos sistemas externos comunes.  

**Abstracción de solicitudes y respuestas web**  

La aplicación responderá a las solicitudes y respuestas HTTP. El puerto que necesitamos diseñar representa la solicitud y la respuesta en términos de el modelo de dominio, eliminando la tecnología web. 

El ejemplo de informe de ventas podría presentar estas ideas como dos objetos de dominio simples. Estas solicitudes se pueden representar mediante una clase `RequestSalesReport`:  

```
package com.sales.domain; 
import java.time.LocalDate; 
public class RequestSalesReport {

    private final LocalDate start; 
    private final LocalDate end; 
    public RequestSalesReport(LocalDate start, 
                              LocalDate end){ 
        this.start = start; 
        this.end = end; 
    } 
    public SalesReport produce(SalesReporting reporting) { 
        return reporting.reportForPeriod(start, end); 
    } 
} 
```

Aquí, podemos ver las piezas críticas de el modelo de dominio de la solicitud:  

- Lo que estamos solicitando, es decir, un informe de ventas, capturado en el nombre de la clase
- Los parámetros de esa solicitud, es decir, las fechas de inicio y finalización del período del informe.  

Podemos ver cómo se representa la respuesta:  

- La clase `SalesReport` contendrá la información sin procesar solicitada.  

También podemos ver lo que no está presente:  

- Los formatos de datos utilizados en la solicitud web
- Códigos de estado HTTP, como 200 OK
- `HTTPServletRequest` y `HttpServletResponse` u objetos de marco equivalentes.  

Esta es una representación de modelo de dominio puro de una solicitud de informe de ventas entre dos fechas. No hay indicios de que esto haya venido de la web, un hecho que es muy útil ya que podemos solicitarlo desde otras fuentes de entrada, como una GUI de escritorio o una línea de comandos. 
Aún mejor, podemos crear estos objetos de modelo de dominio muy fácilmente en una prueba unitaria.  

**Abstrayendo la base de datos**  

En una arquitectura hexagonal, comenzamos diseñando el puerto con el que interactuará el modelo de dominio, de nuevo en términos de dominio puro. La forma de crear una abstracción de base de datos es pensar en qué datos se deben almacenar y no en cómo se almacenarán.  

Un puerto de base de datos tiene dos componentes:  

- Una interfaz para invertir la dependencia de la base de datos.
- La interfaz a menudo se conoce como repositorio y  tiene la función de aislar el modelo de dominio de cualquier parte de la base de datos y su tecnología de acceso.
- Objetos de valor que representan los datos en sí mismos, en términos de modelo de dominio.  

Existe un objeto de valor para transferir datos de un lugar a otro. Dos objetos de valor que contienen los mismos valores de datos se consideran iguales. Son ideales para transferir datos entre la base de datos y el código.  

Volviendo a el ejemplo de informe de ventas, un posible diseño para el repositorio sería este: 

```
package com.sales.domain; 
public interface SalesRepository { 
    List<Sale> allWithinDateRange(LocalDate start, 
                                  LocalDate end); 
} 
```

Aquí, tenemos un método llamado `allWithinDateRange()` que nos permite obtener un conjunto de transacciones de ventas individuales que se encuentran dentro de un rango de fechas particular. 
Los datos se devuelven como `java.util.List` de objetos de valor `Sale`.  Estos son objetos de modelo de dominio con todas las funciones. 

Es posible que tengan métodos que realicen parte de la lógica de la aplicación crítica. Pueden ser poco más que estructuras de datos básicas, tal vez utilizando una estructura `record` de Java 17. Esta elección es parte de el trabajo al decidir cómo se ve un diseño bien diseñado en el caso específico.  

Nuevamente, podemos ver lo que no está presente:  

- Cadenas de conexión de base de datos
- Detalles de la API de JDBC o JPA: la biblioteca estándar de conectividad de bases de datos de Java
- Consultas SQL (o consultas NoSQL)
- Esquema de base de datos y nombres de tablas
- Detalles del procedimiento almacenado en la base de datos  

El diseño de repositorio se centran en lo que el modelo de dominio necesita que proporcione la base de datos, pero no restringe la forma en que lo proporciona. Como resultado, se deben tomar algunas decisiones interesantes al diseñar el repositorio, con respecto a cuánto trabajo ponemos en la base de datos y cuánto hacemos en el propio modelo de dominio. 

Ejemplos de esto incluyen decidir si escribiremos una consulta compleja en el adaptador de base de datos o si escribiremos consultas más simples y realizaremos trabajo adicional en el modelo de dominio. Asimismo, ¿haremos uso de procedimientos almacenados en la base de datos?. 

Cualesquiera que sean las compensaciones que decidamos en estas decisiones, una vez más, el adaptador de la base de datos es donde residen todas esas decisiones. El adaptador es donde vemos las cadenas de conexión de la base de datos, las cadenas de consulta, los nombres de las tablas, etc. El adaptador encapsula los detalles de diseño de el esquema de datos y tecnología de base de datos. 

**Abstracción de llamadas a servicios web** 

Hacer llamadas a otros servicios web es una tarea de desarrollo frecuente. Los ejemplos incluyen llamadas a procesadores de pagos y servicios de búsqueda de direcciones. 

A veces, estos son servicios externos de terceros y, a veces, viven dentro de la flota de servicios web. De cualquier manera, generalmente requieren que se realicen algunas llamadas HTTP desde la aplicación.  

La abstracción de estas llamadas procede de manera similar a la abstracción de la base de datos. El puerto se compone de una interfaz que invierte la dependencia del servicio web al que llamamos y algunos objetos de valor que transfieren datos.  

Un ejemplo de abstracción de una llamada a una API de mapeo como Google Maps, por ejemplo, podría verse así:  

```
package com.sales.domain; 
public interface MappingService { 
   void addReview(GeographicLocation location, 
                   Review review); 
} 
```

Tenemos una interfaz que representa a `MappingService` como un todo. Hemos agregado un método para agregar una revisión de una ubicación en particular en cualquier proveedor de servicios que terminemos usando. 

Usamos `GeographicLocation` para representar un lugar. Bien puede tener un par de latitud y longitud o puede estar basado en un código postal. Esa es otra decisión de diseño. Nuevamente, no vemos ninguna señal del servicio de mapas subyacente o sus detalles de API. Ese código vive en el adaptador, que se conectaría al servicio web de mapeo externo real.  

Esta abstracción nos ofrece beneficios al poder usar un doble de prueba para ese servicio externo y poder cambiar de proveedor de servicios en el futuro. 

Nunca se sabe cuándo un servicio externo podría cerrarse o volverse demasiado costoso de usar. Es bueno mantener las opciones abiertas usando la arquitectura hexagonal. 


