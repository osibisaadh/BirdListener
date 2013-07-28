package Drivers;

import Wav.WaveFile;
import fingerprint.FingerPrint;
import spectrogram.Spectrogram;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/27/13
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class FingerPrintTest {
    public static void main(String[] args){
        String fileName = "02 Canyon Wren";
        WaveFile waveFile = new WaveFile("testData\\" + fileName + ".wav");
        Spectrogram wavSpec = new Spectrogram(waveFile);
        FingerPrint fingerPrint = new FingerPrint(wavSpec);
    }

}
