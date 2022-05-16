package nl.windesheim.ictm2f.gui;

import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.Logger;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    static int marginTop = 55;
    static int marginLeft = 0;
    static int width = 400;
    static int screenHeight = 400;
    static int height = 300;

    private String orderNumber = "testOrder";

    private GUIThemes guiTheme;
    private Dimension screenDimension;

    public StatusPanel(GUIThemes guiTheme){
        this.screenDimension = new Dimension(width, screenHeight);

        this.guiTheme = guiTheme;

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(this.guiTheme.getTheme().getBackgroundColor());

        // grid background
        g.setColor(this.guiTheme.getTheme().getGridBackgroundColor());
        g.fillRect(marginLeft, marginTop, width, height);

        // text
        g.setColor(this.guiTheme.getTheme().getGridTitleColor());
        g.setFont(new Font("default", Font.PLAIN, 30));
        g.drawString("Status", (width / 2) - 50, marginTop - 10);

        String fulltext = String.format("Order: %s", orderNumber);
        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.setFont(new Font("default", Font.PLAIN, 20));
        g.drawString(fulltext, (width / 2) - 76, marginTop + 20);
        g.drawString("Logs:", (width / 2) - 30, marginTop + 50);

        // dividers
        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.drawLine(0, marginTop + 25, width, marginTop + 25);
        g.drawLine(0, marginTop + 55, width, marginTop + 55);

        // logs
        g.setFont(new Font("default", Font.PLAIN, 12));

        int logCount = 0;
        for (int i = Logger.getLog().size(); i > 0; i--){
            if(logCount == 20) return;
            String line = Logger.getLog().get(i - 1);

            if (line.toLowerCase().contains("[info]")) {
                g.setColor(this.guiTheme.getTheme().getInfoTextColor());
            } else if (line.toLowerCase().contains("[warn]")) {
                g.setColor(this.guiTheme.getTheme().getWarningTextColor());
            } else if (line.toLowerCase().contains("[severe]")) {
                g.setColor(this.guiTheme.getTheme().getErrorTextColor());
            } else {
                g.setColor(this.guiTheme.getTheme().getTextColor());
            }
            g.drawString(line, 2, (marginTop + 70) + (logCount * 12));

            logCount++;
        }
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
