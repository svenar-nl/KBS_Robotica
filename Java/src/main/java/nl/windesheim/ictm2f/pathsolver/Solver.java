package nl.windesheim.ictm2f.pathsolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.util.GridPoint;
import nl.windesheim.ictm2f.util.Logger;

public class Solver {
    private ArrayList<GridPoint> points;
    private ArrayList<Integer> resultPath;
    private int pathLength;

    public Solver() {
        points = new ArrayList<>();
    }

    public void SolveDynamic() {
        Main.getInstance().getOrderManager().getCurrentOrder().setPoints(getPoints());
        
        int pointCount = points.size();
        if (pointCount == 0) {
            return;
        }

        if (pointCount < 3) {
            if (pointCount == 2) {
                int[] path = { 0, 1, 0 };
                this.resultPath = (ArrayList<Integer>) Arrays.stream(path).boxed().collect(Collectors.toList());
            } else  if (pointCount == 1) {
                int[] path = { 0, 0 };
                this.resultPath = (ArrayList<Integer>) Arrays.stream(path).boxed().collect(Collectors.toList());
            } else {
                Logger.severe("No points selected!");
                return;
            }
            Logger.warning("Less than 3 points not recommended!");
            return;
        }

        // int[] GFGPoints = GFG.GFG(pointCount, CreateIntDistanceMap());
        // StringBuilder r = new StringBuilder();
        // for (int i : GFGPoints){
        // r.append(i);
        // }
        // Logger.info(String.format("GFG PATH: %s", r));

        long start = System.currentTimeMillis();

        TSPSolver solver = new TSPSolver(0, CreateDistanceMap());

        this.resultPath = solver.getTour();

        pathLength = (int) solver.getTourCost();

        Logger.info(String.format("finished creating path in: %sms", System.currentTimeMillis() - start));
    }

    // generate an array with all the distances between all the points, distance
    // between itself is 0
    public double[][] CreateDistanceMap() {
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
        return Math.sqrt((Math.pow(p1.getX() - p2.getX(), 2)) + (Math.pow(p1.getY() - p2.getY(), 2)));
    }

    public ArrayList<GridPoint> getPoints() {
        return this.points;
    }

    public void addPoint(GridPoint p) {
        points.add(p);
    }

    public void removePoint(GridPoint p) {
        this.points.remove(p);
    }

    public ArrayList<Integer> getResultPath() {
        return resultPath;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void clearPath() {
        if (this.resultPath != null) {
            this.resultPath.clear();
        }
    }

    public void clearPoints() {
        this.points.clear();
    }
}
