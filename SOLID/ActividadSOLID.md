## SOLID


###  Actividad grupal

Descarga la actividad incompleta desde aquí:
https://github.com/kapumota/Actividades/tree/main/SOLID/SOLID  
Debes construir una carpeta llamada SOLID-CC3S2 en github y subir el proyecto
desarrollado. 
No es una tarea calificada, solo es un repaso de lo realizado en clase.

### Principio de responsabilidad única
Una clase actúa como un contenedor que puede contener muchas cosas, como datos, propiedades o
métodos. Si ingresas demasiados datos o métodos que no están relacionados entre sí, terminarás con
una clase voluminosa que puede crear problemas en el futuro. 

#### Programa Inicial
La demostración 1 tiene una clase Empleado con tres métodos diferentes. Aquí están los detalles:

- displayEmpDetail() muestra el nombre del empleado y su experiencia laboral en años.

- El método generateEmpId() genera una identificación de empleado mediante la concatenación
de cadenas. La lógica es simple: concateno la primera palabra del primer nombre con un número
aleatorio para formar una identificación de empleado. Después de la demostración dentro del
método main() (el código del cliente) creo dos instancias de Empleado y uso estos métodos para
mostrar los detalles relevantes.

- El método checkSeniority() evalúa si un empleado es una persona mayor. Supongamos que si el
empleado tiene más de 5 años de experiencia, es un empleado senior; de lo contrario, es un
empleado junior

#### Demostración sin SRP

- Empleado.java

- Cliente.java

**Pregunta**: Realiza una salida de muestra. Ten en cuenta que la identificación(ID) de un empleado puede
variar en tu caso porque genera un número aleatorio para obtener la identificación (ID) del empleado.

**Pregunta**: ¿Cuál es el problema con este diseño?

#### Mejor programa

En la siguiente demostración, se presentan dos clases más. La clase SeniorityChecker ahora contiene el
método checkSeniority() y la clase GeneradorIDEmpleado contiene el método generateEmpId(...) para
generar la identificación del empleado. Como resultado, en el futuro, si necesitas cambiar la lógica del
programa para determinar el nivel de antigüedad o utilizar un nuevo algoritmo para generar una
identificación del empleado, puedes realizar los cambios en las clases respectivas. Otras clases están
intactas, por lo que no necesitas volver a probar esas clases.

Para mejorar la legibilidad del código y evitar torpezas dentro del método main(), se utiliza el método
estático showEmpDetail(...). Este método llama al método displayEmpDetail() de Empleado, al método
generateEmpId() de GeneradorIDEmpleado y al método checkSeniority() de SeniorityChecker. 

Debes entender que este método no era necesario, pero hace que el código del cliente sea simple y fácilmente
comprensible.

#### Demostración con SRP

- Empleado.java
- GeneradorIdEmpleado.java
- SeniorityChecker.java
- Cliente.java

**Pregunta:** Realiza una demostración completa que sigue a SRP.

**Importante:** ten en cuenta que el SRP no dice que una clase deba tener como máximo un método. Aquí el
énfasis está en la responsabilidad individual. puede haber métodos estrechamente relacionados que
pueden ayudarte a implementar una responsabilidad. 

### Principio abierto/cerrado

El Principio Abierto-Cerrado (OCP) fue acuñado en 1988 por Bertrand Meyer. Dice: Un artefacto de
software debe estar abierto para extensión pero cerrado para modificación. 

En 1988, Bertrand Meyer sugirió el uso de la herencia en este contexto. Wikipedia
(https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle), el menciona su cita de la siguiente
manera:

"Una clase está cerrada, ya que puede compilarse, almacenarse en una biblioteca, establecer una línea de
base y ser utilizada por clases de clientes. Pero también es abierto, ya que cualquier clase nueva puede
usarlo como padre, agregando nuevas funciones. Cuando se define una clase descendiente, no hay
necesidad de cambiar el original o molestar a sus clientes"

Pero la herencia promueve un acoplamiento estrecho. En programación, se gusta eliminar estos
acoplamientos estrechos. Robert C. Martin mejoró la definición y la convirtió en OCP polimórfico. La
nueva propuesta usa clases base abstractas que usan los protocolos en lugar de una superclase para
permitir diferentes implementaciones. Estos protocolos están cerrados a la modificación y proporcionan
otro nivel de abstracción que permite un acoplamiento débil.


#### Programa Inicial

Supongamos que hay un pequeño grupo de estudiantes que toman un examen semestral. Irene, Jessica, Chalo y Claudio John y Kate son los cuatro estudiantes de
este ejemplo. Todos ellos pertenecen a la clase Estudiante. Para crear una instancia de clase de
Estudiante, debes proporcionar un nombre, un número de registro y las calificaciones obtenidas en el
examen. También mencionas si un estudiante pertenece al departamento de Ciencias o al departamento
de Artes. Para simplificar, supongamos que una universidad en particular tiene los siguientes cuatro
departamentos:

- Ciencias de la Computación
- Física
- Historia
- Inglés

Cuando un estudiante opta por informática o física, decimos que opta por la rama de ciencias. De igual
forma, cuando un estudiante pertenece al departamento de Historia o Inglés, es un estudiante de artes.
Comenzamos con dos métodos de instancia en este ejemplo. displayResult() muestra el resultado con
todos los detalles necesarios de un estudiante y el método de evaluateDistinction() evalúa si un
estudiante es elegible para un certificado de distinción. 

Si un estudiante de ciencias obtiene una puntuación superior a 80 en este examen, obtiene el certificado con distinción. 
Pero el criterio para un estudiante de artes se relaja un poco. Un estudiante de artes obtiene la distinción si su puntaje es
superior a 70.
Si entiendes SRP mencionado anteriormente no querrás colocar displayResult() y evaluateDistinction()
en la misma clase.

```
class Student {
public void displayResult() {
   // código
  }
public void evaluateDistinction() {   
   // código
   }
}
`
```

**Pregunta:** ¿Por qué?

#### Demostración sin OCP

- Estudiante.java
- DistinctionDecider.java
- Cliente.java


**Pregunta:** Realiza una salida de muestra.

**Pregunta:** Modifica el método de evaluateDistinction() y agrega otra instrucción if para considerar a
los estudiantes de comercio. ¿Está bien modificar el método evaluateDistinction()de esta manera?


#### Mejor programa

**Pregunta:** Para abordar este problema, puedes escribir un mejor programa. Puedes modificar el código dado de la actividad y utilizar el  principio OCP que sugiere que escribamos segmentos de código (como clases o métodos) que están abiertos para la extensión pero cerrados para la modificación. 

El OCP se puede lograr de diferentes maneras, pero la abstracción es el corazón de este principio. Si puedes diseñar tu aplicación siguiendo el OCP, tu aplicación es flexible y extensible.  No siempre es fácil implementar completamente este principio, pero el cumplimiento parcial de OCP puede generarte un
mayor beneficio. 


#### Demostración con OCP

- Estudiante.java
- ArteEstudiante.java
- CienciaEstudiante.java
- DistinctionDecider.java
- ScienceDistinctionDecider.java
- ArtsDistinctionDecider.java
- Cliente.java

**Pregunta:**. Realiza una salida de muestra de tu respuesta.
**Pregunta:**  ¿Cuáles son las principales ventajas ahora?

### Principio de Sustitución de Liskov

El LSP dice que deberías poder sustituir un tipo padre (o base) con un subtipo. Significa que en un segmento de programa. puedes usar una clase derivadaen lugar de una clase base sin alterar la correción del programa.

#### Programa inicial

Utilizo un portal de pago en línea para pagar una factura. Como soy un usuario registrado, cuando realizo una solicitud de pago en este portal, también muestra mis pagos anteriores. Consideremos un ejemplo simplificado basado en este escenario de la vida real. 

Supongamos que también tienes un portal de pago donde un usuario registrado puede realizar una solicitud de pago. Utiliza el método newPayment() para esto. En este portal, también puede mostrar los detalles del último pago del usuario utilizando un método llamado previousPaymentInfo().

Además, se crea la clase de ayuda PaymentHelper para mostrar todos los pagos anteriores y las nuevas solicitudes de pago de estos usuarios. Utiliza showPreviousPayments() y processNewPayments() para estas actividades.
Estos métodos llaman a previousPaymentInfo() y newPayment() en las respectivas instancias de pago. 

Utilizas una instrucción for mejorada (a menudo se denomina bucle for mejorado) para cumplir estos propósitos.

Dentro del código del cliente, crea dos usuarios y muestra sus solicitudes de pago actuales junto con los pagos anteriores. Todo está bien hasta ahora.

#### Demostración con LSP

- Payment.java
- RegisteredUserPayment.java
- PaymentHelper.java
- Cliente.java

**Pregunta:**. Realiza una salida de muestra.

Este programa parece estar bien. Ahora supongamos que tienes un nuevo requisito que dice que necesitas admitir usuarios invitados en el futuro. Puedes procesar la solicitud de pago de un usuario invitado, pero no se muestra su último detalle de pago. Entonces, creas la siguiente clase que implementa la interfaz de pago:


```
class GuestUserPayment implements Payment {
String name;
   public GuestUserPayment() {
   this.name = "guest"; 
   }
   @Override
   public void previousPaymentInfo(){
      throw new UnsupportedOperationException();
}
@Override
   public void newPayment(){
   System.out.println("Procesando de "+name+ "pago actual
   request.");
   }
}
```
**Pregunta:** Dentro del método main(), ahora crea una instancia de usuario invitado e intenta usar su clase auxiliar de la misma manera. 

#### ¿Cuál es la solución con LSP ?

La primera solución obvia que se te puede ocurrir es introducir una cadena if-else para verificar si la instancia de pago es un pago de usuario invitado (GuestUserPayment) o un pago de usuario registrado (RegisteredUserPayment). ¿Es una buena solución?

**Pregunta:** Elimina el método newPayment() de la interfaz de payment. Coloca este método en otra interfaz llamada NewPayment. 

Ahora tienes dos interfaces con las operaciones específicas. Dado que todos los tipos de usuarios pueden generar una nueva solicitud de pago, las clases concretas de RegisteredUserPayment y GuestUserPayment implementan la interfaz NewPayment y muestran el último detalle de pago solo para los usuarios registrados. Así la clase RegisteredUser implementa la interfaz payment. 
Dado que Payment contiene el método previousPaymentInfo(), tiene sentido elegir un nombre mejor, como PreviousPayment en lugar de Payment.


```
interface PreviousPayment {
   void previousPaymentInfo();
}
interface NewPayment {
   void newPayment();
}
```

Ajusta estos nuevos nombres en la clase auxilial. Debes tener completados los siguientes archivos.

- PreviousPayment.java
- NewPayment.java
- RegisteredUserPayment.java
- GuestUserPayment.java
- PaymentHelper.java
- Cliente.java

**Problema:** ¿cuáles son los cambios clave?

### Principio de segregación de interfaz

Sugiere que no contamines una interfaz con  métodos innecesarios solo para admitir una (o algunas) de las clases de implementación de esta interfaz. La idea es que un cliente no debe depender de un método que no utiliza.

#### Programa inicial

Supongamos que tiene la interfaz Impresora con dos métodos, printDocument() y sendFax(). Hay varios usuarios de esta clase. Para simplificar, consideremos solo dos de ellos: BasicPrinter y AdvancedPrinter.

Una impresora básica puede imprimir documentos. No es compatible con ninguna otra funcionalidad. Por lo tanto, BasicPrinter solo necesita el método printDocument(). Una impresora avanzada puede imprimir documentos y enviar faxes. Entonces, AdvancedPrinter necesita ambos métodos.

En este caso, un cambio en el método sendFax() en AdvancedPrinter puede obligar a la interfaz Printer a cambiar, lo que a su vez obliga al código de BasicPrinter a recompilarse. Esta situación no es deseada y puede causarle problemas potenciales en el futuro.

**Pregunta:** En este caso ISP sugiere que diseñes tu interfaz con los métodos adecuados que un cliente en particular pueda necesitar. ¿Por qué un usuario necesita cambiar una clase base (o una interfaz)?

**Pregunta:** ¿Ayuda escribir código polimórfico como el siguiente?. Explica tu respuesta.

```
Impresora impresora = new ImpresoraAvanzada();
impresora.printDocument();
impresora.sendFax();
impresora = new ImpresoraBasica();
impresora.printDocument();
// impresora.sendFax();
``` 

**Pregunta:** ¿Qué sucede si escribimos algo así en el código dado?


```
List<Impresora> impresoras = new ArrayList<Impresoras>();
impresoras.add(new ImpresoraAvanzada());
impresoras.add(new ImpresoraBasica());
for (Impresora device : impresoras) {
  device.printDocument();
// device.sendFax();
}
```

Realiza una demostración:

- Impresora.java
- ImpresoraBasica.java
- ImpresoraAvanzada.java
- Cliente.java

#### Mejor programa

Busquemos una mejor solución. Entiendes que hay dos actividades diferentes: una es imprimir unos documentos y la otra es enviar un fax. Entonces, en el siguiente ejemplo, crea dos interfaces llamada Impresora y DispositivoFax. Impresora contiene el método printDocument() y FaxDevice contiene el
método SendFax(). La idea es sencilla:

- La clase que desea la función de impresión implementa la interfaz Impresora y la clase que desea la función de fax implementa la interfaz DispositivoFax.
- Si una clase quiere ambas funcionalidades, implementa ambas interfaces.
-
No debes asumir que el ISP dice que una interfaz debe tener solo un método. En este ejemplo, hay dos métodos en la interfaz de Impresora y la clase ImpresoraBasica necesita solo uno de ellos. Es por eso que ves las interfaces segregadas con un solo método.

**Pregunta:** Realiza una salida de muestra

- Impresora.java
- DispositivoFax.java
- ImpresoraBasica.java
- ImpresoraAvanzada.java
- Cliente.java

**Pregunta:**¿Qué sucede si usa un método predeterminado dentro de la interfaz? Por ejemplo, si proporcionas un método de fax predeterminado en una interfaz (o una clase abstracta), ImpresoraBasica debes sobreescribirlo y decir algo similar a lo siguiente:


```
@Override
public void sendFax() {
  throw new UnsupportedOperationException();
}
```

¿Viste el problema potencial con esto! . Pero, ¿qué sucede si usas un método vacío, en lugar de lanzar la excepción?.
