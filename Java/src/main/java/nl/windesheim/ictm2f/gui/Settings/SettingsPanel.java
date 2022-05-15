package nl.windesheim.ictm2f.gui.Settings;

import nl.windesheim.ictm2f.themes.DarkTheme;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private SettingsFrame parentFrame;
    private GUIThemes currentTheme;
    private JButton ThemeToggle;

    public SettingsPanel(Dimension startDimensions, SettingsFrame parentFrame, GUIThemes currentTheme) {
        this.parentFrame = parentFrame;
        this.currentTheme = currentTheme;
        this.setLayout(new GridLayout(5,1));
        draw();
    }

    public void draw() {
        this.setBackground(currentTheme.getTheme().getBackgroundColor());
        this.setForeground(currentTheme.getTheme().getTextColor());
        this.ThemeToggle = new JButton("Toggle Theme");
        this.ThemeToggle.setBackground(currentTheme.getTheme().getTextColor());
        this.ThemeToggle.setForeground(currentTheme.getTheme().getBackgroundColor());
        this.add(this.ThemeToggle);
        this.ThemeToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTheme.getTheme().getTextColor().equals(new DarkTheme().getTextColor())) {
                    parentFrame.getManager().changeTheme(GUIThemes.Theme.LIGHT);
                } else {
                    parentFrame.getManager().changeTheme(GUIThemes.Theme.DARK);
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.ThemeToggle.setBackground(currentTheme.getTheme().getBackgroundColor());
        this.ThemeToggle.setForeground(currentTheme.getTheme().getTextColor());
    }
}
