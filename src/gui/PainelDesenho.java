package gui;

import gui.geom.Forma;
import aindaNaoSei.FilaCircular;
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

    public PainelDesenho() {
        formas = new ArrayList<>();
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

        // Desenha as formas que ainda não foram consolidadas
        for (Forma forma : formas) {
            forma.desenhar(g);
        }
    }

    public void adicionarForma(Forma forma) {
        formas.add(forma);
    }

    /**
     * Flood Fill com Fila Circular usando TYPE_INT_RGB (evita problemas com Alpha).
     */
    public void preencherBalde(int startX, int startY, Color novaCor) {
        inicializarBufferSeNecessario();
        if (imagemBuffer == null) return;

        int width = imagemBuffer.getWidth();
        int height = imagemBuffer.getHeight();

        if (startX < 0 || startX >= width || startY < 0 || startY >= height) {
            return;
        }

        // 1. Estampa todas as formas já feitas na imagem de pixels
        // para que suas linhas e bordas sirvam de parede para o balde
        if (!formas.isEmpty()) {
            Graphics2D gBuffer = imagemBuffer.createGraphics();
            for (Forma forma : formas) {
                forma.desenhar(gBuffer);
            }
            gBuffer.dispose();
            formas.clear();
        }

        // Usamos máscara 0x00FFFFFF para ignorar o canal Alfa e comparar só RGB puro
        int corAlvo = imagemBuffer.getRGB(startX, startY) & 0x00FFFFFF;
        int corSubstituta = novaCor.getRGB() & 0x00FFFFFF;

        // Se a cor clicada for idêntica à cor que vai pintar, não faz nada
        if (corAlvo == corSubstituta) {
            return;
        }

        // 2. Fila BFS usando a sua FilaCircular
        FilaCircular<Point> fila = new FilaCircular<>(Point.class);

        // Matriz de visitados para evitar que o mesmo pixel entre múltiplas vezes na fila
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