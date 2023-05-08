## Curso de Desarrollo de Software 

El presente texto ha sido preparado de manera exclusiva para los alumnos del curso Desarrollo de Software CC3S2 basado en el libro Effective Software Testing de Muricio Aniche Manning 2022.

### La pirámide de pruebas y dónde debemos centrarnos 

Siempre que hablamos de pruebas pragmáticas, una de las primeras decisiones que debemos tomar es el nivel en el que probar el código. Por nivel de prueba, mencionamos al nivel de unidad, integración o sistema.  

#### Pruebas unitarias

En algunas situaciones, el objetivo de un evaluador es probar una sola característica del software, ignorando deliberadamente las otras unidades del sistema. 
Por supuesto, nos preocupamos por cómo este método interactuaría con el resto del sistema, y es por eso que probamos sus contratos. 
Sin embargo, no lo probamos junto con las otras piezas del sistema. 

Cuando probamos unidades de forma aislada, estamos haciendo pruebas unitarias. Este nivel de prueba ofrece las siguientes ventajas: 

- Las pruebas unitarias son rápidas. Una prueba unitaria generalmente toma solo un par de milisegundos para ejecutarse. Las pruebas rápidas nos permiten probar grandes porciones del sistema en una pequeña cantidad de tiempo. Los conjuntos de pruebas rápidos y automatizados nos brindan retroalimentación constante. Esta red de seguridad rápida nos hace sentir más cómodos y confiados al realizar cambios evolutivos en el sistema de software en el que estamos trabajando.

- Las pruebas unitarias son fáciles de controlar. Una prueba unitaria prueba el software dando ciertos parámetros a un método y luego comparando el valor de retorno de este método con el resultado esperado. Estos valores de entrada y el valor del resultado esperado son fáciles de adaptar o modificar en la prueba. 
- Las pruebas unitarias son fáciles de escribir. No requieren una configuración complicada ni trabajo adicional. Una sola unidad también suele ser cohesiva y pequeña, lo que facilita el trabajo del evaluador. Las pruebas se vuelven mucho más complicadas cuando tenemos bases de datos, interfaces y servicios web todos juntos. 

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

Los sistemas de software comúnmente se basan en sistemas de bases de datos. Para comunicarse con la base de datos, los desarrolladores a menudo crean una clase cuya única responsabilidad es interactuar con este componente externo (piensa en las clases de objetos de acceso a datos [DAO]). Estos DAO pueden contener código SQL complicado. 

Por lo tanto  un evaluador siente la necesidad de probar las consultas SQL. El evaluador no quiere probar todo el sistema, solo la integración entre la clase DAO y la base de datos. 
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
- Las pruebas del sistema son más propensas a ser inestables. Una prueba inestable presenta un comportamiento errático: si la ejecutas, puede pasar o fallar para la misma configuración. Las pruebas inestables son un problema importante para los equipos de desarrollo de software.

#### Cuándo usar cada nivel de prueba 

Con una comprensión clara de los diferentes niveles de prueba y sus beneficios, tenemos que decidir si invertir más en pruebas unitarias o pruebas del sistema y determinar qué componentes deben probarse mediante pruebas unitarias y qué componentes deben probarse mediante pruebas del sistema. Una decisión equivocada puede tener un impacto considerable en la calidad del sistema: un nivel incorrecto puede costar demasiados recursos y no encontrar suficientes errores. Como habrás adivinado, la mejor respuesta aquí es: "Depende". 

A menudo se usa una pirámide para ilustrar esta idea, como se muestra en la figura:


<img src="https://github.com/kapumota/Actividades/blob/main/PiramidePruebas/Imagenes/TrianguloPruebas.png" alt="drawing" width="500"/>

El tamaño de una parte en la pirámide representa el número relativo de pruebas a realizar en cada nivel de prueba. 

Las pruebas unitarias están en la base de la pirámide y tienen el área más grande. Esto significa que los desarrolladores que siguen este esquema favorecen las pruebas unitarias (es decir, escriben más pruebas unitarias). 

Subiendo en el diagrama, el siguiente nivel es la prueba de integración. El área es más pequeña, lo que indica que en la práctica, estos desarrolladores escriben menos pruebas de integración que pruebas unitarias. Dado el esfuerzo adicional que requieren las pruebas de integración, los desarrolladores escriben pruebas solo para las integraciones que necesitan.

El diagrama muestra que estos desarrolladores favorecen menos las pruebas del sistema que las pruebas de integración y tienen aún menos pruebas manuales. 

#### ¿Por qué se prefieren las pruebas unitarias? 

Las pruebas unitarias son fáciles de escribir, son rápidas, puedes escribirlas entrelazadas con el código de producción, etc. También las pruebas unitarias encajan muy bien con la forma en que trabajan los desarrolladores de software. Cuando los desarrolladores implementan una nueva característica, escriben unidades separadas que eventualmente trabajarán juntas para ofrecer una mayor funcionalidad. 

Al desarrollar cada unidad, es fácil asegurarse de que funcione como se espera. 
Probar unidades pequeñas de manera rigurosa y efectiva es mucho más fácil que probar una funcionalidad más grande. 

Debido a que debes ser consciente de las desventajas de las pruebas unitarias, piensa detenidamente en cómo las otras unidades del sistema utilizarán la unidad en desarrollo. Hacer cumplir contratos claros y probarlos sistemáticamente te da más certeza de que las cosas funcionarán cuando se armen. 

Finalmente, dada la intensidad con la que pruebas tu código usando pruebas unitarias (simples y baratas), puedes usar pruebas de integración y de sistema para las partes que realmente importan. No tienes que volver a probar todas las funcionalidades de nuevo en estos niveles. 

Usa la integración o las pruebas del sistema para probar partes específicas del código que crees que puedan causar problemas durante la integración. 


#### ¿Qué pruebas en los diferentes niveles? 

Usa las pruebas unitarias para unidades relacionadas con un algoritmo o una sola pieza de lógica comercial del sistema de software. La mayoría de los sistemas empresariales se utilizan para transformar datos. Dicha lógica comercial a menudo se expresa mediante el uso de clases de entidad (por ejemplo, una clase de Factura y una clase de Pedido) para intercambiar mensajes. 

La lógica empresarial a menudo no depende de servicios externos, por lo que puede probarse fácilmente y controlarse por completo a través de pruebas unitarias. 
Las pruebas unitarias nos brindan un control total sobre los datos de entrada, así como una observabilidad total en términos de afirmar que el comportamiento es el esperado. 

La forma en que diseñas tus clases tiene un impacto significativo en lo fácil que es escribir pruebas unitarias para tu código. 

Usa pruebas de integración cada vez que el componente bajo prueba interactúa con un componente externo (como una base de datos o un servicio web). 

Una DAO, cuya única responsabilidad es comunicarse con una base de datos, se prueba mejor en el nivel de integración: aquí debes asegurarte de que la comunicación con la base de datos funcione, la consulta SQL devuelve lo que desea y las transacciones se confirman en la base de datos. 

Nuevamente, ten en cuenta que las pruebas de integración son más costosas y más difíciles de configurar que las pruebas unitarias, y debes usarlas porque son la única forma de probar una parte particular del sistema.

Como ya sabemos, las pruebas del sistema son muy costosas (son difíciles de escribir y lentas de ejecutar) y, por lo tanto, se encuentran en la cima de la pirámide. 

Es imposible volver a probar todo el sistema a nivel del sistema. Por lo tanto, tienes que priorizar qué probar a este nivel, y realizar un análisis de riesgo simple para decidir. ¿Cuáles son las partes críticas del sistema de software bajo prueba? En otras palabras, ¿qué partes del sistema se verían significativamente afectadas por un error? Estas son las áreas donde realizo algunas pruebas del sistema. 

Recuerda la **paradoja de los pesticidas**: una sola técnica generalmente no es suficiente para identificar todos los errores. 

La figura anterior también incluye pruebas manuales. Se ha dicho que todas las pruebas deben automatizarse, pero hay algo de valor en las pruebas manuales cuando estas pruebas se centran en la exploración y la validación. 

Como desarrollador, es bueno usar y explorar el sistema de software que estás creando de vez en cuando, tanto de forma real como a través de un script de prueba. Abre el navegador o la aplicación y juega con ellos; puedes obtener una mejor perspectiva de qué más probar. 

**Ejercicios**

Responde las siguientes preguntas:

1. ¿Qué nivel de prueba suele realizar el personal de administración de un sistema?

2. Considera este requisito: "Una tienda web ejecuta un trabajo por lotes, una vez al día, para entregar todos los pedidos que se han pagado. También establece la fecha de entrega según si el pedido es de un cliente internacional. Los pedidos se recuperan de una base de datos externa. Los pedidos que se han pagado se envían a un servicio web externo”. Como evaluador, debes decidir qué nivel de prueba (unidad, integración o sistema) aplicar.  ¿Qué tipo de prueba aplicarias a este caso?

3. Inspectora Motita acaba de comenzar una consultoría para una empresa que desarrolla una aplicación móvil para ayudar a las personas a mantenerse al día con sus ejercicios diarios. Los miembros del equipo de desarrollo son fanáticos de las pruebas de software automatizadas y más específicamente, de las pruebas unitarias. Tienen una alta cobertura de código de prueba de unidad  pero los usuarios aún informan una cantidad significativa de errores. Motita, que está bien versada en pruebas de software, explica un principio de prueba al equipo. ¿De cuál principio habló?.

4. Monky  un tester de software junior, acaba de unirse a una empresa de pago en línea muy grande en Escocia. Como primera tarea, Monky analiza los informes de errores de los últimos dos años. Él observa que más del 50% de los errores ocurren en el módulo de pagos internacionales. El le promete a su gerente que diseñará casos de prueba que cubran completamente el módulo de pagos internacionales y así encontrar todos los errores. ¿Pueden las pruebas exhaustivas pueden explicar por qué esto no es posible?

5. ¿Cuál es la razón principal por la que el número de pruebas recomendadas del sistema en la pirámide de pruebas es menor que el número de pruebas unitarias?

6. Una universidad (X) ha creado un software interno para gestionar la nómina de los empleados. La aplicación utiliza tecnologías web  de Java y almacena datos en una base de datos de Postgres. La aplicación recupera, modifica e inserta con frecuencia grandes cantidades de datos. Toda esta comunicación se realiza mediante clases Java que envían consultas SQL (complejas) a la base de datos. Como evaluadores sabemos que un error puede estar en cualquier lugar, incluso en las consultas SQL. También sabemos que hay muchas formas de ejercitar nuestro sistema. ¿Cuál  es una buena opción para detectar errores en consultas SQL?

7. Chalito, un evaluador de software con mucha experiencia, visita FCX!, una red social enfocada en emparejar personas según los cursos que llevan. Los usuarios no informan errores a menudo, ya que  los desarrolladores cuentan con sólidas prácticas de prueba. Sin embargo, los usuarios dicen que el software no cumple lo que promete. ¿Qué principio de prueba se aplica aquí?

8. Kapumota cree firmemente en las pruebas unitarias. De hecho, este es el único tipo de prueba que realiza para cualquier proyecto del que forma parte. ¿Qué principio de prueba no ayudará a convencer a Kapumota de que debe alejarse de su enfoque de "pruebas unitarias únicas"?

9. TDD se ha convertido en una práctica popular entre los desarrolladores. Según ellos, el TDD tiene varios beneficios. Indica algunos ejemplos no se considera un beneficio el TDD.

10. Indica algunas recomendaciones a seguir para mantener una aplicación web comprobable.


### Comentarios

Mucha gente no está de acuerdo con la idea de una pirámide de pruebas y si deberíamos favorecer las pruebas unitarias. Estos desarrolladores abogan por un nivel inferior más delgado con pruebas unitarias, una porción intermedia más grande con pruebas de integración y una parte superior más delgada con pruebas de sistema. 

Claramente, estos desarrolladores ven el mayor valor en escribir pruebas de integración. 

En muchos sistemas de software, la mayor parte de la complejidad está en la integración de componentes. Por ejemplo en una arquitectura de microservicios altamente distribuida el desarrollador puede sentirse más cómodo si las pruebas automatizadas realizan llamadas reales a otros microservicios en lugar de confiar en stubs o mocks que los simulan. ¿Por qué escribir pruebas unitarias para algo que tiene que probar de todos modos a través de pruebas de integración?.

Otro caso común a favor de las pruebas de integración en lugar de las pruebas unitarias involucra los sistemas de información centrados en bases de datos: es decir, sistemas donde la responsabilidad principal es almacenar, recuperar y mostrar información. En tales sistemas, la complejidad se basa en garantizar que el flujo de información viaje con éxito a través de la interfaz de usuario a la base de datos y viceversa. 

Tales aplicaciones a menudo no están compuestas por algoritmos complejos o reglas comerciales. En ese caso, las pruebas de integración para garantizar que las consultas SQL (que a menudo son complejas) funcionen como se espera y las pruebas del sistema para garantizar que la aplicación en general se comporte como se espera pueden ser el camino a seguir.

Preferir un enfoque sobre otro es en gran medida una cuestión de gusto personal, experiencia y contexto. Debes realizar el tipo de prueba que crees que beneficiará a tu software. Todos los enfoques tienen pros y contras, y tendrás que encontrar el que funcione mejor para ti y tu equipo de desarrollo. 


En [Software Engineering at Google](https://abseil.io/resources/swe-book)  (Winters, Manshreck y Wright, 2020), los autores mencionan que Google suele optar por pruebas unitarias, ya que tienden a ser más económicas y se ejecutan con mayor rapidez. También se realizan pruebas de integración y del sistema, pero en menor medida. Según los autores, alrededor del 80% de sus pruebas son pruebas unitarias. 

Google también tiene una definición interesante de tamaños de prueba, que los ingenieros consideran al diseñar casos de prueba. 

Una **prueba pequeña** es una prueba que se puede ejecutar en un solo proceso. Tales pruebas no tienen acceso a las principales fuentes de lentitud o determinismo de prueba. En otras palabras, son rápidas y no inestables. 

Una **prueba mediana** puede abarcar varios procesos, usar subprocesos y realizar llamadas externas (como llamadas de red) al localhost. Las pruebas medianas tienden a ser más lentas e inestables que las pequeñas. 

Finalmente, las **pruebas grandes** eliminan la restricción localhost y, por lo tanto, pueden requerir y realizar llamadas a varias máquinas. Google reserva pruebas grandes para pruebas completas de extremo a extremo. 

La idea de clasificar las pruebas no en términos de sus límites (unidad, integración, sistema) sino en términos de qué tan rápido se ejecutan también es popular entre muchos desarrolladores. Una vez más, lo que importa es que, para cada parte del sistema, tu objetivo sea maximizar la eficacia de la prueba. 

La idea es que tu prueba sea lo más económica posible de escribir, lo más rápida posible de ejecutar y que te brinde la mayor cantidad de comentarios posible sobre la calidad del sistema. 


