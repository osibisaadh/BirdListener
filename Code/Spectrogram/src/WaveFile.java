import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/15/13
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaveFile {
    private WaveHeader header;
    private byte[] rawData;
    private double[] audioData;
    private byte[] fingerprint;

    public WaveFile(String fileName){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));
            AudioFormat format = audioInputStream.getFormat();
            audioInputStream.getFrameLength();
            header = new WaveHeader(format);
            audioInputStream.available();
            rawData = new  byte[audioInputStream.available()];
            audioInputStream.read(rawData);
            //Create byte array for data

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void getRawData(){

    }

}
