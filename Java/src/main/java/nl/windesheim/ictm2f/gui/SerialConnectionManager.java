package nl.windesheim.ictm2f.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

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

public class SerialConnectionManager extends JPanel {

    private GUIThemes guiTheme;
    private Dimension screenDimension;
    private JButton connectionButton, startButton, settingsButton;

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

        this.startButton = new JButton("Run order");
        this.startButton.setBounds(540, 10, 120, 30);

        this.startButton.setBorderPainted(false);
        this.startButton.setFocusPainted(false);
        this.startButton.setContentAreaFilled(true);
        this.startButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.startButton.setForeground(this.guiTheme.getTheme().getTextColor());

        Icon settingsIcon = new ImageIcon(getClass().getResource("/gear.png"));
        this.settingsButton = new JButton(settingsIcon);
        this.settingsButton.setBounds(750, 10, 30, 30);

        this.settingsButton.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.settingsButton.setForeground(this.guiTheme.getTheme().getTextColor());
        this.settingsButton.setBorderPainted(false);
        this.settingsButton.setFocusPainted(false);
        this.settingsButton.setContentAreaFilled(true);

        this.add(jComboBox);
        this.add(this.connectionButton);
        this.add(this.startButton);
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

        this.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.getInstance().getSolver().SolveDynamic();
                    Main.getInstance().getGuiManager().getControlPanel().repaint(); // repaint panel
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
    }

    public ITheme getTheme() {
        return this.guiTheme.getTheme();
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
        g.drawString("Select device:", 15, this.screenDimension.getY() - 20);

        this.connectionButton.setText(Main.getInstance().getSerialManager().isConnected() ? "Disconnect" : "Connect");

        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("RX", 480, 20);
        g.drawString("TX", 510, 20);

        g.setColor(!Main.getInstance().getSerialManager().isConnected() ? this.guiTheme.getTheme().getGridPointColor()
                : (!Main.getInstance().getSerialManager().pingRX() ? this.guiTheme.getTheme().getGridPathColor()
                        : this.guiTheme.getTheme().getGridFetchedPointColor()));
        g.fillOval(482, this.screenDimension.getY() - 22, 12, 12);
        g.setColor(!Main.getInstance().getSerialManager().isConnected() ? this.guiTheme.getTheme().getGridPointColor()
                : (!Main.getInstance().getSerialManager().pingTX() ? this.guiTheme.getTheme().getGridPathColor()
                        : this.guiTheme.getTheme().getGridFetchedPointColor()));
        g.fillOval(512, this.screenDimension.getY() - 22, 12, 12);
    }
}
