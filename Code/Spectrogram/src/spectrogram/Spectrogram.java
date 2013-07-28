package spectrogram;

import Wav.WaveFile;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/18/13
 * Time: 1:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class Spectrogram {
    public static final float MIN_VALID_AMP = 0.00000000001F;
    public static final int DEFAULT_FFT_SAMPLE_SIZE = 1024;
    public static final int DEFAULT_OVERLAP = 0;

    private int framesPerSecond;
    private double[] data;
    private double[][] spectrogram;
    private WaveFile waveFile;

    public Spectrogram(WaveFile waveFile){
        this.waveFile = waveFile;
        this.data = waveFile.getNormalizedData();
        buildSpectrogram(DEFAULT_FFT_SAMPLE_SIZE);

    }

    //builds a normalized spectrogram
    private void buildSpectrogram(int fftSampleSize) {
        double[] waveData = waveFile.getNormalizedData();
        int numSamples = waveData.length;

        int numFrames=numSamples/fftSampleSize;
        framesPerSecond = (int) (numFrames / waveFile.totalSeconds());
        // set signals for fft
//        WindowFunction window = new WindowFunction();
//        window.setWindowType("Hamming");
//        double[] win=window.generate(fftSampleSize);

        double[][] signals=new double[numFrames][];
        for(int f=0; f<numFrames; f++) {
            signals[f]=new double[fftSampleSize];
            int startSample=f*fftSampleSize;
            for (int n=0; n<fftSampleSize; n++){
                signals[f][n]=waveData[startSample+n];//*win[n];
            }
        }

        double[][] rawSpectrogram = new double[numFrames][];
        // for each frame in signals, do fft on it
        FastFT fft = new FastFT();
        for (int i=0; i<numFrames; i++){
            rawSpectrogram[i]=fft.getMagnitudes(signals[i]);
        }

        int numFrequencyUnit = rawSpectrogram[0].length;
        // normalization of absoluteSpectrogram
        spectrogram = new double[numFrames][numFrequencyUnit];

        // find min and max in order to normalize spectrogram
        double maxAmp=Double.MIN_VALUE;
        double minAmp=Double.MAX_VALUE;
        for (int i=0; i<numFrames; i++){
            for (int j=0; j<numFrequencyUnit; j++){
                if (rawSpectrogram[i][j]>maxAmp){
                    maxAmp=rawSpectrogram[i][j];
                }
                else if(rawSpectrogram[i][j]<minAmp){
                    minAmp=rawSpectrogram[i][j];
                }
            }
        }

        // normalization
        if (minAmp==0){
            minAmp=MIN_VALID_AMP;
        }

        double diff=Math.log10(maxAmp/minAmp);
        for (int i=0; i<numFrames; i++){
            for (int j=0; j<numFrequencyUnit; j++){
                if (rawSpectrogram[i][j]<MIN_VALID_AMP){
                    spectrogram[i][j]=0;
                }
                else{
                    spectrogram[i][j]=(Math.log10(rawSpectrogram[i][j]/minAmp))/diff;
                    if(spectrogram[i][j] < 0.83)
                        spectrogram[i][j] = 0;
                }
            }

        }
    }

    public double[] getAmplitudes(int length){
        double[] freqIntensity = getFrequencyIntensity();
        double[] amplitudes = new double[data.length/2];
        for(int i = 0; i < freqIntensity.length; i++ ){

            amplitudes[i] = freqIntensity[i]/length;
        }
        return  amplitudes;
    }

    public double[] mag_sqrd(){
        double[] mag_sqrd = new double[data.length/2];
        for(int i = 0; i < mag_sqrd.length; i++ ){
            double re = data[i];
            double im = data[i+(mag_sqrd.length)];
            mag_sqrd[i] = (re*re)+(im*im);
        }
        return  mag_sqrd;
    }

    public double[] decibles(){
        double[] decibles = new double[data.length/2];
        double[] mag_sqrd = mag_sqrd();
        for(int i = 0; i <  decibles.length; i++ ){
            double re = data[i];
            double im = data[i+(decibles.length)];

            decibles[i] = (re == 0 && im == 0) ? 0: 10.0*Math.log10(mag_sqrd[i]);
        }
        return  decibles;
    }

    public double[] getFrequencyIntensity(){
        double[] freqIntensity = new double[data.length/2];
        for(int i = 0; i < freqIntensity.length; i++ ){
            double re = data[i];
            double im = data[i+(freqIntensity.length)];
            freqIntensity[i] = Math.sqrt((re*re)+(im*im));
        }
        return  freqIntensity;
    }

    //getters and setters
    public WaveFile getWaveFile() {
        return waveFile;
    }

    public double[] getData() {
        return data;
    }

    public double[][] getSpectrogram() {
        return spectrogram;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    //end getters and setters

}
