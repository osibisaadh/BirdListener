package Drivers;

import Wav.WaveFile;
import com.tomgibara.cluster.gvm.intgr.IntClusters;
import com.tomgibara.cluster.gvm.intgr.IntListKeyer;
import com.tomgibara.cluster.gvm.intgr.IntResult;
import fingerprint.FingerPrint;
import spectrogram.Spectrogram;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/12/13
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {
    private static List<int[]> pts = new ArrayList<int[]>();

    private static FingerPrint getFingerPrint(String fileName){

        WaveFile waveFile = new WaveFile("testData\\" + fileName + ".wav");
        Spectrogram wavSpec = new Spectrogram(waveFile);
        FingerPrint fingerPrint = new FingerPrint(wavSpec);
        return fingerPrint;
    }

    public static void main(String[] args) {
        try{

            String fileName = "480A";
//            String fileName = "3876a";
//            String fileName = "3877a";
            FingerPrint fingerPrint = getFingerPrint(fileName);
            int[][] prints = fingerPrint.getPrint();
            addPoints(prints);

            String fileName2 = "480A";
            fileName2 = "02 Canyon Wren";
            fingerPrint = getFingerPrint(fileName2);
            addPoints(fingerPrint.getPrint());

            fileName2 = "3876a";
            fingerPrint = getFingerPrint(fileName2);
            addPoints(fingerPrint.getPrint());

            fileName2 = "3877a";
            fingerPrint = getFingerPrint(fileName2);
            addPoints(fingerPrint.getPrint());
//
//            fileName2 = "10 Western Meadowlark";
//            fingerPrint = getFingerPrint(fileName2);
//            addPoints(fingerPrint.getPrint());

//            fileName2 = "07 Northern Cardinal";
//            fingerPrint = getFingerPrint(fileName2);
//            addPoints(fingerPrint.getPrint());

//            fileName2 = "06 White-throated Sparrow";
//            fingerPrint = getFingerPrint(fileName2);
//            addPoints(fingerPrint.getPrint());
            fileName2 = "04 Wood Thrush";
            fingerPrint = getFingerPrint(fileName2);
            addPoints(fingerPrint.getPrint());


            IntClusters <List<int[]>> clusters = new IntClusters<List<int[]>>(9, 2);
            clusters.setKeyer(new IntListKeyer<int[]>());

            for (int[] pt : pts) {
                ArrayList<int[]> key = new ArrayList<int[]>();
                key.add(pt);
                clusters.add(1, pt, key);
            }

            FileWriter writer = new FileWriter("testData/out/cluster/ii-clustered.txt");
            final List<IntResult<List<int[]>>> results = clusters.results();
            System.out.println(results.size());
            for (int i = 0; i < results.size(); i++) {
                writer.write("Cluster " + i + ":");
                for (int[] pt : results.get(i).getKey()) {
                    writer.write(pt[0] + "-" +  pt[1] + "-"  + i+1 + "\t");
                }
               writer.write("\n");
            }
            writer.close();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void addPoints(int[][] prints){
        for(int i = 0; i < prints.length; i++){
            pts.add(prints[i]);
        }
    }

}
