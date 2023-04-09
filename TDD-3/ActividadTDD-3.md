## Curso de desarrollo de software

###  Actividad grupal

Descarga la actividad incompleta desde aquí:https://github.com/kapumota/Actividades/tree/main/TDD-3/TDD-3 

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada TDD-3 y coloca todas tus respuestas e implementaciones.

## El ciclo RGR

Vimos en la actividad anterior cómo una prueba de una sola unidad se divide en tres partes, conocidas  `Arrange`, `Act` y `Assert`. Esto forma de trabajo simple que nos guía a través de la escritura de cada prueba. Nos fuerza a diseñar cómo se utilizará el código: el exterior del código. Si pensamos en un objeto como un límite de encapsulación, tiene sentido hablar de lo que está dentro y fuera de ese límite. **Los métodos públicos forman el exterior del objeto**.


Una vez que hemos escrito la prueba, pasamos a crear el código que está dentro del objeto: los campos y métodos privados. Para ello, hacemos uso de otra técnica llamada `RGR`. Este es un proceso de tres pasos que nos ayuda a generar confianza en la prueba, crear una implementación básica del código y luego refinarlo de manera segura. 

### Comenzando en rojo

![](https://github.com/kapumota/Actividades/blob/main/TDD-3/Imagenes/RGB1.png)

El objetivo de esta fase es utilizar la plantilla `Arrange`, `Act` y `Assert` para poner en marcha la prueba y prepararla para probar el código que escribiremos a continuación. La parte más importante de esta fase es asegurarse de que la prueba no pase. A esto llamamos prueba fallida, o prueba roja, debido al color que la mayoría de las herramientas gráficas de prueba usan para indicar una prueba fallida. 

Queremos que la prueba falle en esta etapa para tener la confianza de que funciona correctamente. Si la prueba pasa en este punto, es una preocupación. ¿Por qué pasa? Sabemos que aún no hemos escrito nada del código que estamos probando. Si la prueba pasa ahora, eso significa que no necesitamos escribir ningún código nuevo o que cometimos un error en la prueba. 

Aquí se tiene un enlace https://medium.com/pragmatic-programmers/3-5-getting-green-on-red-d189240b1c87  con ocho razones por las que una prueba podría no funcionar correctamente. 

### Pásate al verde 

![](https://github.com/kapumota/Actividades/blob/main/TDD-3/Imagenes/RGB2.png)

Una vez que tenemos una prueba fallida, somos libres de escribir el código que la hará pasar. A esto llamamos el **código de producción**, el código que formará parte del sistema de producción. 

Tratamos el código de producción como un componente de caja negra. El componente tiene un interior y un exterior. El interior es donde escribimos el código de producción. Es donde ocultamos los datos y los algoritmos de la implementación. Podemos hacer esto utilizando cualquier enfoque que elijamos: orientado a objetos, funcional, declarativo o procedimental, etc. 

El exterior es la interfaz de programación de aplicaciones (API). Esta es la parte que usamos para conectarnos a el componente y usarlo para crear piezas de software más grandes. 

Si elegimos un enfoque orientado a objetos, esta API estará compuesta por métodos públicos en un objeto. 

Con TDD, la primera pieza a la que nos conectamos es la prueba, y eso nos da una respuesta rápida sobre qué tan fácil es usar la conexión. 

El siguiente diagrama muestra las diferentes piezas: el interior, el exterior, el código de prueba y otros usuarios del componente: 


![](https://github.com/kapumota/Actividades/blob/main/TDD-3/Componentes.png)


## Refactorización para limpiar el código

![](https://github.com/kapumota/Actividades/blob/main/TDD-3/RGB3.png)

Esta es la fase en la que entramos en modo de ingeniería de software. 

Tenemos un código simple y funcional con una prueba que pasa. 

Ahora es el momento de refinar eso en un código limpio, es decir, un código que será fácil de leer más adelante. 

Algunos ejemplos de técnicas de refactorización que podemos usar durante esta fase incluyen los siguientes: 

- Extracción de un método para eliminar el código duplicado 
- Cambiar el nombre de un método para expresar lo que hace mejor 
- Cambiar el nombre de una variable para expresar mejor lo que contiene 
- Dividir un método largo en varios más pequeños 
- Extraer una clase más pequeña
- Combinar una larga lista de parámetros en su propia clase 

Todas estas técnicas tienen un objetivo: hacer que el código sea más fácil de entender. Esto hará que sea más fácil de mantener. 

Al final de esta fase, tendremos una prueba unitaria que cubrirá un fragmento de código de producción que hemos diseñado para que sea fácil trabajar con él en el futuro. 
