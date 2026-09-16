package estruturasdedados;

import gui.EstadoDesenho;
import java.util.EmptyStackException;

public class Pilha {

    private static class Node {
        EstadoDesenho value;
        Node previous;

        Node(EstadoDesenho value, Node previous) {
            this.value = value;
            this.previous = previous;
        }
    }

    private Node top;
    private int size;

    public Pilha() {
        this.top = null;
        this.size = 0;
    }

    public void push(EstadoDesenho element) {
        Node newNode = new Node(element, top);
        this.top = newNode;
        this.size++;
    }

    public EstadoDesenho pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        
        EstadoDesenho removedValue = top.value;
        this.top = top.previous; 
        this.size--;
        
        return removedValue;
    }

    public EstadoDesenho peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void clear() {
        this.top = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }
}