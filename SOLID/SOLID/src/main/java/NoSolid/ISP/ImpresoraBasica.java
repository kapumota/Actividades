package NoSolid.ISP;

class ImpresoraBasica implements Impresora {
    @Override
    public void printDocument() {
        System.out.println("La impresora basica imprime un documento.");
    }

    @Override
    public void sendFax() {
        throw new UnsupportedOperationException();
    }
}
