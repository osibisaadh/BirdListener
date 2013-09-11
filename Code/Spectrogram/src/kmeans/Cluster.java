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
public class Cluster<T extends Number> {
    private int dimensions = 0;
    protected List<Item<T>> points = new ArrayList<Item<T>>();
    private T[] centerPoint;
    private Class<T> type;

    public Cluster(Class<T> type){
        centerPoint = (T[])new Number[dimensions];
    }

    public Cluster(int dimensions, Class<T> type){
        this.dimensions = dimensions;
        centerPoint = (T[])new Number[dimensions];
        this.type = type;
    }

    public boolean computeCenter(){
        boolean changed = false;
        Number[] newCenter = new Number[centerPoint.length];
        for(int i = 0; i < newCenter.length;i++){
            try{
                newCenter[i] = type.getConstructor(double.class).newInstance(0);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        for(int k = 0; k < newCenter.length; k++){
            for(int i = 0; i < points.size();i++){
                newCenter[k] = newCenter[k].doubleValue() + points.get(i).getItem()[k].doubleValue();
            }
            newCenter[k] = newCenter[k].doubleValue()/points.size();
            if(!changed){
                if(!newCenter[k].equals(centerPoint[k]))
                    changed = true;
            }
        }
        centerPoint = (T[])newCenter;
        return changed;
    }

    public void resetPoints(){
        points = new ArrayList<Item<T>>();
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

    public List<Item<T>> getPoints() {
        return points;
    }

    public void setPoints(List<Item<T>> points) {
        this.points = points;
    }

    public T[] getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(T[] centerPoint) {
        this.centerPoint = centerPoint;
    }
}
