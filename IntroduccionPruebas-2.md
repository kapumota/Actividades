## Curso de desarrollo de software

**Indicaciones**

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada Pruebas2 y coloca todas tus respuestas.

Esta actividad es individual.


## Pruebas en software - parte2


### Pruebas de caja negra y caja de cristal 

Una **prueba de caja negra** significa elegir casos de prueba solo de la especificación, no de la implementación de la función. Esto es particionamos y buscamos límites sin mirar el código real para estas funciones. 

De hecho, siguiendo el enfoque TDD todavia no hemos escrito el código para estas funciones todavía.

La **prueba de caja de cristal (blanca o estructural)**  significa elegir casos de prueba con conocimiento de cómo se implementa realmente la función.

Al realizar pruebas de caja de cristal, debes tener cuidado de que tus casos de prueba no requieran un comportamiento de implementación específico que no se requiera en la especificación. 

Lectura: [Black Box vs. White Box Testing: Understanding 3 Key Differences](https://www.spiceworks.com/tech/devops/articles/black-box-vs-white-box-testing/).

**Ejercicio** 

Considera la siguiente función:

```
/**
 * Ordena una lista de enteros en orden nodecreciente Modifica la lista tal que
 * valores.get(i) <= valores.get(i+1) para todo 0<=i<valores.size()-1
 */
public static void sort(List<Integer> valores) {
    // escoge un buen algoritmo para el tamano de la lista
    if (valores.size() < 10) {
        radixSort(valores);
    } else if (valores.size() < 1000*1000*1000) {
        quickSort(valores);
    } else {
        mergeSort(valores);
    }
}
``` 
¿Cuál de los siguientes casos de prueba es probable que sean valores límite producidos por la prueba de caja de cristal?

```
valores = [ ] (lista vacia)
valores = [1, 2, 3]
valores = [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
valores = [0, 0, 1, 0, 0, 0, 0]
```

```
# Tu respuesta
```

### Pruebas unitarias y de integración 


A diferencia de una prueba unitaria, una prueba de integración evalúa una combinación de módulos o incluso el programa completo. 

Supongamos que estás creando un motor de búsqueda de documentos. Dos de tus módulos pueden ser `load()` que carga un archivo y `extract()`  que divide 
un documento en tus palabras componentes:

```
/** 
 * @ devuelve el contenido del archivo
 */
public static String load(File file) { ... }

/** 
 * @devuelve las palabras en cadenas s, en el orden en que aparecen, 
 *  donde una palabra es una secuencia contigua de caracteres que no son 
 * espacios en blanco ni signos de puntuación
 */
public static List<String> extract(String s) { ... }
```

Estos métodos pueden ser utilizados por otro módulo `index()` para hacer el índice del motor de búsqueda:

```
/**
 * @devuelve un índice que asigna una palabra al conjunto de archivos
 * que contienen esa palabra, para todos los archivos en el conjunto de entrada
 */
public static Map<String, Set<File>> index(Set<File> files) { 
    ...
    for (File file : files) {
        String doc = load(file);
        List<String> words = extract(doc);
        ...
    }
    ...
}
```

En el conjunto de pruebas, queremos: 

- Pruebas unitarias solo para `load` que prueban en varios archivos 

- Pruebas unitarias solo para `extract` que  prueban en varias cadenas 

- Pruebas unitarias para `index` que  prueban en varios conjuntos de archivos


**Análisis**

Un error que a veces cometen los programadores es escribir casos de prueba para `extract` de tal manera que los casos de prueba dependen de `load` para ser correctos. 
Por ejemplo, un caso de prueba podría usar `load` para cargar un archivo y luego pasar su resultado como entrada para `extract`. 

Pero esto no es una prueba unitaria de `extract`. Si el caso de prueba falla, no sabemos si la falla se debe a un error en `load`  o en `extract`.
Es mejor pensar y probar `extract` de forma aislada. 

El uso de particiones de prueba que involucren contenido de un archivo  podría ser razonable,  porque así es como se usa realmente `extract` en el programa. 
No llames a `load `desde el caso de prueba, porque `load` puede tener errores. En su lugar, almacena el contenido del archivo como una cadena literal y páselo directamente para `extract`. 

De esa manera, estás escribiendo una prueba de unidad aislada y  si falla, puedes estar más seguro de que el error está en el módulo que realmente estás probando. 

Ten en cuenta que las pruebas unitarias para  `index` no se pueden aislar fácilmente de esta manera. 
Cuando un caso de prueba llama a `index`, estás probando la corrección no solo del código dentro de `index`  sino también de todos los métodos llamados por `index`. 

Si la prueba falla, el error podría estar en cualquiera de esos métodos. 

Es por eso que queremos pruebas separadas para `load` y `extract`, para aumentar la confianza en esos módulos individualmente y localizar el problema  en el código de `index` que los conecta. 

Es posible aislar un módulo de nivel superior como `index` si escribimos versiones de código auxiliar de los módulos a los que llama. 

Por ejemplo, un `stub` para `load` no accedería al sistema de archivos en absoluto, sino que devolvería el contenido del archivo `mock`  sin  importar qué `File` se le pasara. Un `stub` para una clase a menudo se denomina `mock object`. 

Los `stubs` son una técnica importante cuando se construyen sistemas grandes.

**Ejercicio**

Supongamos que está desarrollando una nueva receta de pizza. Hacer pizza involucra tres “módulos” (subrecetas) para: 

- Hacer la masa para la base

- Hacer la salsa 

- Preparar el queso y otros productos

Haces la masa para la base, la horneas sola y ves lo crocante y sabrosa que queda. Esto es `.........` 

Decides comprar una mezcla de especias prefabricada en una tienda especializada. Haces una salsa con las especias y luego la pruebas. Esto es `........` 

Pones salsa y aderezos en la base  y las horneas, para ver si la base todavía se cocina bien con las cosas húmedas encima. Esto es `..........`.

### Pruebas de regresión automatizadas

Las pruebas automatizadas significan ejecutar las pruebas y verificar los resultados automáticamente. 

Los frameworks de prueba automatizados como JUnit facilitan la ejecución de las pruebas, pero tú mismo tienes que crear buenos casos de prueba. 
La generación automática de pruebas es un problema difícil y todavía es un tema de investigación activa en informática. 

Una vez que tengas la automatización de pruebas, es muy importante volver a ejecutar tus pruebas cuando modifiques el código. 

Cada vez que encuentres y corrijas un error, toma la entrada que provocó el error y agrégala al conjunto de pruebas automatizadas como un caso de prueba. 

Este tipo de caso de prueba se llama **prueba de regresión**. Esto ayuda a llenar tu conjunto de pruebas con buenos casos de prueba. 

Guardar las pruebas de regresión también protege contra las reversiones que vuelven a introducir el error. El error puede ser un error fácil de cometer ya que sucedió una vez. 

Esta idea también conduce a la depuración de prueba primero (`test-first debugging`). Cuando surja un error, escribe inmediatamente un caso de prueba
que lo provoque y agréguelo inmediatamente a tu conjunto de pruebas. Una vez que encuentres y corrijas el error, todos tus casos de prueba pasarán, 
y habrás terminado con la depuración y tendrás una `prueba de regresión` para ese error. 

En la práctica, estas dos ideas, pruebas automatizadas y pruebas de regresión, casi siempre se usan en combinación. 

Las pruebas de **regresión** solo son prácticas si las pruebas se pueden ejecutar con frecuencia, de forma automática.  Por el contrario, si ya tienes pruebas automatizadas para tu proyecto, también podrías usarlas para evitar regresiones. 

Por lo tanto, las pruebas de regresión automatizadas son una de las mejores prácticas de la ingeniería de software moderna. 

**Ejercicio**

¿Cuál de los siguientes define mejor las pruebas de regresión? 


- Debe ejecutar un conjunto de pruebas cada vez que cambie el código. 

- Cada componente en tu código debe tener un conjunto asociado de pruebas que ejerza todos los casos extremos en tu especificación. 

- Las pruebas deben escribirse antes de escribir el código como una forma de verificar su comprensión de la especificación. 

- Cuando una nueva prueba expone un error, debe ejecutarla en todas las versiones anteriores del código hasta que encuentre la versión en la que se introdujo el error.


```
# Tu respuesta
```

**Ejercicio**

¿Cuáles de los siguientes son buenos momentos para volver a ejecutar todas tus pruebas JUnit?


- Antes de hacer git add/commit/push

- Después de reescribir una función para hacerla más rápida 

- Al usar una herramienta de cobertura de código 

- Después de pensar que solucionaste un error

```
# Tu respuesta
```


###  Test First Development

La ingeniería de software efectiva no sigue un proceso lineal. Practica el TFD, para volver y revisar tu trabajo en pasos anteriores: 

- Escribe una especificación para la función.
- Escribe pruebas para la especificación.
- Escribe una implementación. 

A medida que encuentres problemas, repite las especificaciones, las pruebas y la implementación. Cada paso ayuda a validar los pasos anteriores. 

Dado que puede ser necesario repetir los pasos anteriores, no tiene sentido dedicar una gran cantidad de tiempo a hacer que un paso sea 
perfecto antes de pasar al siguiente. 

#### Plan de iteración: 

Para una especificación grande, comienza escribiendo solo una parte de la especificación, procede a probar e implementar esa parte, luego
itera con una especificación más completa.

Para un conjunto de pruebas complejo, comienza eligiendo algunas particiones importantes y crea un pequeño conjunto de pruebas. 

Continúa con una implementación simple que pase esas pruebas y luego itera en el conjunto de pruebas con más particiones. 

Para una implementación complicada, primero escribe una implementación simple de fuerza bruta que pruebe tu especificación y valide tu conjunto de pruebas. Luego pasas a la implementación más difícil con la confianza de que tu especificación es buena y tus pruebas son correctas.

**La iteración** es una característica de todos los procesos modernos de ingeniería de software (como Agile y Scrum), con un buen respaldo empírico para su efectividad. 
La iteración requiere una mentalidad diferente a la que puedes estar acostumbrado como estudiante para resolver problemas de tareas y exámenes. 

En lugar de tratar de resolver un problema a la perfección de principio a fin, la iteración significa llegar a una solución aproximada lo antes posible y luego refinarla y mejorarla constantemente, de modo que tengas tiempo para descartar y volver a trabajar si es necesario. 

La iteración hace el mejor uso de su tiempo cuando un problema es difícil y se desconoce el espacio de solución.

**Ejercicio** 

¿Cuáles de estas técnicas son útiles para elegir casos de prueba en el TFD antes de escribir cualquier código? 

- Caja negra 
- Regresión 
- Tipificación estática  
- Particionamiento 
- Límites 
- Caja de cristal
- Cobertura

```
# Tu respuesta
```

**Ejercicio** 

Supongamos que estás escribiendo un método de búsqueda binaria y se espera no solo una implementación funcional, sino también una 
especificación clara para los clientes y un conjunto de pruebas útil. 

Aunque la búsqueda binaria es un algoritmo sencillo de entender, es notoriamente difícil de implementar correctamente, por lo que estás buscando toda la 
ayuda que puedas obtener para asegurarse de hacerlo bien. 

¿Cuál de estos pasos te ayudará a validar tu especificación antes de implementar el algoritmo de búsqueda binaria? 

- Pruebas de caja negra

- Prueba de caja de cristal

- Ejecución  JUnit 

- Escribir un algoritmo de búsqueda lineal simple 

```
# Tu respuesta
```
**Ejercicio**

¿Cuál de estos pasos ayudará a validar tu conjunto de pruebas antes de implementar el algoritmo de búsqueda binaria? 

- Escribir un algoritmo de búsqueda lineal simple 

- Ejecutar una herramienta de cobertura de código en una implementación simple

- Verificación de tipos estáticos ejecutando el compilador de Java 

```
# Tu respuesta
```

### Documenta tu estrategia de prueba 

Es una buena idea anotar la estrategia de prueba que se usó para crear un conjunto de pruebas: las particiones, los subdominios y qué subdominios se eligió para cubrir cada caso de prueba. Escribir la estrategia hace que la minuciosidad de tu conjunto de pruebas sea mucho más visible para el lector.
 
Documenta las particiones y los subdominios en un comentario en la parte superior de la clase de prueba JUnit. 

Por ejemplo, para documentar la estrategia para probar `max` escribiríamos esto en el archivo `MaxTest.java`: 

```
public class MaxTest {
  /*
   * Estrategia de prueba
   *
   * particion:
   *    a < b
   *    a > b
   *    a = b
   */

```

Cada caso de prueba debe tener un comentario arriba que diga qué subdominio(s) cubre, por ejemplo: 

```
// cubre a < b
  @Test
  public void testALessThanB() {
      assertEquals(2, Math.max(1, 2));
  }


```
La mayoría de los conjuntos de pruebas tendrán más de una partición y la mayoría de los casos de prueba cubrirán varios subdominios. 

Por ejemplo, aquí hay una estrategia para `multiply` usando siete particiones: 

```
public class Multiply {
  /*
   * Estrategia de prueba
   *
   * cubre el producto cartesiano de esas particiones:
   *   particion en a: positivo, negativo, 0
   *   particion en b: positivo, negativo, 0
   *   particion en a: 1, !=1
   *   particion en b: 1, !=1
   *   particion en a: pequeño (este es un valor large), o grande
   *   particion en b: pequeño, grande
   * 
   * cubre el subdominio de esas particiones:
   *   particion de signos de a y b:
   *      ambos positivos
   *      ambos negativos
   *      diferentes signos
   *      uno o ambos ceros
   */


```
Luego, cada caso de prueba tiene un comentario que identifica los subdominios que se eligió cubrir, por ejemplo: 

```
// cubre a positivo, b negativo, 
  //        a cabe en  large, b cabe en large,
  //        a y b tienen diferentes signos
  @Test
  public void testDifferentSigns() {
      assertEquals(BigInteger.valueOf(-146), BigInteger.valueOf(73).multiply(BigInteger.valueOf(-2)));
  }

  // cubre a = 1, b != 1, a y b tienen el mismo signo
  @Test
  public void testIdentity() {
      assertEquals(BigInteger.valueOf(33), BigInteger.valueOf(1).multiply(BigInteger.valueOf(33)));
  }

```
**Ejercicios**

Supongamos que está escribiendo un conjunto de pruebas para el método `max(int a, int b)` en `Math.java`. 
Por convención, colocas tus pruebas JUnit en otro archivo `MathTest.java`. 

¿Adónde va cada una de estas piezas de tu documentación de prueba?

```
La partición para el parámetro a:

- En un comentario al comienzo de Math.java 

- En un comentario al comienzo de MathTest.java 

- En un comentario justo antes del método max()

- En un comentario justo antes de un método de prueba JUnit 

```

```
La anotación @Test 

- Justo antes de la clase Math

- Justo antes de la clase MathTest 

- Justo antes del método max() 

- Justo antes de un método de prueba JUnit

```

```
El comentario  a < b 

- En un comentario al comienzo de Math.java

- En un comentario al comienzo de MathTest.java

- En un comentario justo antes del método max() 

- En un comentario justo antes de un método de prueba JUnit
```

```
El comentario @return el máximo de a y b

- En un comentario al comienzo de Math.java

- En un comentario al comienzo de MathTest.java

- En un comentario justo antes del método max()

- En un comentario justo antes de un método de prueba JUnit

``` 

Escribe el código completo de la prueba teniendo en cuenta las indicaciones anteriores.

```
# Tu respuesta
```
