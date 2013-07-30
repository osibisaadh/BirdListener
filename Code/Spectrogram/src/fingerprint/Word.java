package fingerprint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/25/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    private double[][] spectrogram;
    private WordRange wordRange;
    public Word(double[][] spectrogram){
        this.spectrogram = spectrogram;
    }

    public Word(double[][] spectrogram, WordRange wordRange){
        this.spectrogram = spectrogram;
        this.wordRange = wordRange;
    }

    public double match(Word word){
        List<IntensityPoint> curPoints = findIntensityPoints();
        boolean[] matches = new boolean[curPoints.size()];
        List<IntensityPoint> paramPoints = word.findIntensityPoints();
        for(int i = 0; i < curPoints.size(); i++){
            boolean pointMatched = false;
            for(int k = 0; k < paramPoints.size() && !pointMatched; k++){
                matches[i] = curPoints.get(i).isMatch(paramPoints.get(k));
                if(matches[i]){
                    pointMatched = true;
                    paramPoints.remove(k);
                }
            }
        }
        int numTrue =0;
        for(int i = 0; i < matches.length; i++){
            if(matches[i])
                numTrue++;
        }

        return numTrue / matches.length;
    }

    private List<IntensityPoint> findIntensityPoints(){
        List<IntensityPoint> points = new ArrayList<IntensityPoint>();
        for(int i = 0; i < spectrogram.length; i++){
            for(int k =0; k < spectrogram[i].length; k++){
                if(spectrogram[i][k] >= 0.8)
                    points.add(new IntensityPoint(i,k,spectrogram[i][k]));
            }
        }
        return points;
    }

    public double[][] getSpectrogram() {
        return spectrogram;
    }

    public WordRange getWordRange() {
        return wordRange;
    }
}
