## Curso de Desarrollo de Software

En esta actividad debes crear un proyecto llamado `PruebasEstructurales` y configurar el archivo `pom.xml` para utilizar JUnit 5.

Sube el proyecto a un repositorio con el nombre de la actividad de manera individual con todos tus resultados.


### Pruebas estructurales 

En esta actividad, aprendemos cómo utilizar sistemáticamente el código fuente, ver como se está ejerciendo el conjunto de pruebas que derivamos con la ayuda de las especificaciones y qué nos queda probar. 

El uso de la estructura del código fuente para guiar las pruebas también se conoce como pruebas estructurales. Comprender las técnicas de prueba estructural significa comprender los criterios de cobertura. 

#### Cobertura de código

Considera el siguiente requisito para un pequeño programa que cuenta la cantidad de palabras en una cadena que terminan con "r" o "s" : 

Dada una oración, el programa debe contar la cantidad de palabras que terminan con "s" o "r". 
Una palabra termina cuando aparece una no letra. El programa devuelve el número de palabras. 

Un desarrollador implementa este requisito como se muestra en la siguiente lista. Archivo `CountWords.java``.

```
public class CountWords {
   public int count(String str) {
     int words = 0;
          char last = ' ';
   
    for (int i = 0; i < str.length(); i++) {  // 1

        if (!isLetter(str.charAt(i)) &&    // 2
                (last == 's' || last == 'r')) {
               words++;
                }
        last = str.charAt(i);     //3
    }
   if (last == 'r' || last == 's') {
        words; 
          }
       return words;
   }

```

**Pregunta:** explica qué hacen las líneas 1, 2, 3 en el código. 

Ahora, considera a un desarrollador que no sabe mucho sobre técnicas de prueba basadas en especificaciones y escribe las siguientes dos pruebas JUnit para la implementación. 
Archivo `CountWordTest.java`.


```
@Test
void twoWordsEndingWithS() {   // 1
    int words = new CountLetters().count("dogs cats");
         assertThat(words).isEqualTo(2);
}
@Test
void noWordsAtAll() {  // 2
   int words = new CountLetters().count("dog cat");
        assertThat(words).isEqualTo(0);
}
```

**Pregunta:** explica qué hacen las líneas 1, 2 del código. Presenta un informe generado por JaCoCo (www.jacoco.org/jacoco) o otra herramienta de código de tu preferencia en el ide del curso.  


Continuando con el ejemplo, escribimos un caso de prueba que ejercita la partición "palabras que terminan en 'r'" de la siguiente manera. 


```




