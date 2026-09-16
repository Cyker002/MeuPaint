/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.ferramentas;

import estruturadedados.FilaCircular;
import gui.PainelDesenho;
import gui.geom.Forma;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
/**
 *
 * @author raul
 */
public class BaldeDeTinta {
    

    public static void preencher(PainelDesenho painel, int startX, int startY, Color novaCor) {
        painel.inicializarBufferSeNecessario();
        BufferedImage imagemBuffer = painel.getImagemBuffer();

        if (imagemBuffer == null) {
            return;
        }

        int width = imagemBuffer.getWidth();
        int height = imagemBuffer.getHeight();

        if (startX < 0 || startX >= width || startY < 0 || startY >= height) {
            return;
        }

        // Salva estado para possibilitar desfazer/refazer antes da alteração de pixels
        painel.salvarEstado();

        // Estampa as formas vetoriais no buffer para servirem de barreira
        List<Forma> formas = painel.getFormas();
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

        painel.repaint();
    }
}