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

    private static final double AMP_THRESHHOLD = 0.85;
    private static final double SECONDS_BETWEEN_WORDS = 1;
    private List<Phrase> phrases;
    private double[][] data;
    private int framesPerSecond;
    private int freqRange;

    public FingerPrint(Spectrogram spectrogram){
        data = spectrogram.getSpectrogram();
        framesPerSecond = spectrogram.getFramesPerSecond();
        freqRange = data[0].length;
        List<WordRange> ranges = findPoints();
        phrases = getPhrases(getWords(ranges));
    }

    private List<Word> getWords(List<WordRange> wordRanges){
        List<Word> words = new ArrayList<Word>();
        for(WordRange p : wordRanges){
            double[][] wordData = new double[p.getEnd() - p.getStart()+1][freqRange];
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
        int lastLoc  =0;
        for(int i = 0; i < words.size(); i++){
            int startLoc = words.get(i).getWordRange().getStart();
            if(startLoc - lastLoc > SECONDS_BETWEEN_WORDS * framesPerSecond){
                int end = i;
                phrases.add(new Phrase(words.subList(start,end)));
                start = i+1;
            }
            lastLoc = words.get(i).getWordRange().getEnd();
        }
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
                int end = findEndPoint(i+1);
                if(i+2 < end)
                    ranges.add(new WordRange(i, end));
                i = end + 1;
            }

        }
        return ranges;
    }

    public double match(FingerPrint print){
        List<Phrase> first;
        List<Phrase> second;
        if(print.phrases.size() > phrases.size()){
            first = new ArrayList<Phrase>(print.phrases);
            second = new ArrayList<Phrase>(phrases);
        }
        else{
            first = new ArrayList<Phrase>(phrases);
            second = new ArrayList<Phrase>(print.phrases);
        }
        double[][] similarity = new double[first.size()][2];

        for(int i = 0; i < first.size();i++){
            for(int k = 0; k < second.size(); k++){
                double wordSimilarity = first.get(i).match(second.get(k));
                if(similarity[i][0] < wordSimilarity){
                    similarity[i][0] = wordSimilarity;
                    similarity[i][1] = k;
                }
            }
            second.remove(similarity[i][1]);
        }

        double similaritySum = 0;
        for(int i = 0; i < similarity.length; i++){
            similaritySum += similarity[i][0];
        }
        return similaritySum / similarity.length;
    }

    private int findEndPoint(int start){
        int end =0;
        for(int i = start; i < data.length && end == 0; i++){
            double maxAmp = 0.0;
            for(int j = 0; j < data[i].length; j++){
                if(data[i][j] > maxAmp)
                    maxAmp = data[i][j];
            }
            if(maxAmp < AMP_THRESHHOLD)
                end = i-1;
        }
        return end;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }
}
