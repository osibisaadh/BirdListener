package Drivers;

import Wav.WaveFile;
import com.musicg.graphic.GraphicRender;
import com.tomgibara.cluster.gvm.intgr.IntClusters;
import com.tomgibara.cluster.gvm.intgr.IntListKeyer;
import com.tomgibara.cluster.gvm.intgr.IntResult;
import fingerprint.FingerPrint;
import spectrogram.Spectrogram;

import java.io.File;
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
                    addPoints(fingerPrint.getPrint());
                    createSpectrogram(fileName);
                }
            }

            IntClusters <List<String>> clusters = new IntClusters<List<String>>(20, 10);
            clusters.setKeyer(new IntListKeyer<String>());
            int count = 0;
            for (int[] pt : pts) {
                ArrayList<String> key = new ArrayList<String>();
                System.out.println("Count: " + count + "\t Max: " + pts.size());
                key.add(names.get(count));
                clusters.add(1, pt, key);
                count++;
            }

            FileWriter writer = new FileWriter("testData/out/cluster/ii-clustered.txt");
            final List<IntResult<List<String>>> results = clusters.results();
            System.out.println(results.size());
            for (int i = 0; i < results.size(); i++) {
                System.out.println("Cluster: " + i + ": " + "Size - " + results.get(i).getKey().size());
                writer.write("Cluster " + i + ":");
                for (String pt : results.get(i).getKey()) {
                    writer.write(pt +  "\t");
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

    private static void addPoints(int[] print){
        pts.add(print);
    }

}
