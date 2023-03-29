package Solid.OCP;

public class CienciaEstudiante extends Estudiante{
    public CienciaEstudiante(String name, String regNumber, double score,String dept) {
        super(name, regNumber, score);
        this.department = dept;
    }
}
