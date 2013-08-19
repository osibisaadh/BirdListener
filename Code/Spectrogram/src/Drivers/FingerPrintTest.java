package Drivers;

import Wav.WaveFile;
import com.musicg.graphic.GraphicRender;
import fingerprint.FingerPrint;
import fingerprint.Phrase;
import fingerprint.Word;
import spectrogram.Spectrogram;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/27/13
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class FingerPrintTest {
    public static void main(String[] args){
        String fileName = "480A";
//        String fileName = "3876a";
//        String fileName = "3877a";
        WaveFile waveFile = new WaveFile("testData\\" + fileName + ".wav");
        Spectrogram wavSpec = new Spectrogram(waveFile);
        FingerPrint fingerPrint = new FingerPrint(wavSpec);
//        System.out.println("End File 1");

        String fileName2 = "480A";
//      fileName2 = "10 Western Meadowlark";
      fileName2 = "07 Northern Cardinal";
//        fileName2 = "06 White-throated Sparrow";
//        fileName2 = "04 Wood Thrush";
//      fileName2 = "02 Canyon Wren";
//      fileName2 = "3876a";
//      fileName2 = "3877a";

        WaveFile waveFile2 = new WaveFile("testData\\" + fileName2 + ".wav");
        Spectrogram wavSpec2 = new Spectrogram(waveFile2);
        FingerPrint fingerPrint2 = new FingerPrint(wavSpec2);
//        System.out.println("End File 2");

//        System.out.println("Phrases: " + fingerPrint2.getPhrases().size() + ", " + fingerPrint.getPhrases().size());
//        double similarity = fingerPrint.match(fingerPrint2);
//        System.out.println("Similarity: " + similarity );

        System.out.println("First: ______________________\n" + fingerPrint.toString());
        System.out.println("Second: _____________________\n" + fingerPrint2.toString());


    }

}
