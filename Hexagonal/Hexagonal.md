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

<img src="Imagenes/Ejemplo1.png" width="500px" height="180px">

#### Los problemas de entorno traen problemas  

El entorno en el que se ejecuta el software a menudo genera desafíos. 


**Problema:** Supongamos que el código lee datos de una base de datos. Incluso si el código es correcto, es posible que no pueda leer esos datos debido a problemas en el entorno que escapan a el control.  Indica los problemas que puedan ocurrir debido al entorno.  


Una base de datos almacena datos, causando problemas para las pruebas. Supongamos que escribimos una prueba contra una base de datos de prueba, que comienza escribiendo un nombre de usuario de prueba. Si hemos ejecutado esta prueba antes, el nombre de usuario de la prueba ya estará almacenado en la base de datos. Por lo general, la base de datos informará un error de elemento duplicado y la prueba fallará.  

Las pruebas contra bases de datos necesitan limpieza. Todos los datos de prueba almacenados deben eliminarse después de que se hayan completado las pruebas. Si intentamos eliminar datos después de que la prueba haya tenido éxito, es posible que el código de eliminación nunca se ejecute si la prueba falla. Podríamos evitar esto eliminando siempre los datos antes de que se ejecute la prueba. Dichas pruebas serán lentas de ejecutar.  

Una tarea común en el software comercial es aceptar el pago de un cliente. Para eso, inevitablemente usamos un procesador de pago de terceros como PayPal o Stripe, como dos ejemplos. Además de los desafíos de la conectividad de la red, las API de terceros nos presentan desafíos adicionales:  

**Problema:** Enuncia algunos desafios que se presentan en este caso.

Existen muchos desafíos cuando mezclamos servicios externos y una sola pieza de código monolítica, lo que complica tanto el mantenimiento como las pruebas. 

**Pregunta:** ¿Qué podemos hacer al respecto para resolver o hacer que los sistemas externos sean más fáciles de manejar?.


#### Inversión de dependencia al rescate  

Aprendimos sobre el principio de inversión de dependencia anteriormente. Vimos que nos ayuda a aislar algún código que queríamos probar de los detalles de sus colaboradores. Notamos que era útil para probar cosas que se conectaban a sistemas externos que estaban fuera de el control. 

Vimos cómo el principio de responsabilidad única nos guió a dividir el software en tareas más pequeñas y más enfocadas.  

Aplicando estas ideas a el ejemplo anterior de informe de ventas, llegaríamos a un diseño mejorado, como se muestra en el siguiente diagrama:  

<img src="Imagenes/Ejemplo2.png" width="520px" height="180px">

El diagrama anterior muestra cómo hemos aplicado los principios SOLID para dividir el código de informe de ventas. Hemos utilizado el principio de responsabilidad única para dividir la tarea general en tres tareas separadas:  

- Dar formato al informe
- Cálculo del total de ventas
- Lectura de los datos de ventas de la base de datos  

Esto ya hace que sea un poco más fácil trabajar con la aplicación.

Aquí también podemos aplicar el principio de inversión de dependencia. ¿Cómo?.

El mayor beneficio es que podemos intercambiar cualquier pieza de código que pueda acceder a cualquier base de datos, sin cambiar el código de cálculo. Por ejemplo, podríamos cambiar de una base de datos Postgres SQL a una base de datos Mongo NoSQL sin cambiar el código de cálculo. Podemos usar un doble de prueba para la base de datos para que podamos probar el código de cálculo como una prueba unitaria FIRST. Estas son ventajas muy significativas, no solo en términos de TDD y pruebas, sino también en términos de cómo se organiza el código. 

