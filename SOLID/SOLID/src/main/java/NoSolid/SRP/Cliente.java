package NoSolid.SRP;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("Demostracion sin SRP");

        Empleado jessica = new Empleado("Jessica", "Abejita", 7.5);
        showEmpDetail(jessica);

        System.out.println("\n*******\n");

        Empleado chalo  = new Empleado("Chalito", "Smart", 3.2);
        showEmpDetail(chalo );

    }

    private static void showEmpDetail(Empleado emp) {
        emp.displayEmpDetail();
        System.out.println("El ID del empleado es: "+ emp.generateEmpId(emp.firstName));
        System.out.println("Este empleado es un" + " empleado " +
                emp.checkSeniority(emp.experienceInYears)
        );
    }
}
