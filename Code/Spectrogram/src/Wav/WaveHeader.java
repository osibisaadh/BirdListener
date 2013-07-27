package Wav;

import javax.sound.sampled.AudioFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/15/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class WaveHeader {

    private final int BITS_PER_BYTE = 8;
    private AudioFormat format;
    private int numChannels;
    private float sampleRate;
    private float byteRate;
    private int bitsPerSample;
    private boolean isBigEndian;
    private float bytesPerSecond;
    private int bytesPerSample;
    private int numOfSamples;
    private int dataLength;


    public WaveHeader(AudioFormat format, int dataLength){
        this.format = format;
        this.numChannels = format.getChannels();
        this.sampleRate = format.getSampleRate();
        this.bitsPerSample = format.getSampleSizeInBits();
        this.byteRate = sampleRate * numChannels * bitsPerSample;
        this.isBigEndian = format.isBigEndian();
        this.bytesPerSecond = sampleRate * (bitsPerSample/BITS_PER_BYTE) *numChannels;
        this.bytesPerSample = bitsPerSample/BITS_PER_BYTE;
        this.numOfSamples = dataLength/bytesPerSample;
        this.dataLength = dataLength;

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

    public int getBitsPerSample() {
        return bitsPerSample;
    }

    public int getBytesPerSample() {
        return bytesPerSample;
    }

    public int getNumOfSamples() {
        return numOfSamples;
    }

    public int getDataLength() {
        return dataLength;
    }

}
