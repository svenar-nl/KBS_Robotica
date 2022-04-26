package nl.windesheim.ictm2f.util;

import java.util.ArrayList;

public class GridArray {
    private ArrayList<ArrayList<Box>> storage;

    public GridArray(int SizeX, int SizeY) {
        this.storage = new ArrayList<ArrayList<Box>>();
        for (int i = 0; i <= SizeX; i++) {
            this.storage.add(new ArrayList<Box>(SizeY));
            for (int i2 = 0; i2 <= SizeY; i2++) {
                this.storage.get(i).add(new Box(i,i2));
            }
        }

        addBox(1,1,"AA");
        addBox(1,2,"AAA");
        addBox(4,4,"WAdw");
        System.out.println(this.storage.get(1).get(1).toString());
    }

    public Dimension addBox(int x, int y, String content) {
        Box box = new Box(x,y,content);
        //this.storage.get(x).add(y,box);
        if (this.storage.get(x).get(y).getClass() == Box.class) {
            return new Dimension(x,y);
        } else {
            return new Dimension(-1,-1);
        }

    }
}
