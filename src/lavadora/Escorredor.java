package lavadora;

import auxiliar.CircularBuffer;
import pratos.Prato;

public class Escorredor {

    private CircularBuffer filaEscorredor;
    private int capacidade;
    private int count = 0;

    public Escorredor(int qtdeMaxPratos) {
        filaEscorredor = new CircularBuffer(qtdeMaxPratos);
        capacidade = qtdeMaxPratos;
    }

    public synchronized void colocarPrato(Prato prato) throws InterruptedException {
        while (count > capacidade) {
            wait();
        }

        if (!filaEscorredor.cheio()) {
            count++;
            filaEscorredor.add(prato);
            notifyAll();
            System.out.println("ADICIONADO - O escorredor está com " + situacaoEscorredor() + " de lotação");
        } else {
            System.out.println("Prato NÃO adicionado - O escorredor está com " + situacaoEscorredor() + " de lotação ---");
        }
    }

    public synchronized Prato pegarPrato() throws InterruptedException {
        while (count < 0) {
            wait();
        }
        count--;
        notifyAll();
        System.out.println("RETIRADA - O escorredor está com " + situacaoEscorredor() + " de lotação");
        return filaEscorredor.get();
    }

    public synchronized int situacaoEscorredor() {
        return filaEscorredor.getEspacoOcupado();
    }

    public synchronized boolean escorredorCheio() {
        return filaEscorredor.cheio();
    }
}
