## Proyecto Tictactoe

###  Actividad grupal

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

El paquete `sprintX_autoplay` contiene el código fuente que permite a un jugador jugar contra la computadora. Se utiliza para discutir la evolución del software. T

en en cuenta que el código está organizado de forma ilustrativa. Su estructura es ciertamente diferente a la de un proyecto de campo real, que generalmente utiliza una herramienta de control de versiones como git.


### Sprint 1

Considera la primera historia: como jugador, necesito un tablero vacío de 3 x 3 para comenzar un juego de TicTacToe. Escribimos el primer criterio de aceptación de la siguiente manera: 

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

Tareas para el sprint 1

Código de producción: separación de la lógica empresarial y la interfaz de usuario 
Lógica empresarial: crear una clase board
Interfaz de usuario: crear una clase del GUI del tablero (o Console) 

Separación de la lógica empresarial y la interfaz de usuario



 Escribir código de prueba 
Crear pruebas a partir de los criterios de aceptación. 

Escribir código de producción


Para escribir una prueba, necesitamos tomar las siguientes decisiones: 

¿Cuál es el tipo de datos de las celdas del tablero? 
 
¿Qué valor representa "vacío"? 

 ¿Cómo obtener una celda de tablero dada?

 ¿Cuál es el tipo de datos de  ' turn de X'? 

¿Cómo obtener el estado del turn? 


Así, tenemos la siguiente prueba:


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
El código de prueba anterior tiene varios errores de sintaxis porque el código de producción, es decir, la clase Board con los métodos getCell y getTurn, aún no está disponible. Entonces, escribimos el siguiente código de producción para que la prueba pase. 

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


El trabajo anterior podría realizarse a través de la programación en pares o con el estilo conductor-navegador: el conductor escribe el código en el teclado, mientras que el navegador piensa estratégicamente si el código debe ir (arquitectura, problemas, mejoras). 

Test Code: EmptyBoardGUITest


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

Código de Producción: GUI

public class GUI extends JFrame {
    public GUI(Board board);
    public Board getBoard(){
  }


Código de Prueba: TestBoardConsole

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

Código de Producción: Console

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

¡Se desaconseja encarecidamente la interfaz de consola interactiva para el proyecto de clase!

Pruebas para AC 1.2 y AC 1.3

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

Aunque estas pruebas no tienen ningún error de sintaxis, fallan porque grid [3] [0] y grid [0] [3] en la declaración de retorno de getCell están fuera de límite a lo siguiente:

Código de producción: board 

public int getCell(int row, int column){
       if(row >= 0 && row < 3 && column >= 0 && column < 3)
           return grid[row][column]; 
      else
           return -1
    }

Ahora el código de trabajo ha implementado la primera historia de usuario. 

Cobertura de código 

Comenzamos con la misma prueba para AC1.1, como se discutió antes. Para mayor comodidad, se copia a continuación.

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

Ahora escribimos un nuevo código de producción para que pase la prueba. El nuevo código con información de cobertura se muestra a continuación.

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

Ejercicio : Realiza la cobertura de código.

Estándares de codificación 

Los estándares de codificación deben seguirse durante todo el proceso. Los siguientes son algunos ejemplos en TicTacToe: 

Separación de código de producción y prueba en diferentes paquetes
Nombres autoexplicativos y convenciones de nomenclatura
Estilo de bloque, es decir, "{" no comienza una nueva línea
Líneas vacías entre métodos
Comentarios 
No hay variable de instancias públicas. 





