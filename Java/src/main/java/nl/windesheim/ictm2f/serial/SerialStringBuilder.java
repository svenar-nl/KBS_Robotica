package nl.windesheim.ictm2f.serial;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.util.GridPoint;
import nl.windesheim.ictm2f.util.Logger;

import java.util.ArrayList;

public class SerialStringBuilder {
    private SerialManager serialManager = Main.getInstance().getSerialManager();
    private ArrayList<GridPoint> points;
    private ArrayList<Integer> path;
    private String serialString;

    public SerialStringBuilder(ArrayList<GridPoint> points){
        this.points = points;

        // TODO maybe check if it is already solved?
        Main.getInstance().getSolver().SolveDynamic();
        this.path = Main.getInstance().getSolver().getResultPath();

        if(!serialManager.isConnected()){
            // TODO connect serialManager.connect();
        }

        // if success send
    }
    // get points from canvas
    public SerialStringBuilder(){
        this(Main.getInstance().getSolver().getPoints());
    }

    public String buildString(){
        // prefix to control the exist E.I. x2y4 > x to 2 y to 4
        String r = "";

        for (int name : this.path){
            for (GridPoint gp : this.points){
                if(Integer.parseInt(gp.getName()) == name + 1){
                    r += String.format("x%sy%sp", gp.getX(), gp.getY());
                    continue;
                }
            }
        }

        Logger.info(String.format("built string: %s", r));

        this.serialString = r;

        return r;
    }

    private void Send(String s){
        serialManager.write(s);
    }

    public String getSerialString() {
        return serialString;
    }
}
