package nl.windesheim.ictm2f.gui.order;

import javax.swing.JFrame;

import nl.windesheim.ictm2f.themes.GUIThemes;

import java.awt.Dimension;

public class OrderManagerGUI extends JFrame {

    private Dimension size = new Dimension(380, 500);

    public OrderManagerGUI(GUIThemes guiTheme) {
        this.setPreferredSize(this.size);
        this.setSize((int) this.size.getWidth(), (int) this.size.getHeight());
        this.setTitle("Manage Orders");
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public void display() {
        this.setVisible(true);
    }
}
