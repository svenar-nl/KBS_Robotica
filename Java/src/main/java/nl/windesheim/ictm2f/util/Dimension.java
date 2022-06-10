package nl.windesheim.ictm2f.util;

public class Dimension {

    private int x, y;

    public Dimension() {

    }

    public Dimension(int s) {
        this(s, s);
    }

    public Dimension(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public java.awt.Dimension getDimension() {
        return new java.awt.Dimension(this.x, this.y);
    }
}
