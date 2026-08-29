/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.geom;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Cayke
 */
public class Poligono extends Forma {

    private int quantidadeLados;
    
    public Poligono( int quantidadeLados ) {
        this.quantidadeLados = quantidadeLados;
    }
    
    public void desenhar( Graphics g ) {
        
        int cat1 = fimX - iniX;
        int cat2 = fimY - iniY;
        double tamanho = Math.hypot( cat1, cat2 );
        double tamanhoAngulo = 360.0 / quantidadeLados;
        double anguloAtual = Math.toDegrees( Math.atan2( cat2, cat1 ) );
        
        int[] xs = new int[quantidadeLados];
        int[] ys = new int[quantidadeLados];
        
        for ( int i = 0; i < quantidadeLados; i++ ) {
            double x = iniX + tamanho * Math.cos( Math.toRadians( anguloAtual ) );
            double y = iniY + tamanho * Math.sin( Math.toRadians( anguloAtual ) );
            xs[i] = (int) x;
            ys[i] = (int) y;
            anguloAtual += tamanhoAngulo;
        }
        
        g.setColor( corPreenchimento );
        g.fillPolygon( xs, ys, quantidadeLados );
        
        g.setColor( corContorno );
        g.drawPolygon( xs, ys, quantidadeLados );
        
    }
    
}
