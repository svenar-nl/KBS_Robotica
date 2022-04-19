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
}
