package Solid.DIP;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("Demostracion con DIP");

        // Usando Oracle
        BaseDatos basedatos = new OracleDatabase();
        InterfazUsuario usuario = new InterfazUsuario(basedatos);
        usuario.saveEmployeeId("E001");

        // completa


    }
}
