package nl.windesheim.ictm2f.gui;

import javax.swing.JFrame;

import nl.windesheim.ictm2f.util.Dimension;

public class GUIManager extends JFrame {
    
    public GUIManager(Dimension screenDimension) {
        setSize(screenDimension.getX(), screenDimension.getY());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
