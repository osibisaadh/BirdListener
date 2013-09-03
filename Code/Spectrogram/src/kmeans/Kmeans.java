package kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 9/2/13
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Kmeans{
    private List<Cluster> clusters;
    private int clusterNum;
    private int dimensions = 0;
    private List<int[]> items = new ArrayList<int[]>();
    private Random random = new Random();

    public Kmeans(int dimensions,int clusterNum){
        this.clusterNum = clusterNum;
        this.dimensions = dimensions;
        clusters = new ArrayList<Cluster>();
    }

    private List<int[]> getCopyOfItems(){
        List<int[]> newList = new ArrayList<int[]>();
        for(int[] pt : items){
            newList.add(pt);
        }
        return newList;
    }

    public void init(){
        List<int[]> tempitems = getCopyOfItems();
        for(int i = 0; i < clusterNum; i++){
            Cluster cluster = new Cluster(dimensions);
            int randomPoint = random.nextInt(tempitems.size());
            cluster.setCenterPoint(tempitems.get(randomPoint));
            tempitems.remove(randomPoint);
            clusters.add(cluster);
        }
    }

    public void run(){
        init();
        for(int i = 0; i < items.size(); i++){
            findMinDistance(items.get(i),null);
        }
        boolean changed = true;
        while(changed){
            changed = false;
            for(int i =0; i < items.size(); i++){

            }
        }

    }

    public boolean addItems(int[][] items){
        boolean addedall = true;
        for(int i = 0; i < items.length; i++){
            if(addedall){
                addedall = addItem(items[i]);
            }
        }
        return addedall;
    }

    private void findMinDistance(int[] item, Cluster previousCluster){
        double minDistance = Double.MAX_VALUE;
        int minDistanceIndex = 0;
        for(int i = 0; i <clusters.size(); i++){
            double distance = getDistance(item, clusters.get(i).getCenterPoint());
            if(minDistance > distance){
                minDistance = distance;
                minDistanceIndex = i;
            }
        }
        clusters.get(minDistanceIndex).addPoint(item);
        if(previousCluster != null){
            previousCluster.removePoint(item);
        }
    }


    public boolean addItem(int[] item){
        boolean added = items.add(item);

        return added;
    }

    public double getDistance(int[] point, int[] center){
        int[] distanceArray = new int[point.length];
        double distance = 0;
        for(int i = 0; i < point.length; i++){
            distanceArray[i] = point[i] - center[i];
            distance += distanceArray[i];
        }
        return distance;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public int getClusterNum() {
        return clusterNum;
    }

    public void setClusterNum(int clusterNum) {
        this.clusterNum = clusterNum;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public List<int[]> getItems() {
        return items;
    }

    public void setItems(List<int[]> items) {
        this.items = items;
    }
}
