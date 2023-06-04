package ejemplos;

import ejemplos.NumerosAleatorios;

import java.util.random.RandomGenerator;

public class NumerosGeneradosAleatoriamente implements NumerosAleatorios {
    @Override
    public int nextInt(int upperBoundExclusive) {
        var rnd = RandomGenerator.getDefault();
        return rnd.nextInt(upperBoundExclusive);
    }
}
