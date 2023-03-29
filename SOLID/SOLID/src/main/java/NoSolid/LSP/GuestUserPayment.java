package NoSolid.LSP;

public class GuestUserPayment implements Payment{
    String name;
    public GuestUserPayment() {
        this.name = "guest";
    }
    @Override
    public void previousPaymentInfo(){
        throw new UnsupportedOperationException();
    }

    @Override
    public void newPayment(){
        System.out.println("Procesando de " +name+ ", la actual solicitud de pagos.");
    }

}
