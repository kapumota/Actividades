## Proyecto Tictactoe

###  Actividad Individual

Descarga la actividad incompleta desde aquí:https://github.com/kapumota/Actividades/tree/main/Total/TictocToe 

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada TicTacToe y coloca todas tus respuestas.


## El ejemplo con el juego Tic-Tac-Toe

A continuación,  usamos el juego TicTacToe para ilustrar el proceso Scrum para el desarrollo de software. Nos enfocamos en la integración de prácticas de ingeniería de software en el proceso más que en la gestión del trabajo en equipo. 


### Implementación e Ingeniería de Software 

Antes de profundizar en los sprints individuales, proporcionamos una descripción general del código fuente completo de TicTacToe. 

![](https://github.com/kapumota/Actividades/blob/main/Total/Imagenes/Sprints.png)


Supongamos que la planificación inicial del proyecto TicTacToe ha determinado las siguientes historias de usuarios en el backlog products: 

**Historia de usuario 1:** Como jugador, necesito un tablero vacío de 3 x 3 para comenzar un juego tic-tac-toe. 


**Historia de usuario 2:**  Como jugador X, necesito colocar una ficha en un tablero tic-tac-toe para poder hacer un movimiento. 

**Historia de usuario 3:**  Como jugador, necesito saber si el juego termina después de cada movimiento. 

El sprint 1 tiene dos paquetes que muestran el incremento de producción y código de prueba desde la implementación de `AC1.1` a `AC 1.2-AC 1.3`. 

Los dos paquetes del `sprint 2` presentan el código antes y después de la refactorización cuando se implementa inicialmente la funcionalidad de la segunda y tercera historia. 

El primer paquete de `sprint 3` (`sprint 3_0`) es la implementación inicial de todas las historias. El segundo paquete, `sprint 3_1`, resulta de la refactorización del sprint `3_0`. Los cambios en el tercer paquete, `sprint3_2`, se ocupan de los informes de análisis de código estático. 

El paquete `sprintX_autoplay` contiene el código fuente que permite a un jugador jugar contra la computadora. Se utiliza para discutir la evolución del software. 

Ten en cuenta que el código está organizado de forma ilustrativa. Su estructura es ciertamente diferente a la de un proyecto de campo real, que generalmente utiliza una herramienta de control de versiones como git.


## Sprint 1

Considera la primera historia: como jugador, necesito un tablero vacío de 3 x 3 para comenzar un juego de TicTacToe. Escribimos el primer criterio de aceptación de la siguiente manera : 

```
AC 1.1 Tablero vacío 
Cuando 
Entonces
 
Y
 ```

Sin embargo aquí  no se especifica completamente el requisito. ¿Se puede jugar el juego en un tablero más grande (por ejemplo, 5 x 5) usando una cuadrícula de 3 x 3? Agregamos dos criterios de aceptación sobre los límites del tablero.

```
 AC 1.2 Referencia de fila no válida
 Dado 
 Cuando
 Entonces 
```
```
AC `1.3 Referencia de columna no válida
Dado 
Cuando 
Entonces
```

### Características sprint 1

Código de producción: separación de la lógica empresarial y la interfaz de usuario 

- Lógica empresarial: crear una clase board
- Interfaz de usuario: crear una clase del GUI del tablero (o Console) 

Separación de la lógica empresarial y la interfaz de usuario

![](https://github.com/kapumota/Actividades/blob/main/Total/Imagenes/Design.png)

Escribir código de prueba 

 - Crear pruebas a partir de los criterios de aceptación. 

Para escribir una prueba, necesitamos tomar las siguientes decisiones: 

- ¿Cuál es el tipo de datos de las celdas del tablero? 
 
- ¿Qué valor representa "vacío"? 

-  ¿Cómo obtener una celda de tablero dada?

. ¿Cuál es el tipo de datos de  ' turn de X'? 

- ¿Cómo obtener el estado de `turn`? 


Así, tenemos la siguiente prueba:

```
public class TestEmptyBoard {
    private Board board = new Board ( );
    // criterio de aceptación 1.1
   @ Test
    public void  testNewBoard() {
         for (int row =0; row < 3; row ++) {
            for (int column = 0; column < 3; column ++){
	assertEquals(“ “, board.getCell(row, column), 0);
	}
         }
         assertEquals(“ “, board.getTurn( ), ‘X’)
      }
}

``` 
El código de prueba anterior tiene varios errores de sintaxis porque el código de producción, es decir, la clase Board con los métodos getCell y getTurn, aún no está disponible. Entonces, escribimos el siguiente código de producción para que la prueba pase. 

```
public class Board {
     private int [ ][ ] grid;
     private char turn = ‘X’;
     public Board( ){
             grid = new int [3 ][ 3];
     } 

public int getCell(int row, int column) {
      return grid [row][column]; 
    }
  public char getTurn(){
     return turn:
  }
}
``` 
#### Programación en pares

El trabajo anterior podría realizarse a través de la programación en pares o con el estilo conductor-navegador: el driver escribe el código en el teclado, mientras que el navegador piensa estratégicamente si el código debe ir (arquitectura, problemas, mejoras). 

Para completar la clase de GUI, primero escribimos una prueba de GUI. El objetivo es simplemente mostrar el tablero vacío visualizado, cuando la clase GUI está disponible. No se necesita una aserción. La prueba hará que la visualización se quede dos segundos para que podamos ver el efecto. 


![](https://github.com/kapumota/Actividades/blob/main/Total/Imagenes/Tablero1.png)

```
public class TestBoardGUI {
    private Board board;
   								
    @ Test
    public void testEmptyBoard ( ){
          new GUI (board);
           try {
               Thread. sleep(2000);
          } catch (InterruptedException e) {
                e.printStackTrace( ){
           }
    }
}
``` 

Procedemos a escribir la clase GUI para que pase la prueba. Actualmente, no hay necesidad de admitir ninguna acción de GUI. La interfaz pública se muestra a continuación, donde la GUI se compone de un objeto `Board`. 

```
public class GUI extends JFrame {
    public GUI(Board board);
    public Board getBoard(){
  }
```

**Pregunta:** Explica el funcionamiento de los siguientes código dentro del sprint1.

```
public class TestBoardConsole {
    private Board board;

    @Before
    public void setUp() throws Exception {
   	 board = new Board();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmptyBoard() {
   	 new Console(board).displayBoard();
    }

}

``` 

```
public class Console {
    private Board board;

    public Console(Board board) {
   	 this.board = board;
    }

    public void displayBoard() {
   	 for (int row = 0; row<3; row++) {
   		 System.out.println("-------");
   		 System.out.print("|"+ board.getCell(row, 0));
   		 System.out.print("|"+ board.getCell(row, 1));
   		 System.out.print("|"+ board.getCell(row, 2));
   		 System.out.println("|");
   	 }
   	 System.out.println("-------");
    }
}
````


Ahora consideramos `AC 1.2` y `AC 1.3` porque son similares. La decisión que debemos tomar es cómo representar una celda no válida. El código existente ha usado `0` para representar una celda vacía. Sea `-1` denota una celda inválida. Agregamos el siguiente código de prueba nuevo, donde un `3` denota una fila o columna no válida. 

```
// Criterio de aceptación 1.2 
@ Test
public void testInvalidRow(){
   assertEqual(“ “, board.getCell(3, 0), -1);
  }

 // criterio de aceptación 1.3
@ Test
public void testInvalidColumn(){
   assertEqual(“ “, board.getCell(0, 3), -1);
  }


```
Aunque estas pruebas no tienen ningún error de sintaxis, fallan porque `grid [3] [0]` y `grid [0] [3]` en la declaración de retorno de `getCell` están fuera de límite a lo siguiente:

```
public int getCell(int row, int column){
       if(row >= 0 && row < 3 && column >= 0 && column < 3)
           return grid[row][column]; 
      else
           return -1
    }

``` 
Ahora el código de trabajo ha implementado la primera historia de usuario. 

**Pregunta:**¿se necesita refactorización?

Según el DoD , debemos verificar si se cumple el objetivo de cobertura de la prueba y si el código fuente ha cumplido con las pautas de codificación. En efecto, cada declaración en `Board` ha sido compilada por al menos una de las tres pruebas. La revisión del código no encontró ningún problema con el estilo de codificación. Así se realiza el Sprint 1. 

#### Cobertura de código 

Cuando se completó el código de producción para el primer criterio de aceptación `AC1.1`, la prueba también cubrió completamente el código de producción. 

Cuando escribimos un nuevo código de producción para pasar una nueva prueba, el código de producción se limita exactamente a lo que se necesita para pasar la prueba. Ningún código adicional fue al código de producción. 

Sin embargo, un desarrollador puede escribir código de producción que sea más general de lo que necesita una prueba en particular. Por ejemplo, restablezcamos Sprint 1 desde cero. Comenzamos con la misma prueba para AC1.1, como se discutió antes. Para mayor comodidad, se copia a continuación.

```
public class TestEmptyBoard {
    private Board board = new Board ( );
    // Criterio de aceptación 1.1
   @ Test
    public void  testNewBoard() {
         for (int row =0; row < 3; row ++) {
            for (int column = 0; column < 3; column ++){
	assertEquals(“ “, board.getCell(row, column), 0);
	}
         }
         assertEquals(“ “, board.getTurn( ), ‘X’)
      }
}
``` 
Ahora escribimos un nuevo código de producción para que pase la prueba. El nuevo código con información de cobertura se muestra a continuación.

```
public class Board {
     private int[] grid
     private char turn = ‘X’;
     public Board(){
         grid = new int[3][3];  
       }
      public int getCell(int row, int column){
            if (row >=0 && row < 3 && column >=0 && column < 3)
                       return grid[row][column];
            else
                    return -1
            }
         public char getTurn() {
                  return turn;
     }
}
``` 
**Pregunta:** Realiza la cobertura de código. Explica tus respuestas.

Revisa: https://www.jetbrains.com/help/idea/code-coverage.html 


#### Estándares de codificación 

Los estándares de codificación deben seguirse durante todo el proceso. Los siguientes son algunos ejemplos en TicTacToe: 

- Separación de código de producción y prueba en diferentes paquetes
- Nombres autoexplicativos y convenciones de nomenclatura
- Estilo de bloque, es decir, "{" no comienza una nueva línea
- Líneas vacías entre métodos
- Comentarios 
- No hay variable de instancias públicas. 


## Sprint 2 

El equipo de desarrollo pensó que la segunda historia es un poco vaga porque involucra a ambos jugadores y, por lo tanto, las dividió en dos historias: 

**Historia de usuario 2:** Como jugador X, necesito colocar X en una celda vacía para poder hacer un movimiento. 

**Historia de usuario 3:** Como jugador O, necesito colocar O en una celda vacía para poder hacer un movimiento.

Tenemos los siguientes criterios de aceptación (completa): 

```
AC 2.1 Un movimiento X válido 
Dado 
Cuando  
Entonces
Y 

```
```
AC 2.2 Un movimiento X ilegal en una celda ocupada
 Dado 
 Cuando  
 Entonces
 Y 
 ```
 
 ```
 AC 2.3 Un movimiento X ilegal fuera del tablero 
 Dado 
 Cuando  
 Entonces
  Y 
```

````
 AC 3.1 Un movimiento O válido
 Dado 
 Cuando  
 Entonces
 Y 

``` 
```
 AC` 3.2 Un movimiento O ilegal en una celda ocupada 
 Dado 
 Cuando  
 Entonces
 Y 
``` 
 
```
 AC 3.3 Un movimiento O ilegal fuera del tablero 
 Dado 
 Cuando  
 Entonces
 Y 
```   
En Sprint 2, el objetivo es completar el segundo y tercer piso de las historias. Comenzamos con AC 2.1, que se describe a continuación:

```
AC 2.1 Un movimiento X válido 
Dado un juego en curso con el turno de X 
Cuando el jugador X hace un movimiento válido 
Entonces X se coloca en la celda Y el turno se cambia a 0 
``` 

"Dado en el juego en curso con el turno de X" se cumple cuando se inicia un nuevo juego. 

Para "el jugador X hace un movimiento válido", concebimos que el código de producción tendrá un método `makeMove`. 

Un movimiento válido significa que la celda objetivo que se va a probar no está ocupada. Por ejemplo, `cell(0, 0)` es una celda vacía cuando se inicia un nuevo juego. Debido a que el valor de la celda es del tipo int, usamos 1 para X para tratar con "X se coloca en la celda". 

El criterio de aceptación también ha introducido nueva información, es decir, el turno O. Como tal, creamos la siguiente prueba: 


```
public class TestCrossMoves {
     private Board board;
     @ Before

     public void setUp() throws Exception {
         board = new Board():
    }

  // Criterios de aceptación 2.1
   @ Test  
    public void testCrossTurnMoveVacantCell() {
           board.makeMove(0, 0);
           assertEquals(“ “, board.getCell(0, 0), 1);
           assertEquals(“ “, board.getTurn( ), ‘O’);
     }
}

```

Para que la prueba pase, actualizamos el código de producción para introducir el método `makeMove`. 

La siguiente es una implementación simple. Ni siquiera comprueba los límites del tablero o las celdas ocupadas. 


```
public void makeMove(int row, int columns) {
     grid [row][column] = 1;
      turn = ‘O’:
}
```

La prueba para AC 2.2 requerirá el código de producción para verificar si una celda está ocupada, como "movimiento ilegal dentro del tablero". Para AC 2.3, creamos dos pruebas para cubrir dos escenarios fuera de límite para "movimiento ilegal fuera del tablero" (fila no válida y columna no válida).  Se sugerirán actualizaciones del método makeMove para comprobar los límites del tablero. 

De manera similar, la prueba para AC3.1, AC3.2 y AC3.3 ayudará a desarrollar una versión completa de makeMove de la siguiente manera, donde X (u O) en una celda se representa por 1 (o 2):

```
public void makeMove(int row, int column) {
            if (row >=0 && row < 3 && column >=0 && column < 3
                       && grid[row][column] == 0 {
              grid[row][column] = (turn =’= X’)? 1:2;
              turn = (turn == ‘X’)? ‘O’: ‘X’;
   }
}

``` 
**Aquí las dos historias (y por lo tanto sus criterios de aceptación) son simétricas.** El código de prueba y el de producción para ellos se pueden desarrollar juntos, especialmente para un desarrollador experimentado que comprenda los requisitos. 

Los criterios de aceptación también sugieren la necesidad de nuevas pruebas y código de producción para visualizar las celdas ocupadas. Agregamos una nueva prueba de GUI, `testNonEmptyBoard`, para mostrar un tablero no vacío donde dos celdas están ocupadas con `X` y `O` a través de dos movimientos válidos. 

```
public class TestBoardGUI {
   private Board board;
   @Before 
public void setUp () throws Exception{
    board = new Board();
   }

…
@ Test
   public void testNonEmptyBoard(){
        board.makeMove(0, 0);
        board.makeMove(1, 1);
        new GUI(board);
        try {
               Thread.sleep(2000);
      } catch (InterruptedException e) {
          e.printStackTrace();
       }
    }
}

``` 
Por lo tanto, la clase GUI se actualizó para dibujar `X` u `O` según el contenido de cada celda. 

### Refactorización

Si bien el proceso iterativo de escribir código de prueba y código de producción ha implicado la refactorización, existen olores de código, como los números mágicos `-1, 0, 1, 2, 3`. 

Es hora de refactorizar más el código. Introducimos un tipo de enumeración para representar los posibles contenidos de las celdas:

```
public enum Cell {EMPTY, CROSS, NOUGHT}

```

Por lo tanto, el tipo de valor devuelto de `getCell` cambia a `Cell` y la representación de una celda no válida cambia de -1 a `null`. Agregamos el método `initBoard` para la inicialización explícita del tablero, sin depender del valor predeterminado. También decidimos reemplazar el máximo de filas y columnas (3 x 3) con constantes con nombre. 

El siguiente es el nuevo código para la construcción e inicialización del tablero:

```
public Board(){
   grid = new Cell |TOTALROWS|TOTALCOLUMNS|;
   initBoard();
}
public void initBoard(){
       for (int row =0; row < TOTALROWS; row++){
             for (int column =0; columns < TOTALCOLUMNS; column++){
                  grid[row][column] = Cell.EMPTY;
           }
     }
    turn = ‘X’;
 }
```

Después de la refactorización, continuamos midiendo la cobertura de código de `Board` y revisando los estilos de codificación. Como no se encuentra ningún problema, realizamos el Sprint 2. 

