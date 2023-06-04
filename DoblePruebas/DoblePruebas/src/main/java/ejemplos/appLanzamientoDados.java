package ejemplos;

public class appLanzamientoDados{
    public static void main(String[] args) {
        new appLanzamientoDados().run();
    }

    private void run() {
        var rnd = new NumerosGeneradosAleatoriamente();
        var lanzado = new LanzamientoDados(rnd);

        System.out.println(lanzado.asText());
    }
}

