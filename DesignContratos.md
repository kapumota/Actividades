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

**Problema:** En el ejemplo anterior indica una violación de precondición. 


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
