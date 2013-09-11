package Drivers;

import fingerprint.FingerPrint;
import kmeans.Cluster;
import kmeans.Item;
import kmeans.Kmeans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 9/3/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class KMeansTest {

    private static final Double[][] pts = {
        {1.0,1.0},
            {1.5,2.0},
            {3.0,4.0},
            {5.0,7.0},
            {3.5,5.0},
            {4.5,5.0},
            {3.5,4.5},

    };

    private static final Integer[][] integerPoints = {
            {2},{3},{6},{8},{12},{15},{18},{22}

    };

    public static void main(String[] args) {


        Kmeans kmeans = new Kmeans(2,2, Double.class);
        List<Item<Double>> items = new ArrayList<Item<Double>>();
        for(int i = 0; i < pts.length; i++){
            items.add(new Item<Double>(pts[i],"" + i));
        }
//        List<Item<Integer>> items = new ArrayList<Item<Integer>>();
//        for(int i = 0; i < integerPoints.length; i++){
//            items.add(new Item<Integer>(integerPoints[i],"" + i));
//        }

        kmeans.setItems(items);
        kmeans.run();
        List<Cluster<Double>> clusters = kmeans.getClusters();
        for( int i = 0; i < clusters.size();i++){
            System.out.println("Cluster: " + i + " Size: " + clusters.get(i).getPoints().size() + " Centroid: " + getPrint(clusters.get(i).getCenterPoint()));
            List<Item<Double>> cItems = clusters.get(i).getPoints();
            for(int k =0; k < cItems.size(); k++){
                System.out.println("\t" + cItems.get(k).getKey() + ": " + getPrint(cItems.get(k).getItem()));
            }
        }

    }

    public static String getPrint(Number[] print){
        String word = "";
        for(int i = 0; i < print.length; i++){
            word += print[i].doubleValue();
            if(i < print.length-1)
                word += "-";
        }
        return word;
    }
}
