/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aindaNaoSei;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 *
 * @author raul
 */
public class FilaCircular<T> {



    private T[] dados;
    private int inicio;
    private int fim;
    private int tamanho;
    private final Class<T> tipoElemento; // Guarda o tipo de tempo de execução

    private static final int CAPACIDADE_INICIAL_PADRAO = 4;

    public FilaCircular(Class<T> tipoElemento) {
        this(tipoElemento, CAPACIDADE_INICIAL_PADRAO);
    }
    
    // Dentro da sua classe FilaCircular:
    public Class<?> getTipoDoArrayInterno() {
        return dados.getClass();
    }

    @SuppressWarnings("unchecked")
    public FilaCircular(Class<T> tipoElemento, int capacidadeInicial) {
        if (tipoElemento == null) {
            throw new IllegalArgumentException("O tipo da classe não pode ser nulo.");
        }
        if (capacidadeInicial <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero.");
        }
        this.tipoElemento = tipoElemento;
        // Cria fisicamente um array do tipo T em tempo de execução
        this.dados = (T[]) Array.newInstance(tipoElemento, capacidadeInicial);
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
        // Aloca o novo array com o tipo exato T[]
        T[] novosDados = (T[]) Array.newInstance(tipoElemento, novaCapacidade);

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

public static void main(String[] args) {
        // Passa Point.class para o construtor saber o Type exato
        FilaCircular<Point> fila = new FilaCircular<>(Point.class);

        fila.enfileirar(new Point(10, 20));
        fila.enfileirar(new Point(30, 40));

        // Internamente o array é de fato um Point[], e não um Object[] disfarçado
        System.out.println("Tipo real do array: " + fila.getTipoDoArrayInterno().getTypeName());
        System.out.println("Desenfileirado: " + fila.desenfileirar());
    }
}
