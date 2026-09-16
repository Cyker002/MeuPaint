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
public abstract class Forma {
    
    protected int iniX;
    protected int iniY;
    protected int fimX;
    protected int fimY;
    protected Color corContorno;
    protected Color corPreenchimento;

    public abstract void desenhar( Graphics g );
    
    public int getIniX() {
        return iniX;
    }

    public void setIniX( int iniX ) {
        this.iniX = iniX;
    }

    public int getIniY() {
        return iniY;
    }

    public void setIniY( int iniY ) {
        this.iniY = iniY;
    }

    public int getFimX() {
        return fimX;
    }

    public void setFimX( int fimX ) {
        this.fimX = fimX;
    }

    public int getFimY() {
        return fimY;
    }

    public void setFimY( int fimY ) {
        this.fimY = fimY;
    }

    public Color getCorContorno() {
        return corContorno;
    }

    public void setCorContorno( Color corContorno ) {
        this.corContorno = corContorno;
    }

    public Color getCorPreenchimento() {
        return corPreenchimento;
    }

    public void setCorPreenchimento( Color corPreenchimento ) {
        this.corPreenchimento = corPreenchimento;
    }
    
}
