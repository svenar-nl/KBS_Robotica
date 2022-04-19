package nl.windesheim.ictm2f.gui;

import javax.swing.JFrame;

import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.themes.GUIThemes.Theme;
import nl.windesheim.ictm2f.util.Dimension;

public class GUIManager extends JFrame {

    private GUIThemes guiTheme;

    private SerialConnectionManager scmPanel;
    private ControlPanel controlPanel;

    public GUIManager(Dimension screenDimension) {
        this.guiTheme = new GUIThemes();

        this.scmPanel = new SerialConnectionManager(screenDimension, this.guiTheme);
        this.controlPanel = new ControlPanel(screenDimension, this.guiTheme);

        this.setPreferredSize(screenDimension.getDimension());
        this.setSize(screenDimension.getX(), screenDimension.getY());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(null);

        this.add(this.scmPanel);
        this.add(this.controlPanel);
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
        super.repaint();
        this.getContentPane().setBackground(this.guiTheme.getTheme().getBackgroundColor());
    }
}
