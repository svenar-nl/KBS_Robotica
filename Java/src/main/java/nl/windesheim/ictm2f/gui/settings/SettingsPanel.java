package nl.windesheim.ictm2f.gui.settings;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.themes.LightTheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class SettingsPanel extends JPanel {

    private String[] optionsToChoose = { "MySQL", "SQLite" };

    private SettingsFrame parentFrame;
    private GUIThemes currentTheme;
    private JLabel storagelabel, titleMysqlSettings, lblMysqlHost, lblMysqlDB, lblMysqlUser, lblMysqlPass;
    private JButton buttonToggleTheme, buttonSaveSettings;
    private JComboBox<String> selectStorage;
    private JTextField mysqlIP, mysqlPort, mysqlDatabase, mysqlUsername, mysqlPassword;
    private Icon iconThemeLight, iconThemeDark;
    private boolean saveQueued;

    public SettingsPanel(Dimension startDimensions, SettingsFrame parentFrame, GUIThemes currentTheme) {
        this.parentFrame = parentFrame;
        this.currentTheme = currentTheme;
        this.saveQueued = false;

        this.setLayout(null);
        this.setPreferredSize(startDimensions);
        this.setSize(startDimensions);
        this.setMaximumSize(startDimensions);

        iconThemeLight = new ImageIcon(getClass().getResource("/theme_light.png"));
        iconThemeDark = new ImageIcon(getClass().getResource("/theme_dark.png"));
        this.buttonToggleTheme = new JButton(
                this.currentTheme.getTheme() instanceof LightTheme ? iconThemeDark : iconThemeLight);
        this.buttonToggleTheme.setBounds((int) startDimensions.getWidth() - 42, 0, 42, 42);

        this.buttonSaveSettings = new JButton("Save settings");
        this.buttonSaveSettings.setBounds(0,
                (int) startDimensions.getHeight() - 56 - parentFrame.getInsets().top - parentFrame.getInsets().bottom,
                (int) startDimensions.getWidth(), 56);

        this.storagelabel = new JLabel("Select storage method");
        this.storagelabel.setBounds(10, 10, (int) startDimensions.getWidth() - 52, 20);
        this.storagelabel.setForeground(this.currentTheme.getTheme().getTextColor());
        this.selectStorage = new JComboBox<>(optionsToChoose);
        this.selectStorage.setBounds(10, 30, (int) startDimensions.getWidth() / 2, 25);
        this.selectStorage.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.selectStorage.setForeground(this.currentTheme.getTheme().getTextColor());
        this.selectStorage.setSelectedItem(Main.getInstance().getDatabase().getType());

        this.titleMysqlSettings = new JLabel("MySQL connection settings");
        this.lblMysqlHost = new JLabel("Host");
        this.lblMysqlDB = new JLabel("DB");
        this.lblMysqlUser = new JLabel("User");
        this.lblMysqlPass = new JLabel("Pass");
        this.mysqlIP = new JTextField("127.0.0.1");
        this.mysqlPort = new JTextField("3306");
        this.mysqlDatabase = new JTextField("db");
        this.mysqlUsername = new JTextField("username");
        this.mysqlPassword = new JTextField("password");

        this.titleMysqlSettings.setBounds(30, 80, (int) startDimensions.getWidth() - 52, 20);
        this.lblMysqlHost.setBounds(10, 100, (int) startDimensions.getWidth() - 52, 20);
        this.lblMysqlDB.setBounds(10, 130, (int) startDimensions.getWidth() - 52, 20);
        this.lblMysqlUser.setBounds(10, 160, (int) startDimensions.getWidth() - 52, 20);
        this.lblMysqlPass.setBounds(10, 190, (int) startDimensions.getWidth() - 52, 20);
        this.mysqlIP.setBounds(50, 100, (int) startDimensions.getWidth() / 3 - 10, 25);
        this.mysqlPort.setBounds(50 + (int) startDimensions.getWidth() / 3, 100, (int) (startDimensions.getWidth() / 2 - startDimensions.getWidth() / 3), 25);
        this.mysqlDatabase.setBounds(50, 130, (int) startDimensions.getWidth() / 2, 25);
        this.mysqlUsername.setBounds(50, 160, (int) startDimensions.getWidth() / 2, 25);
        this.mysqlPassword.setBounds(50, 190, (int) startDimensions.getWidth() / 2, 25);

        this.add(this.buttonToggleTheme);
        this.add(this.buttonSaveSettings);
        this.add(this.storagelabel);
        this.add(this.selectStorage);
        this.add(this.titleMysqlSettings);
        this.add(this.lblMysqlHost);
        this.add(this.lblMysqlDB);
        this.add(this.lblMysqlUser);
        this.add(this.lblMysqlPass);
        this.add(this.mysqlIP);
        this.add(this.mysqlPort);
        this.add(this.mysqlDatabase);
        this.add(this.mysqlUsername);
        this.add(this.mysqlPassword);

        setupListeners();
    }

    private void setupListeners() {
        this.buttonToggleTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentTheme.getTheme() instanceof LightTheme) {
                    parentFrame.getManager().changeTheme(GUIThemes.Theme.DARK);
                } else {
                    parentFrame.getManager().changeTheme(GUIThemes.Theme.LIGHT);
                }

                saveQueued = true;
                Main.getInstance().getCachedData().set("theme",
                        Main.getInstance().getGuiManager().getTheme().getThemeName());
                buttonToggleTheme
                        .setIcon(currentTheme.getTheme() instanceof LightTheme ? iconThemeDark : iconThemeLight);
                currentTheme = Main.getInstance().getGuiManager().getTheme();
                repaint();
            }
        });

        this.buttonSaveSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getDatabase().save(Main.getInstance().getCachedData().getData());
                Main.getInstance().getConfigManager().save();
                parentFrame.dispose();
            }
        });

        this.selectStorage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getInstance().getConfigManager()
                        .setStorageMethod(selectStorage.getItemAt(selectStorage.getSelectedIndex()));
                saveQueued = true;
            }
        });

        this.mysqlIP.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(((JTextField) e.getSource()).getText());
                saveQueued = true;          
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(currentTheme.getTheme().getBackgroundColor());
        this.setForeground(currentTheme.getTheme().getTextColor());

        this.buttonToggleTheme.setBackground(new Color(0, 0, 0, 0));

        this.buttonSaveSettings.setBackground(this.saveQueued ? currentTheme.getTheme().getGridPathColor()
                : currentTheme.getTheme().getAltBackgroundColor());
        this.buttonSaveSettings.setForeground(this.saveQueued
                ? (this.currentTheme.getTheme() instanceof LightTheme
                        ? this.currentTheme.getTheme().getAltTextColor()
                        : this.currentTheme.getTheme().getAltBackgroundColor())
                : this.currentTheme.getTheme().getAltTextColor());

        this.storagelabel.setForeground(this.currentTheme.getTheme().getTextColor());
        this.selectStorage.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.selectStorage.setForeground(this.currentTheme.getTheme().getTextColor());

        this.titleMysqlSettings.setForeground(this.currentTheme.getTheme().getTextColor());
        this.lblMysqlHost.setForeground(this.currentTheme.getTheme().getTextColor());
        this.lblMysqlDB.setForeground(this.currentTheme.getTheme().getTextColor());
        this.lblMysqlUser.setForeground(this.currentTheme.getTheme().getTextColor());
        this.lblMysqlPass.setForeground(this.currentTheme.getTheme().getTextColor());
        this.mysqlIP.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.mysqlIP.setForeground(this.currentTheme.getTheme().getTextColor());
        this.mysqlPort.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.mysqlPort.setForeground(this.currentTheme.getTheme().getTextColor());
        this.mysqlDatabase.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.mysqlDatabase.setForeground(this.currentTheme.getTheme().getTextColor());
        this.mysqlUsername.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.mysqlUsername.setForeground(this.currentTheme.getTheme().getTextColor());
        this.mysqlPassword.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.mysqlPassword.setForeground(this.currentTheme.getTheme().getTextColor());

        boolean mysqlVisible = selectStorage.getItemAt(selectStorage.getSelectedIndex()).equalsIgnoreCase("MySQL");
        this.titleMysqlSettings.setVisible(mysqlVisible);
        this.lblMysqlHost.setVisible(mysqlVisible);
        this.lblMysqlDB.setVisible(mysqlVisible);
        this.lblMysqlUser.setVisible(mysqlVisible);
        this.lblMysqlPass.setVisible(mysqlVisible);
        this.mysqlIP.setVisible(mysqlVisible);
        this.mysqlPort.setVisible(mysqlVisible);
        this.mysqlDatabase.setVisible(mysqlVisible);
        this.mysqlUsername.setVisible(mysqlVisible);
        this.mysqlPassword.setVisible(mysqlVisible);
    }
}
