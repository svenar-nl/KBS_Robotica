package nl.windesheim.ictm2f.gui.order;

import javax.swing.JFrame;

import nl.windesheim.ictm2f.themes.GUIThemes;

import java.awt.Dimension;

public class OrderManagerGUI extends JFrame {

    private Dimension size = new Dimension(380, 500);
    private OrderGUIPanel orderGUIPanel;

    public OrderManagerGUI(GUIThemes guiTheme) {
        this.setPreferredSize(this.size);
        this.setSize((int) this.size.getWidth(), (int) this.size.getHeight());
        this.setTitle("Manage Orders");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(false);

        this.orderGUIPanel = new OrderGUIPanel(this.size, guiTheme);
        this.add(this.orderGUIPanel);
    }

    public void display() {
        this.setVisible(true);
    }

    public OrderGUIPanel getOrderGUIPanel() {
        return this.orderGUIPanel;
    }
}