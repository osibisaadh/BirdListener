import javax.sound.sampled.AudioFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/15/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaveHeader {

    private AudioFormat format;
    private int numChannels;
    private float sampleRate;
    private float byteRate;
    private int bitsPerSample;
    private boolean isBigEndian;         s
    private float bytesPerSecond;


    public WaveHeader(AudioFormat format){
        this.format = format;
        this.numChannels = format.getChannels();
        this.sampleRate = format.getSampleRate();
        this.bitsPerSample = format.getSampleSizeInBits();
        this.byteRate = sampleRate * numChannels * bitsPerSample;
        this.isBigEndian = format.isBigEndian();
        this.bytesPerSecond = sampleRate * (bitsPerSample/8) *numChannels;

    }

    public boolean isBigEndian() {
        return isBigEndian;
    }

    public float getBytesPerSecond() {
        return bytesPerSecond;
    }

    public AudioFormat getFormat() {
        return format;
    }

    public int getNumChannels() {
        return numChannels;
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public float getByteRate() {
        return byteRate;
    }

    public int getBytesPerSample() {
        return bitsPerSample;
    }

}
