package Drivers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import kmeans.*;
public class Test {
    public static void main(String[] args) {
        List<Double[]> doubles = readFile();
        List<Item<Double>> items = new ArrayList<Item<Double>>();
        for(int i = 0; i < doubles.size(); i++){
            items.add(new Item<Double>(doubles.get(i), i +""));
        }
        Kmeans kmeans = new Kmeans(2,5, Double.class);
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

    public static List<Double[]> readFile(){
        List<Double[]> doubles = new ArrayList<Double[]>();

        try{
            File file = new File("TestData.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            boolean hasNext = true;
            while(hasNext){
                String line = reader.readLine();
                if(line != null){
                    Double[] array = {
                            Double.parseDouble(line.split("    ")[0]),
                            Double.parseDouble(line.split("    ")[1]),
                    };
                    doubles.add(array);
                }
                else
                    hasNext = false;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return doubles;
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
