/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.geom;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cayke
 */

public class Caneta extends Forma {
    
    private List<Point> pontos;

    public Caneta() {
        this.pontos = new ArrayList<>();
    }

    @Override
    public void setFimY(int fimY) {
        super.setFimY(fimY);
        
        pontos.add(new Point(this.fimX, this.fimY));
    }

    @Override
    public void desenhar(Graphics g) {
        g.setColor(this.corContorno);
        
        int xAnterior = this.iniX;
        int yAnterior = this.iniY;
        
        for (Point p : pontos) {
            g.drawLine(xAnterior, yAnterior, p.x, p.y);
            xAnterior = p.x;
            yAnterior = p.y;
        }
    }
}
