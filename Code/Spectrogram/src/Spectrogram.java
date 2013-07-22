import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/18/13
 * Time: 1:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class Spectrogram {
    private double[] spectrogramData;
    private WaveFile waveFile;
    private double Fmax;

    public Spectrogram(WaveFile waveFile){
        this.waveFile = waveFile;
        Fmax = waveFile.getHeader().getSampleRate()/2;
        buildSpectrogram(waveFile.getDataInDouble());
    }

    private void buildSpectrogram(double[] data){
        spectrogramData = new double[2048];
        for(int i = 0; i < 1024; i++){
            spectrogramData[i] = data[i];
        }
        DoubleFFT_1D fft = new DoubleFFT_1D(1024);
        fft.complexForward(spectrogramData);
    }

    public double[] getSpectrogramData() {
        return spectrogramData;
    }

    public double[][] getFFTSpectrogramData(int fftSampleSize, int overlapFactor) {
        short[] data = waveFile.getData();
        int numSamples = data.length;

        double[][] spectrogram =null;
        // overlapping
        if (overlapFactor>1){
            int overlappedSamples=numSamples*overlapFactor;
            int backSamples=fftSampleSize*(overlapFactor-1)/overlapFactor;
            int fftSampleSize_1=fftSampleSize-1;
            short[] overlapAmp= new short[overlappedSamples];
            int counter=0;
            for (int i=0; i<data.length; i++){
                overlapAmp[counter++]=data[i];
                if (counter%fftSampleSize==fftSampleSize_1){
                    // overlap
                    i-=backSamples;
                }
            }
            numSamples=overlappedSamples;
            data=overlapAmp;
        }
        // end overlapping

        int numFrames=numSamples/fftSampleSize;
        int framesPerSecond=(int)(numFrames/waveFile.length());

        // set signals for fft
//        WindowFunction window = new WindowFunction();
//        window.setWindowType("Hamming");
//        double[] win=window.generate(fftSampleSize);

        double[][] signals=new double[numFrames][];
        for(int f=0; f<numFrames; f++) {
            signals[f]=new double[fftSampleSize];
            int startSample=f*fftSampleSize;
            for (int n=0; n<fftSampleSize; n++){
                signals[f][n]=data[startSample+n];//*win[n];
            }
        }
        // end set signals for fft
        double[][] absoluteSpectrogram=new double[numFrames][];
        // for each frame in signals, do fft on it
        FastFT fft = new FastFT();
        for (int i=0; i<numFrames; i++){
            absoluteSpectrogram[i]=fft.getMagnitudes(signals[i]);
        }

        if (absoluteSpectrogram.length>0){

            int numFrequencyUnit=absoluteSpectrogram[0].length;
            double unitFrequency=(double)waveFile.getHeader().getSampleRate()/2/numFrequencyUnit;  // frequency could be caught within the half of nSamples according to Nyquist theory

            // normalization of absoultSpectrogram
            spectrogram =new double[numFrames][numFrequencyUnit];

            // set max and min data
            double maxAmp=Double.MIN_VALUE;
            double minAmp=Double.MAX_VALUE;
            for (int i=0; i<numFrames; i++){
                for (int j=0; j<numFrequencyUnit; j++){
                    if (absoluteSpectrogram[i][j]>maxAmp){
                        maxAmp=absoluteSpectrogram[i][j];
                    }
                    else if(absoluteSpectrogram[i][j]<minAmp){
                        minAmp=absoluteSpectrogram[i][j];
                    }
                }
            }
            // end set max and min data

            // normalization
            // avoiding divided by zero
            double minValidAmp=0.00000000001F;
            if (minAmp==0){
                minAmp=minValidAmp;
            }

            double diff=Math.log10(maxAmp/minAmp);  // perceptual difference
            for (int i=0; i<numFrames; i++){
                for (int j=0; j<numFrequencyUnit; j++){
                    if (absoluteSpectrogram[i][j]<minValidAmp){
                        spectrogram[i][j]=0;
                    }
                    else{
                        spectrogram[i][j]=(Math.log10(absoluteSpectrogram[i][j]/minAmp))/diff;
                    }
                }
            }
            // end normalization
        }
        return spectrogram;
    }

    public double[] getAmplitudes(int length){
        double[] freqIntensity = getFrequencyIntensity();
        double[] amplitudes = new double[spectrogramData.length/2];
        for(int i = 0; i < freqIntensity.length; i++ ){

            amplitudes[i] = freqIntensity[i]/length;
        }
        return  amplitudes;
    }

    public double[] mag_sqrd(){
        double[] mag_sqrd = new double[spectrogramData.length/2];
        for(int i = 0; i < mag_sqrd.length; i++ ){
            double re = spectrogramData[i];
            double im = spectrogramData[i+(mag_sqrd.length)];
            mag_sqrd[i] = (re*re)+(im*im);
        }
        return  mag_sqrd;
    }

    public double[] decibles(){
        double[] decibles = new double[spectrogramData.length/2];
        double[] mag_sqrd = mag_sqrd();
        for(int i = 0; i <  decibles.length; i++ ){
            double re = spectrogramData[i];
            double im = spectrogramData[i+(decibles.length)];

            decibles[i] = (re == 0 && im == 0) ? 0: 10.0*Math.log10(mag_sqrd[i]);
        }
        return  decibles;
    }

    public double[] getFrequencyIntensity(){
        double[] freqIntensity = new double[spectrogramData.length/2];
        for(int i = 0; i < freqIntensity.length; i++ ){
            double re = spectrogramData[i];
            double im = spectrogramData[i+(freqIntensity.length)];
            freqIntensity[i] = Math.sqrt((re*re)+(im*im));
        }
        return  freqIntensity;
    }

    public WaveFile getWaveFile() {
        return waveFile;
    }

}
