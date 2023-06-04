package ejemplos;

public class StubNumeroAleatorio implements NumerosAleatorios {
    @Override
    public int nextInt(int upperBoundExclusive) {
        return 4;  
    }
}
