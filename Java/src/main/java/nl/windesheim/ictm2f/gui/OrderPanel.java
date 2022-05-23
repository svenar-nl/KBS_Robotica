package nl.windesheim.ictm2f.gui;

import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.Logger;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    private final int marginTop = 360;
    private final int marginLeft = 0;
    private final int width = 400;
    private final int height = 115;

    private GUIThemes guiTheme;
    private Dimension screenDimension;
    private JButton managerOrderButton, startOrderButton;

    public OrderPanel(GUIThemes guiTheme){
        this.guiTheme = guiTheme;
        this.screenDimension = new Dimension(width, height);

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
        this.setBounds(marginLeft, marginTop, this.screenDimension.getX(), this.screenDimension.getY());
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(null);

        this.managerOrderButton = new JButton("Manager Order");
        this.managerOrderButton.setBounds(20, 55, width / 2 - 40, 40);
        this.managerOrderButton.setBorderPainted(false);
        this.managerOrderButton.setFocusPainted(false);
        this.managerOrderButton.setContentAreaFilled(true);

        this.startOrderButton = new JButton("Start Order");
        this.startOrderButton.setBounds(width / 2 + 20, 55, width / 2 - 40, 40);
        this.startOrderButton.setBorderPainted(false);
        this.startOrderButton.setFocusPainted(false);
        this.startOrderButton.setContentAreaFilled(true);

        this.add(managerOrderButton);
        this.add(startOrderButton);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(this.guiTheme.getTheme().getGridBackgroundColor()); // getAltBackgroundColor?
        int backgroundOffset = 35;
        g.fillRect(0, backgroundOffset, width, height - backgroundOffset);

        // Title
        g.setColor(this.guiTheme.getTheme().getGridTitleColor());
        g.setFont(new Font("default", Font.PLAIN, 30));
        g.drawString("Order", (width / 2) - 45, 25); // (width / 2) - 50, marginTop - 10

        this.managerOrderButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.managerOrderButton.setForeground(this.guiTheme.getTheme().getTextColor());
        this.startOrderButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.startOrderButton.setForeground(this.guiTheme.getTheme().getTextColor());
    }
}