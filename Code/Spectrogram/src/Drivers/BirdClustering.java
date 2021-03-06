package Drivers;

import Wav.WaveFile;
import com.musicg.graphic.GraphicRender;
import fingerprint.FingerPrint;
import kmeans.Cluster;
import kmeans.Item;
import kmeans.Kmeans;
import spectrogram.Spectrogram;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 8/26/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class BirdClustering {

    private static List<Integer[]> pts = new ArrayList<Integer[]>();
    private static List<String> names = new ArrayList<String>();
    private static final String dirName = "testData\\DB";

    private static void createSpectrogram(String fileName){
        WaveFile waveFile2 = new WaveFile(dirName + "\\" + fileName + ".wav");
        Spectrogram wavSpec2 = new Spectrogram(waveFile2);

        GraphicRender render = new GraphicRender();
        render.renderSpectrogramData(wavSpec2.getSpectrogram(), "testData\\out\\" + fileName + ".jpg");
    }

    private static FingerPrint getFingerPrint(String fileName){

        WaveFile waveFile = new WaveFile(dirName + "\\" + fileName + ".wav");
        Spectrogram wavSpec = new Spectrogram(waveFile);
        FingerPrint fingerPrint = new FingerPrint(wavSpec);
        return fingerPrint;
    }

    public static void main(String[] args) {
        try{

            File dir = new File(dirName);

            if(dir.isDirectory()){
                for(File file : dir.listFiles()){
                    String fileName = file.getName().split("\\.")[0];
                    FingerPrint fingerPrint = getFingerPrint(fileName);
                    names.add(fileName);
                    addPoints(fingerPrint.getPrint()[0]);
                    createSpectrogram(fileName);
                }
            }

            Kmeans kmeans = new Kmeans(23,10, Double.class);
            List<Item<Integer>> items = new ArrayList<Item<Integer>>();
            for(int i = 0; i < pts.size(); i++){
                items.add(new Item<Integer>(pts.get(i), names.get(i)));
//                System.out.println(pts.get(i) + " " + names.get(i));
            }

            kmeans.setItems(items);
            kmeans.run();
            List<Cluster<Integer>> clusters = kmeans.getClusters();
            for( int i = 0; i < clusters.size();i++){
                System.out.println("Cluster: " + i + "Size: " + clusters.get(i).getPoints().size());// + " Centroid: " + getSPrint(clusters.get(i).getCenterPoint()));
                List<Item<Integer>> cItems = clusters.get(i).getPoints();
                for(int k =0; k < cItems.size(); k++){
                    System.out.println("\t" + cItems.get(k).getKey() + "\t" + getSPrint(cItems.get(k).getItem()));
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String getSPrint(Number[] print){
        String word = "";
        for(int i = 0; i < print.length; i++){
            word += print[i];
            if(i < print.length-1)
                word += "-";
        }
        return word;

    }

    private static void addPoints(int[] prints){
        Integer[] itegerprint = new Integer[prints.length];
        for(int i = 0; i < prints.length; i++){
            itegerprint[i] = new Integer(prints[i]);
        }
        pts.add(itegerprint);
    }

    private static void addPoints(Integer[] print){
        pts.add(print);
    }

}
