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

displayEmpDetail() muestra el nombre del empleado y su experiencia laboral en años.

El método generateEmpId() genera una identificación de empleado mediante la concatenación
de cadenas. La lógica es simple: concateno la primera palabra del primer nombre con un número
aleatorio para formar una identificación de empleado. Después de la demostración dentro del
método main() (el código del cliente) creo dos instancias de Empleado y uso estos métodos para
mostrar los detalles relevantes.

El método checkSeniority() evalúa si un empleado es una persona mayor. Supongamos que si el
empleado tiene más de 5 años de experiencia, es un empleado senior; de lo contrario, es un
empleado junior

#### Demostración 1 - Sin SRP

Empleado.java

Cliente.java

Pregunta 1 Realiza una salida de muestra. Ten en cuenta que la identificación(ID) de un empleado puede
variar en tu caso porque genera un número aleatorio para obtener la identificación (ID) del empleado.

Pregunta 2 ¿Cuál es el problema con este diseño?

#### Mejor programa

En la siguiente demostración, se presentan dos clases más. La clase SeniorityChecker ahora contiene el
método checkSeniority() y la clase GeneradorIDEmpleado contiene el método generateEmpId(...) para
generar la identificación del empleado. Como resultado, en el futuro, si necesitas cambiar la lógica del
programa para determinar el nivel de antigüedad o utilizar un nuevo algoritmo para generar una
identificación del empleado, puedes realizar los cambios en las clases respectivas. Otras clases están
intactas, por lo que no necesito volver a probar esas clases.
Para mejorar la legibilidad del código y evitar torpezas dentro del método main(), se utiliza el método
estático showEmpDetail(...). Este método llama al método displayEmpDetail() de Empleado, al método
generateEmpId() de GeneradorIDEmpleado y al método checkSeniority() de SeniorityChecker. Debes
entender que este método no era necesario, pero hace que el código del cliente sea simple y fácilmente
comprensible.

#### Demostración 2 Con SRP

Pregunta 3: realiza una demostración completa que sigue a SRP.
Empleado.java
GeneradorIdEmpleado.java
SeniorityChecker.java
Cliente.java

Importante : ten en cuenta que el SRP no dice que una clase deba tener como máximo un método. Aquí el
énfasis está en la responsabilidad individual. puede haber métodos estrechamente relacionados que
pueden ayudarte a implementar una responsabilidad. 

### Principio abierto/cerrado
El Principio Abierto-Cerrado (OCP) fue acuñado en 1988 por Bertrand Meyer. Dice: Un artefacto de
software debe estar abierto para extensión pero cerrado para modificación. 

En 1988, Bertrand Meyer sugirió el uso de la herencia en este contexto. Wikipedia
(https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle), el menciona su cita de la siguiente
manera:
“Una clase está cerrada, ya que puede compilarse, almacenarse en una biblioteca, establecer una línea de
base y ser utilizada por clases de clientes. Pero también es abierto, ya que cualquier clase nueva puede
usarlo como padre, agregando nuevas funciones. Cuando se define una clase descendiente, no hay
necesidad de cambiar el original o molestar a sus clientes.
Pero la herencia promueve un acoplamiento estrecho. En programación, nos gusta eliminar estos
acoplamientos estrechos. Robert C. Martin mejoró la definición y la convirtió en OCP polimórfico. La
nueva propuesta usa clases base abstractas que usan los protocolos en lugar de una superclase para
permitir diferentes implementaciones. Estos protocolos están cerrados a la modificación y proporcionan
otro nivel de abstracción que permite un acoplamiento débil. En el curso seguimos la idea de Robert C.
Martin que promueve el OCP polimórfico.

#### Programa Inicial

Supongamos que hay un pequeño grupo de estudiantes que toman un examen semestral. (Para
demostrar esto, elijo un pequeño número de participantes para ayudarlo a concentrarse en el principio,no en detalles innecesarios). Irene, Jessica, Chalo y Claudio John y Kate son los cuatro estudiantes de
este ejemplo. Todos ellos pertenecen a la clase Estudiante. Para crear una instancia de clase de
Estudiante, debes proporcionar un nombre, un número de registro y las calificaciones obtenidas en el
examen. También mencionas si un estudiante pertenece al departamento de Ciencias o al departamento
de Artes. Para simplificar, supongamos que una universidad en particular tiene los siguientes cuatro
departamentos:

Ciencias de la Computación
Física
Historia
Inglés

Cuando un estudiante opta por informática o física, decimos que opta por la rama de ciencias. De igual
forma, cuando un estudiante pertenece al departamento de Historia o Inglés, es un estudiante de artes.
Comenzamos con dos métodos de instancia en este ejemplo. displayResult() muestra el resultado con
todos los detalles necesarios de un estudiante y el método de evaluateDistinction() evalúa si un
estudiante es elegible para un certificado de distinción. Si un estudiante de ciencias obtiene una
puntuación superior a 80 en este examen, obtiene el certificado con distinción. Pero el criterio para un
estudiante de artes se relaja un poco. Un estudiante de artes obtiene la distinción si su puntaje es
superior a 70.
Si entiendes SRP mencionado anteriormente no querrás colocar displayResult() y evaluateDistinction()
en la misma clase . ¿Por qué?

Primero, viola el SRP porque tanto displayResult() como el métodos evaluationDistinction()
están dentro de la clase Student.
Estos dos métodos no están relacionados. En el futuro, la autoridad examinadora puede cambiar
los criterios de distinción. En este caso, tendrás que cambiar el método evaluationDistinction().
¿Resuelve el problema? En la situación actual, la respuesta es sí. Pero una autoridad
universitaria puede cambiar los criterios de distinción de nuevo. ¿Cuántas veces volverás a
probar la clase Student debido a la modificación del método evaluateDistinction()?●
Recuerda que cada vez que modificas el método, cambias el que contiene la clase y también
necesitas modificar los casos de prueba existentes.
Puede ver que cada vez que cambia el criterio de distinción, necesita modificar el método de
evaluateDistinction()() en la clase Estudiante. Entonces, esta clase no sigue el SRP y tampoco está
cerrada por modificación. Una vez que comprenda estos problemas, puedes comenzar con un mejor
diseño que sigue el SRP.
Estas son las principales características del diseño:
●
●
●
●
En el siguiente programa, Student y DistinctionDecider son dos diferentes clases
La clase DistinctionDecider contiene el método evaluateDistinction() en este ejemplo.
Para mostrar los detalles de un estudiante, puedes sobrescribir toString(), en lugar de usar el
método separado displayResult(). Así que, dentro de la clase Estudiante, verás el método
toString() ahora.
Dentro de main(), verá la siguiente línea:
List<Student> enrolledStudents = enrollStudents();
●
El método enrollStudents() crea una lista de estudiantes. Tu usas esta lista para imprimir los
detalles de los estudiantes uno por uno. También usas la misma lista. antes de invocar
evaluateDistinction() para identificar a los estudiantes
Demostración 3 sin OCP
Estudiante.java
DistinctionDecider.java
Cliente.java
Pregunta 4. Realiza una salida de muestra.
Ahora estás siguiendo el SRP. Si en el futuro la autoridad examinadora cambia los criterios de distinción,
no toca la clase de Estudiante. Por lo tanto, esta parte está cerrada por modificación. Esto resuelve una
parte del problema. Ahora piensa en otra posibilidad futura:
● La autoridad del colegio puede introducir una nueva corriente como el comercio y establecer un
nuevo criterio de distinción para esta corriente.
Pregunta 5: Modifica el método de evaluateDistinction() y agrega otra instrucción if para considerar a
los estudiantes de comercio. ¿Está bien modificar el método evaluateDistinction()de esta manera?
Mejor programa
Para abordar este problema, puedes escribir un mejor programa. El siguiente programa muestra un
ejemplo de este tipo. Está escrito siguiendo el principio OCP que sugiere que escribamos segmentos de
código (como clases o métodos) que están abiertos para la extensión pero cerrados para la modificación.
El OCP se puede lograr de diferentes maneras, pero la abstracción es el corazón de este principio. Si
puedes diseñar tu aplicación siguiendo el OCP, tu aplicación es flexible y extensible. No siempre es fácilimplementar completamente este principio, pero el cumplimiento parcial de OCP puede generarle un
mayor beneficio. También observa que comenzó la demostración 3 siguiendo el SRP. Si no sigues el OCP,
puedes terminar con una clase que realiza varias tareas, lo que significa que el SRP no funciona.
Para la situación actual, puedes dejar la clase Estudiante como está. Pero quieres mejorar el código. Tu
comprendes que en el futuro puedes necesitar considerar una corriente diferente, como el comercio.
¿Cómo eliges una corriente? Se basa en el tema elegido por un estudiante, ¿verdad? Entonces, en el
siguiente ejemplo, haces que la clase Student sea abstracta.
ArteEstudiante y CienciaEstudiante son las clases concretas que amplían la clase Subject y se utilizan
para proporcionar la información del "departamento" (en otras palabras, la materia cursada por un
estudiante). El siguiente código muestra una implementación de muestra para tu rápida referencia:
abstract class Estudiante {
String nombre;
String regNumber;
double puntuación;
String departmento;
public Student(String nombre,
String regNumber,
double puntuación) {
this.name = nombre;
this.regNumber = regNumber;
this.score = puntuación;
}
public String toString() {
return ("Nombre: " + número +
"\nReg Nombre: " + regNumber +
"\nDept:" + departamento +
"\nMarks:"

+ puntuación +
"\n*******");
}
}
public class ArteEstudiante extends Student{
public ArteEstudiante(String nombre,
String regNumber,
double puntuación,
String dept) {
super(name, regNumber, puntuación;
this.department = dept;
}
}
// The ScienceStudent class no se muestra
La construcción anterior lo ayuda a inscribir estudiantes de ciencias y estudiantes de artes por separado
dentro del código del cliente de la siguiente manera:
private static List<Estudiante> enrollScienceStudents() {
Estudiante Irene = new CienciaEstudiante("Irene", "R1", 81.5,"Ciencia de la computación.");
Estudiante jessica = new CienciaEstudiante("Jessica", "R2", 72,"Fisica");
List<Estudiante> CienciasEstudiantes = new ArrayList<Estudiante>();CienciasEstudiantes.add(Irene);
CienciasEstudiantes.add(jessica);
return CienciasEstudiantes;
}
private static List<Estudiante> enrollArtsStudents() {
Estudiante chalo = new ArteEstudiante("Chalo", "R3", 71,"Historia");
Estudiante claudio = new ArteEstudiante("Claudio", "R4", 66.5,"Literatura");
List<Estudiante> ArtesEstudiantes = new ArrayList<Estudiante>();
ArtesEstudiantes.add(chalo);
ArtesEstudiantes.add(claudio);
return ArtesEstudiantes;
}
Ahora centrémonos en los cambios más importantes. Debes abordar el método de evaluación para la
distinción de una mejor manera. Por lo tanto, crea la interfaz DistinctionDecider que contiene un
método llamado EvaluationDistinction. Aquí está la interfaz:
interface DistinctionDecider {
void evaluateDistinction(Estudiante estudiante);
}
ArtsDistinctionDecider y ScienceDistinctionDecider implementan esta interfaz y sobreescriben el
método de evaluateDistinction(...) para especificar los criterios de evaluación según sus necesidades. De
esta forma, los criterios de distinción específicos de flujo se envuelven en una unidad independiente.
Aquí está el segmento de código para ti. Los diferentes criterios para cada clase se muestran en negrita.
// ScienceDistinctionDecider.java
public class ScienceDistinctionDecider implements DistinctionDecider {
@Override
public void evaluateDistinction(Estudiante estudiante) {
if (estudiante.score > 80) {
System.out.println(estudiante.regNumber+" ha recibido una distinción en ciencias.");
}
}
}
// ArtsDistinctionDecider.java
public class ArtsDistinctionDecider implements DistinctionDecider{
@Override
public void evaluateDistinction(Estudiante estudiante) {
if (estudiante.score > 70) {
System.out.println(estudiante.regNumber+" ha recibido una distinción en Artes.");
}
}
}
Nota El método de evaluateDistinction(...) acepta un parámetro Estudiante. Significa que ahora también
puede pasar un objeto ArtsStudent o un objeto ScienceStudent a este método.El código restante es fácil y no debería tener ningún problema para comprender la siguiente
demostración ahora.
Demostración 4
Estudiante.java
ArteEstudiante.java
CienciaEstudiante.java
DistinctionDecider.java
ScienceDistinctionDecider.java
ArtsDistinctionDecider.java
Cliente.java
Pregunta 6. Realiza una salida de muestra.
Pregunta 7. ¿Cuáles son las principales ventajas ahora?



