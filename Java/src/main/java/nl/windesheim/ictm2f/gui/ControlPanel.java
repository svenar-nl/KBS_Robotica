package nl.windesheim.ictm2f.gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.gui.graphics.DirectionalLine;
import nl.windesheim.ictm2f.pathsolver.Solver;
import nl.windesheim.ictm2f.themes.GUIThemes;
import nl.windesheim.ictm2f.util.Dimension;
import nl.windesheim.ictm2f.util.GridPoint;
import nl.windesheim.ictm2f.util.Logger;

public class ControlPanel extends JPanel {

    static int marginTop = 30;
    static int marginLeft = 0;
    static int gridSize = 60;
    static int textPaddingTop = 43;
    static int textPaddingLeft = 5;
    static int circleSize = 25;
    static int pathWidth = 5;
    int[] usedNames = new int[25];

    private GUIThemes guiTheme;
    private Dimension screenDimension;
    private GridPoint robotLocation;

    public ControlPanel(int screenDimension, GUIThemes guiTheme) {
        this.screenDimension = new Dimension(screenDimension + 2, screenDimension + 101); // +2 for the grid lines
        this.guiTheme = guiTheme;
        this.robotLocation = new GridPoint("Robot", 1, 1);

        this.setPreferredSize(this.screenDimension.getDimension());
        this.setSize(this.screenDimension.getX(), this.screenDimension.getY());
    }

    public void mouseClicked(int x, int y) {
        if (Main.getInstance().getOrderManager().getCurrentOrder() == null) {
            Logger.severe("No order selected!");
            return;
        }
        Solver solver = Main.getInstance().getSolver();
        ArrayList<GridPoint> destinationPoints = solver.getPoints();

        // translate mouse coords to cell
        int cellX = ((x - marginLeft) / gridSize) + 1;
        int cellY = ((y - marginTop) / gridSize) + 1;

        // run some checks to see if the point can exist
        if (cellX > 5 || cellY > 5 || cellY < 0 || cellX < 0) {
            return;
        }
        for (GridPoint p : destinationPoints) {
            if (p.getX() == cellX && p.getY() == cellY) {
                solver.removePoint(p);
                solver.clearPath();
                solver.SolveDynamic();

                usedNames[Integer.parseInt(p.getName()) - 1] = 0; // de allocate name

                repaint();
                return;
            }
        }

        // add point to list
        int name = 0;
        for (int i = 0; i < usedNames.length; i++) {
            if (usedNames[i] == 0) {
                name = i + 1;
                usedNames[i] = 1;
                break;
            }
        }
        solver.addPoint(new GridPoint(String.valueOf(name), cellX, cellY));

        // solve
        solver.SolveDynamic();

        // redraw the grid so the point can be updated
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Solver solver = Main.getInstance().getSolver();
        ArrayList<GridPoint> destinationPoints = solver.getPoints();
        ArrayList<Integer> path = solver.getResultPath();

        super.paintComponent(g);

        setBackground(this.guiTheme.getTheme().getBackgroundColor());

        // Grid background
        g.setColor(this.guiTheme.getTheme().getGridBackgroundColor());
        g.fillRect(marginLeft, marginTop, 300, 300);

        // Titles
        g.setColor(this.guiTheme.getTheme().getGridTitleColor());
        g.setFont(new Font("default", Font.PLAIN, 30));
        g.drawString("Rack", 110, marginTop - 5);

        // Grid lines
        for (int i = 0; i < 360; i += gridSize) {
            g.setColor(this.guiTheme.getTheme().getGridLineColor());
            g.drawLine(i, marginTop, i, 300 + marginTop);
            g.drawLine(0, marginTop + i, 300, marginTop + i);
        }

        // Grid numbers
        g.setColor(this.guiTheme.getTheme().getGridTextColor());
        g.setFont(new Font("default", Font.PLAIN, 35));
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                String txt = String.format("%s:%s", row + 1, col + 1);
                g.drawString(txt,
                        (row * gridSize) + marginLeft + textPaddingLeft,
                        (col * gridSize) + marginTop + textPaddingTop);
            }
        }

        // Draw robot path
        if (path != null) {
            Graphics2D g2d = (Graphics2D) g;
            Stroke previousStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(pathWidth));

            for (int i = 0; i < path.size(); i++) {
                if (i == 0)
                    continue;

                GridPoint p1 = destinationPoints.get(path.get(i - 1));
                GridPoint p2 = destinationPoints.get(path.get(i));

                DirectionalLine directionalLine = new DirectionalLine(p1, p2);
                directionalLine.draw(g2d, this.guiTheme.getTheme().getGridPathColor(), gridSize, marginLeft, marginTop);
            }

            // g2d.dispose();
            g2d.setStroke(previousStroke);
        }

        // Draw points
        g.setFont(new Font("default", Font.PLAIN, 15));

        int pointIndex = 0;
        for (GridPoint p : solver.getPoints()) {
            g.setColor(pointIndex > 0 ? this.guiTheme.getTheme().getGridPointColor()
                    : this.guiTheme.getTheme().getGridStartPointColor());
            g.fillOval((p.getX() * gridSize) + marginLeft - circleSize / 2 - gridSize / 2,
                    (p.getY() * gridSize) + marginTop - circleSize / 2 - gridSize / 2, circleSize, circleSize);

            // Point name
            g.setColor(this.guiTheme.getTheme().getGridPointTextColor());
            g.drawString(p.getName(),
                    (p.getX() * gridSize) - gridSize / 2 - g.getFontMetrics().stringWidth(p.getName()) / 2,
                    (p.getY() * gridSize) + circleSize + gridSize / 2 - marginTop - 18);

            pointIndex++;
        }

        // Robot point
        g.setColor(this.guiTheme.getTheme().getGridRobotColor());
        g.fillOval((robotLocation.getX() * gridSize) + marginLeft - circleSize / 4 - gridSize / 2,
                (robotLocation.getY() * gridSize) + marginTop - circleSize / 4 - gridSize / 2,
                circleSize / 2,
                circleSize / 2);

        // Legend
        g.setColor(this.guiTheme.getTheme().getGridRobotColor());
        g.fillRect(0, marginTop + 310, 20, 20);
        g.setColor(this.guiTheme.getTheme().getGridPathColor());
        g.fillRect(0, marginTop + 340, 20, 20);
        g.setColor(this.guiTheme.getTheme().getGridStartPointColor());
        g.fillRect(150, marginTop + 310, 20, 10);
        g.setColor(this.guiTheme.getTheme().getGridPointColor());
        g.fillRect(150, marginTop + 320, 20, 10);
        g.setColor(this.guiTheme.getTheme().getGridFetchedPointColor());
        g.fillRect(150, marginTop + 340, 20, 20);

        g.setColor(this.guiTheme.getTheme().getTextColor());
        g.drawRect(0, marginTop + 310, 20, 20);
        g.drawRect(0, marginTop + 340, 20, 20);
        g.drawRect(150, marginTop + 310, 20, 20);
        g.drawRect(150, marginTop + 340, 20, 20);

        g.setFont(new Font("default", Font.PLAIN, 15));
        g.drawString("Robot location", 25, marginTop + 325);
        g.drawString("Robot path", 25, marginTop + 355);
        g.drawString("Unvisited product", 175, marginTop + 325);
        g.drawString("Fetched product", 175, marginTop + 355);
    }
}