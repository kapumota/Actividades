package NoSolid.OCP;
import java.util.Arrays;
import java.util.List;

public class DistinctionDecider {
    List<String> science= Arrays.asList("Ciencia de la Computacion.","Fisica");
    List<String> arts= Arrays.asList("Historia","Literatura");
    public void evaluateDistinction(Estudiante estudiante) {

        if (science.contains(estudiante.department)) {
            if (estudiante.score > 80) {
                System.out.println(estudiante.regNumber+" ha recibido una distincion en ciencias.");
            }
        }

        if (arts.contains(estudiante.department)) {
            if (estudiante.score > 70) {
                System.out.println(estudiante.regNumber+" ha recibido una distincion en artes.");
            }
        }
    }

}
