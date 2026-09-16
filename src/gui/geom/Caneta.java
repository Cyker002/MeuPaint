/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.geom;

import gui.geom.Forma;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Caneta extends Forma {

    private List<Point> pontos = new ArrayList<>();

    public void adicionarPonto(int x, int y) {
        pontos.add(new Point(x, y));
    }

    @Override
    public void desenhar(Graphics g) {
        if (pontos.size() < 2) {
            // Se tiver só um clique rápido sem arrasto, desenha um ponto
            if (!pontos.isEmpty()) {
                g.setColor(corContorno);
                Point p = pontos.get(0);
                g.drawLine(p.x, p.y, p.x, p.y);
            }
            return;
        }

        g.setColor(corContorno);
        for (int i = 0; i < pontos.size() - 1; i++) {
            Point p1 = pontos.get(i);
            Point p2 = pontos.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}