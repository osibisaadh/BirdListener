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
    private List<Cluster<T>> clusters;
    private int clusterNum;
    private int dimensions = 0;
    private List<Item<T>> items = new ArrayList<Item<T>>();
    private Random random = new Random();
    private Class<T> type;

    public Kmeans(int dimensions,int clusterNum, Class<T> type){
        this.clusterNum = clusterNum;
        this.dimensions = dimensions;
        clusters = new ArrayList<Cluster<T>>();
        this.type = type;
    }

    private List<Item<T>> getCopyOfItems(){
        List<Item<T>> newList = new ArrayList<Item<T>>();
        for(Item<T> pt : items){
            newList.add(pt);
        }
        return newList;
    }

    public void init(){
        List<Item<T>> tempitems = sortForInit(getCopyOfItems());
        List<Cluster<T>> clusters = new ArrayList<Cluster<T>>();
        for(int i = 0; i < clusterNum; i++){
            Cluster<T> cluster = new Cluster<T>(dimensions, type);
            int point = tempitems.size()/clusterNum * i;
            cluster.setCenterPoint(tempitems.get(point).getItem());
            tempitems.remove(point);
            clusters.add(cluster);
        }

        this.clusters = clusters;
    }

    private List<Item<T>> sortForInit(List<Item<T>> items){
        List<Item<T>> resultList = new ArrayList<Item<T>>();
        Number[] center = new Number[dimensions];
        for(int i = 0; i < dimensions;i++){
            try{
                center[i] = type.getConstructor(double.class).newInstance(0);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        items.get(0).distance = getDistance(items.get(0).getItem(),(T[])center);
        resultList.add(items.get(0));
        for(int i = 1; i < items.size(); i++){
            items.get(i).distance = getDistance(items.get(i).getItem(),(T[])center);
            boolean placed = false;
            for(int j = 0; j < resultList.size() && !placed; j++){
                if(resultList.get(j).distance > items.get(i).distance){
                    resultList.add(j, items.get(i));
                    placed = true;
                }
            }
            if(!placed){
                resultList.add(items.get(i));
            }
        }
        return resultList;
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
        for(int i =0; i < clusters.size(); i++){
            if(changed)
                changed = clusters.get(i).computeCenter();
            else
                clusters.get(i).computeCenter();
            //System.out.println("Cluster " + i + ": " + "c1-" +clusters.get(i).getCenterPoint()[0] );
        }
        while(changedCount < 2){

            reinitClusters();
            for(int i =0; i < items.size(); i++){
                findMinDistance(items.get(i));

            }
            for(int i =0; i < clusters.size(); i++){
                if(changed)
                    changed = clusters.get(i).computeCenter();
                else
                    clusters.get(i).computeCenter();
                //System.out.println("Cluster " + i + ": " + "c1-" +clusters.get(i).getCenterPoint()[0] );
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
        double minDistance = getDistance(item.getItem(), clusters.get(0).getCenterPoint());
        int minDistanceIndex = 0;
        for(int i = 1; i <clusters.size(); i++){
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


    public boolean addItem(Item<T> item){
        boolean added = items.add(item);

        return added;
    }

    public double getDistance(T[] point, T[] center){
        double distance = 0;
        for(int i = 0; i < point.length; i++){
            double sum = point[i].doubleValue() - center[i].doubleValue();
            distance += sum * sum;
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
