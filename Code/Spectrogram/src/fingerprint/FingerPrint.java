package fingerprint;


import spectrogram.Spectrogram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/16/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class FingerPrint {

    private static final double AMP_THRESHHOLD = 0.80;
    private static final double SECONDS_BETWEEN_WORDS = 1;
    private List<Phrase> phrases;
    private double[][] data;
    private int framesPerSecond;
    private int freqRange;
    private String birdName;

    public FingerPrint(Spectrogram spectrogram){
        data = spectrogram.getSpectrogram();
        System.out.println(spectrogram.getWaveFile().getFileName());
        String[] nameParts = spectrogram.getWaveFile().getFileName().split("\\\\");
        birdName = nameParts[nameParts.length-1].split(" -- ")[0];
        //System.out.println(birdName);
        framesPerSecond = spectrogram.getFramesPerSecond();
        freqRange = data[0].length;
        List<WordRange> ranges = findPoints();
        phrases = getPhrases(getWords(ranges));
    }

    private List<Word> getWords(List<WordRange> wordRanges){
        List<Word> words = new ArrayList<Word>();
        for(WordRange p : wordRanges){
            double[][] wordData = new double[p.getEnd() - p.getStart()][freqRange];
            for(int i =p.getStart(); i < p.getEnd(); i++){
                for(int j = 0; j < data[i].length; j++){
                    wordData[(i-p.getStart())][j] = data[i][j];
                }
            }
            words.add(new Word(wordData, p));
        }
        return words;
    }

    private List<Phrase> getPhrases(List<Word> words){
        List<Phrase> phrases = new ArrayList<Phrase>();
        int start = 0;
        int lastLoc = 0;
        int end = 0;
        //System.out.println(words.size());
        for(int i = 0; i < words.size(); i++){
            int startLoc = words.get(i).getWordRange().getStart();
            if(lastLoc == 0)
                lastLoc = startLoc;
            end = i+1;
            if(startLoc - lastLoc > SECONDS_BETWEEN_WORDS * framesPerSecond){
                Phrase phrase = new Phrase(words.subList(start,end));
                if(phrase.getWords().size() > 0)
                    phrases.add(phrase);
                start = i+1;
            }
            lastLoc = words.get(i).getWordRange().getEnd();
        }
        if(phrases.size() == 0)
            phrases.add(new Phrase(words.subList(start,end)));
        return phrases;
    }

    private List<WordRange> findPoints(){
        List<WordRange> ranges = new ArrayList<WordRange>();
        for(int i =0; i < data.length; i++){
            double maxAmp = 0.0;
            for(int j = 0; j < data[i].length; j++){
                if(data[i][j] > maxAmp)
                    maxAmp = data[i][j];
            }
            if(maxAmp > AMP_THRESHHOLD){
                int end = findEndPoint(i);
                if(i+1 < end){
                    ranges.add(new WordRange(i, end, framesPerSecond));
                    i = end + 1;
                }
            }
        }
        return ranges;
    }

    public double match(FingerPrint print){
        List<Phrase> first;
        List<Phrase> second;
        if(print.phrases.size() > phrases.size()){
            first = new ArrayList<Phrase>(phrases);
            second = new ArrayList<Phrase>(print.phrases);
        }
        else{
            first = new ArrayList<Phrase>(print.phrases);
            second = new ArrayList<Phrase>(phrases);
        }
        double[][] similarity = new double[first.size()][2];

        for(int i = 0; i < first.size();i++){
            for(int k = 0; k < second.size(); k++){
                double wordSimilarity = first.get(i).match(second.get(k));
                if(similarity[i][0] < wordSimilarity){
                    similarity[i][0] = wordSimilarity;
                }
            }
        }

        double similaritySum = 0;
        int matchCount =0;
        for(int i = 0; i < similarity.length; i++){
            similaritySum += similarity[i][0];
            if(similarity[i][0] >= 0.8)
                matchCount++;

        }

        return similaritySum / (double)similarity.length;
    }

    public int[][] getPrint(){
        int[][] print = new int[phrases.size()][];
        for(int i = 0; i < phrases.size(); i++){
            print[i] = phrases.get(i).getPrint();
            //.out.println("\t" + phrases.get(i).toString() );
        }
        return print;
    }

    public int[] getFullPrint(){
        int[] sum = new int[phrases.get(0).getPrint().length];
        try{
            for(Phrase p : phrases){
                int[] tempPrint=  p.getPrint();
                for(int i = 0; i < sum.length; i++){
                    sum[i] += tempPrint[i];
                }
            }
            for(int i = 0; i < sum.length; i++){
                sum[i] = sum[i]/phrases.size();
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return sum;
    }

    private int findEndPoint(int start){
        int end = start-1;
        for(int i = start; i < data.length && end < start ; i++){
            double maxAmp = 0.0;
            for(int j = 0; j < data[i].length; j++){
                if(data[i][j] > maxAmp)
                    maxAmp = data[i][j];

            }
            if(maxAmp < AMP_THRESHHOLD )
                end = i-1;
        }
        return end;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public String toString(){
        String word = "";
        for(int i = 0; i < phrases.size(); i++){
            int[] print = phrases.get(i).getPrint();
            for(int k = 0; k < print.length; k++){
                word += print[k];
                if(k < print.length-1)
                    word += "-";
            }
            if(i < phrases.size()-1)
                word += "\n";
        }
        return word;
    }
}
