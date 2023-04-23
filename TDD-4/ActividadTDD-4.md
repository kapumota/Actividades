## Curso de desarrollo de software

###  Actividad individual

Descarga la actividad incompleta desde aquí: https://github.com/kapumota/Actividades/tree/main/TDD-4/TDD-4

Inicia un repositorio llamado CC-3S2 y dentro una carpeta llamada Actividades. Dentro de esta carpeta abre una carpeta llamada TDD-4 y coloca todas tus respuestas e implementaciones.

En las siguientes secciones, aprenderemos cuáles son estos principios y cómo nos ayudan a escribir código y pruebas bien diseñados. Comenzaremos con SRP, que posiblemente sea el principio fundamental de cualquier estilo de diseño de programas.

### SRP: bloques de construcción simples 

En esta sección, examinaremos el primer principio, conocido como SRP. Usaremos un solo ejemplo de código en todas las secciones. Esto aclara cómo se aplica cada principio a un diseño orientado a objetos (OO). Vamos a ver un ejemplo clásico de diseño OO: dibujar formas. 

El siguiente diagrama es una descripción general del diseño en el lenguaje de modelado unificado (UML), que describe el código presentado en la actividad:

![](https://github.com/kapumota/Actividades/blob/main/TDD-4/Imagenes/UML1.png)

### Contraejemplo: código  que viola SRP 

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

### Aplicación SRP para simplificar el mantenimiento futuro 

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
````

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
