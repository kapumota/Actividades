## Curso de desarrollo de software

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada DesignContracts y coloca todas tus respuestas.

Esta actividad es individual.

### Diseño por contrato

El diseño por contrato ve a la precondición y la postcondición de un método como un contrato entre el método (proveedor o servidor) y sus llamadores (cliente): 
si un cliente llama al método con la precondición satisfecha, entonces el proveedor entrega un estado final en el que el se satisface la poscondición. 

El **contrato** es un acuerdo formal de los derechos y obligaciones de cada parte: 
la precondición es una obligación para el cliente y un derecho para el proveedor, mientras que la postcondición es un derecho para el cliente y una obligación para el proveedor. 

#### Regla de precondición asumida

El diseño por contrato adopta el diseño de precondición. El método asume que tu precondición siempre se cumple. 

El siguiente método `reportTriangle` informa el tipo de triángulo: recto, agudo u obtuso. 

La precondición asumida es que los tres ángulos sean positivos y su suma sea 180.

``` 
Precondition: a>0, b>0, c>0 and a +b +c =180
Postcondition:  
right, if a  =90 or b = 90 or c= 90; 
 obtuse  if a > 90 or >=90 or c > 90
else acute  
public enum TriangleType {RIGHT, ACUTE, OBTUSE, OBTUSE};

public TriangleType reportTriangle(double a, double b, double c){
        if (a==90||b==90||c==90){
            return TriangleType.RIGHT;
        } else		
        if (a>90||b>90||c>90) 
            return TriangleType.OBTUSE;
        else 
            return TriangleType.ACUTE;
}
```  

**Problema:** Considera el siguiente código de cliente, que primero obtiene tres ángulos y llama a reportTriangle(a,b,c): 

```
double a = ...;
double b = ...;
double c = ...;
TriangleType result = reportTriangle(a,b,c);
``` 
¿cuáles son los resultados para `(90, 45, 45)`, `(120, 40, 20)` y `(50, 60, 70)`= , ¿qué sucede con `(90, -45, 135)` ?. 
¿Quién es el responsable de este fallo, el proveedor o el cliente?. Corrige este error. 

#### Reglas de violación de pre/postcondiciones

La violación de una precondición o postcondición en tiempo de ejecución indica la existencia de un error. 

La regla de violación de la precondición establece que `una violación de la precondición es la manifestación de un error en el cliente`, independientemente de si la postcondición se cumple o no. 

La regla de violación de la postcondición establece que `una violación de la postcondiciónes es la manifestación de un error en el proveedor` porque el proveedor no cumplió con su contrato. 

**Pregunta:** En el ejemplo anterior indica una violación de precondición. 


Considera `sqrt(double x)` que devuelve la raíz cuadrada de un valor doble no negativo. 

La poscondición establece que la raíz cuadrada de `x` al cuadrado es aproximadamente igual a `x`.  
Dos números de punto flotante son aproximadamente iguales si el valor absoluto de su diferencia es lo suficientemente pequeño. 

```
Precondición: x >= 0 
Postcondición: abs(sqrt(x)* sqrt(x) - x) < epsilon
double sqrt (double x) 

``` 
El siguiente código de cliente se rompe cuando `x` es `-1`. Es defectuoso porque viola la precondición. 

```
double x = ... ; 
double y = sqrt(x); 
assert abs(y*y -x) < épsilon 
```


**Pregunta:** El siguiente método `isVowel` verifica si una letra determinada es una vocal. "Y" a veces se considera una vocal cuando aparece en palabras como `cry`, `fly` y `sky`.

```
Precondition: letter ∈ {'a'-'z', 'A'-'Z'}   
Postcond: true if letter ∈ {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'}; otherwise, false
boolean isVowel(char letter) { 
      String vowels = "aeiouy&@";
      char ch = Character.toLowerCase(letter);
returns vowels.indexOf(ch) >= 0;  
}
```

Como el  parámetro `letter` es del tipo char, la precondición asume que es una letra mayúscula o minúscula.
El siguiente código de cliente primero obtiene un carácter, no necesariamente una letra, y luego llama a `isVowel(letter)`:

```
char letter = ...;
boolean result = isVowel(letter);
``` 

Si el carácter obtenido es `'A'`, es decir, `letter = 'A', isVowel('A')` devuelve `true`  entonces `result = true`. 

Si el carácter es `'Z'`, entonces `result = false`. 

Para cada una de estas llamadas, se cumple la precondición. 

¿Qué sucede cuando `letter = '@'` ?.

Modifiquemos ligeramente a la siguiente versión, donde falta `'e'`  en la lista de vocales:

``` 
Precondition: letter ∈ {'a'-'z', 'A'-'Z'}   
Postcond: true if letter ∈ {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'}; otherwise, false
boolean isVowel(char letter) { 
      String vowels = "aiouy"; // e esta perdido
      char ch = Character.toLowerCase(letter);
returns vowels.indexOf(ch) >= 0;  
}
``` 
La llamada `isVowel('E')` devuelve `false` lo cual es incorrecto. En este caso, se cumple la precondición, pero se viola la postcondición. 

Según la regla de violación de la postcondición, el código de proveedor anterior es defectuoso. 


#### Regla de precondición razonable 

La regla de la precondición razonable requiere que la precondición aparezca en la documentación oficial distribuida a los autores de los clientes y que la necesidad de la precondición se justifique lógicamente en términos de la especificación, no para la conveniencia de implementación del proveedor. 

En el diseño por contrato, la precondición pretende aclarar qué casos no puede manejar el método en relación con los requisitos lógicos. Por ejemplo, es razonable requerir `p.length > 0` para `sort(int [ ] p)`. Otros buenos ejemplos son `list.length >0` para `max(int[ ] list)` , `not empty()` para `pop()` y `x>=0` para `sqrt(double x)`. 

#### Regla de disponibilidad de precondiciones

La regla de disponibilidad de precondiciones establece que cada cliente del método debe poder verificar su precondición. La precondición no debe utilizar métodos privados ocultos a los clientes. Por ejemplo, los clientes de isVowel(letter) deberían poder llamar a Character.isLetter(letter). Para  la precondición amount>0 y getBalance() >=amount del método withdraw(double amount) en una clase BankAccount, el método getBalance() debe ser visible para los clientes. 

### Cambio de Contrato

#### Impacto del cambio de contrato

Durante el desarrollo y el mantenimiento del software, podemos actualizar un método por varios motivos, como la adición y modificación de la funcionalidad, la corrección de errores, la refactorización y el ajuste del rendimiento. 

Cuando se cambia la firma del método, la necesidad de actualizar el código de cliente existente es obvia. Aquí el código del cliente incluye pruebas unitarias del método. 

Cuando una actualización conserva la firma del método, el compilador no informa errores. 

Sin embargo, podrías haber cambiado el contrato original del método (precondición y postcondición). 

En esencia, este es un cambio de interfaz implícito que puede o no romper el código del cliente existente. 

Consideremos `int f1(int x)` cuya precondición es `x > 5` y la poscondición es `y = f1(x) > 0`. Supongamos que el código del cliente es el siguiente:

```
int x = ... //supongamos x = 6
assert (x > 5);
int y = f1(x);
assert y > 0;
``` 

Ahora actualicemos `int f1(int x)` con una nueva precondición `x > 10` mientras mantenemos la postcondición sin cambios. 
El cliente ya no cumple la nueva precondición sin cambios, el código de cliente aún cumple la precondición. Sin embargo, si la llamada devuelve 0, la aserción después de la llamada falla y  por lo tanto, el código de cliente finaliza de manera anormal. 

#### Regla de cambio de contrato 

Generalmente, necesitamos inspeccionar todo el código del cliente cuando el contrato de un método ha cambiado. Hay algunas circunstancias en las que el cambio de precondición y posterior puede no romper el código del cliente, incluido el código de prueba. 

Una precondición más débil  no causa daño al cliente que satisface la precondición existente antes de la llamada, mientras que una poscondición más fuerte no causa daño al cliente que confía en que la poscondición existente se satisfaga después de la llamada . 

Por lo tanto, la actualización no romperá el contrato original. 

Dadas dos condiciones desiguales `P1` y `P2`. Se dice que `P1` es más fuerte que `P2` (o `P2` es más débil que `P1`) si `P1` implica `P2`. 

Por ejemplo, `x > 10` es más fuerte que `x > 5`, `y > 0` es más fuerte que y >=0. 

En el ejemplo anterior de `int f1(int x)`, la actualización no es segura debido a una precondición más sólida y una postcondición más débil. 

El cambio de precondición `x > 0` y postcondición `y > 1` estaría bien. 

Ten en cuenta que dos condiciones aparentemente diferentes pueden ser equivalentes. 

Por ejemplo, `x > 0` y `2x > 0` son equivalentes, `even(x)` es lo mismo que `!odd(x)`. 

Dos condiciones desiguales pueden no tener una relación más fuerte ni más débil. 

Por ejemplo, `x = 1` no es ni más fuerte ni más débil que `x = 2`. Otros ejemplos son `x >0 vs. x +y > 0`, `x >0 vs. y >0`, `isDigit(ch) vs. isLetter(ch)`. 

Si la nueva precondición (o postcondición) no es ni más débil ni más extraña que la precondición (o postcondición) original, la actualización puede romper el código del cliente. 

**Pregunta:**

Considera `int [ ] genRandomIntegers(int count)` que devuelve una lista de enteros aleatorios. Su precondición y postcondición son `count >0` y `list.length = count`, respectivamente (`list` denota el valor devuelto). El código del cliente es el siguiente:

```
int count =2; // calculado
int[ ] list = var.genRandomIntegers(count);
for (int i = 0; i < count; i ++){
    process(list [i])
}
```

¿Qué sucede si modificamos `genRandomIntegers`. mantenemos la precondición pero cambiamos la postcondición a `list.length=count-1`?.  

#### Cambio de contrato en desarrollo incremental

Un caso especial de cambio de contrato es agregar código nuevo a un método mientras se conserva la funcionalidad existente para el desarrollo incremental (por ejemplo, en TDD). 

Aquí necesitamos asegurarnos de que siempre que la nueva versión sea correcta, el código original también sea correcto, o tenemos que modificar el código existente. 

Lo contrario no es necesariamente cierto: una versión original correcta no garantiza que el nuevo código sea correcto. 

Esto se puede formalizar de la siguiente manera: `(P2 => Q2) => (P1 => Q1)` donde `P1` y `Q1` son la precondición y postcondición originales y `P2` y `Q2` son las nuevas. 

La regla de una nueva precondición igual o más débil (es decir, `P1 => P2`) y una nueva postcondición igual o más fuerte (es decir, `Q1 => Q2`) es una condición suficiente pero no necesaria del argumento de corrección anterior. La prueba se da al final de esta sección. 

Considera el siguiente método `getCell()` en el programa TicTacToe.

```
public Cell getCell(int row, int column){
    return grid [row][column];
}
```

El código se crea para pasar la prueba del primer criterio de aceptación (tablero vacío AC 1.1). 

La precondición y postcondición se dan a continuación:

```
Precondición: 0 <= row < 3 and 0 <= column < 3
Postcondición: return 0
````
Después de pasar las pruebas para AC 1.2 y AC 1.3, el código se cambia a lo siguiente:

``` 
public int getCell(int row, int column){
    if(row >= 0 && row < 3 && column >=0 && column < 3){
         return grid[row][column];
	} else{
                  return -1;
     }
}
```

**Pregunta:** ¿Cómo cambia la precondición y la postcondición en el ejemplo anterior?. 

