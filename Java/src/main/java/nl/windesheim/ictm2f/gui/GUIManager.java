package nl.windesheim.ictm2f.gui;

import javax.swing.*;

import nl.windesheim.ictm2f.gui.order.OrderManagerGUI;
import nl.windesheim.ictm2f.gui.settings.SettingsFrame;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.themes.GUIThemes.Theme;
import nl.windesheim.ictm2f.util.Dimension;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUIManager extends JFrame implements MouseListener {

    private GUIThemes guiTheme;

    private SerialConnectionManager scmPanel;
    private ControlPanel controlPanel;
    private StatusPanel statusPanel;
    private OrderManagerGUI orderManagerGui;
    private SettingsFrame settings;
    private boolean mouseDown;
    private Thread scmThread;

    public GUIManager(Dimension screenDimension) {
        this.guiTheme = new GUIThemes();

        this.scmPanel = new SerialConnectionManager(screenDimension, this.guiTheme);
        this.scmThread = new Thread(this.scmPanel);
        this.scmThread.start();

        this.controlPanel = new ControlPanel(350, this.guiTheme);
        this.controlPanel.addMouseListener(this);

        this.statusPanel = new StatusPanel(this.guiTheme);
        this.settings = new SettingsFrame(this.guiTheme, this);

        this.orderManagerGui = new OrderManagerGUI(this.guiTheme);

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
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (mouseDown) {
            return;
        }
        mouseDown = true;
        controlPanel.mouseClicked(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public SerialConnectionManager getSerialConnectionManager() {
        return this.scmPanel;
    }

    public ControlPanel getControlPanel() {
        return this.controlPanel;
    }

    public StatusPanel getStatusPanel() {
        return this.statusPanel;
    }

    public SettingsFrame getSettings() {
        return this.settings;
    }

    public OrderManagerGUI getOrderManagerGUI() {
        return this.orderManagerGui;
    }

    public GUIThemes getTheme() {
        return this.guiTheme;
    }
}
