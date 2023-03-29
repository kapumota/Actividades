package NoSolid.SRP;
import java.util.Random;
public class Empleado {
    public String firstName, lastName, empId;
    public double experienceInYears;
    public Empleado(String firstName, String lastName, double experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experienceInYears = experience;
    }
    public void displayEmpDetail(){
        System.out.println("Nombre del empleado: "+lastName+","+firstName);
        System.out.println("Este empleado tiene "+ experienceInYears+" años de experiencia.");
    }

    public String checkSeniority(double experienceInYears){
        return  experienceInYears > 5 ?"senior":"junior";
    }
    public String generateEmpId(String empFirstName){
        int random = new Random().nextInt(1000);
        empId = empFirstName.substring(0,1)+random;
        return empId;
    }

}
