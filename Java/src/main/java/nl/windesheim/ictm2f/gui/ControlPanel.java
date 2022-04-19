package nl.windesheim.ictm2f.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;

public class ControlPanel extends JPanel {

    private GUIThemes guiTheme;
    private Dimension screenDimension;

    public ControlPanel(Dimension screenDimension, GUIThemes guiTheme) {
        this.screenDimension = new Dimension(screenDimension.getX(), screenDimension.getY() - 50);
        this.guiTheme = guiTheme;

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(this.guiTheme.getTheme().getBackgroundColor());
        g.fillRect(0, 0, screenDimension.getX(), screenDimension.getY());
        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.drawLine(0, this.screenDimension.getY(), screenDimension.getX(), screenDimension.getY());

        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.drawString(getClass().getCanonicalName(), 0, this.screenDimension.getY() - 5); // TODO: for testing only :)
    }
}
