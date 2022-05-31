package nl.windesheim.ictm2f.gui.order;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import nl.windesheim.ictm2f.themes.GUIThemes;

public class OrderGUIPanel extends JPanel {

    private GUIThemes guiTheme;
    private Dimension screenDimension;

    public OrderGUIPanel(Dimension size, GUIThemes guiTheme) {
        this.screenDimension = size;
        this.guiTheme = guiTheme;
        this.setPreferredSize(size);
        this.setSize(size);
        this.setBounds(0, 0, (int) size.getWidth(), (int) size.getHeight());

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(this.guiTheme.getTheme().getBackgroundColor());
        this.setForeground(this.guiTheme.getTheme().getTextColor());

        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.setFont(new Font("default", Font.PLAIN, 30));
        g.drawString("Orders", (int)(this.screenDimension.getWidth() / 2 - g.getFontMetrics(g.getFont()).stringWidth("Orders") / 2), 30);
    }
}
