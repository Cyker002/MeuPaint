package gui;

import java.awt.Color;
import java.awt.Graphics;

public class GradePixelArt {

    private int colunas;
    private int linhas;
    private Color[][] grade;
    private boolean visivel;

    public GradePixelArt(int resolucao) {
        this(resolucao, resolucao);
    }

    public GradePixelArt(int colunas, int linhas) {
        this.visivel = false;
        redimensionar(colunas, linhas);
    }

    public void redimensionar(int novasColunas, int novasLinhas) {
        this.colunas = novasColunas;
        this.linhas = novasLinhas;
        this.grade = new Color[novasLinhas][novasColunas];

        // Inicializa transparente/nulo
        for (int i = 0; i < novasLinhas; i++) {
            for (int j = 0; j < novasColunas; j++) {
                grade[i][j] = null;
            }
        }
    }

    public void pintarPixel(int mouseX, int mouseY, int larguraPainel, int alturaPainel, Color cor) {
        if (larguraPainel <= 0 || alturaPainel <= 0) return;

        double tamanhoPixelX = (double) larguraPainel / colunas;
        double tamanhoPixelY = (double) alturaPainel / linhas;

        int c = (int) (mouseX / tamanhoPixelX);
        int l = (int) (mouseY / tamanhoPixelY);

        if (c >= 0 && c < colunas && l >= 0 && l < linhas) {
            grade[l][c] = cor;
        }
    }

    public void desenhar(Graphics g, int larguraPainel, int alturaPainel) {
        if (!visivel || larguraPainel <= 0 || alturaPainel <= 0) return;

        double tamanhoPixelX = (double) larguraPainel / colunas;
        double tamanhoPixelY = (double) alturaPainel / linhas;

        // 1. Desenha os pixels preenchidos
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (grade[i][j] != null) {
                    g.setColor(grade[i][j]);
                    int px = (int) Math.round(j * tamanhoPixelX);
                    int py = (int) Math.round(i * tamanhoPixelY);
                    int pw = (int) Math.round((j + 1) * tamanhoPixelX) - px;
                    int ph = (int) Math.round((i + 1) * tamanhoPixelY) - py;
                    g.fillRect(px, py, pw, ph);
                }
            }
        }

        // 2. Linhas guias da grade
        g.setColor(new Color(200, 200, 200, 120));
        for (int c = 0; c <= colunas; c++) {
            int x = (int) Math.round(c * tamanhoPixelX);
            g.drawLine(x, 0, x, alturaPainel);
        }
        for (int l = 0; l <= linhas; l++) {
            int y = (int) Math.round(l * tamanhoPixelY);
            g.drawLine(0, y, larguraPainel, y);
        }
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public int getColunas() {
        return colunas;
    }

    public int getLinhas() {
        return linhas;
    }
}