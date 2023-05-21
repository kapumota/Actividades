## Curso de desarrollo de software

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada Pre-postcondiciones y coloca todas tus respuestas.

Esta actividad es individual.

### Pre-post condiciones

Las precondiciones y postcondiciones son  conceptos para describir la funcionalidad de los programas de computadora. 

Aquí nos enfocamos en métodos y constructores en programas orientados a objetos. 

Las **precondiciones** de un método (o constructor) se refieren a las restricciones en la entrada del método y los estados de todos los objetos relacionados que deben cumplirse antes de llamar al método. 
Involucra los parámetros explícitos del método, parámetros implícitos (por ejemplo, variables de instancia en la misma clase) y variables globales. 

La **postcondición** de un método se refiere a las restricciones en la salida del método y los estados de todos los objetos relacionados que el método garantiza cuando regresa. 


La precondición P y la postcondición Q de un método M se pueden representar mediante la fórmula de corrección `{P}M{Q}` también conocida como triples de Hoare. 

- `M` método o programa
- `P` pre-condición
- `Q` post-condición


Esto significa que `cualquier ejecución de M, comenzando en un estado de programa donde se mantiene P, terminará en un estado de programa donde se mantiene Q`:

``` 
{x >= 9} x := x + 5; {x >= 13}
```

La precondición y postcondición de un método describen qué (`what`) se espera que haga el método, en lugar de cómo (`how`) hacerlo en el cuerpo del método. 

Se pueden especificar como comentarios en el código fuente y/o como parte de la documentación de la API. 

También pueden codificarse como aserciones ejecutables en el código fuente que expresan condiciones de corrección. 

La violación en tiempo de ejecución de una aserción de precondición o postcondición terminaría la ejecución del programa. Indica una falla inesperada. Es diferente del manejo de excepciones. 

Este último se ocupa de las condiciones (por ejemplo, datos de entrada incorrectos) que se espera que ocurran. 

#### Precondiciones asumidas frente a precondiciones validadas 

Hay dos opciones para manejar la precondición de un método: la precondición asumida y la precondición de validación. 

- Precondición asumida (también conocida como diseño de precondición exigente). El método asume que la precondición siempre es satisfecha por todos los clientes. 
- Los clientes verifican la precondición antes de llamar al método. 

- Precondición validada (también conocido como diseño de precondición tolerante). La precondición se valida en el cuerpo del método mediante un `if-then` o una estructura de control de equivalencia.


La elección de una precondición asumida se adopta en el diseño por contrato. Deben seguirse las reglas de diseño por contrato para utilizar correctamente las precondiciones asumidas. 


Al validar la precondición completa en el cuerpo del método, cambiamos la precondición real del método a una tautología (es decir, siempre verdadera) o convertimos el método de una función parcial 
que no maneja todas las entradas a una función total que maneja todas las entradas. 

La precondición original se ha convertido en parte de la postcondición actual con un efecto condicional. 

Considera el siguiente método `getCell()` en el programa TicTacToe dado anteriormente:

```
public Cell getCell(int row, int column) {
	return grid[row][column];
}

```
Suponiendo que `row`  y `column`  dadas representan una celda válida del tablero de tictactoe, devuelve la celda a la que se hace referencia. 
La precondición supuesta y la postcondición correspondiente son las siguientes:

```
Precondition: 0<=row < TOTALROWS and 
0 <=column < TOTALCOLUMNS
Postcondition: return value is either Cell.EMPTY, 
Cell.CROSS, or Cell.NAUGHT
``` 

Una alternativa es validar la precondición en el cuerpo del método de la siguiente manera:

```
public Cell getCell(int row, int column) {
	if (row >= 0 && row < TOTALROWS 
 && column >= 0 && column < TOTALCOLUMNS) {
		return grid[row][column];
	} else {
		return null;
	}
}
```

**Pregunta:** Indica cuales son los cambios de la precondición y la postcondición del ejemplo anterior.

Un caso especial de precondiciones validadas es el manejo de excepciones: el método genera una excepción cuando no se cumple la precondición. 

El método `getCell()` anterior se puede implementar de la siguiente manera:


```
if (row >= 0 && row < TOTALROWS 
 && column >= 0 && column < TOTALCOLUMNS) {
	   return grid[row][column];
	} else {
	   throw new Exception("Out of bound");
	}
}

```
**Pregunta:** Escribe la nueve precondición. ¿Se relacionada con la postcondición revisada?.

En TDD, la precondición y la postcondición de un método cambian con el tiempo a medida que evoluciona el proceso de desarrollo. 
 

La precondición y la poscondición en un momento determinado solo implican las suposiciones que subyacen en el código actual. 

Ten en cuenta que un programa TicTacToe puede adoptar la primera versión de `getCell()`, suponiendo que todas las llamadas a `getCell` proporcionen una fila y una columna válidas. 

La razón es que `getCell` solo será llamado por `TicTacToeGUI`  que es confiable porque el mismo desarrollador o el mismo equipo lo escribe. 
Esto implica que las siguientes pruebas para los criterios de aceptación AC 1.2 y AC 1.3 se vuelven redundantes e inútiles.

```
public void testInvalidRow(){
    assertEquals(" ", board.getCell(3, 0), null);
   }
public void testInvalidColumn() {
      assertEquals(" ", board.getCell(0, 3), null);
}
```
#### Especificación de pre-postcondiciones

Las precondiciones  y postcondiciones deben especificarse con precisión, ya sea documentación API escrita o aserciones ejecutables. 

Por ejemplo, la precondición y la postcondición a continuación brindan una especificación rigurosa del método `int max(int [ ], list)`, que devuelve el valor máximo de una lista dada de enteros, 
donde `max` representa el valor de retorno.

```
Precondition: list.length>0
Postcondition: max>= list[i] for each i 
  (0 ≤ i< list.length), and there exists j 
  (0 ≤ j< list.length) such that max=list[j]
```
Esta especificación se puede usar para razonar sobre la corrección de una implementación concreta de `max (lista int [ ])`. Considera el siguiente código:

```
int max(int[] list){
	int result=list[0];
	for (int i=0; i<list.length-1; i++){
	     if (result<list[i])
		result=list[i];
	}
	return result;
}
``` 
**Pregunta:**  Dada `list = [3,2, 5]` ¿se cumple La postcondición ?. La expresión `i < list.length -1` debe cambiarse a `i < list.length` o `i < list.length -1`. 

También se pueden usar precondiciones y postcondiciones para diseñar casos de prueba. 

Obtener una especificación precisa puede ser una tarea no trivial. 

Considera `int [ ] sort(int[ ] p)` que ordena una lista dada de enteros en orden ascendente. Sea `q` la lista resultante. 
La precondición es `p.length > 0`. Una posible postcondición es que `p` y `q` tengan el mismo tamaño, y todos los valores en `q` se clasifiquen en orden ascendente. 

Se puede formalizar de la siguiente manera: 

**Postcondición (v1):** `q.length = p.length y q[i] <= q[i +1]` para cualquier `i, 0 <= i < q.length -1`. 

Si bien parece captar los significados de tipo, es incorrecto. 

Como contraejemplo, se satisface con la siguiente implementación defectuosa, que simplemente devuelve una lista de 1 con la misma longitud:

```
public int [ ] sort (int p [ ]){
   int[ ] q = new int [p.length];
   for (int i = 0; i < p.length; i ++)
       q[i] = 1;
   return q;
}
```

Mejoremos requiriendo que cada elemento en `p` debería aparecer en `q` y cada elemento en `q` debería aparecer en `p`. Se formaliza de la siguiente manera: 

**Poscondición (v2):** `q.length = p.length` y `q[i] <= q[i +1]` para cualquier `i, 0 <= i < q.length -1` y para cualquier `j (0 <= j < p.length)`, existe `k (0 <= k < q.length)` tal que 
`p[j] = p[k]` y para cualquier `j (0 <= j < q.length)` existe `k ( 0 <= k < p.length)` tal que `q[j] = p[k]`. 

Desafortunadamente, esta especificación de postcondición sigue siendo incorrecta. 

**Pregunta:** Encuentra un ejemplo que indique que la implementación es defectuosa. 

La siguiente es una especificación correcta, donde `count(int x, int[ ] y)` denota el número de ocurrencias del elemento `x` en la lista `y`: 

**Postcondición (v3):** `p.length = q.length` y `q[i] <= q[i +1]` para cualquier `i, 0 <= i < q.length -1` y `count(p[j], p) = count(p[j], q)` para cualquier `j, 0 <= j < p.length`. 

Si la postcondición anterior se traduce en aserciones ejecutables, puede consumir más tiempo que el algoritmo `sort` debido a la verificación de permutación. 

La especificación de la postcondición a menudo se centra en los cambios que resultan de una llamada al método. 

Sin embargo, una especificación completa puede necesitar considerar lo que debe permanecer sin cambios después de la llamada. 

Considera el siguiente método en una clase `VendingMachine` que permite a un cliente comprar artículos de una máquina expendedora.

``` 
public boolean purchase(String drink){
        if (drink.equalsIgnoreCase(COFFEE)){
            if (coffee.getCount()>0&&deposit>=coffee.getPrice()){
                coffee.sell();
                calculateChange(coffee.getPrice());
                return true;
            }
        } 
    ...
``` 
Una máquina expendedora vende varios artículos, como café, refrescos y jugos. 
Cuando se compra un artículo de café, los cambios esperados incluyen: 

(a) el conteo de artículos de café se reduce en uno
(b) se devuelve el dinero de cambio 
(c) el depósito se restablece. 

Estos cambios normalmente se especifican como parte de la postcondición. 

Sin embargo, también esperábamos que otra información permaneciera sin cambios. como el precio del café y el conteo de artículos de refrescos.

Si esto no se verifica, podemos perder la oportunidad de revelar un error. 

Considera el siguiente código.

``` 
public boolean purchase(String drink){
        if (drink.equalsIgnoreCase(COFFEE)){
            if (coffee.getCount()>0&&deposit>=coffee.getPrice()){
                coffee.sell();
                calculateChange(coffee.getPrice());
                return true;
            }
        } 
   ...
}
``` 

**Pregunta:** Explica que sucede en este código. ¿Qué se puede mejorar en el código dado?. 

**Pregunta:** Realiza los siguientes, escribiendo la implementación correspondiente . 

- `int[] reverse(int[] list)` devuelve el orden inverso de todos los elementos de la lista. 

- `int linearSearch(int[] list, int key)` devuelve el índice de la primera aparición de la clave en la lista o -1 si no se encuentra. 

- `bool isVowel(char letra)` devuelve `true` si letter es una vocal.

- `boolean isLeapYear(int year)` devuelve `true` si es un año bisiesto 

- `TriangleType reportTriangleType(int a, int b, int c)` devuelve el tipo de triángulo para los lados dados a, b y c, donde `TriangleType` es un tipo de enumeración `{SCALENE, ISOSCELES, EQUILATERAL}`. 
