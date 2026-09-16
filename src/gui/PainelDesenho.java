package gui;

import estruturasdedados.Pilha;
import gui.geom.Forma;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class PainelDesenho extends JPanel {

    private GradePixelArt gradePixelArt = new GradePixelArt(16);
    private List<Forma> formas;
    private BufferedImage imagemBuffer;
    private Pilha pilhaDesfazer;
    private Pilha pilhaRefazer;

    public PainelDesenho() {
        formas = new ArrayList<>();
        pilhaDesfazer = new Pilha();
        pilhaRefazer = new Pilha();
    }

    public void salvarEstado() {
        inicializarBufferSeNecessario();
        pilhaDesfazer.push(new EstadoDesenho(imagemBuffer, formas));
        pilhaRefazer.clear();
    }

    public void desfazer() {
        if (!pilhaDesfazer.isEmpty()) {
            pilhaRefazer.push(new EstadoDesenho(imagemBuffer, formas));
            EstadoDesenho estadoAnterior = pilhaDesfazer.pop();
            this.imagemBuffer = estadoAnterior.getImagem();
            this.formas = estadoAnterior.getFormas();
            repaint();
        }
    }

    public void refazer() {
        if (!pilhaRefazer.isEmpty()) {
            pilhaDesfazer.push(new EstadoDesenho(imagemBuffer, formas));
            EstadoDesenho estadoSeguinte = pilhaRefazer.pop();
            this.imagemBuffer = estadoSeguinte.getImagem();
            this.formas = estadoSeguinte.getFormas();
            repaint();
        }
    }

    public void inicializarBufferSeNecessario() {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return;

        if (imagemBuffer == null || imagemBuffer.getWidth() != w || imagemBuffer.getHeight() != h) {
            BufferedImage novaImagem = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = novaImagem.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, w, h);

            if (imagemBuffer != null) {
                g2.drawImage(imagemBuffer, 0, 0, null);
            }
            g2.dispose();
            imagemBuffer = novaImagem;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
       
        super.paintComponent(g);
        inicializarBufferSeNecessario();

        // 1. Desenha o fundo e a imagem do buffer
        if (imagemBuffer != null) {
            g.drawImage(imagemBuffer, 0, 0, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // 2. Desenha as formas geométricas normais
        for (Forma forma : formas) {
            forma.desenhar(g);
        }

        // 3. Desenha a grade e os pixels da Pixel Art por cima de tudo
        if (gradePixelArt != null) {
            gradePixelArt.desenhar(g, getWidth(), getHeight());
        }
    }

    public void adicionarForma(Forma forma) {
        formas.add(forma);
    }

    public BufferedImage getImagemBuffer() {
        return imagemBuffer;
    }

    public List<Forma> getFormas() {
        return formas;
    }
    
    public GradePixelArt getGradePixelArt() {
        return gradePixelArt;
    }

    public void pintarPixel(int mouseX, int mouseY, Color cor) {
        gradePixelArt.pintarPixel(mouseX, mouseY, getWidth(), getHeight(), cor);
        repaint();
    }
}