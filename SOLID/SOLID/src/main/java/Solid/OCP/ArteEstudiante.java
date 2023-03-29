package Solid.OCP;

public class ArteEstudiante extends Estudiante{
    public ArteEstudiante(String name, String regNumber, double score,String dept) {
        super(name, regNumber, score);
        this.department = dept;
    }

}
