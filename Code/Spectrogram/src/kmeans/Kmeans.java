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
    private List<Item> items = new ArrayList<Item>();
    private Random random = new Random();

    public Kmeans(int dimensions,int clusterNum){
        this.clusterNum = clusterNum;
        this.dimensions = dimensions;
        clusters = new ArrayList<Cluster>();
    }

    private List<Item> getCopyOfItems(){
        List<Item> newList = new ArrayList<Item>();
        for(Item pt : items){
            newList.add(pt);
        }
        return newList;
    }

    public void init(){
        List<Item> tempitems = getCopyOfItems();
        for(int i = 0; i < clusterNum; i++){
            Cluster cluster = new Cluster(dimensions);
            int randomPoint = random.nextInt(tempitems.size());
            cluster.setCenterPoint(tempitems.get(randomPoint).getItem());
            tempitems.remove(randomPoint);
            clusters.add(cluster);
        }
    }

    private void reinitClusters(){
        for(int i = 0; i < clusters.size(); i++){
            clusters.get(i).resetPoints();
        }
    }

    public void run(){
        init();
        for(int i = 0; i < items.size(); i++){
            findMinDistance(items.get(i));
        }
        boolean changed = true;
        while(changed){
            changed = false;
            for(int i =0; i < clusters.size(); i++){
                if(changed)
                    changed = clusters.get(i).computeCenter();
                else
                    clusters.get(i).computeCenter();
            }
            reinitClusters();
            for(int i =0; i < items.size(); i++){
                findMinDistance(items.get(i));
            }
        }

    }

    public boolean addItems(Item[] items){
        boolean addedall = true;
        for(int i = 0; i < items.length; i++){
            if(addedall){
                addedall = addItem(items[i]);
            }
        }
        return addedall;
    }

    private void findMinDistance(Item item){
        double minDistance = Integer.MAX_VALUE;
        int minDistanceIndex = 0;
        for(int i = 0; i <clusters.size(); i++){
            double distance = getDistance(item.getItem(), clusters.get(i).getCenterPoint());
            if(minDistance > distance){
                minDistance = distance;
                minDistanceIndex = i;
            }
        }
        clusters.get(minDistanceIndex).addPoint(item);
//        if(previousCluster != null){
//            previousCluster.removePoint(item);
//        }
    }


    public boolean addItem(Item item){
        boolean added = items.add(item);

        return added;
    }

    public double getDistance(int[] point, int[] center){
        int[] distanceArray = new int[point.length];
        double distance = 0;
        for(int i = 0; i < point.length; i++){
            distanceArray[i] = point[i] - center[i];
            distance += Math.pow((double)distanceArray[i],2.0);
        }
        return Math.sqrt(distance);
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
