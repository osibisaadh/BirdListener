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
public class Kmeans<T extends Number>{
    private double MAX_DOUBLE = 999999.9;
    private List<Cluster<T>> clusters;
    private int clusterNum;
    private int dimensions = 0;
    private List<Item<T>> items = new ArrayList<Item<T>>();
    private Random random = new Random();

    public Kmeans(int dimensions,int clusterNum){
        this.clusterNum = clusterNum;
        this.dimensions = dimensions;
        clusters = new ArrayList<Cluster<T>>();
    }

    private List<Item<T>> getCopyOfItems(){
        List<Item<T>> newList = new ArrayList<Item<T>>();
        for(Item<T> pt : items){
            newList.add(pt);
        }
        return newList;
    }

    public void init(){
        List<Item<T>> tempitems = getCopyOfItems();

        for(int i = 0; i < clusterNum; i++){
            Cluster<T> cluster = new Cluster<T>(dimensions);
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
        int changedCount = 0;
        init();
        for(int i = 0; i < items.size(); i++){
            findMinDistance(items.get(i));
        }
        boolean changed = true;

        while(changedCount < 5){
            for(int i =0; i < clusters.size(); i++){
                if(changed)
                    changed = clusters.get(i).computeCenter();
                else
                    clusters.get(i).computeCenter();
                System.out.println("Cluster " + i + ": " + "c1-" +clusters.get(i).getCenterPoint()[0]+ " c2-" +clusters.get(i).getCenterPoint()[1] );
            }
            reinitClusters();
            for(int i =0; i < items.size(); i++){
                findMinDistance(items.get(i));
            }
            if(changed == false){
                changedCount++;
                changed = true;
            }
        }

    }

    public boolean addItems(Item<T>[] items){
        boolean addedall = true;
        for(int i = 0; i < items.length; i++){
            if(addedall){
                addedall = addItem(items[i]);
            }
        }
        return addedall;
    }

    private void findMinDistance(Item<T> item){
        double minDistance = MAX_DOUBLE;
        int minDistanceIndex = 0;
        for(int i = 0; i <clusters.size(); i++){
            double distance = getDistance(item.getItem(), clusters.get(i).getCenterPoint());
            if(minDistance > distance){
                minDistance = (double)distance;
                minDistanceIndex = i;
            }
        }
        clusters.get(minDistanceIndex).addPoint(item);
//        if(previousCluster != null){
//            previousCluster.removePoint(item);
//        }
    }


    public boolean addItem(Item<T> item){
        boolean added = items.add(item);

        return added;
    }

    public double getDistance(T[] point, T[] center){
        double[] distanceArray = new double[point.length];
        double distance = 0;
        for(int i = 0; i < point.length; i++){
            distanceArray[i] = point[i].doubleValue() - center[i].doubleValue();
            distance += Math.pow(distanceArray[i], 2.0);
        }
        return Math.sqrt(distance);
    }

    public List<Cluster<T>> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster<T>> clusters) {
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

    public List<Item<T>> getItems() {
        return items;
    }

    public void setItems(List<Item<T>> items) {
        this.items = items;
    }
}
