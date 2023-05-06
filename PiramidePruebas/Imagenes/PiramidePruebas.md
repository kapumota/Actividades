## Curso de Desarrollo de Software 

El presente texto ha sido preparado de manera exclusiva para los alumnos del curso Desarrollo de Software CC3S2 basado en el libro Effective Software Testing de Muricio Aniche. 

### La pirámide de pruebas y dónde debemos centrarnos 

Siempre que hablamos de pruebas pragmáticas, una de las primeras decisiones que debemos tomar es el nivel en el que probar el código. Por nivel de prueba, mencionamos al nivel de unidad, integración o sistema.  

#### Pruebas unitarias

En algunas situaciones, el objetivo de un evaluador es probar una sola característica del software, ignorando deliberadamente las otras unidades del sistema. 
Por supuesto, nos preocupamos por cómo este método interactuaría con el resto del sistema, y es por eso que probamos sus contratos. 
Sin embargo, no lo probamos junto con las otras piezas del sistema. 

Cuando probamos unidades de forma aislada, estamos haciendo pruebas unitarias. Este nivel de prueba ofrece las siguientes ventajas: 

- Las pruebas unitarias son rápidas. Una prueba unitaria generalmente toma solo un par de milisegundos para ejecutarse. Las pruebas rápidas nos permiten probar grandes porciones del sistema en una pequeña cantidad de tiempo. Los conjuntos de pruebas rápidos y automatizados nos brindan retroalimentación constante. Esta red de seguridad rápida nos hace sentir más cómodos y confiados al realizar cambios evolutivos en el sistema de software en el que estamos trabajando.

- Las pruebas unitarias son fáciles de controlar. Una prueba unitaria prueba el software dando ciertos parámetros a un método y luego comparando el valor de retorno de este método con el resultado esperado. Estos valores de entrada y el valor del resultado esperado son fáciles de adaptar o modificar en la prueba. Una vez más, mira el ejemplo de identifiqueExtremes() y lo fácil que fue proporcionar diferentes entradas y afirmar su salida.  

- Las pruebas unitarias son fáciles de escribir. No requieren una configuración complicada ni trabajo adicional. Una sola unidad también suele ser cohesiva y pequeña, lo que facilita el trabajo del tester. Las pruebas se vuelven mucho más complicadas cuando tenemos bases de datos, interfaces y servicios web todos juntos. 

En cuanto a las desventajas, se deben considerar las siguientes:  

- Las pruebas unitarias carecen de realidad. Un sistema de software rara vez se compone de una sola clase. El gran número de clases en un sistema y su interacción puede causar que el sistema se comporte de manera diferente en su aplicación real que en las pruebas unitarias. Por lo tanto, las pruebas unitarias no representan a la perfección la ejecución real de un sistema de software. 

- Algunos tipos de errores no se detectan. Algunos tipos de errores no se pueden detectar en el nivel de prueba unitaria; solo ocurren en la integración de los diferentes componentes (que no se ejercen en una prueba unitaria pura). Piensa en una aplicación web que tiene una interfaz de usuario compleja: es posible que hayas probado el backend y el frontend a fondo, pero es posible que un error solo se revele cuando se juntan el backend y el frontend. O imagina un código de subprocesos múltiples: todo puede funcionar a nivel de unidad, pero pueden aparecer errores una vez que los subprocesos se ejecutan juntos. 

Curiosamente, uno de los desafíos más difíciles en las pruebas unitarias es definir qué constituye una unidad. Una unidad puede ser un método o varias clases. 

Aquí hay una definición de prueba unitaria dada por Roy Osherove (2009): “Una prueba unitaria es una pieza de código automatizada que invoca una unidad de trabajo en el sistema. Y una unidad de trabajo puede abarcar un solo método, una clase completa o varias clases que trabajan juntas para lograr un solo propósito lógico que se puede verificar”. 

Para muchos desarrolladores una  prueba unitaria significa probar un (pequeño) conjunto de clases que no dependen de sistemas externos (como bases de datos o servicios web) o cualquier otra cosa que no se controle por completo. Cuando pruebas un conjunto de clases juntas, el número de clases tiende a ser pequeño. Esto se debe principalmente a que probar muchas clases juntas puede ser demasiado difícil, no porque no sea una prueba unitaria. 

Pero, ¿qué pasa si una clase que quiero probar depende de otra clase que se comunica, por ejemplo, con una base de datos (figura )? 


![](https://github.com/kapumota/Actividades/blob/main/PiramidePruebas/Imagenes/Prueba.png).


Cuando realizamos pruebas unitarias de clase A, el enfoque está en probar A ¡lo más aislado posible del resto!. Si A depende de otras clases, tenemos que decidir si simularlas o hacer que nuestra prueba unitaria sea un poco más grande.

Aquí es donde las pruebas unitarias se vuelven más complicadas. 

Aquí hay una respuesta corta: si quieres probar una clase, y esta clase depende de otra clase que depende de una base de datos, simula la clase de la base de datos. 
En otras palabras, crea un **stub**  que actúe como la clase original pero que sea mucho más simple y fácil de usar durante las pruebas.

#### Pruebas de integración 

Las pruebas unitarias se centran en las partes más pequeñas del sistema. Sin embargo, probar los componentes de forma aislada a veces no es suficiente. 
Esto es especialmente cierto cuando el código bajo prueba va más allá de los límites del sistema y utiliza otros componentes (a menudo externos). 

La prueba de integración es el nivel de prueba que usamos para probar la integración entre el código y partes externas. 

**Ejemplo** 

Los sistemas de software comúnmente se basan en sistemas de bases de datos. Para comunicarse con la base de datos, los desarrolladores a menudo crean una clase cuya única responsabilidad es interactuar con este componente externo (piensa en las clases de objetos de acceso a datos [DAO]). 

Estos DAO pueden contener código SQL complicado. 
Por lo tanto  un evalaudor siente la necesidad de probar las consultas SQL. El evaluador no quiere probar todo el sistema, solo la integración entre la clase DAO y la base de datos. 
El evaluador tampoco quiere probar la clase DAO en completo aislamiento. Después de todo, la mejor manera de saber si una consulta SQL funciona es enviarla a la base de datos y ver qué devuelve la base de datos. Este es un ejemplo de una prueba de integración. 

Las pruebas de integración tienen como objetivo probar múltiples componentes de un sistema juntos, centrándose en las interacciones entre ellos en lugar de probar el sistema como un todo. ¿Se están comunicando correctamente? ¿Qué sucede si el componente A envía un mensaje X al componente B? ¿Siguen presentando un comportamiento correcto?

Las pruebas de integración se centran en dos partes: el componente a analizar y el componente externo. Escribir una prueba de este tipo es menos complicado que escribir una prueba que atraviese todo el sistema e incluya componentes que no nos interesan. 

En comparación con las pruebas unitarias, las pruebas de integración son más difíciles de escribir. 

#### Pruebas del sistema 

Para obtener una vista más realista del software y, por lo tanto, realizar pruebas más realistas, debemos ejecutar todo el sistema de software con todas sus bases de datos, aplicaciones de interfaz y otros componentes.
Cuando probamos el sistema en su totalidad, en lugar de probar pequeñas partes del sistema de forma aislada, estamos haciendo pruebas del sistema . 
No nos importa cómo funciona el sistema desde adentro, no nos importa si fue desarrollado en Java o Ruby, o si utiliza una base de datos relacional. 

Solo nos importa que, dada la entrada X, el sistema proporcione la salida Y. 

Al probar el sistema, deseas ejercitar todo el sistema en conjunto, incluidas todas sus clases, dependencias, bases de datos, servicios web y cualquier otro componente que pueda tener.

La ventaja obvia de las pruebas del sistema es cuán realistas son las pruebas. Nuestros clientes finales no ejecutarán el método identifyExtremes() de forma aislada. Más bien, visitarán una página web, enviarán un formulario y verán los resultados. Las pruebas del sistema ejercitan el sistema de esa manera precisa. Cuanto más realistas sean las pruebas (es decir, cuando las pruebas realicen acciones similares a las del usuario final), más confianza podremos tener sobre todo el sistema. 

Sin embargo, las pruebas del sistema tienen sus desventajas: 

- Las pruebas del sistema suelen ser lentas en comparación con las pruebas unitarias. 
- Las pruebas del sistema también son más difíciles de escribir. 
- Las pruebas del sistema son más propensas a ser inestables. Una prueba inestable presenta un comportamiento errático: si la ejecutas, puede pasar o fallar para la misma configuración. Las pruebas inestables son un problema importante para los equipos de desarrollo de software
