package nl.windesheim.ictm2f.gui.Settings;

import nl.windesheim.ictm2f.themes.ITheme;
import nl.windesheim.ictm2f.util.Dimension;

import javax.swing.*;

public class SettingsFrame extends JFrame {
    private ITheme currentTheme;
    private Dimension startDimension = new Dimension(500,500);

    public SettingsFrame(ITheme currentTheme) {
        this.currentTheme = currentTheme;
        drawFrame(this.startDimension);
    }

    public SettingsFrame(ITheme currentTheme, Dimension startDimension) {
        this.currentTheme = currentTheme;
        this.startDimension = startDimension;
        drawFrame(this.startDimension);
    }

    public void drawFrame(Dimension startDimension) {
        this.setSize(startDimension.getX(),startDimension.getY());
        this.setTitle("Settings");
        this.setVisible(true);
    }
}
