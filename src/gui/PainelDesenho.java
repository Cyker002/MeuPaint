/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import gui.geom.Forma;
import gui.geom.Linha;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class PainelDesenho extends JPanel {
    
    private List<Forma> formas;
    
    public PainelDesenho() {
        formas = new ArrayList<>();
    }

    @Override
    protected void paintComponent( Graphics g ) {
        
        super.paintComponent( g );
        
        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, getWidth(), getHeight() );
        
        for ( Forma forma : formas ) {
            forma.desenhar( g );
        }
        
    }
    
    public void adicionarForma( Forma forma ) {
        formas.add( forma );
    }
    
}
