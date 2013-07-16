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
    private short[] data;
    private double[] normalizedData;
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
            initSampleAmplitudes();
            initNormalAmplitudes();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //get a short[] array representation of the audio
    private void initSampleAmplitudes(){
        data = new short[header.getNumOfSamples()];
        int pointer = 0;
        for (int i = 0; i < header.getNumOfSamples(); i++) {
            short amplitude = 0;
            for (int byteNumber = 0; byteNumber < header.getBytesPerSample(); byteNumber++) {
                //(& 0xFF gets the least significant byte from the bytearray)

                amplitude |= (short) ((rawData[pointer++] & 0xFF) << (byteNumber * 8));
            }
            data[i] = amplitude;
        }
    }


    private void initNormalAmplitudes(){
        normalizedData = new double[header.getNumOfSamples()];
        int maxAmplitude = 1 << (header.getBitsPerSample() - 1);
        for(int i = 0; i < normalizedData.length; i++){
            normalizedData[i] = (double) data[i] / maxAmplitude;
        }
    }

    public byte[] getRawData(){
        return  rawData;
    }

    public WaveHeader getHeader() {
        return header;
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

    public double[] getNormalizedData() {
        return normalizedData;
    }

    public short[] getData() {
        return data;
    }

}
