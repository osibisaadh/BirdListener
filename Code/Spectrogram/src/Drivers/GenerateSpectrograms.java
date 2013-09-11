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
 * Date: 7/30/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateSpectrograms {

    //generates visual representation of each word extracted from the spectrogram
    public static void main(String[]args){

        String fileName = "480A";
        WaveFile waveFile = new WaveFile("testData\\" + fileName + ".wav");
        Spectrogram wavSpec = new Spectrogram(waveFile);
        FingerPrint fingerPrint = new FingerPrint(wavSpec);

    //    String fileName2 = "480A";
//        String fileName2 = "10 Western Meadowlark";
    //    String fileName2 = "07 Northern Cardinal";
    //    String fileName2 = "06 White-throated Sparrow";
    //    String fileName2 = "04 Wood Thrush";
        String fileName2 = "Canyon Wren -- 1";
        WaveFile waveFile2 = new WaveFile("testData\\DB" + fileName2 + ".wav");
        Spectrogram wavSpec2 = new Spectrogram(waveFile2);
//        FingerPrint fingerPrint2 = new FingerPrint(wavSpec2);
//        System.out.println("Phrases: " + fingerPrint2.getPhrases().size() + ", " + fingerPrint.getPhrases().size());
//        double similarity = fingerPrint.match(fingerPrint2);
//        System.out.println("Similarity: " + similarity );
        List<Phrase> phrases = fingerPrint.getPhrases();

        GraphicRender render = new GraphicRender();
        render.renderSpectrogramData(wavSpec2.getSpectrogram(), "testData\\out\\" + "Canyon Wren -- 1" + ".jpg");

        //generates visual representation of each word extracted from the spectrogram

//        int index = 0;
//        for(Phrase p : fingerPrint.getPhrases()){
//            for(Word w : p.getWords()){
//                render.renderSpectrogramData(w.getSpectrogram(),"testData\\out\\fingerprint1\\" + fileName + index +".jpg");
//                index++;
//            }
//        }

        //generates visual representation of each word extracted from the spectrogram
//        index = 0;
//        for(Phrase p : fingerPrint2.getPhrases()){
//            for(Word w : p.getWords()){
//                render.renderSpectrogramData(w.getSpectrogram(),"testData\\out\\fingerprint2\\" + 2 + index +".jpg");
//                index++;
//            }
//        }



    }
}
