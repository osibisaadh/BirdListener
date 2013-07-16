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
            header = new WaveHeader(format, audioInputStream.available());
            audioInputStream.available();
            rawData = new  byte[audioInputStream.available()];
            audioInputStream.read(rawData);
            //Create byte array for data

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public short[] getSampleAmplitudes(){
        short[] amplitudes = new short[header.getNumOfSamples()];

        int pointer = 0;
        for (int i = 0; i < header.getNumOfSamples(); i++) {
            short amplitude = 0;
            for (int byteNumber = 0; byteNumber < header.getBytesPerSample(); byteNumber++) {
                amplitude |= (short) ((rawData[pointer++] & 0xFF) << (byteNumber * 8));
            }
            amplitudes[i] = amplitude;
        }

        return amplitudes;
    }

    private byte[] getRawData(){
        return  rawData;
    }

    public WaveHeader getHeader() {
        return header;
    }

    public double[] getAudioData() {
        return audioData;
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }
}
