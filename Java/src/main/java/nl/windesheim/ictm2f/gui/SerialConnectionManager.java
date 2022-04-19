package nl.windesheim.ictm2f.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;

public class SerialConnectionManager extends JPanel {

    private GUIThemes guiTheme;
    private Dimension screenDimension;

    public SerialConnectionManager(Dimension screenDimension, GUIThemes guiTheme) {
        this.screenDimension = new Dimension(screenDimension.getX(), 50);
        this.guiTheme = guiTheme;

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(this.guiTheme.getTheme().getAltBackgroundColor());
        g.fillRect(0, 0, this.screenDimension.getX(), this.screenDimension.getY());
        g.setColor(this.guiTheme.getTheme().getAltTextColor());
        g.drawLine(0, this.screenDimension.getY() - 1, this.screenDimension.getX(), this.screenDimension.getY() - 1);

        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.drawString(getClass().getCanonicalName(), 0, this.screenDimension.getY() - 5); // TODO: for testing only :)
    }
}
