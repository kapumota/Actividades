package ejemplos;

import ejemplos.MailServer;

public class MockMailServer implements MailServer {
     boolean fueLlamado;
     String actualRecipiente;
     String actualTema;
    String actualTexto;
    @Override
    public void sendEmail(String recipiente, String tema,
                      	String texto) {
        fueLlamado= true;
        actualRecipiente = recipiente;
        actualTema = tema;
        actualTexto = texto;
	}
}

