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

**Pregunta** Escribe el Javadoc del método calculateTax describiendo su contrato, en el código anterior.  Revisa el archivo TaxCalculator.java

La palabra clave assert

El lenguaje Java ofrece la palabra clave assert, que es una forma nativa de escribir aserciones. En el ejemplo anterior, en lugar de lanzar una excepción, podríamos escribir assert value >= 0 : "Value cannot be negative." . Si value no es mayor o igual a 0, la máquina virtual de Java (JVM) generará un AssertionError. 

Pregunta 2 Escribe una versión de TaxCalculator usando asserts para ello completa el archivo TaxCalculator1.java

Decidir si usar instrucciones assert o declaraciones if simples que arrojan excepciones es algo para discutir con los miembros de tu equipo.

Precondiciones  y postcondiciones fuertes y débiles

Al definir precondiciones y posteriores, una decisión importante es cuán débiles o fuertes deseas que sean. En el ejemplo anterior, manejamos la precondición con mucha fuerza: si entra un valor negativo, viola la precondición del método, por lo que detenemos el programa. 

Una forma de evitar detener el programa debido a números negativos sería debilitar la precondición. En otras palabras, en lugar de aceptar solo valores mayores que cero, el método podría aceptar cualquier valor, positivo o negativo. 

Listado 2 TaxCalculator con una precondición  más débil

public double calculateTax(double value) {
	No hay precondiciones para verificar,cualquier valor es válido
   // methods continues 
}

Las precondiciones más débiles facilitan que otras clases invoquen el método. Después de todo, sin importar el valor que le pases a calculateTax, el programa devolverá algo. Esto contrasta con la versión anterior, donde un número negativo arroja un error. 

Pregunta 3 ¿puedes aplicar el mismo razonamiento a las postcondiciones? , ¿como relacionas el siguiente listado que devuelve un código de error en lugar de una excepción?

public double calculateTax(double value) {
// pre-condition check
Si la precondición  no se cumple, el método devuelve 0. El cliente de este método no necesita preocuparse por las excepciones.

	if (value < 0) {
               return 0;
	}

  //…
}

Invariantes 

Hemos visto en clase  que las precondiciones deben cumplirse antes de la ejecución de un método, y las postcondiciones deben cumplirse después de la ejecución de un método. Ahora pasamos a las condiciones que siempre deben cumplirse antes y después de la ejecución de un método. 

Estas condiciones se llaman invariantes. Un invariante es, por lo tanto, una condición que se mantiene durante toda la vida útil de un objeto o una estructura de datos. 

Imagina una clase Basket que almacena los productos que el usuario compra en una tienda en línea. La clase ofrece métodos como  add(Product p, int quantity) , que agrega un producto p a quantity, y remove(Product p), que elimina el producto por completo del carrito. Aquí hay un esqueleto de la clase.

Listado 3 Clase Basket

public class Basket {
  private BigDecimal totalValue = BigDecimal.ZERO;
	Usamos BigDecimal en lugar de double para evitar problemas de redondeo en Java.
      private Map<Product, Integer> basket = new HashMap<>();

	Agrega el producto al carrito y actualiza el valor total del carrito 
  public void add(Product product, int qtyToAdd) {
      // add the product
        // update the total value
  }
        Elimina un producto del carrito y actualiza su valor total
public void remove(Product product) {
   // remove the product from the basket
    // update the total value
   }
}

Pregunta 4  Escribe para el método add() sus pre/postcondiciones.    





