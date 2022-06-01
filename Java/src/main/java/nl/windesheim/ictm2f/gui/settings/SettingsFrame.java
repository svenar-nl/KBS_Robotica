package nl.windesheim.ictm2f.gui.settings;

import nl.windesheim.ictm2f.gui.GUIManager;
import nl.windesheim.ictm2f.themes.GUIThemes;

import java.awt.Dimension;

import javax.swing.*;

public class SettingsFrame extends JFrame {
    private GUIThemes currentTheme;
    private Dimension startDimension = new Dimension(380, 500);
    private SettingsPanel panel;
    private GUIManager manager;

    public SettingsFrame(GUIThemes currentTheme, GUIManager manager) {
        this.currentTheme = currentTheme;
        this.manager = manager;
        drawFrame(this.startDimension);
    }

    public GUIManager getManager() {
        return manager;
    }

    public void drawFrame(Dimension startDimension) {
        this.setPreferredSize(startDimension);
        this.setSize((int) startDimension.getWidth(), (int) startDimension.getHeight());
        this.setTitle("Settings");
        this.setLocationRelativeTo(null);
    }

    public void display() {
        this.setVisible(true);

        if (this.panel == null) {
            this.panel = new SettingsPanel(this.getPreferredSize(), this, currentTheme);
            this.add(this.panel);
        }
    }
}
