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
public class Retangulo extends Forma {

    public void desenhar( Graphics g ) {
        
        int iniXD = iniX < fimX ? iniX : fimX;
        int iniYD = iniY < fimY ? iniY : fimY;
        int fimXD = iniX > fimX ? iniX : fimX;
        int fimYD = iniY > fimY ? iniY : fimY;
        
        int largura = fimXD - iniXD;
        int altura = fimYD - iniYD;
        
        g.setColor( corPreenchimento );
        g.fillRect( iniXD, iniYD, largura, altura );
        
        g.setColor( corContorno );
        g.drawRect( iniXD, iniYD, largura, altura );
        
    }
    
}
