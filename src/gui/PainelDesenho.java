package gui;

import estruturadedados.FilaCircular;
import estruturasdedados.Pilha;
import gui.geom.Forma;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class PainelDesenho extends JPanel {

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

   

    private void inicializarBufferSeNecessario() {
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
    }

    public void adicionarForma(Forma forma) {
        formas.add(forma);
    }

    public void preencherBalde(int startX, int startY, Color novaCor) {
        inicializarBufferSeNecessario();
        if (imagemBuffer == null) return;

        int width = imagemBuffer.getWidth();
        int height = imagemBuffer.getHeight();

        if (startX < 0 || startX >= width || startY < 0 || startY >= height) {
            return;
        }

        // SALVA O ESTADO ANTES DE PINTAR COM O BALDE
        salvarEstado();

        if (!formas.isEmpty()) {
            Graphics2D gBuffer = imagemBuffer.createGraphics();
            for (Forma forma : formas) {
                forma.desenhar(gBuffer);
            }
            gBuffer.dispose();
            formas.clear();
        }

        int corAlvo = imagemBuffer.getRGB(startX, startY) & 0x00FFFFFF;
        int corSubstituta = novaCor.getRGB() & 0x00FFFFFF;

        if (corAlvo == corSubstituta) {
            return;
        }

        FilaCircular<Point> fila = new FilaCircular<>();
        boolean[][] visitado = new boolean[width][height];

        fila.enfileirar(new Point(startX, startY));
        visitado[startX][startY] = true;
        imagemBuffer.setRGB(startX, startY, novaCor.getRGB());

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!fila.estaVazia()) {
            Point p = fila.desenfileirar();

            for (int i = 0; i < 4; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    if (!visitado[nx][ny] && (imagemBuffer.getRGB(nx, ny) & 0x00FFFFFF) == corAlvo) {
                        visitado[nx][ny] = true;
                        imagemBuffer.setRGB(nx, ny, novaCor.getRGB());
                        fila.enfileirar(new Point(nx, ny));
                    }
                }
            }
        }
        repaint();
    }
}