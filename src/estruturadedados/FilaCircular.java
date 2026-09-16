package estruturadedados;

import java.util.NoSuchElementException;

public class FilaCircular<T> {

    private T[] dados;
    private int inicio;
    private int fim;
    private int tamanho;

    private static final int CAPACIDADE_INICIAL_PADRAO = 4;

    public FilaCircular() {
        this(CAPACIDADE_INICIAL_PADRAO);
    }
    
    @SuppressWarnings("unchecked")
    public FilaCircular(int capacidadeInicial) {
        if (capacidadeInicial <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero.");
        }
        
        this.dados = (T[]) new Object[capacidadeInicial];
        this.inicio = 0;
        this.fim = 0;
        this.tamanho = 0;
    }

    public void enfileirar(T valor) {
        if (tamanho == dados.length) {
            redimensionar(dados.length * 2);
        }

        dados[fim] = valor;
        fim = (fim + 1) % dados.length;
        tamanho++;
    }

    public T desenfileirar() {
        if (estaVazia()) {
            throw new NoSuchElementException("A fila está vazia.");
        }

        T valorRemovido = dados[inicio];
        dados[inicio] = null; 

        inicio = (inicio + 1) % dados.length;
        tamanho--;

        if (tamanho > 0 && tamanho == dados.length / 4 && dados.length / 2 >= CAPACIDADE_INICIAL_PADRAO) {
            redimensionar(dados.length / 2);
        }

        return valorRemovido;
    }

    public T espiar() {
        if (estaVazia()) {
            throw new NoSuchElementException("A fila está vazia.");
        }
        return dados[inicio];
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getCapacidade() {
        return dados.length;
    }

    @SuppressWarnings("unchecked")
    private void redimensionar(int novaCapacidade) {
        T[] novosDados = (T[]) new Object[novaCapacidade];

        for (int i = 0; i < tamanho; i++) {
            novosDados[i] = dados[(inicio + i) % dados.length];
        }

        this.dados = novosDados;
        this.inicio = 0;
        this.fim = tamanho; 
    }

    @Override
    public String toString() {
        if (estaVazia()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamanho; i++) {
            sb.append(dados[(inicio + i) % dados.length]);
            if (i < tamanho - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}