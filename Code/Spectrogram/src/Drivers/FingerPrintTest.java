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
        WaveFile waveFile = new WaveFile("testData\\" + fileName + ".wav");
        Spectrogram wavSpec = new Spectrogram(waveFile);
        FingerPrint fingerPrint = new FingerPrint(wavSpec);

//        String fileName2 = "480A";
//        String fileName2 = "10 Western Meadowlark";
//        String fileName2 = "07 Northern Cardinal";
//        String fileName2 = "06 White-throated Sparrow";
//        String fileName2 = "04 Wood Thrush";
        String fileName2 = "02 Canyon Wren";
        WaveFile waveFile2 = new WaveFile("testData\\" + fileName2 + ".wav");
        Spectrogram wavSpec2 = new Spectrogram(waveFile2);
        FingerPrint fingerPrint2 = new FingerPrint(wavSpec2);

        System.out.println("Phrases: " + fingerPrint2.getPhrases().size() + ", " + fingerPrint.getPhrases().size());
        double similarity = fingerPrint.match(fingerPrint2);
        System.out.println("Similarity: " + similarity );

    }

}
