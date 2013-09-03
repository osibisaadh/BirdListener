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
    private List<Item> points = new ArrayList<Item>();
    private int[] centerPoint;

    public Cluster(){
        centerPoint = new int[dimensions];
    }

    public Cluster(int dimensions){
        this.dimensions = dimensions;
        centerPoint = new int[dimensions];
    }

    public boolean computeCenter(){
        boolean changed = false;
        int[] newCenter = new int[centerPoint.length];
        for(int k = 0; k < newCenter.length; k++){
            for(int i = 0; i < points.size();i++){
                newCenter[k] += points.get(i).getItem()[k];
            }
            newCenter[k] /= dimensions;
            if(!changed){
                if(newCenter[k] != centerPoint[k])
                    changed = true;
            }
        }
        return changed;
    }

    public void resetPoints(){
        points = new ArrayList<Item>();
    }

    public boolean addPoint( Item point){
        return points.add(point);
    }

    public boolean removePoint( Item point){
        return points.remove(point);
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public List<Item> getPoints() {
        return points;
    }

    public void setPoints(List<Item> points) {
        this.points = points;
    }

    public int[] getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(int[] centerPoint) {
        this.centerPoint = centerPoint;
    }
}
