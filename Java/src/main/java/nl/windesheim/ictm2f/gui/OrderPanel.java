package nl.windesheim.ictm2f.gui;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.order.Order;
import nl.windesheim.ictm2f.serial.SerialStringBuilder;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderPanel extends JPanel {
    private final int marginTop = 330;
    private final int marginLeft = 0;
    private final int width = 400;
    private final int height = 130;

    private GUIThemes guiTheme;
    private Dimension screenDimension;
    private JButton managerOrderButton, startOrderButton;
    private JLabel orderLabel;

    public OrderPanel(GUIThemes guiTheme) {
        this.guiTheme = guiTheme;
        this.screenDimension = new Dimension(width, height);

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
        this.setBounds(marginLeft, marginTop, this.screenDimension.getX(), this.screenDimension.getY());
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(null);

        this.orderLabel = new JLabel("Order: None", SwingConstants.CENTER);
        this.orderLabel.setBounds(0, 40, width, 20);
        this.orderLabel.setFont(new Font(this.orderLabel.getFont().getName(), Font.PLAIN, 22));

        this.managerOrderButton = new JButton("Manager Order");
        this.managerOrderButton.setBounds(20, height - 60, width / 2 - 40, 40);
        this.managerOrderButton.setBorderPainted(false);
        this.managerOrderButton.setFocusPainted(false);
        this.managerOrderButton.setContentAreaFilled(true);

        this.startOrderButton = new JButton("Start Order");
        this.startOrderButton.setBounds(width / 2 + 20, height - 60, width / 2 - 40, 40);
        this.startOrderButton.setBorderPainted(false);
        this.startOrderButton.setFocusPainted(false);
        this.startOrderButton.setContentAreaFilled(true);

        this.add(this.orderLabel);
        this.add(this.managerOrderButton);
        this.add(this.startOrderButton);

        setupListeners();
    }

    private void setupListeners() {
        this.managerOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getGuiManager().getOrderManagerGUI().display();
            }
        });

        this.startOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order currentOrder = Main.getInstance().getOrderManager().getCurrentOrder();
                if (currentOrder == null) {
                    Logger.warning("No order selected!");

                    return;
                }

                if(!Main.getInstance().getSerialManager().isConnected()){
                    Logger.warning("Cannot send order, please connect serial first");
                    return;
                }

                if(new SerialStringBuilder().send()){
                    Logger.info(String.format("Sending order: \"%s\"", currentOrder.getName()));
                }else{
                    Logger.info(String.format("Failed to send order: \"%s\"", currentOrder.getName()));
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Order currentOrder = Main.getInstance().getOrderManager().getCurrentOrder();

        // Background
        g.setColor(this.guiTheme.getTheme().getGridBackgroundColor()); // getAltBackgroundColor?
        int backgroundOffset = 35;
        g.fillRect(0, backgroundOffset, width, height - backgroundOffset);

        // Title
        g.setColor(this.guiTheme.getTheme().getGridTitleColor());
        g.setFont(new Font("default", Font.PLAIN, 30));
        g.drawString("Orders", (width / 2) - 45, 30); // (width / 2) - 50, marginTop - 10

        this.orderLabel.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.orderLabel.setForeground(this.guiTheme.getTheme().getTextColor());
        this.managerOrderButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.managerOrderButton.setForeground(this.guiTheme.getTheme().getTextColor());
        this.startOrderButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.startOrderButton.setForeground(this.guiTheme.getTheme().getTextColor());

        this.orderLabel.setText("Order: " + (currentOrder == null ? "None" : currentOrder.getName()));
    }
}