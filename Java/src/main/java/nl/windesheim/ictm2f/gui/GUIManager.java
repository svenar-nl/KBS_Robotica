package nl.windesheim.ictm2f.gui;

import javax.swing.*;

import nl.windesheim.ictm2f.gui.Settings.SettingsFrame;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.themes.GUIThemes.Theme;
import nl.windesheim.ictm2f.util.Dimension;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

public class GUIManager extends JFrame implements MouseListener {

    private GUIThemes guiTheme;

    private static SerialConnectionManager scmPanel;
    private static ControlPanel controlPanel;
    private static StatusPanel statusPanel;

    public GUIManager(Dimension screenDimension) {
        this.guiTheme = new GUIThemes();

        scmPanel = new SerialConnectionManager(screenDimension, this.guiTheme);
        controlPanel = new ControlPanel(350, this.guiTheme);
        controlPanel.addMouseListener(this);

        statusPanel = new StatusPanel(this.guiTheme);

        this.setPreferredSize(screenDimension.getDimension());
        this.setSize(screenDimension.getX(), screenDimension.getY());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new FlowLayout());

        this.add(this.scmPanel);
        this.add(this.controlPanel);
        this.add(this.statusPanel);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        controlPanel.mouseClicked(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static SerialConnectionManager getSerialConnectionManager() {
        return scmPanel;
    }

    public static ControlPanel getControlPanel() {
        return controlPanel;
    }
    public static StatusPanel getStatusPanel() {
        return statusPanel;
    }
}
