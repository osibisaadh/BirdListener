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
    private  final int MAX_FREQ_DIFF = 30;
    private final int MAX_NOTABLE_FREQ_DIFF = 10;
    private final int MAX_LENGTH_DIFF = 10;
    private final int NUM_OF_FREQUENCIES = 3;
    private double[][] spectrogram;
    private int lengthInMiliSec;
    private int maxFreq = Integer.MIN_VALUE;
    private int minFreq = Integer.MAX_VALUE;
    private double freqChange;
    private WordRange wordRange;
    private FreqDirection direction;
    private int[] notableFreq = new int[NUM_OF_FREQUENCIES];
    private String textRepresentation;

    public Word(double[][] spectrogram){
        this.spectrogram = spectrogram;
        initNotableFreq();
        findMinAndMaxFreq();
    }

    public Word(double[][] spectrogram, WordRange wordRange){
        this.spectrogram = spectrogram;
        this.wordRange = wordRange;
        this.lengthInMiliSec = (int)((float)(wordRange.getEnd() - wordRange.getStart())/wordRange.getFramesPerSecond() * 1000);
        initNotableFreq();
        findMinAndMaxFreq();
    }

    private void findMinAndMaxFreq(){

        for(int i =0; i < spectrogram.length; i ++){
            for(int k = 0; k < spectrogram[i].length; k++){
                if(spectrogram[i][k] >= 0.80){
                    if(k > maxFreq)
                        maxFreq = k;
                    if(k < minFreq)
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

        maxAmpIndex = 0;
        //Most intense freq in middle
        for(int i = 0; i < spectrogram[spectrogram.length/2].length; i++){
            if(spectrogram[spectrogram.length/2][i] > spectrogram[spectrogram.length/2][maxAmpIndex])
                maxAmpIndex = i;
        }
        notableFreq[1] = maxAmpIndex;

        maxAmpIndex = 0;
        //Most intense freq at end.

        for(int i = 0; i < spectrogram[spectrogram.length-1].length; i++){
            if(spectrogram[spectrogram.length-1][i] > spectrogram[spectrogram.length-1][maxAmpIndex])
                maxAmpIndex = i;
        }
        notableFreq[2] = maxAmpIndex;
    }

    public double match(Word word){
        double matchPrecent = 1.0;
        for(int i = 0; i < notableFreq.length; i++){
            if(!(notableFreq[i] >= word.notableFreq[i] - MAX_NOTABLE_FREQ_DIFF && notableFreq[i] <= word.notableFreq[i] + MAX_NOTABLE_FREQ_DIFF)){
                matchPrecent *= 0.8;
            }
        }

        if(!(freqChange >= word.freqChange - 3 && freqChange <= word.freqChange + 3))
            matchPrecent *= 0.5;

        if(!(lengthInMiliSec >= word.lengthInMiliSec - MAX_LENGTH_DIFF && lengthInMiliSec <= word.lengthInMiliSec + MAX_LENGTH_DIFF))
            matchPrecent *= 0.7;

        if(direction.ordinal() != word.direction.ordinal()){
            matchPrecent *= 0.32;
        }

        if(!(maxFreq >= word.maxFreq - MAX_FREQ_DIFF && maxFreq <= word.maxFreq + MAX_FREQ_DIFF))
            matchPrecent *= 0.4;
        if(!(minFreq >= word.minFreq - MAX_FREQ_DIFF && minFreq <= word.minFreq + MAX_FREQ_DIFF))
            matchPrecent *=0.4;


        return matchPrecent;
    }

    public int[] getPrint(){
        int[] print = new int[10];
        print[0] = notableFreq[0];
        print[1] = getDirection(notableFreq[0], notableFreq[1]).ordinal();
        print[2] = notableFreq[1];
        print[3] = getDirection(notableFreq[1], notableFreq[2]).ordinal();
        print[4] = notableFreq[2];
        print[5] = maxFreq;
        print[6] = minFreq;
        print[7] = lengthInMiliSec;
        print[8] = Math.abs(notableFreq[1] - notableFreq[0]);
        print[9] = Math.abs(notableFreq[1] - notableFreq[2]);
        return print;
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

    private FreqDirection getDirection(int f1, int f2){
        FreqDirection direction = null;
        if(f1 > f2)
            direction = FreqDirection.Down;
        else if(f1 < f2)
            direction = FreqDirection.Up;
        if(f1 > f2-2 && f1 < f2 + 2)
            direction = FreqDirection.Even;
        return direction;
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

    public String toString(){
        String word = "";
        int[] print = getPrint();
        for(int i = 0; i < print.length; i++){
            word += print[i];
            if(i < print.length-1)
                word += "-";
        }
        return word;
    }
}
