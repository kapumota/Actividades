## Curso de desarrollo de software

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

Para poder instalar Spring Boot puedes utilizar la [documentación](https://docs.spring.io/spring-boot/docs/2.0.0.M5/reference/html/getting-started-installing-spring-boot.html) o utilizar [SDKMAN!](https://sdkman.io/), que se puede usar para administrar varias versiones de varios SDK binarios, incluidos Groovy y Spring Boot CLI.

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
admitir la ejecución en un contenedor de servlet como Apache Tomcat y de servidores web integrados reactivos que no están basados en Servlet, como [Netty](https://netty.io/). 

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

#### Ejemplos de código para enviar y recibir mensajes 

Para comprender mejor cómo encaja todo esto, veamos algunos ejemplos de código fuente. 

Spring Cloud Stream viene con dos modelos de programación: un modelo más antiguo y actualmente en desuso basado en el uso de anotaciones (por ejemplo, `@EnableBinding`, `@Output` y `@StreamListener`) y un modelo más nuevo basado en funciones de escritura. 

Para implementar un `publisher`, solo necesitamos implementar la interfaz funcional `java.util.function.Supplier` como `Spring Bean`.  Por ejemplo, el siguiente es un editor 
que publica mensajes como una cadena: 

```
 @Bean 
   public Supplier<String> myPublisher() { 
         return () -> new Date().toString(); 
    } 
```
Un `suscriber` se implementa como `Spring Bean` implementando la Interfaz funcional `java.util.function.Consumer`. Por ejemplo, el siguiente es un suscriptor que consume mensajes como cadenas: 

```
@Bean 
public Consumer<String> mySubscriber() {
     return s -> System.out.println("ML RECIBIDO: " + s); 
} 
```

También es posible definir un Spring Bean que procese mensajes, lo que significa que consume y publica mensajes. Esto se puede hacer implementando la interfaz funcional `java.util.function.Function`. Por ejemplo, un Spring Bean que consume mensajes entrantes y publica un nuevo mensaje después de algún procesamiento (ambos mensajes son cadenas en este ejemplo): 

```
@Bean 
public Function<String, String> myProcessor() { 
     return s -> "ML PROCESADO: " + s; 
} 
```

Para que Spring Cloud Stream conozca estas funciones, debemos declararlas mediante la propiedad de configuración `spring.cloud.function.definition`. 
Por ejemplo, para las tres funciones definidas anteriormente, esto se vería de la siguiente manera: 

```
spring.cloud.function: 
  definition: myPublisher;myProcessor;mySubscriber 
```
 
Finalmente, debemos decirle a Spring Cloud Stream qué destino usar para cada función. Para conectar las tres funciones para que el procesador consuma mensajes del editor 
y el suscriptor consuma mensajes del procesador, podemos proporcionar la siguiente configuración: 

```
spring.cloud.stream.bindings: 
  myPublisher-out-0: 
    destination: myProcessor-in  
  myProcessor-in-0: 
    destination: myProcessor-in 
  myProcessor-out-0: 
    destination: myProcessor-out 
  mySubscriber-in-0: 
    destination: myProcessor-out
```

Esto dará como resultado el siguiente flujo de mensajes: 

```
myPublisher --> myProcessor --> mySubscribe 
```

Spring Cloud Stream activa un proveedor de forma predeterminada cada segundo, por lo que podríamos esperar un resultado como el siguiente si iniciamos una aplicación Spring Boot que incluye las funciones y la configuración descritas anteriormente: 

```
ML RECIBIDO: ML PROCESADO: Wed Jan 28 16:28:30 CET 2023 
ML RECIBIDO: ML PROCESADO: Wed Jan 28 16:28:31 CET 2023 
ML RECIBIDO: ML PROCESADO: Wed Jan 28 16:28:32 CET 2023 
ML RECIBIDO: ML PROCESADO: Wed Jan 28 16:28:33 CET 2023
```

En los casos en los que el `Supplier` deba ser activado por un evento externo en lugar de usar un temporizador, se puede usar la clase auxiliar `StreamBridge`. 

Por ejemplo, si se debe publicar un mensaje en el procesador cuando se llama a una API REST, `sampleCreateAPI` el código podría tener el siguiente aspecto: 

```
@Autowired 
private StreamBridge streamBridge; 

@PostMapping 
void sampleCreateAPI(@RequestBody String body) {
   streamBridge.send("myProcessor-in-0", body); 
} 
```

### Docker 

Docker hizo muy popular el concepto de contenedores como una alternativa liviana a las máquinas virtuales. Un contenedor es en realidad un proceso que usa espacios de nombres de Linux para proporcionar aislamiento entre diferentes contenedores, en términos de su uso de recursos del sistema global como usuarios, procesos, sistemas de archivos y redes. 

Los grupos de control de Linux (también conocidos como cgroups) se utilizan para limitar la cantidad de CPU y memoria que puede consumir un contenedor. 

En comparación con una máquina virtual que usa un hipervisor para ejecutar una copia completa de un sistema operativo en cada máquina virtual, la sobrecarga en un contenedor es una fracción de la sobrecarga en una máquina virtual tradicional. Esto conduce a tiempos de inicio mucho más rápidos y una sobrecarga significativamente menor en términos de uso de CPU y memoria. 

Sin embargo, el aislamiento que se proporciona para un contenedor no se considera tan seguro como el aislamiento que se proporciona para una máquina virtual. 

Los contenedores son muy útiles durante el desarrollo y las pruebas. Ser capaz de iniciar un panorama de sistema completo de microservicios cooperativos y administradores de recursos (por ejemplo, servidores de bases de datos, agentes de mensajería, etc.) con un solo comando para realizar pruebas es simplemente increíble. 

Por ejemplo, podemos escribir scripts para automatizar las pruebas de extremo a extremo de un panorama de microservicios. Un script de prueba puede poner en marcha el panorama de microservicios, ejecutar pruebas con las API expuestas y derribar el panorama. 

Este tipo de secuencia de comandos de prueba automatizada es muy útil, tanto para ejecutarse localmente en una PC de desarrollador antes de enviar el código a un repositorio de código fuente, como para ejecutarse como un paso en un pipeline de entrega. 

Un servidor de compilación puede ejecutar este tipo de pruebas en su proceso continuo de integración e implementación cada vez que un desarrollador inserta código en el repositorio de origen. 

Para el uso de producción, necesitamos un orquestador de contenedores como Kubernetes. 

Para la mayoría de los microservicios que veremos un [Dockerfile](https://iesgn.github.io/curso_docker_2021/sesion6/dockerfile.html) como el siguiente es todo lo que se requiere para ejecutar el microservicio como un contenedor de Docker: 

```
FROM openjdk:16 

MAINTAINER Kapumota <kapumota@awd.com>  

EXPOSE 8080 
ADD ./build/libs/*.jar app.jar 
ENTRYPOINT ["java","-jar","/app.jar"] 
```

Si queremos iniciar y detener muchos contenedores con un solo comando, [Docker Compose](https://docs.docker.com/compose/) es la herramienta perfecta.

Docker Compose utiliza un archivo YAML para describir los contenedores que se administrarán.

Para los microservicios, podría parecerse a lo siguiente: 

```
product: 
 build: microservicios/product-service
 
recommendation: 
  build: microservicios/recommendation-service 
  
review: 
  build: microservicios/review-service 

composite: 
  build: microservicios/product-composite-service 
  ports: 
     - "8080:8080" 
```
     
Explicamos un poco el código fuente anterior: 

- La directiva `build` se usa para especificar qué Dockerfile usar para cada microservicio. `Docker Compose` lo usará para crear una imagen de Docker y luego lanzará un contenedor de Docker basado en esa imagen de Docker.
- La directiva de `ports` para el servicio compuesto se usa para exponer el puerto 8080 en el servidor donde se ejecuta Docker. En la máquina de un desarrollador, esto significa que se puede acceder al puerto del servicio compuesto simplemente usando `localhost: 8080`. 

Todos los contenedores en los archivos YAML se pueden administrar con comandos simples como los siguientes: 

- `docker-compose up -d`: inicia todos los contenedores. `-d` significa que los contenedores se ejecutan en segundo plano, sin bloquear la terminal desde donde se ejecutó el comando.
- `docker-compose down`: detiene y elimina todos los contenedores.
-  `docker-compose logs -f --tail=0`: imprime los mensajes de registro de todos los contenedores. `-f` significa que el comando no se completará y en su lugar, esperará nuevos mensajes de registro `--tail=0` significa que no queremos ver ningún mensaje de registro anterior, solo los nuevos. 

Para obtener una lista completa de los comandos de Docker Compose, consulta https://docs.docker.com/compose/reference/.

### Preguntas

1. ¿Cuál es el propósito de la anotación `@SpringBootApplication`?

2. ¿Cuáles son las principales diferencias entre el antiguo componente Spring para desarrollar servicios REST, Spring Web MVC y el nuevo Spring WebFlux?

3. ¿Cómo ayuda `springdoc-openapi` a un desarrollador a documentar las API REST?

4. ¿Cuál es la función de un repositorio en `Spring Data` y cuál es la implementación más simple posible de un repositorio?

5. ¿Cuál es el propósito de un `binder` en `Spring Cloud Stream`?

6. ¿Cuál es el propósito de `Docker Compose`?.
