## Curso de desarrollo de software

**Indicaciones**

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada HistoriasUsuarios-CriteriosAceptacion y coloca todas tus respuestas.

### Historias de usuarios y criterios de aceptación

1 . ¿Cuáles de las siguientes no son buenas historias? ¿Por qué?

- El usuario puede ejecutar el sistema en Windows XP y Linux. 
- Todos los gráficos y tablas se realizan utilizando una biblioteca de terceros. 
- El usuario puede deshacer hasta cincuenta comandos. 
- El software se lanzará el 30 de junio. 
- El software estará escrito en Java. 
- El usuario puede seleccionar su país de una lista desplegable. 
- El sistema utilizará Log4J para registrar todos los mensajes de error en un archivo. 
- Se le pedirá al usuario que guarde su trabajo si no lo ha guardado durante 15 minutos. 
- El usuario puede seleccionar la función "Exportar a XML". 
- El usuario puede exportar datos a XML


2 .  ¿Cuáles de las siguientes no son buenas historias? ¿Por qué?

- Un usuario puede dominar rápidamente el sistema. 
- Un usuario puede editar la dirección en un currículum. 
- Un usuario puede agregar, editar y eliminar múltiples currículos. 
- El sistema puede calcular aproximaciones de puntos de silla para distribuciones de formas cuadráticas en variables normales. 
- Todos los errores de tiempo de ejecución se registran de manera coherente.

3 . ¿Cuáles son cuatro buenas razones para usar historias de usuarios para expresar requisitos? 


4 . ¿Cuáles pueden ser dos inconvenientes de usar historias de usuario? 


5 . ¿Cómo debes manejar un requisito para que un sistema escale y  lo usen 1000 usuarios simultáneos? 


6 . Proporciona algunos ejemplos de sistemas que podrían beneficiarse de una consideración más directa de la interfaz de usuario de lo que normalmente se da en un proyecto ágil.


7 . Imaginemos que tu y un amigo tienen una web de Pet Store y, durante este mes, presentan un 10% de descuento en la suscripción Premium. Entonces, cada vez que un usuario de una cuenta gratuita inicia sesión, se le muestra un mensaje que dice:

```
 ¡Suscríbete a Premium ahora con un 10 % de descuento!

``` 
En este punto y técnicamente hablando, queremos crear un punto final desde donde podamos obtener un descuento de suscripción para un usuario determinado.

`Planificación`

Tú eres el que crea este punto final! Comenzamos reuniendo y creando un ticket de trabajo. Allí, queremos asegurarnos de incluir un  criterios de aceptación. Cuando termines tu trabajo, le pasarás el boleto a tu amigo. El será responsable de probarlo y de asegurarse  de que la implementación  cubra todos los casos de uso que escribimos en esos criterios de aceptación. 

Completa: 

`Descripción` 

```
Queremos crear un punto final en nuestra API que nos permita obtener el descuento disponible para un usuario determinado. 
Más tarde, la interfaz de usuario manejará el valor de descuento recibido y proporcionará el mensaje. 

```

`Criterios de aceptación` 

`Dado`

`Cuando`

`Entonces`

8 .  Planificación de Poker
 
```
Grupos de 3 estudiantes 
    Cartas  0, 1/2, 1, 2, 3, 5, 8, 13, 20, 40, 100, ∞ y ? 
 ```
Estima el esfuerzo (en horas):

```
"Como jugador, necesito un tablero vacío de 3*3  para jugar un juego de TicTacToe".

```


9 . Given-When-Then es un estilo de representación de pruebas, o como dirían sus defensores, especifica el comportamiento de un sistema utilizando [SpecificationByExample](https://en.wikipedia.org/wiki/Specification_by_example). Es un enfoque desarrollado por Daniel Terhorst-North y Chris Matts como parte del [Behavior-Driven Development (BDD)](https://en.wikipedia.org/wiki/Behavior-driven_development). Aparece como un enfoque de estructuración para muchos frameworks  de prueba como Cucumber. 

También puedes verlo como una reformulación del [Four-Phase Test](https://medium.com/test-go-where/developing-automated-tests-with-the-four-phase-test-pattern-b5fe05c24c67) .

Como estamos hablando de usar ejemplos como especificaciones, tiene sentido mostrar esto con un ejemplo: 

```
Característica: el usuario negocia acciones 
Escenario: el usuario solicita una venta antes del cierre de operaciones 
   Dado que tengo 100 acciones de MSFT 
     Y tengo 150 acciones de APPL 
      Y el tiempo es antes del cierre de operaciones.
     Cuando pido vender 20 acciones de MSFT 
      
      Entonces debería tener 80 acciones de MSFT 
       Y debería tener 150 acciones de APPL
        Y debería haberse ejecutado una orden de venta de 20 acciones de MSFT
``` 
El ejemplo anterior usa [Cucumber](https://cucumber.io/), que es una forma popular de escribir [BusinessFacingTest](https://www.martinfowler.com/bliki/BusinessFacingTest.html), pero puedes usar el estilo Given-When-Then con cualquier tipo de prueba. 

Encuentra más ejemplos usando Cucumber.

10 . ¿Prefieres escribir historias en tarjetas de notas o en un sistema de software? 



