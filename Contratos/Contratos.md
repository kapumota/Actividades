## Curso de Desarrollo de Software

En esta actividad debes crear un proyecto llamado `Contratos` y configurar el archivo `pom.xml` para utilizar JUnit 5.

Sube el proyecto a un repositorio con el nombre de la actividad de manera individual con todos tus resultados.


### Diseño de contratos 

Imagina una pieza de software que maneja un proceso financiero muy complejo. Para que suceda esa gran rutina, el sistema de software encadena llamadas a varias subrutinas (o clases) en un flujo complejo de información: es decir, los resultados de una clase se pasan a la siguiente clase, cuyos resultados se pasan nuevamente a la siguiente clase  y así. 

Como es habitual, los datos provienen de diferentes fuentes, como bases de datos, servicios web externos y usuarios. En algún momento de la rutina, se llama a la clase `TaxCalculator` (que maneja el cálculo de un impuesto específico).

De los requisitos de esta clase, el cálculo solo tiene sentido para números positivos. 


#### Precondiciones y postcondiciones 

Del ejemplo del cálculo de impuestos, debemos reflexionar sobre las precondiciones que el método necesita para funcionar correctamente, así como sus postcondiciones: lo que el método garantiza como resultados. 

Ya mencionamos una precondición: el método no acepta números negativos. Una posible postcondición de este método es que tampoco devuelve números negativos.
 

Una vez que se establecen las precondiciones y postcondiciones del método, es hora de agregarlas al código fuente. 

Hacerlo puede ser tan simple como una instrucción `if` como se muestra en la siguiente lista.


```
public class TaxCalculator {
    public double calculateTax(double value) {

       if(value < 0) {
        	     throw new RuntimeException("...");
    	}
      double taxValue = 0;

``` 

La postcondición también se implementa como un simple if. Si algo sale mal, lanzamos una excepción, alertando al consumidor que la postcondición no se cumple

```
    if(taxValue < 0) { 
        throw new RuntimeException("...");
    }

    return taxValue;

	}
}
``` 

Dejar claras las precondiciones y posteriores en la documentación también es fundamental y muy recomendable. 

**Pregunta:** Escribe el Javadoc del método `calculateTax` describiendo su contrato, en el código anterior.  Revisa el archivo `TaxCalculator.java`.

#### La palabra clave assert

El lenguaje Java ofrece la palabra clave `assert`, que es una forma nativa de escribir aserciones. En el ejemplo anterior, en lugar de lanzar una excepción, podríamos escribir `assert value >= 0 : " Valor no puede ser negativo"`. . Si `value` no es mayor o igual a 0, la máquina virtual de Java (JVM) generará un `AssertionError`. 

**Pregunta:**  Escribe una versión de `TaxCalculator` usando asserts para ello completa el archivo `TaxCalculator1.java`.

Decidir si usar instrucciones `assert` o declaraciones `if `simples que arrojan excepciones es algo para discutir con los miembros de tu equipo.

### Precondiciones  y postcondiciones fuertes y débiles

Al definir precondiciones y posteriores, una decisión importante es cuán débiles o fuertes deseas que sean. En el ejemplo anterior manejamos la precondición con mucha fuerza: si entra un valor negativo, viola la precondición del método, por lo que detenemos el programa. 

Una forma de evitar detener el programa debido a números negativos sería debilitar la precondición. En otras palabras, en lugar de aceptar solo valores mayores que cero, el método podría aceptar cualquier valor, positivo o negativo. 

```
public double calculateTax(double value) {
	No hay precondiciones para verificar,cualquier valor es válido
   // metodos
}
``` 

Las precondiciones más débiles facilitan que otras clases invoquen el método. Después de todo, sin importar el valor que le pases a `calculateTax`, el programa devolverá algo. 
Esto contrasta con la versión anterior, donde un número negativo arroja un error. 

**Pregunta:** ¿Puedes aplicar el mismo razonamiento a las postcondiciones? , ¿como relacionas el siguiente listado que devuelve un código de error en lugar de una excepción?

```
public double calculateTax(double value) {
// verificación de la precondición
     if (value < 0) {
               return 0;
	}

  //...
}
``` 

### Invariantes 

Hemos visto en clase  que las precondiciones deben cumplirse antes de la ejecución de un método y las postcondiciones deben cumplirse después de la ejecución de un método. 

A las condiciones que siempre deben cumplirse antes y después de la ejecución de un método se llaman **invariantes**. 

Un invariante es, por lo tanto, una condición que se mantiene durante toda la vida útil de un objeto o una estructura de datos. 

Imagina una clase `Basket` que almacena los productos que el usuario compra en una tienda en línea. La clase ofrece métodos como  `add(Product p, int quantity)` que agrega un producto `p` a `quantity` y `remove(Product p)`, que elimina el producto por completo del carrito. 

Aquí hay un esqueleto de la clase.

```
public class Basket {
  private BigDecimal totalValue = BigDecimal.ZERO;
      private Map<Product, Integer> basket = new HashMap<>();
 
  public void add(Product product, int qtyToAdd) {

  }
public void remove(Product product) {

   }
}

``` 

**Pregunta:** Escribe para el método `add()` sus pre/postcondiciones.    

**Pregunta:** Modela otra postcondiciones aquí, como `el nuevo valor total debe ser mayor que el valor total anterior`. Usa  la clase `BigDecimal` en lugar de un `double`. 
`BigDecimals` se recomienda siempre que desees evitar problemas de redondeo que pueden ocurrir cuando usas doubles. 

**Pregunta:** Escribe las pre/post condiciones del método `remove()`.

Independientemente de los productos que se agreguen o eliminen de `basket`, el valor total de `basket` nunca debe ser negativo. 
Esta no es una precondición ni una poscondición: es un invariante y la clase es responsable de mantenerlo. 

**Pregunta:** Explica y completa el siguiente listado de invariantes de la clase `Basket`:

```
public class Basket {
   private BigDecimal totalValue = BigDecimal.ZERO;
   private Map<Product, Integer> basket = new HashMap<>();
   

  public void add(Product product, int qtyToAdd) {
       assert product != null : "...";
        assert qtyToAdd > 0 : "...";
         BigDecimal oldTotalValue = totalValue;

      assert basket.containsKey(product) : "...";
     assert totalValue.compareTo(oldTotalValue) == 1 : "...";
   
    assert totalValue.compareTo(BigDecimal.ZERO) >= 0 :
    	"..."

     }

     public void remove(Product product) {
       assert product != null : "...";
       assert basket.containsKey(product) : "...";



      assert !basket.containsKey(product) : "...";
      assert totalValue.compareTo(BigDecimal.ZERO) >= 0 :
           "El valor total no puede ser negativo ."
     }
}
``` 

**Pregunta:**  ¿Qué función tiene el método `invariant()`  en el siguiente listado?

``` 
 public void add(Product product, int qtyToAdd) {
         // ... metodos ...
             assert invariant() : "...";
         }
          public void remove(Product product) {
                 // ... metosos ...
           assert invariant() : "...";
      }
           private boolean invariant() {
              return totalValue.compareTo(BigDecimal.ZERO) >= 0;
         }
}
```
¿Qué pasa si cambiamos el contrato de una clase o método? Supongamos que el método `calculateTax` que discutimos anteriormente necesita nuevas precondiciones. 
En lugar de `el valor debe ser mayor o igual a 0`, se cambia a `el valor debe ser mayor o igual a 100`. ¿Qué impacto tendría este cambio en el sistema y nuestras suites de prueba?

La forma más sencilla de comprender el impacto de un cambio no es mirar el cambio en sí mismo o la clase en la que se está produciendo el cambio, sino todas las demás clases (o dependencias) que pueden usar la clase cambiante. 

