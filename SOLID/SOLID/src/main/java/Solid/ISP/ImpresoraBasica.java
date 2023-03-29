package Solid.ISP;

public class ImpresoraBasica implements Impresora{
    @Override
    public void printDocument() {
        System.out.println("La impresora basica imprime un documento.");
    }
}
