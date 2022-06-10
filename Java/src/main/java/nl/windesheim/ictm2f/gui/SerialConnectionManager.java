package nl.windesheim.ictm2f.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.themes.ITheme;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.Logger;

public class SerialConnectionManager extends JPanel implements Runnable {

    private GUIThemes guiTheme;
    private Dimension screenDimension;
    private JButton reloadSerialPortsButton, connectionButton, settingsButton, estopButton;
    private DefaultComboBoxModel<String> portsData;
    private JComboBox<String> jComboBox;
    private boolean repaintRXTXOnly = false;

    public SerialConnectionManager(Dimension screenDimension, GUIThemes guiTheme) {
        this.screenDimension = new Dimension(screenDimension.getX(), 50);
        this.guiTheme = guiTheme;

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
        this.setLayout(null);

        List<String> availablePorts = Main.getInstance().getSerialManager().getAvailablePorts();
        String[] ports = new String[availablePorts.size()];
        ports = availablePorts.toArray(ports);
        this.portsData = new DefaultComboBoxModel<>(ports);
        this.jComboBox = new JComboBox<>();
        this.jComboBox.setModel(this.portsData);
        this.jComboBox.setBounds(160, 10, 180, 30);

        Icon reloadIcon = new ImageIcon(getClass().getResource("/reload.png"));
        this.reloadSerialPortsButton = new JButton(reloadIcon);
        this.reloadSerialPortsButton.setBounds(350, 10, 30, 30);

        this.reloadSerialPortsButton.setBorderPainted(false);
        this.reloadSerialPortsButton.setFocusPainted(false);
        this.reloadSerialPortsButton.setContentAreaFilled(true);

        this.connectionButton = new JButton("Connect");
        this.connectionButton.setBounds(390, 10, 120, 30);

        this.connectionButton.setBorderPainted(false);
        this.connectionButton.setFocusPainted(false);
        this.connectionButton.setContentAreaFilled(true);

        this.estopButton = new JButton("E-Stop");
        this.estopButton.setBounds(580, 10, 120, 30);

        this.estopButton.setBorderPainted(false);
        this.estopButton.setFocusPainted(false);
        this.estopButton.setContentAreaFilled(true);

        Icon settingsIcon = new ImageIcon(getClass().getResource("/gear.png"));
        this.settingsButton = new JButton(settingsIcon);
        this.settingsButton.setBounds(750, 10, 30, 30);

        this.settingsButton.setBorderPainted(false);
        this.settingsButton.setFocusPainted(false);
        this.settingsButton.setContentAreaFilled(true);

        this.add(this.jComboBox);
        this.add(this.reloadSerialPortsButton);
        this.add(this.connectionButton);
        this.add(this.estopButton);
        this.add(this.settingsButton);

        this.connectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Main.getInstance().getSerialManager().isConnected()) {
                        Main.getInstance().getSerialManager().disconnect();
                    } else {
                        Main.getInstance().getSerialManager()
                                .connect(jComboBox.getItemAt(jComboBox.getSelectedIndex()));
                    }
                    repaint();
                } catch (SerialPortInvalidPortException ex) {
                    Logger.exception(ex);
                }
            }
        });

        this.estopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Main.getInstance().getSerialManager().isConnected()) {
                        Main.getInstance().getSerialManager().write("s");
                        Logger.warning("Stopping robot.");
                        Logger.severe("Reconnect robot to continue.");
                        Main.getInstance().getSerialManager().disconnect();
                    } else {
                        Logger.warning("Robot not connected.");
                    }
                    repaint();
                } catch (SerialPortInvalidPortException ex) {
                    Logger.exception(ex);
                }
            }
        });

        this.settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getGuiManager().getSettings().display();
            }
        });

        this.reloadSerialPortsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadSerialPorts();
            }
        });
    }

    private void reloadSerialPorts() {
        List<String> availablePorts = Main.getInstance().getSerialManager().getAvailablePorts();
        String[] ports = new String[availablePorts.size()];
        ports = availablePorts.toArray(ports);
        this.portsData.removeAllElements();
        for (String port : ports) {
            this.portsData.addElement(port);
        }
    }

    public ITheme getTheme() {
        return this.guiTheme.getTheme();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!repaintRXTXOnly) {
            this.reloadSerialPortsButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
            this.reloadSerialPortsButton.setForeground(this.guiTheme.getTheme().getTextColor());
            this.jComboBox.setBackground(this.guiTheme.getTheme().getBackgroundColor());
            this.jComboBox.setForeground(this.guiTheme.getTheme().getTextColor());
            this.connectionButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
            this.connectionButton.setForeground(this.guiTheme.getTheme().getTextColor());
            this.estopButton.setBackground(this.guiTheme.getTheme().getGridStartPointColor());
            this.estopButton.setForeground(this.guiTheme.getTheme().getTextColor());
            this.settingsButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
            this.settingsButton.setForeground(this.guiTheme.getTheme().getTextColor());

            g.setColor(this.guiTheme.getTheme().getAltBackgroundColor());
            g.fillRect(0, 0, this.screenDimension.getX(), this.screenDimension.getY());
            g.setColor(this.guiTheme.getTheme().getAltTextColor());
            g.drawLine(0, this.screenDimension.getY() - 1, this.screenDimension.getX(),
                    this.screenDimension.getY() - 1);

            g.setColor(this.guiTheme.getTheme().getTextColor());
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Select device:", 15, this.screenDimension.getY() - 20);

            this.connectionButton
                    .setText(Main.getInstance().getSerialManager().isConnected() ? "Disconnect" : "Connect");

            g.setColor(this.guiTheme.getTheme().getTextColor());
            g.setFont(new Font("Arial", Font.BOLD, 13));
            g.drawString("RX", 520, 20);
            g.drawString("TX", 550, 20);
        }

        Color pingRXColor = Main.getInstance().getSerialManager().pingRX()
                ? this.guiTheme.getTheme().getGridFetchedPointColor()
                : this.guiTheme.getTheme().getGridPathColor();
        Color pingTXColor = Main.getInstance().getSerialManager().pingTX()
                ? this.guiTheme.getTheme().getGridFetchedPointColor()
                : this.guiTheme.getTheme().getGridPathColor();
        g.setColor(
                !Main.getInstance().getSerialManager().isConnected() ? this.guiTheme.getTheme().getGridPointColor()
                        : pingRXColor);
        g.fillOval(522, this.screenDimension.getY() - 22, 12, 12);

        g.setColor(
                !Main.getInstance().getSerialManager().isConnected() ? this.guiTheme.getTheme().getGridPointColor()
                        : pingTXColor);
        g.fillOval(552, this.screenDimension.getY() - 22, 12, 12);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Logger.exception(e);
            }
            repaintRXTXOnly = true;
            repaint();
            repaintRXTXOnly = false;
        }
    }
}
