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
    private final int MAX_LENGTH_DIFF = 100;
    private final int NUM_OF_FREQUENCIES = 2;
    private double[][] spectrogram;
    private WordRange wordRange;
    private int[] notableFreq = new int[NUM_OF_FREQUENCIES];

    public Word(double[][] spectrogram){
        this.spectrogram = spectrogram;
    }

    public Word(double[][] spectrogram, WordRange wordRange){
        this.spectrogram = spectrogram;
        this.wordRange = wordRange;
    }

    private void initNotableFreq(){
        notableFreq[0] = wordRange.getStart();
        notableFreq[1] = wordRange.getEnd();
    }

    public double match(Word word){
        List<IntensityPoint> first;
        List<IntensityPoint> second;
        List<IntensityPoint> curPoints = findIntensityPoints();
        List<IntensityPoint> paramPoints = word.findIntensityPoints();
        if(paramPoints.size() > curPoints.size()){
            first = new ArrayList<IntensityPoint>(paramPoints);
            second = new ArrayList<IntensityPoint>(curPoints);
        }
        else{
            first = new ArrayList<IntensityPoint>(curPoints);
            second = new ArrayList<IntensityPoint>(paramPoints);
        }

        boolean[] matches = new boolean[first.size()];
        for(int i = 0; i < first.size(); i++){
            boolean pointMatched = false;
            for(int k = 0; k < second.size() && !pointMatched; k++){
                matches[i] = first.get(i).isMatch(second.get(k));
                if(matches[i]){
                    pointMatched = true;
                    second.remove(k);
                }
            }
        }
        int numTrue =0;
        for(int i = 0; i < matches.length; i++){
            if(matches[i])
                numTrue++;
        }
//        System.out.println(numTrue + " / " + matches.length);
        double similarity = (double)numTrue/(double)matches.length;
//        System.out.println( curPoints.size()+ ", " + paramPoints.size() + " : " + similarity);


        double lengthDiff = 1.0;
        if(Math.abs(curPoints.size()-paramPoints.size()) > MAX_LENGTH_DIFF){
            double larger = Math.max(curPoints.size(), paramPoints.size());
            double smaller = Math.min(curPoints.size(), paramPoints.size());
            lengthDiff = smaller / larger;

        }
        //System.out.println(similarity + ", " + lengthDiff);
        return similarity * lengthDiff;
    }

    private List<IntensityPoint> findIntensityPoints(){
        List<IntensityPoint> temppoints = new ArrayList<IntensityPoint>();
        for(int i = 0; i < spectrogram.length; i++){
            for(int k =0; k < spectrogram[i].length; k++){
                if(spectrogram[i][k] >= 0.8)
                    temppoints.add(new IntensityPoint(i,k,spectrogram[i][k]));
            }
        }

        List<IntensityPoint> points = new ArrayList<IntensityPoint>();
//        for(int i = 0; i < temppoints.size(); i++){
            points.add(temppoints.get(0));
            points.add(temppoints.get(temppoints.size()-1));
//        }

        return points;
    }

    public double[][] getSpectrogram() {
        return spectrogram;
    }

    public WordRange getWordRange() {
        return wordRange;
    }

    public int[] getNotableFreq() {
        return notableFreq;
    }
}
