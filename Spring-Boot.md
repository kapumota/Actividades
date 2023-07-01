## Curso de desarrollo de sftware

### Introducción a Spring Boot 

En esta nota, se presentará cómo crear un conjunto de microservicios cooperativos utilizando [Spring Boot](https://spring.io/projects/spring-boot), centrándonos en cómo desarrollar 
funcionalidades que brinden valor comercial. 

### Spring Boot 

Spring Boot y [Spring Framework](https://spring.io/projects/spring-framework) en el que se basa Spring Boot, es un excelente framework para desarrollar microservicios en Java.  

Spring Framework proporciona un modelo de desarrollo basado en el concepto de inyección de dependencia y usa archivos de configuración XML mucho más livianos en comparación 
con los descriptores de implementación en J2EE.  

A lo largo de los años, mientras que Spring Framework ganaba cada vez más popularidad, la funcionalidad de Spring Framework creció significativamente. Lentamente, 
la carga de configurar una aplicación Spring utilizando un archivo de configuración XML, que ya no es tan liviano, se convirtió en un problema. 

En 2014, se lanzó Spring Boot v1.0, que solucionó estos problemas. 

### Convención sobre configuración y archivos JAR  

Spring Boot tiene como objetivo el desarrollo rápido de aplicaciones Spring listas para la producción a partir de configurar los módulos principales de Spring Framework y 
productos de terceros, como librerías que se usan para iniciar sesión o conectarse a una base de datos. Spring Boot lo hace aplicando una serie de convenciones de forma predeterminada, lo que minimiza la necesidad de configuración. 

Siempre que sea necesario, cada convención se puede anular escribiendo alguna configuración, caso por caso. 

Este patrón de diseño se conoce como **convention over configuration** y minimiza la necesidad de una configuración inicial.  

Los buenos archivos de configuración antiguos basados en XML todavía se pueden usar, aunque son significativamente más pequeños que antes de que se introdujera Spring Boot. 

Spring Boot también favorece un modelo de tiempo de ejecución basado en un archivo JAR independiente, también conocido como archivo fat JAR . 
Antes de Spring Boot, la forma más común de ejecutar una aplicación Spring era implementarla como un archivo WAR en un servidor web Java EE, como Apache Tomcat. 
La implementación de archivos WAR todavía es compatible con Spring Boot. 

Un archivo fat JAR contiene no solo las clases y los archivos de recursos de la aplicación en sí, sino también todos los archivos JAR de los que depende la aplicación. 
Esto significa que el archivo fat JAR es el único archivo JAR necesario para ejecutar la aplicación, es decir, solo necesitamos transferir un archivo JAR a un entorno en el que 
queremos ejecutar la aplicación en lugar de transferir el archivo JAR de la aplicación junto con todos los archivos JAR de los que depende la aplicación.  

Iniciar un fat JAR no requiere un servidor web Java EE instalado por separado, como Apache Tomcat. En su lugar, se puede iniciar con un comando simple como `java -jar app.jar` lo que 
lo convierte en una opción perfecta para ejecutar en un contenedor Docker. Si la aplicación Spring Boot, por ejemplo, usa HTTP para exponer una API REST, también contendrá un servidor web incorporado. 

### Ejemplos de código para configurar una aplicación Spring Boot 

Para comprender mejor lo que esto significa, veamos algunos ejemplos de código fuente. 

**La anotación mágica `@SpringBootApplication`** 

El mecanismo de configuración automática basado en convenciones se puede iniciar anotando la clase de aplicación, es decir, la clase que contiene el método `main` estático, 
con la anotación `@SpringBootApplication`. El siguiente código muestra esto: 

``` 
@SpringBootApplication 
public class Application1 { 
public static void main(String[] args) { 
     SpringApplication.run(Application1.class, args); 
     } 
} 
```
Esta anotación proporcionará la siguiente funcionalidad: 

- Habilita el escaneo de componentes, es decir, buscar componentes Spring y clases de configuración en el paquete de la clase de aplicación y todos sus subpaquetes.
- La propia clase de aplicación se convierte en una clase de configuración.
- Habilita la autoconfiguración, donde Spring Boot busca archivos JAR en el classpath que puede configurar automáticamente. Por ejemplo, si tienes Tomcat en el classpath, Spring Boot
  configurará automáticamente Tomcat como un servidor web integrado. 

**Escaneo de componentes** 

Supongamos que tenemos el siguiente componente Spring en el paquete de la clase de aplicación (o en uno de sus subpaquetes): 

```
@Component 
public class ComponentImpl1 implements Component1 {... 
```

Otro componente de la aplicación puede inyectar este componente automáticamente, también conocido como `auto-wiring` utilizando la anotación `@Autowired`: 

```
public class AnotherComponent { 
  private final Component1 component1; 
  @Autowired 
   public AnotherComponent(Component1 component1) { 
        this.componente1 = componente1; 
} 
```

Se prefiere usar la inyección de constructor (sobre la inyección de campo y `setter`) para mantener inmutable el estado en mis componentes. 
Un estado inmutable es importante si deseas poder ejecutar el componente en un entorno de tiempo de ejecución multiproceso. 

Si queremos usar componentes que se declaran en un paquete fuera del paquete de la aplicación, por ejemplo, un componente de utilidad compartido por varias 
aplicaciones Spring Boot, podemos complementar la anotación `@SpringBootApplication` en la clase de la aplicación con una anotación `@ComponentScan`: 

```
package com.Kapumota.myapp; 
@SpringBootApplication 
@ComponentScan({"com.Kapumota.myapp","se.Kapumota.utils"}) 
public class Application1 { 
```

Ahora podemos autoconectar (auto-wire) componentes del paquete `com.Kapumota.util` en el código de la aplicación, por ejemplo, un componente de utilidad llamado `Utility1` de la siguiente manera: 

```
package com.Kapumota.utils; 
@Component 
public class Utility1 {...  
```

Este componente de utilidad se puede conectar automáticamente en un componente de aplicación de la siguiente manera: 

```
package com.Kapumota.myapp.services; 
public class AnotherComponent { 
     private final Utility1 utility1; 
        @Autowired 
         public AnotherComponent(Utility1 utility1) { 
             this.utility1 = utility1; 
} 
```

#### Configuración basada en Java 

Si queremos sobrescribir la configuración predeterminada de Spring Boot o si queremos agregar la propia configuración, simplemente podemos anotar una clase con `@Configuration` 
y será detectada por el mecanismo de escaneo de componentes que describimos anteriormente. 

Por ejemplo, si queremos configurar un filtro en el procesamiento de solicitudes HTTP manejado por Spring WebFlux que escribe un mensaje de registro al principio y al final
del procesamiento, podemos configurar un filtro de registro, de la siguiente manera: 

```
@Configuration 
public class SubscriberApplication { 
  @Bean
   public Filter logFilter() {
      CommonsRequestLoggingFilter filter = new 
             CommonsRequestLoggingFilter(); 
      filter.setIncludeQueryString(true);
      filter.setIncludePayload(true);
      filter.setMaxPayloadLength(5120); 
      return filter; 
} 
```

También podemos colocar la configuración directamente en la clase de la aplicación, ya que la anotación `@SpringBootApplication` implica la anotación `@Configuration`. 

Ahora que hemos aprendido sobre Spring Boot, hablemos de Spring WebFlux. 

### Spring WebFlux 

Spring Boot 2.0 se basa en Spring Framework 5.0, que vino con soporte integrado para desarrollar aplicaciones reactivas. 
Spring Framework utiliza Project Reactor como la implementación base de su soporte reactivo, y también viene con un nuevo framework  web, Spring WebFlux, que admite 
el desarrollo de clientes y servicios HTTP reactivos, es decir, sin bloqueo. 

Spring WebFlux admite dos modelos de programación diferentes: 

- Un estilo imperativo basado en anotaciones, similar al marco web ya existente Spring Web MVC, pero con soporte para servicios reactivos
- Un nuevo modelo orientado a funciones basado en routers y controladores 

Spring WebFlux también proporciona un cliente HTTP totalmente reactivo, `WebClient` como complemento del cliente `RestTemplate` existente, además de 
admitir la ejecución en un contenedor de servlet como Apache Tomcat y de servidores web integrados reactivos que no están basados en Servlet, como `Netty` (https://netty.io/). 

La especificación Servlet es una especificación en la plataforma Java EE que estandariza cómo desarrollar aplicaciones Java que se comunican mediante protocolos web como HTTP. 

### Ejemplos de código de configuración de un servicio REST 

Antes de que podamos crear un servicio REST basado en Spring WebFlux, debemos agregar Spring WebFlux (y las dependencias que requiere Spring WebFlux) a la ruta de clase para 
que Spring Boot se detecte y configure durante el inicio. Spring Boot proporciona una gran cantidad de dependencias iniciales convenientes que incorporan una característica específica, 
junto con las dependencias que normalmente requiere cada característica. 

Entonces, usemos la dependencia de inicio para Spring WebFlux y luego veamos cómo se ve un servicio REST simple. 

**Dependencias de inicio**

En este curso usaremos Gradle como la herramienta de compilación, por lo que la dependencia inicial de Spring WebFlux se agregará al archivo `build.gradle`. Se parece a esto: 

```
implementation('org.springframework.boot:spring-boot-starter-webflux') 
```
Cuando se inicia el microservicio, Spring Boot detectará Spring WebFlux en el classpath y lo configurará, además de otras cosas, como iniciar un servidor web incorporado. 
Spring WebFlux usa Netty de forma predeterminada.

Si queremos cambiar de Netty a Tomcat como el servidor web integrado, podemos sobreescribir la configuración predeterminada excluyendo a Netty de la dependencia de inicio 
y agregando la dependencia de inicio para Tomcat: 

```
implementation('org.springframework.boot:spring-boot-starter-webflux') 
{ 
  exclude group: 'org.springframework.boot', module: 'spring-boot-
  starter-reactor-netty' 
} 
implementation('org.springframework.boot:spring-boot-starter-tomcat') 
```

**Archivos de propiedad** 

Como puedes ver en los ejemplos anteriores, el servidor web se inicia con el puerto 8080. Si deseas cambiar el puerto, puedes sobreescribir el valor predeterminado mediante un archivo de propiedades. 
Los archivos de propiedades de la aplicación Spring Boot pueden ser un archivo `.properties` o un archivo YAML. 
 
De forma predeterminada, se denominan `application.properties` y `application. yml` respectivamente. 

Usaremos archivos YAML para que el puerto HTTP que usa el servidor web incorporado se pueda cambiar a, por ejemplo 7001. 
Al hacer esto, podemos evitar colisiones de puertos con otros microservicios que se ejecutan en el mismo servidor. 

Para ello, podemos añadir la siguiente línea al archivo `application.yml`: 

```
 server.port: 7001 
```

**RestController**

Ahora, con Spring WebFlux y un servidor web incorporado de la elección, podemos escribir un servicio REST de la misma manera que cuando usamos Spring MVC, es decir, como un `RestController`: 

```
@RestController 
public class MyRestService { 

   @GetMapping (value = "/my-resource", produces = "application/json")
   List<Resource> listResources() { 
       ... 
}
```

La anotación `@GetMapping` en el método `listResources()` asignará el método Java a una  HTTP GET API en el url `host:8080/myResource`. 
El valor de retorno del tipo `List<Resource>` se convertirá en JSON. 

**springdoc-openapi**


[springdoc-openapi](https://springdoc.org/) es un proyecto de código abierto, independiente de Spring Framework, que puede crear documentación API basada en OpenAPI en tiempo de ejecución. 

springdoc-openapi nos ayuda a documentar las API expuestas por los microservicios. 


### Spring data  

[Spring Data](https://spring.io/projects/spring-data) viene con un modelo de programación común para la persistencia de datos en varios tipos de motores de bases de datos, que van desde 
bases de datos relacionales tradicionales (bases de datos SQL) hasta varios tipos de motores de bases de datos NoSQL, como bases de datos de 
documentos (por ejemplo, [MongoDB](https://www.mongodb.com/)), bases de datos de valores clave (por ejemplo, [Redis](https://redis.io/)) y bases de datos de grafos (por ejemplo, [Neo4J](https://neo4j.com/)). 

El proyecto Spring Data se divide en varios subproyectos como  MongoDB y JPA que se han asignado a una base de datos MySQL. 

Los dos conceptos centrales del modelo de programación en Spring Data son las entidades y repositorios. 

Las entidades y los repositorios generalizan cómo se almacenan y se accede a los datos desde los distintos tipos de bases de datos. Proporcionan una abstracción común, pero aún 
admiten la adición de un comportamiento específico de la base de datos a las entidades y repositorios.   

**Entidad** 

Una entidad describe los datos que Spring Data almacenará. Las clases de entidad se anotan, en general, con una combinación de anotaciones genéricas de Spring Data y anotaciones
que son específicas para cada tecnología de base de datos. 

Por ejemplo, una entidad que se almacenará en una base de datos relacional se puede anotar con anotaciones JPA como las siguientes: 

```
import javax.persistence.Entity; 
import javax.persistence.Id; 
import javax.persistence.IdClass; 
import javax.persistence.Table; 
@Entity 

 @IdClass(ReviewEntityPK.class)
 @Table(name = "review") 
  public class ReviewEntity { 
   @Id private int productId; 
   @Id private int reviewId; 
   private String author;
   private String subject;
   private String content; 
```

Si se va a almacenar una entidad en una base de datos MongoDB, las anotaciones del subproyecto Spring Data MongoDB se pueden usar junto con las anotaciones genéricas de Spring Data. 
Por ejemplo, considera el siguiente código: 

```
import org.springframework.data.annotation.Id; 
import org.springframework.data.annotation.Version; 
import org.springframework.data.mongodb.core.mapping.Document; 

@Document 
public class RecommendationEntity { 
   @Id 
    private String id; 
   @Version
    private int version; 

    private int productId; 
    private int recommendationId; 
    private String author; 
    private int rate; 
    private String content; 
```

Las anotaciones `@Id` y `@Version` son anotaciones genéricas, mientras que la anotación `@Document` es específica del subproyecto Spring Data MongoDB.  

**Repositorios** 

Los repositorios se utilizan para almacenar y acceder a datos de diferentes tipos de bases de datos. En su forma más básica, un repositorio puede declararse como una interfaz Java y 
Spring Data generará su implementación sobre la marcha utilizando convenciones establecidas. 
Estas convenciones se pueden sobreescribirse y/o complementar con una configuración adicional y, si es necesario, algún código Java. 

Spring Data también viene con algunas interfaces básicas de Java, por ejemplo, `CrudRepository` para simplificar aún más la definición de un repositorio. 
La interfaz base, `CrudRepository` nos proporciona métodos estándar para operaciones de creación, lectura, actualización y eliminación. 

Para especificar un repositorio para manejar la entidad `JPA ReviewEntity` solo necesitamos declarar lo siguiente: 

```
import org.springframework.data.repository.CrudRepository; 
public interface ReviewRepository extends 
      CrudRepository<ReviewEntity, ReviewEntityPK> { 
      Collection<ReviewEntity> findByProductId(int productId); 
} 
```
En este ejemplo, usamos una clase `ReviewEntityPK`, para describir una clave principal compuesta. Se ve de la siguiente manera: 

```
public class ReviewEntityPK implements Serializable { 
 public int productId;  
 public int reviewId; 
  }  
```
También hemos agregado un método adicional, `findByProductId` que nos permite buscar entidades de revisión basadas en `productid`, un campo que forma parte de la clave principal. 
La denominación del método sigue una convención de nomenclatura definida por Spring Data que permite que Spring Data también genere la implementación de este método sobre la marcha.  

Si queremos usar el repositorio, simplemente podemos inyectarlo y luego comenzar a usarlo, por ejemplo: 

```
private final ReviewRepository repository; 
@Autowired 

public ReviewService(ReviewRepository repository) { 
   this.repository = repository; 

} 

 public void someMethod() { 
    repository.save(entity); 
    repository.delete(entity); 
    repository.findByProductId(productId); 
```
 

Agregado a la interfaz `CrudRepository`, Spring Data también proporciona una interfaz base reactiva, `ReactiveCrudRepository`, que habilita repositorios reactivos. 
Los métodos de esta interfaz no devuelven objetos ni colecciones de objetos, en su lugar, devuelven objetos `Mono` y `Flux`. 
Son flujos reactivos que pueden devolver `0...1` o `0...m` entidades a medida que estén disponibles en el flujo. 

La interfaz basada en reactivos solo puede ser utilizada por subproyectos de Spring Data que admitan controladores de bases de datos reactivos, es decir, se basan en E/S sin bloqueo. 
El subproyecto Spring Data MongoDB admite repositorios reactivos, mientras que Spring Data JPA no.  

Especificar un repositorio reactivo para manejar la entidad MongoDB, RecommendationEntity  como se describió anteriormente, podría tener un aspecto similar al siguiente: 

```
import org.springframework.data.repository.reactive. 
ReactiveCrudRepository; 
  import reactor.core.publisher.Flux; 
   public interface RecommendationRepository extends ReactiveCrudRepository
      <RecommendationEntity, String> { 
         Flux<RecommendationEntity> findByProductId(int productId); 
   } 
```

### Spring Cloud Stream  

Incorporaremos uno de los módulos que forma parte de Spring Cloud: [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream). 

Spring Cloud Stream proporciona una abstracción de transmisión sobre la mensajería, basada en el patrón de integración de **publish** y **subscribe**.  

Spring Cloud Stream actualmente viene con soporte integrado para [Apache Kafka](https://kafka.apache.org/) y [RabbitMQ](https://www.rabbitmq.com/). 
Existe una serie de proyectos separados que brindan integración con otros sistemas de mensajería populares. 

Consulta https://github.com/spring-cloud?q=binder para obtener más detalles. 

Los conceptos centrales en Spring Cloud Stream son los siguientes: 

- **Message**: una estructura de datos que se utiliza para describir los datos enviados y recibidos desde un sistema de mensajería.
- **Publisher**: envía mensajes al sistema de mensajería, también conocido como `Supplier`.
- **Suscriptor**: recibe mensajes del sistema de mensajería, también conocido como `Consumer`.
- **Destination**: se utiliza para comunicarse con el sistema de mensajería. Los `publisher` usan destinos de salida y los suscriptores usan destinos de entrada.
  Los destinos se asignan mediante `binders` específicos a colas y temas en el sistema de mensajería subyacente. 
- **Binders**: un binder proporciona la integración real con un sistema de mensajería específico, similar a lo que hace un controlador JDBC para un tipo específico de base de datos. 

El sistema de mensajería real que se utilizará se determina en tiempo de ejecución, según lo que se encuentre en el classpath. 
Spring Cloud Stream viene con convenciones obstinadas sobre cómo manejar la mensajería. 
Estas convenciones se pueden sobreescribir especificando una configuración para funciones de mensajería como 
grupos de consumidores, partición, persistencia, durabilidad y manejo de errores, por ejemplo, reintentos y gestión de colas de mensajes fallidos. 

