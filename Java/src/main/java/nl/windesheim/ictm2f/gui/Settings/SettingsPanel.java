package nl.windesheim.ictm2f.gui.Settings;

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
    private JLabel storagelabel;
    private JButton buttonToggleTheme, buttonSaveSettings;
    private JComboBox<String> selectStorage;
    private Icon iconThemeLight, iconThemeDark;

    public SettingsPanel(Dimension startDimensions, SettingsFrame parentFrame, GUIThemes currentTheme) {
        this.parentFrame = parentFrame;
        this.currentTheme = currentTheme;
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

        this.add(this.buttonToggleTheme);
        this.add(this.buttonSaveSettings);
        this.add(this.storagelabel);
        this.add(this.selectStorage);

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

                Main.getInstance().getConfigManager().setStorageMethod(selectStorage.getItemAt(selectStorage.getSelectedIndex()));
                Main.getInstance().getConfigManager().save();
                parentFrame.dispose();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(currentTheme.getTheme().getBackgroundColor());
        this.setForeground(currentTheme.getTheme().getTextColor());

        this.buttonToggleTheme.setBackground(new Color(0, 0, 0, 0));

        this.buttonSaveSettings.setBackground(currentTheme.getTheme().getAltBackgroundColor());
        this.buttonSaveSettings.setForeground(currentTheme.getTheme().getAltTextColor());

        this.storagelabel.setForeground(this.currentTheme.getTheme().getTextColor());
        this.selectStorage.setBackground(this.currentTheme.getTheme().getBackgroundColor());
        this.selectStorage.setForeground(this.currentTheme.getTheme().getTextColor());
    }
}
