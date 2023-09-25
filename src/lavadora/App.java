package lavadora;

public class App {

    private static Escorredor escorredor;
    private static Enxugador enxugador;
    private static Lavador lavador;
    private static Thread threadLavador;
    private static Thread threadEnxugador;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando a lavagem");
        iniciarLavagem();
        System.out.println("Lavagem encerrada");
        encerrarLavagem();
        System.out.println("Lavagem totalmente encerrada");
    }

    static void iniciarLavagem() throws InterruptedException {
        escorredor = new Escorredor(10);
        enxugador = new Enxugador(escorredor);
        lavador = new Lavador(escorredor);

        threadLavador = new Thread(lavador);
        threadEnxugador = new Thread(enxugador);

        lavador.done(false);
        enxugador.done(false);

        threadEnxugador.start();
        threadLavador.start();

        Thread.sleep(10000);
    }

    static void encerrarLavagem() throws InterruptedException {
        lavador.done(true);
        threadLavador.join();

        enxugador.done(true);
        threadEnxugador.join();
    }
}
