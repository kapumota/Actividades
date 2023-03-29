package NoSolid.DIP;

public class InterfazUsuario {
    private OracleDatabase oracleDatabase;

    public InterfazUsuario() {
        this.oracleDatabase = new OracleDatabase();
    }

    public void saveEmployeeId(String empId) {
        oracleDatabase.saveEmpIdInDatabase(empId);
    }
}
