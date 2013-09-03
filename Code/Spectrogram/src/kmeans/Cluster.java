package kmeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 9/2/13
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cluster{
    private int dimensions = 0;
    private List<int[]> points = new ArrayList<int[]>();
    private int[] centerPoint;

    public Cluster(){
        centerPoint = new int[dimensions];
    }

    public Cluster(int dimensions){
        this.dimensions = dimensions;
        centerPoint = new int[dimensions];
    }

    public void computeCenter(){

    }

    public boolean addPoint( int[] point){
        return points.add(point);
    }

    public boolean removePoint( int[] point){
        return points.remove(point);
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public List<int[]> getPoints() {
        return points;
    }

    public void setPoints(List<int[]> points) {
        this.points = points;
    }

    public int[] getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(int[] centerPoint) {
        this.centerPoint = centerPoint;
    }
}
