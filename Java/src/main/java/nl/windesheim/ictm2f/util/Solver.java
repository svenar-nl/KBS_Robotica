package nl.windesheim.ictm2f.util;

import java.util.ArrayList;

public class Solver {
    private ArrayList<GridPoint> points;
    private ArrayList<Integer> resultPath;
    private int pathLength;
    private double[][] distanceMap;
    private int l;
    private int k;

    public Solver(){
        points = new ArrayList<>();
    }

    public void SolveDynamic(){
        int pointCount = points.size();

        if(pointCount < 3){
            // TODO: error not enough points
        }

        this.distanceMap = CreateDistanceMap();
        this.l = 0;
        this.k = 0;

        resultPath = new ArrayList<>();

        //input sample
        int n = distanceMap[0].length;
        int[][] p = new int[n + 1][n + 1];

        // calculate path
        pathLength = TSPDynamic(n, p);

        // build path
        int i = 0;
        int j = k - 1;
        resultPath.add(0);
        while(j > 0 ){
            resultPath.add(p[i][j]);
            i = p[i][j];
            j = j& ~(1 << (i-1));
        }
        resultPath.add(0);
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

    public int TSPDynamic(int n, int[][] p) {
        k = (int)Math.pow(2, (n - 1));
        double[][] D = new double[n + 1][n + 1];

        for (int i = 1; i < n; i++) {
            D[i][0] = distanceMap[i][0];
        }

        //finding the shortest path excluding v1
        for (int i = 1; i <= n - 2; i++) {
            for (int subset = 1; subset < k; subset++) {
                if (len(subset) == i) {
                    for (int v = 1; v < n; v++) {
                        if (!haveI(subset, v-1)) {
                            for (; D[v].length <= subset; D[v] = increaseDoubleArraySize(D[v], 1));
                            D[v][subset] = min(v, D, subset, n);
                            for (; p[v].length <= subset; p[v] = increaseIntArraySize(p[v], 1));
                            p[v][subset] = l;
                        }
                    }
                }
            }
        }
        int m = min(0, D, k-1, n);
        for (int t = k-1; p[0].length <= t; p[0] = increaseIntArraySize(p[0], 1));
        p[0][k-1] = l;
        return m;
    }

    // finding the cardinality of a subset
    public int len(int j){
        int count = 0;
        while (j != 0) {
            j = j & (j - 1);
            count++;
        }
        return count;
    }

    public boolean haveI(int subset, int position) {
        int num = subset & ~(1 << (position));
        return (num & subset) != subset;
    }

    public int min(int v, double[][] D, int set, int n) {
        int[] m = new int[10];
        int[] i = new int[10];
        int ind = 0;

        for(int j = 0 ; j< n-1 ; j++){
            if(haveI(set, j)){
                int num = set & ~(1 << (j));
                num = set & num;
                m[ind] = (int) (distanceMap[v][j + 1] + D[j + 1][num]);
                i[ind] = j + 1;
                ind++;
            }
        }
        int min = m[0];
        l = i[0];

        for(int j = 1; j < len(set); j++){
            if(min > m[j]){
                min = m[j];
                l = i[j];
            }
        }

        return min;
    }

    public double[] increaseDoubleArraySize(double[] from, int amount){
        double[] to = new double[from.length + amount];
        System.arraycopy(from, 0, to, 0, from.length);
        return to;
    }
    public int[] increaseIntArraySize(int[] from, int amount){
        int[] to = new int[from.length + amount];
        System.arraycopy(from, 0, to, 0, from.length);
        return to;
    }

    public ArrayList<GridPoint> getPoints(){
        return this.points;
    }

    public void addPoint(GridPoint p){
        points.add(p);
    }

    public ArrayList<Integer> getResultPath() {
        return resultPath;
    }

    public int getPathLength() {
        return pathLength;
    }
}
