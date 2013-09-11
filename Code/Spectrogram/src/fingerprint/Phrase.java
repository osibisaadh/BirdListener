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
    private final int WORD_WEIGHT = 1;
    private final int MAX_LENGTH_DIFF = 4;
    private List<Word> words;
    private int lengthInMilisec = 0;
    private String birdName;

    public Phrase(List<Word> words) {
        this.words = words;
//        System.out.println(words.size());
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

        //0: WordNum || 1: length in miliseconds || 2-9: beginning word || 3: freqChangeDirection begin:mid || 4: frequencyChange begin:mid || 5: midFreq || 6: freqChangeDirection mid:end || 7: frequencyChange mid:end || 8:endFreq
        int WORD_NUM_INDEX = 0;
//        int MILISEC_INDEX = 1;
        int BEGIN_FREQ_INDEX = 1;
        int BEGIN_FREQ_1 = 2;
        int BEGIN_FREQ_2 = 3;
        int BEGIN_FREQ_3 = 4;
        int BEGIN_FREQ_4 = 5;
        int BEGIN_FREQ_5 = 6;
//        int BEGIN_FREQ_6 = 7;
//        int BEGIN_FREQ_7 = 8;
//        int BEGIN_FREQ_8 = 9;
//        int BEGIN_FREQ_9 = 10;

        int BEG_MID_DIRECTION_INDEX = 7;
        int BEG_MID_CHANGE_INDEX = 8;
        int MID_FREQ_INDEX = 9;

        int MID_FREQ_1 = 10;
        int MID_FREQ_2 = 11;
        int MID_FREQ_3 = 12;
        int MID_FREQ_4 = 13;
        int MID_FREQ_5 = 14;
//        int MID_FREQ_6 = 17;
//        int MID_FREQ_7 = 18;
//        int MID_FREQ_8 = 21;
//        int MID_FREQ_9 = 22;

        int MID_END_DIRECTION_INDEX = 15;
        int MID_END_CHANGE_INDEX = 16;
        int END_FREQ_INDEX = 17;

        int END_FREQ_1 = 18;
        int END_FREQ_2 = 19;
        int END_FREQ_3 = 20;
        int END_FREQ_4 = 21;
        int END_FREQ_5 = 22;
//        int END_FREQ_6 = 21;
//        int END_FREQ_7 = 22;
//        int END_FREQ_8 = 33;
//        int END_FREQ_9 = 34;

        int[] print = new int[23];
        //m.out.println(words.size());
        print[WORD_NUM_INDEX] = words.size() * WORD_WEIGHT;
        //print[MILISEC_INDEX] = lengthInMilisec;
        print[BEGIN_FREQ_INDEX] = words.get(0).getPrint()[0];
        print[MID_FREQ_INDEX] = words.get(words.size()/2).getPrint()[0];

        print[BEGIN_FREQ_1] = words.get(0).getPrint()[1];
        print[BEGIN_FREQ_2] = words.get(0).getPrint()[2];
        print[BEGIN_FREQ_3] = words.get(0).getPrint()[3];
        print[BEGIN_FREQ_4] = words.get(0).getPrint()[4];
        print[BEGIN_FREQ_5] = words.get(0).getPrint()[5];
//        print[BEGIN_FREQ_6] = words.get(0).getPrint()[6];
//        print[BEGIN_FREQ_7] = words.get(0).getPrint()[7];
//        print[BEGIN_FREQ_8] = words.get(0).getPrint()[8];
//        print[BEGIN_FREQ_9] = words.get(0).getPrint()[9];


        print[MID_FREQ_1] = words.get(words.size()/2).getPrint()[1];
        print[MID_FREQ_2] = words.get(words.size()/2).getPrint()[2];
        print[MID_FREQ_3] = words.get(words.size()/2).getPrint()[3];
        print[MID_FREQ_4] = words.get(words.size()/2).getPrint()[4];
        print[MID_FREQ_5] = words.get(words.size()/2).getPrint()[5];
//        print[MID_FREQ_6] = words.get(words.size()/2).getPrint()[6];
//        print[MID_FREQ_7] = words.get(words.size()/2).getPrint()[7];
//        print[MID_FREQ_8] = words.get(words.size()/2).getPrint()[8];
//        print[MID_FREQ_9] = words.get(words.size()/2).getPrint()[9];


        print[BEG_MID_DIRECTION_INDEX] = getDirection(print[BEGIN_FREQ_INDEX], print[MID_FREQ_INDEX]).ordinal();
        print[BEG_MID_CHANGE_INDEX] = Math.abs( print[BEGIN_FREQ_INDEX]- print[MID_FREQ_INDEX]);
        print[END_FREQ_INDEX] = words.get(words.size()-1).getPrint()[0];

        print[END_FREQ_1] = words.get(words.size()-1).getPrint()[1];
        print[END_FREQ_2] = words.get(words.size()-1).getPrint()[2];
        print[END_FREQ_3] = words.get(words.size()-1).getPrint()[3];
        print[END_FREQ_4] = words.get(words.size()-1).getPrint()[4];
        print[END_FREQ_5] = words.get(words.size()-1).getPrint()[5];
//        print[END_FREQ_6] = words.get(words.size()-1).getPrint()[6];
//        print[END_FREQ_7] = words.get(words.size()-1).getPrint()[7];
//        print[END_FREQ_8] = words.get(words.size()-1).getPrint()[8];
//        print[END_FREQ_9] = words.get(words.size()-1).getPrint()[9];



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
