## Curso de desarrollo de software

En esta actividad, construiremos el primer par de microservicios. 
Aprenderemos a crear microservicios cooperativos con funcionalidad minimalista. Luego agregaremos más y más funciones a estos microservicios. 

Al final de esta actividad, tendremos una API RESTful expuesta por un microservicio compuesto. 

El microservicio compuesto llamará a otros tres microservicios utilizando sus API RESTful para crear una respuesta agregada. 

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta completa con código en una carpeta `Microservicios` y coloca todas tus respuestas.

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

1 Primero, creamos el archivo `settings.gradle` que describe qué proyectos debe construir Gradle: 

  ```
  include ':microservicios:product-service' 
  include ':microservicios:review-service' 
  include ':microservicios:recommendation-service' 
  include ':microservicios:product-composite-service' 
  ``` 
2 A continuación, copiamos los archivos ejecutables de Gradle que se generaron a partir de uno de los proyectos para que podamos reutilizarlos para las compilaciones de varios proyectos: 

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

28 actionable tasks: 28 executed 

Con los proyectos de esqueleto para los microservicios creados con Spring Initializr y creados con éxito con Gradle, estamos listos para agregar algo de código a los microservicios en la siguiente sección. 

Desde una perspectiva de DevOps, es posible que no se prefiera una configuración de varios proyectos. En cambio, para permitir que cada microservicio tenga su propio ciclo de compilación y lanzamiento, probablemente sería preferible configurar un pipeline de compilación separada para cada proyecto de microservicio. Sin embargo, para los fines de este curso, utilizaremos la configuración de varios proyectos para que sea más fácil construir e implementar todo el entorno del sistema con un solo comando. 
