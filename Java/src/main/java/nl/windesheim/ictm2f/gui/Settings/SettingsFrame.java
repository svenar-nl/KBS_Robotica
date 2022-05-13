package nl.windesheim.ictm2f.gui.Settings;

import nl.windesheim.ictm2f.gui.GUIManager;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.themes.ITheme;
import nl.windesheim.ictm2f.util.Dimension;

import javax.swing.*;

public class SettingsFrame extends JFrame {
    private GUIThemes currentTheme;
    private Dimension startDimension = new Dimension(500,500);
    private SettingsPanel panel;
    private GUIManager manager;


    public SettingsFrame(GUIThemes currentTheme, GUIManager manager) {
        this.currentTheme = currentTheme;
        this.manager = manager;
        drawFrame(this.startDimension);
    }

    public SettingsFrame(GUIThemes currentTheme, Dimension startDimension) {
        this.currentTheme = currentTheme;
        this.startDimension = startDimension;
        drawFrame(this.startDimension);
    }

    public GUIManager getManager() {
        return manager;
    }

    public void drawFrame(Dimension startDimension) {
        this.setSize(startDimension.getX(),startDimension.getY());
        this.setTitle("Settings");
        this.setLocationRelativeTo(null);
        this.panel = new SettingsPanel(this.startDimension,this,currentTheme);
        this.add(this.panel);
    }
}
