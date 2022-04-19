package nl.windesheim.ictm2f.gui;

import javax.swing.JFrame;

import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.themes.GUIThemes.Theme;
import nl.windesheim.ictm2f.util.Dimension;

public class GUIManager extends JFrame {

    private GUIThemes guiTheme;
    
    public GUIManager(Dimension screenDimension) {
        this.guiTheme = new GUIThemes();
        
        this.setSize(screenDimension.getX(), screenDimension.getY());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void changeTheme(Theme theme) {
        this.guiTheme.setTheme(theme);
        this.repaint();
    }

    public void display() {
        this.setVisible(true);
    }

    @Override
    public void repaint() {
        this.getContentPane().setBackground(this.guiTheme.getTheme().getBackgroundColor());
    }
}
