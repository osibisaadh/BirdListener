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
    private  final int MAX_FREQ_DIFF = 10;
    private final int MAX_LENGTH_DIFF = 40;
    private final int NUM_OF_FREQUENCIES = 2;
    private double[][] spectrogram;
    private int wordLength;
    private int maxFreq = Integer.MIN_VALUE;
    private int minFreq = Integer.MAX_VALUE;
    private WordRange wordRange;
    private FreqDirection direction;
    private double[] notableFreq = new double[NUM_OF_FREQUENCIES];

    public Word(double[][] spectrogram){
        this.spectrogram = spectrogram;
    }

    public Word(double[][] spectrogram, WordRange wordRange){
        this.spectrogram = spectrogram;
        this.wordRange = wordRange;
        this.wordLength = wordRange.getEnd() - wordRange.getStart();
        initNotableFreq();
        findMinAndMaxFreq();
    }

    private void findMinAndMaxFreq(){

        for(int i =0; i < spectrogram.length; i ++){
            for(int k = 0; k < spectrogram[i].length; k++){
                if(spectrogram[i][k] >= 0.85){
                    if(k > maxFreq)
                        maxFreq = k;
                    else if(k < minFreq)
                        minFreq = k;
                }
            }
        }
    }

    private void initNotableFreq(){
        int maxAmpIndex = 0;
        //most intense freq at beginning
        for(int i = 0; i < spectrogram[0].length; i++){
            if(spectrogram[0][i] > spectrogram[0][maxAmpIndex])
                maxAmpIndex = i;
        }
        notableFreq[0] = maxAmpIndex;


        //Most intense freq at end.
        for(int i = 0; i < spectrogram[spectrogram.length-1].length; i++){
            if(spectrogram[spectrogram.length-1][i] > spectrogram[spectrogram.length-1][maxAmpIndex])
                maxAmpIndex = i;
        }
        notableFreq[1] = maxAmpIndex;
        if(notableFreq[0] > notableFreq[1])
            direction = FreqDirection.Down;
        else
            direction = FreqDirection.Up;
    }

    public double match(Word word){
        double matchPrecent =0.0;
        for(int i = 0; i < notableFreq.length; i++){
            if(notableFreq[i] >= word.notableFreq[i] - MAX_FREQ_DIFF && notableFreq[i] <= word.notableFreq[i] + MAX_FREQ_DIFF){
                matchPrecent = 1.0;
            }
        }
        if(wordLength >= word.wordLength - MAX_LENGTH_DIFF && wordLength <= word.wordLength + MAX_LENGTH_DIFF)
            matchPrecent *= 1.0;
        else
            matchPrecent *= 0.3;

        if(word.direction != word.direction){
            matchPrecent *= 0.2;
        }

        if(!(maxFreq >= word.maxFreq - MAX_FREQ_DIFF && maxFreq <= maxFreq + MAX_FREQ_DIFF))
            matchPrecent *= 0.2;
        if(!(minFreq >= word.minFreq - MAX_FREQ_DIFF && minFreq <= minFreq + MAX_FREQ_DIFF))
            matchPrecent *=0.2;


        return matchPrecent;
    }

    public double matchFull(Word word){
        List<IntensityPoint> curPoints = findIntensityPoints();
        List<IntensityPoint> paramPoints = word.findIntensityPoints();

        boolean[] matches = new boolean[curPoints.size()];
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
//        System.out.println(numTrue + " / " + matches.length);
        double similarity = (double)numTrue/(double)matches.length;
//        System.out.println( curPoints.size()+ ", " + paramPoints.size() + " : " + similarity);


//        double lengthDiff = 1.0;
//        if(Math.abs(curPoints.size()-paramPoints.size()) > MAX_LENGTH_DIFF){
//            double larger = Math.max(curPoints.size(), paramPoints.size());
//            double smaller = Math.min(curPoints.size(), paramPoints.size());
//            lengthDiff = smaller / larger;
//
//        }
        //System.out.println(similarity + ", " + lengthDiff);
        return similarity;//* lengthDiff;
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

    public double[] getNotableFreq() {
        return notableFreq;
    }
}
