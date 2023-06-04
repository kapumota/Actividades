package ejemplos;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class LanzamientoDadosTest{
	@Test
	void producesMensage() {
    	var stub = new StubNumeroAleatorio();
    	var lanzado = new LanzamientoDados(stub);
    	var actual = lanzado.asText();
    			assertThat(actual).isEqualTo("Sacastes un 5");
   }
}
