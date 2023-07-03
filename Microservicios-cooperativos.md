## Curso de desarrollo de software

En esta actividad, construiremos el primer par de microservicios. 
Aprenderemos a crear microservicios cooperativos con funcionalidad minimalista. Luego agregaremos más y más funciones a estos microservicios. 

Al final de esta actividad, tendremos una API RESTful expuesta por un microservicio compuesto. 

El microservicio compuesto llamará a otros tres microservicios utilizando sus API RESTful para crear una respuesta agregada. 

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta completa con código en una carpeta `Microservicios` y coloca todas tus respuestas.
Utiliza la carpeta sobre  [microservicios cooperativos](https://github.com/kapumota/Actividades/tree/main/Ejemplo-Microservicios) dado en el repositorio del curso.

Esta actividad es grupal.

### Introducción al panorama de los microservicios 

Presentamos brevemente el panorama del sistema basado en microservicios que usaremos en esta actividad: 

Consta de tres microservicios principales, los servicios `Product`, `Review` y `Recommendation`, todos los cuales se ocupan de un tipo de recurso y un microservicio compuesto 
denominado `Servicio  Product Composite` que agrega información de los tres servicios principales. 

#### Información que manejan los microservicios 

En esta sección, repasaremos la información que maneja cada microservicio, incluida la información relacionada con la infraestructura. 

- El servicio  `product`, gestiona la información del producto y describe cada producto con los siguientes atributos:
    * Product ID
    * Name
    * Weight 

- El servicio `review`, gestiona las reseñas de productos y almacena la siguiente información sobre cada reseña: 

    * Product ID
    * Review ID
    * Author
    * Subject
    * Content 

- El servicio `recommendation`, gestiona las recomendaciones de productos y almacena la siguiente información sobre cada recommendation: 

    * Product ID
    * Recommendation ID
    * Author
    * Rate
    * Content 

- El servicio `composite` de productos, agrega información de los tres servicios principales y presenta información sobre un producto de la siguiente manera: 

   * Información del producto como se describe en el servicio del producto.
   * Una lista de reseñas de productos para el producto especificado, como se describe en el servicio de reseñas.
   * Una lista de recomendaciones de productos para el producto especificado, como se describe en el servicio `recommendation`. 

#### Información relacionada con la infraestructura  

Una vez que comencemos a ejecutar los microservicios como contenedores administrados por la infraestructura (primero Docker y luego Kubernetes), será 
interesante rastrear qué contenedor respondió realmente a las solicitudes. 

Como solución simple, se agregó un atributo `serviceAddress` a todas las respuestas, con el formato `hostname/ip-address:port`.  

Ejecutaremos todos los microservicios en el `localhost` y usaremos números de puerto codificados para cada microservicio: 

- Product composite service: 7000
- Product service: 7001
- Review service: 7002
- Recommendation service: 7003 

No usaremos los puertos codificados más adelante cuando se usen con Docker y Kubernetes.

### Uso de Spring Initializr 

Usaremos [Spring Initializr](https://start.spring.io/) para generar un proyecto esqueleto para cada microservicio. 
Un esqueleto proyecto contiene los archivos necesarios para construir el proyecto, junto con una clase principal `main` y una clase de prueba para el microservicio. 

Después de eso, veremos cómo podemos construir todos los microservicios con un solo comando usando compilaciones de proyectos múltiples con Gradle. 

Spring Initializr se puede usar para configurar y generar nuevas aplicaciones de Spring Boot. 

La herramienta ayuda a los desarrolladores a elegir módulos Spring adicionales para que los use la aplicación y asegura que las dependencias estén configuradas para usar versiones
compatibles de los módulos seleccionados. 

La herramienta admite el uso de Maven o Gradle como sistema de compilación y puede generar código fuente para Java, Kotlin o Groovy. 

Se puede invocar desde un navegador web utilizando la URL https://start.spring.io/ o mediante una herramienta de línea de comandos, spring init. 

Para cada microservicio, crearemos un proyecto Spring Boot que realize lo siguiente: 

- Usar Gradle como una herramienta de construcción
- Generar código para Java 8
- Empaquetar el proyecto como un archivo fat JAR 
- Traer dependencias para los módulos Actuator y WebFlux Spring
- Basarse en Spring Boot v3.1.1. 

Para crear un código esqueleto para los microservicios, debemos ejecutar el siguiente comando para el `product-service`: 

```
spring init \ 
--boot-version=3.1.1 \ 
--build=gradle \ 
--java-version=1.8 \ 
--packaging=jar \ 
--name=product-service \ 
--package-name=com.kapumota.microservicios.core.product \ 
--groupId=com.kapumota.microservicios.core.product \ 
--dependencies=actuator,webflux \ 
--version=1.0.0-SNAPSHOT \ 
product-service 
```

Después de crear los cuatro proyectos tendremos la siguiente estructura de archivos: 

```
microservicios/ 

├── product-composite-service 

├── product-service 

├── recommendation-service 

└── review-service 
```

Para cada proyecto, podemos enumerar los archivos creados. Hagamos esto para el proyecto de `product-service`: 

```
find microservicios/product-service -type f 
```

**Pregunta:** Identifica y explica cuales son los archivos que creó Spring Initializr en este proyecto.  

La clase `main` de la aplicación, `ProductServiceApplication.java` tiene el aspecto esperado según la anotación mágica `@SpringBootApplication` de la actividad de spring boot: 

```
package com.kapumota.microservicios.core.product; 
@SpringBootApplication 
 public class ProductServiceApplication { 
} 
      public static void main(String[] args) { 
          SpringApplication.run(ProductServiceApplication.class, args); 

} 
```

La clase de prueba se ve de la siguiente manera: 

```
package com.kapumota.microservicios.core.product; 
@SpringBootTest 
class ProductServiceApplicationTests { 
 @Test 
    void contextLoads() { 
   } 
} 
```

La anotación `@SpringBootTest` inicializará la aplicación de la misma manera que lo hace `@SpringBootApplication` al ejecutar la aplicación, es decir, el contexto de la aplicación Spring
se configurará antes de que se ejecuten las pruebas mediante el escaneo de componentes y la configuración automática. 

Veamos también el archivo Gradle más importante, `build.gradle`. El contenido de este archivo describe cómo construir el proyecto, por ejemplo, cómo resolver 
dependencias y compilar, probar y empaquetar el código fuente. 

El archivo Gradle comienza enumerando qué complementos aplicar: 

```
 plugins { 
        id 'java' 
        id 'org.springframework.boot' version '3.1.1' 
        id 'io.spring.dependency-management' version '1.1.0' 

} 
```

Los complementos declarados se utilizan de la siguiente manera: 

- El complemento de Java agrega el compilador de Java al proyecto.
- Se declaran los complementos `org.springframework.boot` e `io.spring.dependency-management`, que en conjunto garantizan que Gradle creará un archivo fat JAR pesado
  y que no necesitamos especificar ningún número de versión explícito en las dependencias iniciales de Spring Boot.
  En cambio, están implícitos en la versión del complemento `org.springframework.boot`, es decir, 3.1.1. 

En el resto del archivo de compilación, básicamente declaramos un nombre de grupo y una versión para el proyecto, la versión de Java y sus dependencias: 

```
group = 'com.kapumota.microservicios.core.product' 
version = '0.0.1-SNAPSHOT'  
java { 
        sourceCompatibility = '17' 
} 
repositories { 
        mavenCentral() 
} 

dependencies { 

        implementation 'org.springframework.boot:spring-boot-starter-actuator' 
        implementation 'org.springframework.boot:spring-boot-starter-webflux' 
        testImplementation 'org.springframework.boot:spring-boot-starter-test' 
        testImplementation 'io.projectreactor:reactor-test' 
} 
tasks.named('test') { 
        useJUnitPlatform() 
} 
```
 
Algunas notas sobre las dependencias utilizadas y la declaración final `test`: 

- Las dependencias, al igual que con los complementos anteriores, se obtienen del repositorio central de Maven.
- Las dependencias se configuran como se especifica en los módulos `Actuator` y `WebFlux` junto con un par de dependencias de prueba útiles.
- Finalmente, JUnit está configurado para ejecutar nuestras pruebas en las compilaciones de Gradle. 

Podemos construir cada microservicio por separado con el siguiente comando: 

```
cd microservicios/product-composite-service; ./gradlew build; cd -; \ 
cd microservicios/product-service; ./gradlew build; cd -; \ 
cd microservicios/recommendation-service;  ./gradlew build; cd -; \ 
cd microservicios/review-service;  ./gradlew build; cd -; 
```
 
Ten en cuenta cómo usamos los ejecutables `gradlew` creados por Spring Initializr, es decir, ¡no necesitamos tener Gradle instalado!. 

La primera vez que ejecutamos un comando con gradlew se descargará Gradle automáticamente. 

La versión de Gradle que se usa está determinada por la propiedad `distributionUrl` en los archivos `gradle/wrapper/gradle-wrapper.properties`. 

### Configuración de compilaciones de proyectos múltiples en Gradle 

Para simplificar un poco la creación de todos los microservicios con un solo comando, podemos configurar una compilación de varios proyectos en Gradle. Los pasos son los siguientes: 

1. Primero, creamos el archivo `settings.gradle` que describe qué proyectos debe construir Gradle: 

  ```
  include ':microservicios:product-service' 
  include ':microservicios:review-service' 
  include ':microservicios:recommendation-service' 
  include ':microservicios:product-composite-service' 
  ```
2. A continuación, copiamos los archivos ejecutables de Gradle que se generaron a partir de uno de los proyectos para que podamos reutilizarlos para las compilaciones de varios proyectos: 

```
 cp -r microservicios/product-service/gradle . 
 cp microservicios/product-service/gradlew .  
 cp microservicios/product-service/.gitignore . 
```

3. Ya no necesitamos los archivos ejecutables de Gradle generados en cada proyecto, por lo que podemos eliminarlos con los siguientes comandos: 

```
 find microservicios -depth -name "gradle" -exec rm -rfv "{}" \;
 find microservicios -depth -name "gradlew*" -exec rm -fv "{}" \; 
```

4. Ahora, podemos construir todos los microservicios con un solo comando: 

```
./gradle build 
```
**Pregunta:** comprueba todos estos resultados y muestra los resultados obtenido. 

Con los esqueletos para los microservicios creados con Spring Initializr y creados con éxito con Gradle, estamos listos para agregar algo de código a los microservicios.


Desde una perspectiva de DevOps, es posible que no se prefiera una configuración de varios proyectos. 
En cambio, para permitir que cada microservicio tenga su propio ciclo de compilación y lanzamiento, probablemente sería preferible configurar un **pipeline de compilación separada** 
para cada proyecto de microservicio. 

Sin embargo, para los fines de este curso, utilizaremos la configuración de varios proyectos para que sea más fácil construir e implementar todo el entorno del sistema con un solo comando. 


### Agregar API RESTful 

Ahora que tenemos proyectos configurados para los microservicios, agreguemos algunas API RESTful a los tres microservicios principales. 

Primero, agregaremos dos proyectos (`api` y `util`) que contendrán código compartido por los proyectos de microservicios, y luego implementaremos las API RESTful. 

Para agregar un proyecto api, debemos hacer lo siguiente: 

1. Configuraremos un proyecto Gradle separado donde podemos colocar las definiciones de API. También se definen varias clases de excepción.
2. Luego crearemos un proyecto `util` que pueda contener algunas clases auxiliares que comparten los microservicios, por ejemplo, para manejar errores de manera uniforme. 

#### El proyecto API 

El proyecto `api` se empaquetará como una librería, es decir, no tendrá su propia clase de aplicación `main`. 
Desafortunadamente, Spring Initializr no admite la creación de proyectos de libreróa. En su lugar, un proyecto de librería o biblioteca debe crearse manualmente desde cero.  

La estructura de un proyecto de librería es la misma que para un proyecto de aplicación, excepto que ya no tenemos la clase de aplicación `main`, así como algunas diferencias menores en el archivo `build.gradle`. 
El complemento de Gradle `org.springframework.boot` se reemplaza con una sección `implementation platform`: 

```
ext { 
     springBootVersion = '3.1.1' 
} 

dependencias { 
     implementation platform ("org.springframework.boot:spring-boot-
dependencies:${springBootVersion}") 
```

Esto nos permite retener la administración de dependencias de Spring Boot mientras reemplazamos la construcción de un archivo fat JAR  en el paso de construcción 
con la creación de un archivo JAR normal que solo contiene las clases y los archivos de propiedades del proyecto. 

La estructura de las clases de Java es muy similar para los tres microservicios principales, por lo que solo analizaremos el código fuente del servicio `product`. 

Primero, veremos la interfaz Java de `ProductService.java`, como se muestra en el siguiente código: 

```
package com.kapumota.api.core.product; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;  
public interface ProductService { 

  @GetMapping ( 
    value = "/product/{productId}", 
    produces = "application/json") 
    Product getProduct(@PathVariable int productId); 
} 
```

**Pregunta:** Explica el funcionamiento de la declaración de la interfaz Java. 

El método devuelve un objeto `Product` una clase simple con las variables miembro correspondientes a los atributos de `Product`. 
`Product.java` tiene el siguiente aspecto (con constructores y métodos `getter` excluidos): 

```
public class Product { 
   private final int productId; 
   private final String name; 
   private final int weight; 
   private final String serviceAddress; 
} 
```  

El proyecto API también contiene las clases de excepción `InvalidInputException` y `NotFoundException`. 

#### El proyecto util 

El proyecto util se empaquetará como una biblioteca de la misma manera que el proyecto `api`.  

El proyecto contiene las siguientes clases de utilidad: `GlobalControllerExceptionHandler`, `HttpErrorInfo` y `ServiceUtil`. 

Excepto por el código en `ServiceUtil.java`, estas clases son clases de utilidad reutilizables que podemos usar para asignar excepciones de Java a códigos de estado HTTP adecuados.  

El objetivo principal de `ServiceUtil.java` es averiguar el nombre de host, la dirección IP y el puerto que utiliza el microservicio. La clase expone un método, `getServiceAddress()`, que los microservicios pueden usar para encontrar su nombre de host, dirección IP y puerto.

### Implementando la API 

Ahora podemos comenzar a implementar las API en los microservicios principales-

 La implementación es muy similar para los tres microservicios principales, por lo que solo analizaremos el código fuente del servicio `product`. Veamos cómo hacemos esto: 

1. Necesitamos agregar los proyectos `api` y `util` como dependencias del archivo `build.gradle`, en el proyecto del servicio `product`: 

   ```
   dependencies { 
     implementation project(':api') 
     implementation project(':util') 
   ```
2. Para habilitar la función de configuración automática de Spring Boot para detectar Spring Beans en los proyectos de `api` y `util`, también debemos agregar una
     anotación `@ComponentScan` a la clase de aplicación `main`, que incluye los paquetes de los proyectos de `api` y `util`: 

     ```
      @SpringBootApplication 
      @ComponentScan ("com. kapumota")
      public class ProductServiceApplication { 
    ```

3. A continuación, creamos el archivo de implementación de servicio, `ProductServiceImpl.java`, para implementar la interfaz de Java, `ProductService` desde el proyecto `api` y anotar la clase con `@RestController` para que Spring llame a los métodos de esta clase de acuerdo con las asignaciones especificadas en la clase `interface`: 

   ```
   package com.kapumota.microservicios.core.product.services; 
   @RestController
    public class ProductServiceImpl implements ProductService { 
   } 
   ```
4. Para poder usar la clase `ServiceUtil` del proyecto `util`, la inyectaremos en el constructor, de la siguiente manera: 
  
   ```
   private final ServiceUtil serviceUtil;
   @Autowired 
    public ProductServiceImpl(ServiceUtil serviceUtil) { 
      this.serviceUtil = serviceUtil; 
   } 
   ```
5. Ahora, podemos implementar la API sobreescribiendo el método `getProduct()` desde la interfaz en el proyecto API: 

   ```
     @Override 
      public Product getProduct(int productId) { 
       return new Product(productId, "nombre-" + productId, 123, 
       serviceUtil.getServiceAddress()); 
     } 
   ```

Dado que actualmente no estamos usando una base de datos, simplemente devolvemos una respuesta codificada basada en la entrada de `productId`, junto con la dirección de servicio proporcionada por la clase `ServiceUtil`. 

Para obtener el resultado final, incluido el registro y la gestión de errores, consulta `ProductServiceImpl.java`. 

6. Finalmente, también necesitamos configurar algunas propiedades de tiempo de ejecución: qué puerto usar y el nivel deseado de registro.
   Esto se agrega al archivo de propiedades `application.yml`: 

   ```
    server.port: 7001 
    logging: 
      level: 
        root: INFO 
        com.kapumota.microservicios: DEBUG 
   ```

Ten en cuenta que el archivo `application.properties` vacío generado por Spring Initializr se reemplazó por un archivo YAML, `application.yml`. 

Los archivos YAML brindan una mejor compatibilidad para agrupar propiedades relacionadas en comparación con los archivos `.properties`.

7. Podemos probar el servicio `producto` por sí solo. Crea e inicie el microservicio con los siguientes comandos:
  
    ```
      ./gradlew build
      java -jar microservicios/product-service/build/libs/*.jar &
    ```
8. Realiza una llamada de prueba al servicio `product`.
9. Finalmente, deten el servicio `product`: `kill $(jobs -p)`.

Ahora hemos creado, ejecutado y probado el primer microservicio único. 

**Pregunta:** Realiza los mismos procedimientos para los otros servicios de la actividad.

A partir de Spring Boot v2.5.0, se crean dos archivos jar cuando se ejecuta el comando de compilación `./gradlew`: el archivo jar ordinario, más un archivo jar simple que contiene solo los archivos de clase resultantes de compilar los archivos Java en la aplicación Spring Boot. 

Dado que no necesitamos el nuevo archivo jar simple, se ha desactivado su creación para que sea posible hacer referencia al archivo jar normal mediante un comodín al ejecutar la aplicación Spring Boot, por ejemplo:

```
java -jar microservicios/product-service/buils/libs/*.jar
```
La creación del nuevo archivo jar simple se puede deshabilitar agregando las siguientes líneas en el archivo `build.gradle` para cada microservicio:

```
jar {
  enabled = false
}
```
