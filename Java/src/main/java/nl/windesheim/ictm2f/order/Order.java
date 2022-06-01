package nl.windesheim.ictm2f.order;

import java.util.ArrayList;
import java.util.List;

import nl.windesheim.ictm2f.util.GridPoint;

public class Order {
    private String name = "";
    private List<GridPoint> points;

    public Order() {
        this.points = new ArrayList<GridPoint>();
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public List<GridPoint> getPoints() {
        return this.points;
    }

    public void setPoints(List<GridPoint> points) {
        this.points.clear();
        for (GridPoint point : points) {
            this.points.add(point);
        }
    }

    public void addPoint(String pointName, int x, int y) {
        this.points.add(new GridPoint(pointName, x, y));
    }

    public void removePoint(int x, int y) {
        for (GridPoint point : this.points) {
            if (point.getX() == x && point.getY() == y) {
                this.points.remove(point);
                break;
            }
        }
    }
}
