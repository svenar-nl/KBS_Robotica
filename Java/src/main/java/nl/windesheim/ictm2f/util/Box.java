package nl.windesheim.ictm2f.util;

public class Box {
    private String content;
    private Dimension position;

    public Box(int x, int y, String content) {
        this.position = new Dimension(x,y);
        this.content = content;
        if (content.equals("")) {
            this.content = "empty";
        }
    }
    public Box(int x, int y) {
        this.position = new Dimension(x,y);
        this.content = "empty";
    }
    public Box(Dimension dim) {
        this.position = dim;
        this.content = "empty";
    }
    public Box(Dimension dim, String content) {
        this.position = dim;
        this.content = content;
        if (content.equals("")) {
            this.content = "empty";
        }
    }

    @Override
    public String toString() {
        return "Box position: " + this.position.getX() + ", " + this.position.getY() + ". Box contents: " + this.content;
    }
}
