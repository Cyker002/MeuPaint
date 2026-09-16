package gui;

import gui.geom.Forma;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class EstadoDesenho {
    private BufferedImage imagem;
    private List<Forma> formas;

    public EstadoDesenho(BufferedImage imagem, List<Forma> formas) {
        if (imagem != null) {
            this.imagem = new BufferedImage(imagem.getWidth(), imagem.getHeight(), imagem.getType());
            Graphics g = this.imagem.getGraphics();
            g.drawImage(imagem, 0, 0, null);
            g.dispose();
        }
        
        this.formas = new ArrayList<>(formas);
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    public List<Forma> getFormas() {
        return formas;
    }
}