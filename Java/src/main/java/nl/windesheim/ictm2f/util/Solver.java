package nl.windesheim.ictm2f.util;

import java.util.ArrayList;

public class Solver {
    private ArrayList<GridPoint> points;
    private ArrayList<Integer> resultPath;
    private int pathLength;

    public Solver(){
        points = new ArrayList<>();
    }

    public void SolveDynamic(){
        int pointCount = points.size();

        if(pointCount < 3){
            Logger.warning("not enough points");
            return;
        }

//        int[] GFGPoints = GFG.GFG(pointCount, CreateIntDistanceMap());
//        StringBuilder r = new StringBuilder();
//        for (int i : GFGPoints){
//            r.append(i);
//        }
//        Logger.info(String.format("GFG PATH: %s", r));

        TspDynamicProgrammingIterative solver = new TspDynamicProgrammingIterative(0, CreateDistanceMap());

        this.resultPath = solver.getTour();

        pathLength = (int) solver.getTourCost();

        Logger.info("finished creating path");
    }

    // generate an array with all the distances between all the points, distance between itself is 0
    public double[][] CreateDistanceMap(){
        int pointCount = points.size();
        double[][] distanceMap = new double[pointCount][pointCount];

        for (int i = 0; i < pointCount; i++) {
            for (int i2 = 0; i2 < pointCount; i2++) {
                GridPoint point1 = points.get(i);
                GridPoint point2 = points.get(i2);

                double distance = distanceBetweenTwoPoints(point1, point2);
                distanceMap[i][i2] = distance;
            }
        }

        return distanceMap;
    }

    public double distanceBetweenTwoPoints(GridPoint p1, GridPoint p2) {
        return Math.sqrt((Math.pow(p1.getX() - p2.getX() ,2)) + (Math.pow(p1.getY() - p2.getY(),2)));
    }

    public ArrayList<GridPoint> getPoints(){
        return this.points;
    }

    public void addPoint(GridPoint p){
        points.add(p);
    }
    public void removePoint(GridPoint p){
        this.points.remove(p);
    }

    public ArrayList<Integer> getResultPath() {
        return resultPath;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void clearPath() {
        this.resultPath.clear();
    }
}
