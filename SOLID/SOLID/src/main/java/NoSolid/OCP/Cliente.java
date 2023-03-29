package NoSolid.OCP;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("Demostracion sin OCP");
        List<Estudiante> enrolledStudents = enrollStudents();

        // Muestra todos los resultados
        System.out.println("Resultados:");
        for(Estudiante estudiante:enrolledStudents){
            System.out.println(estudiante);
        }


        System.out.println("Distinciones:");
        DistinctionDecider distinctionDecider = new DistinctionDecider();
        // Evalua las distinciones
        for(Estudiante estudiante:enrolledStudents){
            distinctionDecider.evaluateDistinction(estudiante);
        }
    }

    private static List<Estudiante> enrollStudents() {
        Estudiante irene = new Estudiante("Irene", "R1", 81.5, "Ciencia de la Computacion.");
        Estudiante jessica= new Estudiante("Jessica", "R2", 72, "Fisica");
        Estudiante chalo = new Estudiante("Chalo", "R3", 71, "Historia");
        Estudiante claudio = new Estudiante("Claudio", "R4", 66.5, "Literatura");

        List<Estudiante> estudiantes = new ArrayList<Estudiante>();
        estudiantes.add(irene);
        estudiantes.add(jessica);
        estudiantes.add(chalo);
        estudiantes.add(claudio);
        return estudiantes;
    }
}
