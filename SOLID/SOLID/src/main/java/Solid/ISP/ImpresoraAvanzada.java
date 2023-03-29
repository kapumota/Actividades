package Solid.ISP;

public class ImpresoraAvanzada implements Impresora, DispositivoFax{
    @Override
    public void printDocument() {
        System.out.println("La impresora avanzada imprime un documento.");
    }

    @Override
    public void sendFax() {
        System.out.println("La impresora avanzada envia un fax.");
    }
}
