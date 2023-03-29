package Solid.LSP;

public class RegisteredUserPayment implements NewPayment,PreviousPayment {
    String name;
    public RegisteredUserPayment(String userName) {
        this.name = userName;
    }
    @Override
    public void previousPaymentInfo(){
        System.out.println("Recuperando de "+ name+" detalles del ultimo pago.");
    }

    @Override
    public void newPayment(){
        System.out.println("Procesando de "+name+" solicitud actual de pago.");
    }
}
