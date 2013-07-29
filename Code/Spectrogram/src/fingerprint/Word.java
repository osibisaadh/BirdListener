package fingerprint;

import spectrogram.Spectrogram;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/25/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    private double[][] spectrogram;
    private Point point;
    public Word(double[][] spectrogram){
        this.spectrogram = spectrogram;
    }

    public Word(double[][] spectrogram,Point point){
        this.point = point;
    }

    public boolean match(Word word){
        boolean isMatch = false;



        return isMatch;
    }

    public double[][] getSpectrogram() {
        return spectrogram;
    }

    public Point getPoint() {
        return point;
    }
}
