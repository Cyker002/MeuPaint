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
public class Linha extends Forma {

    public void desenhar( Graphics g ) {
        g.setColor( corContorno );
        g.drawLine( iniX, iniY, fimX, fimY );
    }
    
}
