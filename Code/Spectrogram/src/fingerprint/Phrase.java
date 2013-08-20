package fingerprint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/26/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Phrase {

    private final int MAX_LENGTH_DIFF = 4;
    private List<Word> words;
    private int lengthInMilisec = 0;
    private String birdName;

    public Phrase(List<Word> words) {
        this.words = words;
        try{
            this.lengthInMilisec = (int)((double)(words.get(words.size() - 1).getWordRange().getEnd() - words.get(0).getWordRange().getStart()) / words.get(0).getWordRange().getFramesPerSecond() * 1000);
        }catch (Exception e){
        }

    }

    public double match(Phrase phrase){
        List<Word> first;
        List<Word> second;
//        System.out.println(phrase.words.size() + "--" + words.size());
        if(phrase.words.size() < words.size()){
            first = new ArrayList<Word>(phrase.words);
            second = new ArrayList<Word>(words);
        }
        else{
            first = new ArrayList<Word>(words);
            second = new ArrayList<Word>(phrase.words);
        }
        double[][] similarity = new double[first.size()][2];

        for(int i = 0; i < first.size();i++){
            double matchs = 0;
            for(int k = 0; k < second.size(); k++){
                double wordSimilarity = first.get(i).match(second.get(k));
                if(wordSimilarity > 0.6)
                    matchs++;
                if(similarity[i][0] < wordSimilarity){
                    similarity[i][0] = wordSimilarity;
                    similarity[i][1] = matchs;
                }
            }
//            second.remove(similarity[i][1]);
        }

        double similaritySum = 0;
        int matchCount =0;
        for(int i = 0; i < similarity.length; i++){
            similaritySum += similarity[i][0];
            if(similarity[i][0] >= 0.8)
                matchCount++;

        }

        double lengthDiff = 1.0;
//        if(Math.abs(phrase.words.size()-words.size()) > MAX_LENGTH_DIFF){
//            double larger = Math.max(words.size(), phrase.words.size());
//            double smaller = Math.min(words.size(), phrase.words.size());
//            lengthDiff = smaller / (larger - MAX_LENGTH_DIFF);
//
//        }

        return similaritySum / (double)similarity.length * lengthDiff;
    }

    public List<Word> getWords() {
        return words;
    }

    public int[] getPrint(){
//        int[] wordPrintSums = new int[words.get(0).getPrint().length];
//        System.out.println("PHRASE_____________");
//        for(int i = 0; i < words.size(); i++){
//            int[] wordPrint = words.get(i).getPrint();
//            for(int k = 0; k < wordPrintSums.length; k++){
//                wordPrintSums[k] += wordPrint[k];
//            }
//            System.out.println("Word " + i + " : " + words.get(i).toString());
//        }

        //0: WordNum || 1: length in miliseconds || 2: beginning freq || 3: freqChangeDirection begin:mid || 4: frequencyChange begin:mid || 5: midFreq || 6: freqChangeDirection mid:end || 7: frequencyChange mid:end || 8:endFreq
        int WORD_NUM_INDEX = 0;
        int MILISEC_INDEX = 1;
        int BEGIN_FREQ_INDEX = 2;
        int BEG_MID_DIRECTION_INDEX = 3;
        int BEG_MID_CHANGE_INDEX = 4;
        int MID_FREQ_INDEX = 5;
        int MID_END_DIRECTION_INDEX = 6;
        int MID_END_CHANGE_INDEX = 7;
        int END_FREQ_INDEX = 8;

        int[] print = new int[9];
        print[WORD_NUM_INDEX] = words.size();
        print[MILISEC_INDEX] = lengthInMilisec;
        print[BEGIN_FREQ_INDEX] = words.get(0).getPrint()[0];
        print[MID_FREQ_INDEX] = words.get(words.size()/2).getPrint()[0];
        print[BEG_MID_DIRECTION_INDEX] = getDirection(print[BEGIN_FREQ_INDEX], print[MID_FREQ_INDEX]).ordinal();
        print[BEG_MID_CHANGE_INDEX] = Math.abs( print[BEGIN_FREQ_INDEX]- print[MID_FREQ_INDEX]);
        print[END_FREQ_INDEX] = words.get(words.size()-1).getPrint()[0];
        print[MID_END_DIRECTION_INDEX] = getDirection(print[MID_FREQ_INDEX], print[END_FREQ_INDEX]).ordinal();
        print[MID_END_CHANGE_INDEX] = Math.abs(print[MID_FREQ_INDEX] - print[END_FREQ_INDEX]);
//        for(int k = 0; k < wordPrintSums.length; k++){
//            print[k + 2] = wordPrintSums[k]/print[0];
//        }

        return print;
    }

    public void setWords(List<Word> words) {
        this.words = words;
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
}
