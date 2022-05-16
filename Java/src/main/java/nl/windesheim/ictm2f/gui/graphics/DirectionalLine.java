package nl.windesheim.ictm2f.gui.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Polygon;
import java.awt.Color;

import nl.windesheim.ictm2f.util.GridPoint;

public class DirectionalLine {

    private GridPoint point1, point2;

    public DirectionalLine(GridPoint point1, GridPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public void draw(Graphics2D g2d, Color color, int gridSize, int marginLeft, int marginTop) {
        g2d.setColor(color);

        g2d.drawLine(((this.point1.getX() * gridSize) + marginLeft) - gridSize / 2,
                ((this.point1.getY() * gridSize) + marginTop) - gridSize / 2,
                ((this.point2.getX() * gridSize) + marginLeft) - gridSize / 2,
                ((this.point2.getY() * gridSize) + marginTop) - gridSize / 2);

        double l = Math.sqrt(Math.pow(((this.point2.getX() * gridSize) - (this.point1.getX() * gridSize)), 2)
                + Math.pow(((this.point2.getY() * gridSize) - (this.point1.getY() * gridSize)), 2));
        double d = 25;

        double newX = (((this.point2.getX() * gridSize)
                + ((((this.point1.getX() * gridSize) - (this.point2.getX() * gridSize)) / (l) * d)))) + marginLeft
                - gridSize / 2;
        double newY = (((this.point2.getY() * gridSize)
                + ((((this.point1.getY() * gridSize) - (this.point2.getY() * gridSize)) / (l) * d)))) + marginTop
                - gridSize / 2;

        double dx = (this.point2.getX() * gridSize) - (this.point1.getX() * gridSize),
                dy = (this.point2.getY() * gridSize) - (this.point1.getY() * gridSize);
        double angle = (Math.atan2(dy, dx));
        angle = -Math.toDegrees(angle);
        if (angle < 0) {
            angle = 360 + angle;
        }
        angle = -angle;
        angle = Math.toRadians(angle);
        AffineTransform previousTransform = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.translate(newX, newY);
        at.rotate(angle);
        g2d.transform(at);

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(5, 0);
        arrowHead.addPoint(-5, 5);
        arrowHead.addPoint(-2, -0);
        arrowHead.addPoint(-5, -5);
        g2d.fill(arrowHead);
        g2d.drawPolygon(arrowHead);
        g2d.setTransform(previousTransform);
    }

}
