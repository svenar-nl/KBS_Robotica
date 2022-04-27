package nl.windesheim.ictm2f.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.Logger;

public class SerialConnectionManager extends JPanel {

    private GUIThemes guiTheme;
    private Dimension screenDimension;
    private JButton connectionButton, btn0, btn1, startButton;

    public SerialConnectionManager(Dimension screenDimension, GUIThemes guiTheme) {
        this.screenDimension = new Dimension(screenDimension.getX(), 50);
        this.guiTheme = guiTheme;

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
        this.setLayout(null);

        List<String> availablePorts = Main.getInstance().getSerialManager().getAvailablePorts();
        String[] ports = new String[availablePorts.size()];
        ports = availablePorts.toArray(ports);
        JComboBox<String> jComboBox = new JComboBox<>(ports);
        jComboBox.setBounds(160, 10, 180, 30);
        jComboBox.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        jComboBox.setForeground(this.guiTheme.getTheme().getTextColor());

        this.connectionButton = new JButton("Connect");
        this.connectionButton.setBounds(350, 10, 120, 30);

        this.connectionButton.setBorderPainted(false);
        this.connectionButton.setFocusPainted(false);
        this.connectionButton.setContentAreaFilled(true);
        this.connectionButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.connectionButton.setForeground(this.guiTheme.getTheme().getTextColor());

        this.startButton = new JButton("Start");
        this.startButton.setBounds(650, 10, 120, 30);

        this.startButton.setBorderPainted(false);
        this.startButton.setFocusPainted(false);
        this.startButton.setContentAreaFilled(true);
        this.startButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.startButton.setForeground(this.guiTheme.getTheme().getTextColor());

        this.btn0 = new JButton("0");
        this.btn0.setBounds(480, 10, 60, 30);
        this.btn0.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.btn0.setForeground(this.guiTheme.getTheme().getTextColor());
        this.btn1 = new JButton("1");
        this.btn1.setBounds(550, 10, 60, 30);
        this.btn1.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.btn1.setForeground(this.guiTheme.getTheme().getTextColor());

        this.add(jComboBox);
        this.add(this.connectionButton);
        this.add(this.startButton);
        this.add(this.btn0);
        this.add(this.btn1);

        this.connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Main.getInstance().getSerialManager().isConnected()) {
                        Main.getInstance().getSerialManager().disconnect();
                    } else {
                        Main.getInstance().getSerialManager().connect(jComboBox.getItemAt(jComboBox.getSelectedIndex()));
                    }
                    repaint();
                } catch (SerialPortInvalidPortException ex) {
                    Logger.exception(ex);
                }
            }
        });

        this.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                } catch (SerialPortInvalidPortException ex) {
                    Logger.exception(ex);
                }
            }
        });

        this.btn0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getSerialManager().write("0\r\n");

                if (Main.getInstance().getSerialManager().isConnected()) {
                    try {
                        byte[] readBuffer = new byte[Main.getInstance().getSerialManager().getPort().bytesAvailable()];
                        int numRead = Main.getInstance().getSerialManager().getPort().readBytes(readBuffer,
                                readBuffer.length);
                        if (numRead > 0) {
                            System.out.println(new String(readBuffer, StandardCharsets.UTF_8).substring(0, numRead - 2)
                                    + " (" + numRead + " bytes)");
                        }
                    } catch (NegativeArraySizeException nase) {
                        Main.getInstance().getSerialManager().disconnect();
                        Logger.warning("Serial device disconnected!");
                        repaint();
                    }
                }
            }
        });

        this.btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getSerialManager().write("1\r\n");

                if (Main.getInstance().getSerialManager().isConnected()) {
                    try {
                        byte[] readBuffer = new byte[Main.getInstance().getSerialManager().getPort().bytesAvailable()];
                        int numRead = Main.getInstance().getSerialManager().getPort().readBytes(readBuffer,
                                readBuffer.length);
                        if (numRead > 0) {
                            System.out.println(new String(readBuffer, StandardCharsets.UTF_8).substring(0, numRead - 2)
                                    + " (" + numRead + " bytes)");
                        }
                    } catch (NegativeArraySizeException nase) {
                        Main.getInstance().getSerialManager().disconnect();
                        repaint();
                    }
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(this.guiTheme.getTheme().getAltBackgroundColor());
        g.fillRect(0, 0, this.screenDimension.getX(), this.screenDimension.getY());
        g.setColor(this.guiTheme.getTheme().getAltTextColor());
        g.drawLine(0, this.screenDimension.getY() - 1, this.screenDimension.getX(), this.screenDimension.getY() - 1);

        g.setColor(this.guiTheme.getTheme().getTextColor());

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Select device:", 10, this.screenDimension.getY() - 20);

        this.connectionButton.setText(Main.getInstance().getSerialManager().isConnected() ? "Disconnect" : "Connect");
    }
}
