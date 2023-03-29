package NoSolid.ISP;

// import java.util.ArrayList;
// import java.util.List;

class Cliente {
    public static void main(String[] args) {
        System.out.println("Demostracion sin ISP");
        Impresora impresora = new ImpresoraAvanzada();
        impresora.printDocument();
        impresora.sendFax();

        impresora = new ImpresoraBasica();
        impresora.printDocument();
        // printer.sendFax();// Lanza un error
    }

}
