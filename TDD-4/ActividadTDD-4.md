## Curso de desarrollo de software

###  Actividad individual

Descarga la actividad desde aquí: https://github.com/kapumota/Actividades/tree/main/TDD-4/TDD-4

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada TDD-4 y coloca todas tus respuestas e implementaciones.

En las siguientes secciones, aprenderemos cuáles son estos principios y cómo nos ayudan a escribir código y pruebas bien diseñados. Comenzaremos con SRP, que posiblemente sea el principio fundamental de cualquier estilo de diseño de programas.

### SRP: bloques de construcción simples 

En esta sección, examinaremos el primer principio, conocido como SRP. Usaremos un solo ejemplo de código en todas las secciones. Esto aclara cómo se aplica cada principio a un diseño orientado a objetos (OO). 

Vamos a ver un ejemplo clásico de diseño OO: dibujar formas. 

El siguiente diagrama es una descripción general del diseño en el lenguaje de modelado unificado (UML), que describe el código presentado en la actividad:

![](https://github.com/kapumota/Actividades/blob/main/TDD-4/Imagenes/UML1.png)

### Contraejemplo: código  que viola el SRP 

Para ver el valor de aplicar SRP, consideremos un fragmento de código que no lo usa. El siguiente fragmento de código tiene una lista de formas que se dibujan cuando llamamos al método `draw()`: 

```
public class Shapes {
    private final List<Shape> allShapes = new ArrayList<>();
    public void add(Shape s) {
       allShapes.add(s);
  }
 public void draw(Graphics g) {
   for (Shape s : allShapes) {
        	switch (s.getType()) {
               case "textbox":
                   var t = (TextBox) s;
                    g.drawText(t.getText());
                    break;
               case "rectangle":
                   var r = (Rectangle) s;
                   for (int row = 0;
                      	row < r.getHeight();
                      	row++) {
                     g.drawLine(0, r.getWidth());
                }
        	}
        }
   }
}
```

**Pregunta** Este código tiene cuatro responsabilidades, ¿puedes indicarlas?. ¿Podemos cambiar este código para que sea más fácil agregar un nuevo tipo de forma?.

### Aplicación del SRP para simplificar el mantenimiento futuro 

Refactorizaremos este código para aplicar SRP, dando pequeños pasos. Lo primero que hay que hacer es trasladar ese conocimiento de cómo dibujar cada tipo de forma fuera de esta clase, de la siguiente manera:

```
package shapes;
import java.util.ArrayList;
import java.util.List;
public class Shapes {
      private final List<Shape> allShapes = new ArrayList<>();
      
     public void add(Shape s) {
    	allShapes.add(s);
   }
   public void draw(Graphics g) {
       for (Shape s : allShapes) {
          switch (s.getType()) {
             case "textbox":
                var t = (TextBox) s;
                t.draw(g);
                break;
            
             case "rectangle":
                var r = (Rectangle) s;
                r.draw(g);
        }
    }
  }
}
```
El código que solía estar en los bloques de declaraciones `cases` se ha movido a las clases de formas (shape). Veamos la clase `Rectangle` como un ejemplo. Puedes ver lo que ha cambiado en el siguiente fragmento de código: 

```
public class Rectangle {
     private final int width;
     private final int height;
     
   public Rectangle(int width, int height){
       this.width = width;
       this.height = height;
       }

   public void draw(Graphics g) {
       for (int row=0; row < height; row++) {
        	g.drawHorizontalLine(width);
        }
    }
}
```

**SRP**

Haz una cosa y hazla bien. Ten solo una razón para cambiar un bloque de código. 


### DIP: ocultar detalles irrelevantes 

El DIP nos permite dividir el código en componentes separados que pueden cambiar independientemente unos de otros. Luego veremos cómo esto conduce naturalmente a la parte OCP de SOLID. 

La inversión de dependencia (DI) significa que escribimos código para depender de abstracciones, no de detalles. Lo opuesto a esto es tener dos bloques de código, uno que depende de la implementación detallada del otro. Los cambios en un bloque provocarán cambios en otro. Para ver cómo se ve este problema en la práctica, revisemos un contraejemplo. 

El siguiente fragmento de código comienza donde lo dejamos con la clase `Shapes` después de aplicarle SRP:

```
package shapes;
import java.util.ArrayList;
import java.util.List;
public class Shapes {
    private final List<Shape> allShapes = new ArrayList<>();
     
    public void add(Shape s) {
    	allShapes.add(s);
      }
    public void draw(Graphics g) {
       for (Shape s : allShapes) {
        	switch (s.getType()) {
                case "textbox":
                   var t = (TextBox) s;
                   t.draw(g);
                   break;
               case "rectangle":
                  var r = (Rectangle) s;
                  r.draw(g);
        	}
        }
    }
}
```

**Pregunta:** Este código funciona bien para mantener una lista de objetos `Shape` y dibujarlos. ¿Cuál es el problema que sucede aquí?.

El término técnico para que una clase conozca a otra es que existe una dependencia entre ellas. La clase `Shapes` depende de las clases `TextBox` y `Rectangle`. Podemos representar eso visualmente en el siguiente diagrama de clases UML: 

![](https://github.com/kapumota/Actividades/blob/main/TDD-4/Imagenes/UML2.png)


**Pregunta:** ¿Por qué tener estas dependencias hace que trabajar con la clase `Shapes` sea más difícil?. 

### Aplicando DIP

Podemos mejorar el código de formas aplicando DIP.  Agreguemos un método `draw()` a la interfaz `Shape`, de la siguiente manera: 

```
package shapes;
public interface Shape {
    void draw(Graphics g);
}
```

El siguiente paso es hacer que las clases de formas concretas implementen esta interfaz. Tomemos la clase Rectangle como ejemplo. Puedes ver esto aquí:

```
public class Rectangle implements Shape {
   private final int width;
   private final int height;
    public Rectangle(int width, int height){
        this.width = width;
        this.height = height;
        }
     @Override
      public void draw(Graphics g) {
        for (int row=0; row < height; row++) {
        	g.drawHorizontalLine(width);
        }
    }
}
```
 Ahora hemos introducido el concepto OO de polimorfismo en las clases de forma. Esto rompe la dependencia que tiene la clase `Shapes` de conocer las clases `Rectangle` y `TextBox`. Todo lo que ahora depende de la clase `Shapes` es la interfaz `Shape`. Ya no necesita saber el tipo de cada forma. 

Podemos refactorizar la clase `Shapes` para que se vea así:

```
public class Shapes {
    private final List<Shape> all = new ArrayList<>();
    public void add(Shape s) {
       all.add(s);
	}
     public void draw(Graphics graphics) {
    	all.forEach(shape->shape.draw(graphics));
    }
}
```
**Pregunta** ¿Cuáles son lso efectos de esta refactorización?, ¿qué sucede si agregamos un nuevo tipo de forma? 

Un refactor menor mueve el parámetro `Graphics` que pasamos al método `draw()` a un campo, inicializado en el constructor, como se ilustra en el siguiente fragmento de código:

```
public class Shapes {
   private final List<Shape> all = new ArrayList<>();
    private final Graphics graphics;
    public Shapes(Graphics graphics) {
       this.graphics = graphics;
	}
     public void add(Shape s) {
    	all.add(s);
    }
     public void draw() {
    	all.forEach(shape->shape.draw(graphics));
	}
}
```
Esto es DIP en el trabajo. Hemos creado una abstracción en la interfaz `Shape`. La clase `Shapes` es un consumidor de esta abstracción. Las clases que implementan esa interfaz son proveedores. Ambos conjuntos de clases dependen solo de la abstracción; no dependen de los detalles uno dentro del otro. No hay referencias a la clase `Rectangle` en la clase `Shapes`  y no hay referencias a las `Shapes` dentro de la clase `Rectangle`. 

Podemos ver esta inversión de dependencias visualizada en el siguiente diagrama de clases UML:

![](https://github.com/kapumota/Actividades/blob/main/TDD-4/Imagenes/UML3.png)

Hemos invertido el gráfico de dependencia y hemos puesto las flechas al revés. 

DI desacopla completamente las clases entre sí y, como tal, es muy poderoso. 

**DIP**

Hacer que el código dependa de abstracciones y no de detalles. 

Hemos visto cómo DIP es una herramienta importante que podemos usar para simplificar el código. Nos permite escribir código que trata con una interfaz y luego usar ese código con cualquier clase concreta que implemente esa interfaz. Esto plantea una pregunta: ¿podemos escribir una clase que implemente una interfaz pero que no funcione?.

 ### LSP: objetos intercambiables 

Este principio fue provocado por una pregunta en OOP: si podemos extender una clase y usarla en lugar de la clase que extendimos, ¿cómo podemos estar seguros de que la nueva clase no romperá las cosas?

Hay, por supuesto, un lado malo en esto, que LSP pretende evitar. Expliquemos esto mirando un contraejemplo en el código. Supongamos que creamos una nueva clase que implementa la interfaz `Shape`, como esta:

```
public class MaliciousShape implements Shape {
    @Override
    public void draw(Graphics g) {
      try {
        String[] deleteEverything = {"rm", "-Rf", "*"};
        	Runtime.getRuntime().exec(deleteEverything,null);
        	g.drawText("Nada que ver aqui...");
    	} catch (Exception ex) {
        	   // No accion
    	}
      }
}
``` 
**Pregunta:** ¿Notas algo un poco extraño en esa nueva clase?. 


### Definición formal de LSP 

La científica informática estadounidense Barbara Liskov propuso una definición formal: si `p(x)` es una propiedad demostrable sobre objetos `x` de tipo `T`, entonces `p(y)` debería ser cierta para objetos `y` de tipo `S`, donde `S` es un subtipo de `T`. 

### Revisión del uso de LSP

Todas las clases que implementan `Shape` se ajustan a LSP. Esto es claro en la clase `TextBox`, como podemos ver aquí: 

```
public class TextBox implements Shape {
    private final String text;
    
   public TextBox(String text) {
      this.text = text;
       }
     @Override
      public void draw(Graphics g) {
    	g.drawText(text);
	}
}
``` 
**Pregunta:** ¿Qué sucede en el código anterior?.

**LSP** 

Un bloque de código se puede intercambiar con seguridad por otro si puede manejar la gama completa de entradas y proporcionar (al menos) todas las salidas esperadas, sin efectos secundarios no deseados. 

**Pregunta:** Hay algunas violaciones sorprendentes de LSP. Quizás el clásico para el ejemplo de código del ejemplo se trata de agregar una clase `Square`. En código Java, ¿deberíamos hacer que la clase Square amplíe la clase Rectangle? ¿Qué pasa con la clase Rectangle extendiendo Square?.


### OCP: diseño extensible

OCP nos ayuda a escribir código al que podemos agregar nuevas funciones, sin cambiar el código en sí. Esto suena como una imposibilidad al principio, pero fluye naturalmente de DIP combinado con LSP. OCP da como resultado un código que está abierto a la extensión pero cerrado a la modificación. 

Revisemos la refactorización de código que hicimos a la luz de OCP. Comencemos con el código original de la clase `Shapes`, de la siguiente manera: 

```
public class Shapes {
    private final List<Shape> allShapes = new ArrayList<>();
      public void add(Shape s) {
    	allShapes.add(s);
       }
      public void draw(Graphics g) {
    	for (Shape s : allShapes) {
                switch (s.getType()) {
                  case "textbox":
                	var t = (TextBox) s;
                	g.drawText(t.getText());
                	break;
                  case "rectangle":
                	var r = (Rectangle) s;
                	for (int row = 0;
                           row < r.getHeight();
                      	       row++) {
                    	    g.drawLine(0, r.getWidth());
                	}
        	  }
           }
       }
 }
``` 

Agregar un nuevo tipo de forma requiere la modificación del código dentro del método `draw()`. Agregaremos una nueva declaración de caso para respaldar la nueva forma. 

**Pregunta:** ¿La modificación del código existente produce varias desventajas?. Si es afirmativo indica cuales serían.

 Al aplicar DIP y refactorizar el código, terminamos con esto: 

``` 
public class Shapes {
    private final List<Shape> all = new ArrayList<>();
     private final Graphics graphics;
     public Shapes(Graphics graphics) {
        this.graphics = graphics;
	}
         public void add(Shape s) {
    	all.add(s);
        }
         public void draw() {
    	all.forEach(shape->shape.draw(graphics));
	}
}
``` 
Ahora podemos ver que agregar un nuevo tipo de forma no necesita modificar este código. Este es un ejemplo de OCP en el trabajo. La clase `Shapes` está abierta a la definición de nuevos tipos de formas, pero está cerrada a la necesidad de modificaciones cuando se agrega esa nueva forma. Esto también significa que cualquier prueba relacionada con la clase `Shapes` permanecerá sin cambios, ya que no hay diferencia en el comportamiento de esta clase. Esa es una poderosa ventaja.

OCP confía en DI para trabajar. Es más o menos una reafirmación de una consecuencia de aplicar DIP. También nos proporciona una técnica para admitir el comportamiento intercambiable. Podemos usar DIP y OCP para crear sistemas de complementos.

### Agregando un nuevo tipo de forma 

Para ver cómo funciona esto en la práctica, creamos un nuevo tipo de forma, la clase `RightArrow`, de la siguiente manera: 

```
public class RightArrow implements Shape {
  public void draw(Graphics g) {
      g.drawText( "   \" );
      g.drawText( "-----" );
       g.drawText( "   /" );
  }
}
```
La clase RightArrow implementa la interfaz `Shape` y define un método `draw()`. Para demostrar que no es necesario cambiar nada en la clase `Shapes` para usar esto, revisemos un código que usa tanto `Shapes` como la nueva clase, `RightArrow`, de la siguiente manera:

```
package shapes;
public class ShapesDemo {
  public static void main(String[] args) {
       new ShapesExample().run();
	}
      private void run() {
      Graphics console = new ConsoleGraphics();
      var shapes = new Shapes(console);

     shapes.add(new TextBox("Hello!"));
     shapes.add(new Rectangle(32,1));
     shapes.add(new RightArrow());
     shapes.draw();
	}
   }
```

Vemos que la clase `Shapes` se está utilizando de forma completamente normal, sin cambios. De hecho, el único cambio necesario para usar la nueva clase `RightArrow` es crear una instancia de objeto y pasarla al método `add()` de formas. 

**OCP**

Haz que el código esté abierto para nuevos comportamientos, pero cerrado para modificaciones.  

OCP es una excelente manera de administrar la complejidad. 

### ISP: interfaces efectivas 

ISP nos aconseja mantener las interfaces pequeñas y dedicadas a lograr una sola responsabilidad. Por interfaces pequeñas, nos referimos a tener la menor cantidad posible de métodos en una sola interfaz. Todos estos métodos deben relacionarse con algún tema común. Podemos ver que este principio es realmente solo SRP en otra forma. 

Estamos diciendo que una interfaz efectiva debe describir una sola responsabilidad. Debe cubrir una abstracción, no varias. Los métodos en la interfaz deben estar estrechamente relacionados entre sí y también con esa única abstracción. 

Si necesitamos más abstracciones, entonces usamos más interfaces. Mantenemos cada abstracción en su propia interfaz separada, que es de donde proviene el término segregación de interfaz: mantenemos diferentes abstracciones separadas.

El `olor del código` relacionado con esto es una gran interfaz que cubre varios temas diferentes en uno. Podríamos imaginar una interfaz que tenga cientos de métodos en pequeños grupos, algunos relacionados con la administración de archivos, algunos sobre la edición de documentos y otros sobre la impresión de documentos. Tales interfaces rápidamente se vuelven difíciles de trabajar. ISP sugiere que mejoremos esto dividiendo la interfaz en varias más pequeñas. 

Esta división preservaría los grupos de métodos, por lo que es posible que vea interfaces para la administración, edición e impresión de archivos, con métodos relevantes debajo de cada uno. Hemos simplificado la comprensión del código separando estas abstracciones separadas. 

### Revisión del uso de ISP en el código de formas

 El uso más notable de ISP está en la interfaz `Shape`, como se ilustra aquí: 

```
interface Shape {
  void draw(Graphics g);
}
``` 
Esta interfaz claramente tiene un único enfoque. Es una interfaz con un enfoque muy limitado, tanto que solo se necesita especificar un método: `draw()`.  Ese único método es a la vez necesario y suficiente. El otro ejemplo importante está en la interfaz `Graphics`, como se muestra aquí: 

```
public interface Graphics {
	void drawText(String text);
	void drawHorizontalLine(int width);
}
``` 

La interfaz `Graphics` contiene solo métodos relacionados con dibujar primitivas de gráficos en la pantalla. Tiene dos métodos: `drawText` para mostrar una cadena de texto y `drawHorizontalLine` para dibujar una línea en dirección horizontal. Como estos métodos están fuertemente relacionados (conocidos técnicamente por exhibir una alta cohesión) y son pocos en número, el ISP está completo. 

Esta es una abstracción efectiva sobre el subsistema de dibujo de gráficos, adaptada a nuestros propósitos. 

Para completar, podemos implementar esta interfaz de varias maneras. Veamos uno:

```
public class ConsoleGraphics implements Graphics {
    @Override
     public void drawText(String text) {
    	print(text);
     }
    @Override
     public void drawHorizontalLine(int width) {
       var rowText = new StringBuilder();
       
      for (int i = 0; i < width; i++) {
        	rowText.append('X');
    	}
      
     print(rowText.toString());
	
}
    private void print(String text) {
    	System.out.println(text);
     }
}
```
`
Esa implementación también es compatible con LSP: se puede usar donde se espera la interfaz `Graphics`. 

### ISP 

Mantenga las interfaces pequeñas y fuertemente relacionadas con una sola idea. Ahora cubrimos los cinco principios SOLID y mostramos cómo se han aplicado al código. 


