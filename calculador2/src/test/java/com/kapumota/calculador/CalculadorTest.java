package com.kapumota.calculador;

import org.junit.Test; 
import static org.junit.Assert.assertEquals; 
 
public class CalculadorTest { 
     private Calculador calculador = new Calculador(); 
 
     @Test 
     public void testSum() { 
          assertEquals(5, calculador.sum(2, 3)); 
     } 
} 
