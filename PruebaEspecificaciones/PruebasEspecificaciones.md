Pruebas basadas en especificaciones 

Los requisitos de software son, sin duda, el artefacto más valioso de las pruebas de software. 
En esta nota, exploramos las pruebas basadas en especificaciones. Estas técnicas utilizan los requisitos del programa, como historias de usuarios ágiles o casos de uso de UML, como entrada de prueba.

Los requisitos lo dicen todo 

Implementa: un método que busca subcadenas entre dos etiquetas en una cadena determinada y devuelve todas las subcadenas coincidentes. Llamemos a este método substringsBetween()

Requisitos para el método substringsBetween(): 

Método: substringsBetween()

Busca en una cadena subcadenas delimitadas por una etiqueta de inicio y fin, y devuelve todas las subcadenas coincidentes en una matriz. 

str: la cadena que contiene las subcadenas. Null devuelve null; una cadena vacía devuelve otra cadena vacía. 
open: la cadena que identifica el inicio de la subcadena. Una cadena vacía devuelve null. 
close: la cadena que identifica el final de la subcadena. Una cadena vacía devuelve null. 

Ejemplo: si str = “axcaycazc”, open = “a”, y close = “c”, la salida será una matriz que contiene [“x”, “y”, “z”]. Este es el caso porque el "a< algo>c" La subcadena aparece tres veces en la cadena original: la primera contiene "x" en el medio, la segunda "y" y la última "z". Con estos requisitos en mente, escribes la implementación que se muestra en el listado 1. 

