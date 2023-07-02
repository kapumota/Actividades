package com.kapumota.calculador;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController; 
 
@RestController 
class CalculadorController { 
     @Autowired 
     private Calculador calculador; 
 
     @RequestMapping("/sum") 
     String sum(@RequestParam("a") Integer a,  
                @RequestParam("b") Integer b) { 
          return String.valueOf(calculador.sum(a, b)); 
     } 
} 
