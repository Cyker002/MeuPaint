package gui;

import estruturadedados.Pilha;
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

        if (imagemBuffer != null) {
            g.drawImage(imagemBuffer, 0, 0, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        for (Forma forma : formas) {
            forma.desenhar(g);
        }

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
    
    public void apagar(int x1, int y1, int x2, int y2) {
        inicializarBufferSeNecessario();
        if (imagemBuffer == null) return;

        if (!formas.isEmpty()) {
            Graphics2D gBuffer = imagemBuffer.createGraphics();
            for (Forma forma : formas) {
                forma.desenhar(gBuffer);
            }
            gBuffer.dispose();
            formas.clear();
        }

        Graphics2D g2d = imagemBuffer.createGraphics();
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE); 

        int tamanhoBorracha = 12;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            g2d.fillRect(x1 - (tamanhoBorracha / 2), y1 - (tamanhoBorracha / 2), tamanhoBorracha, tamanhoBorracha);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        
        g2d.dispose();
        repaint(); 
    }
}