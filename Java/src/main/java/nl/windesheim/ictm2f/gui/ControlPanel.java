package nl.windesheim.ictm2f.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.GridPoint;

public class ControlPanel extends JPanel {

    static int marginTop = 80;
    static int marginLeft = 0;
    static int gridSize = 60;
    static int textPaddingTop = 43;
    static int textPaddingLeft = 5;
    static int circleSize = 15;

    private GUIThemes guiTheme;
    private Dimension screenDimension;
    private ArrayList<GridPoint> destinationPoints;
    private GridPoint robotLocation;

    public ControlPanel(int screenDimension, GUIThemes guiTheme) {
        this.screenDimension = new Dimension(screenDimension + 2, screenDimension + 31); // +2 for the grid lines
        this.guiTheme = guiTheme;
        this.destinationPoints = new ArrayList<>();
        //this.destinationPoints.add(new GridPoint("1", 3, 3));  // test point
        this.robotLocation = new GridPoint("Robot", 1, 1);

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
    }

    public void mouseClicked(int x, int y){
        // translate mouse coords to cell
        int cellX = ((x - marginLeft) / gridSize) + 1;
        int cellY = ((y - marginTop) / gridSize) + 1;
        String pointName = Integer.toString(destinationPoints.size() + 1);

        // run some checks to see if the point can exist
        if(cellX > 5 || cellY > 5 || cellY < 0 || cellX < 0){
           return;
        }
        for (GridPoint p : destinationPoints){
            if(p.getX() == cellX && p.getY() == cellY){
                //TODO remove if clicked on again
                return;
            }
        }

        // add point to list
        destinationPoints.add(new GridPoint(pointName, cellX, cellY));

        // redraw the grid so the point can be updated
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(this.guiTheme.getTheme().getBackgroundColor());

        // grid background
        g.setColor(Color.white);    // TODO get from theme
        g.fillRect(marginLeft, marginTop, 300, 300);

        // text
        g.setColor(Color.white);    // TODO get from theme
        g.setFont(new Font("default", Font.PLAIN, 20));
        g.drawString("Schap", 120, 70);

        // grid lines
        for (int i = 0; i < 360; i += gridSize){
            g.setColor(Color.gray);    // TODO get from theme
            g.drawLine(i, marginTop, i, 300 + marginTop);
            g.drawLine(0,marginTop + i, 300, marginTop + i);
        }

        // grid numbers
        g.setColor(Color.lightGray);    // TODO get from theme
        g.setFont(new Font("default", Font.PLAIN, 35));
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                String txt = String.format("%s:%s", row + 1, col + 1);
                g.drawString(txt, (row * gridSize) + marginLeft + textPaddingLeft, (col * gridSize) + marginTop + textPaddingTop);
            }
        }

        // draw points
        for (GridPoint p : destinationPoints){
            g.setColor(Color.red);    // TODO get from theme
            g.fillOval((p.getX() * gridSize) + marginLeft - circleSize / 2 - gridSize / 2, (p.getY() * gridSize) + marginTop - circleSize / 2 - gridSize / 2, circleSize, circleSize);
        }

        // robot point
        g.setColor(Color.CYAN);    // TODO get from theme
        g.fillOval((robotLocation.getX() * gridSize) + marginLeft - circleSize / 4 - gridSize / 2, (robotLocation.getY() * gridSize) + marginTop - circleSize / 4 - gridSize / 2, circleSize / 2, circleSize / 2);
    }
}