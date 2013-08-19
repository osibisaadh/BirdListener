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

    public Phrase(List<Word> words) {
        this.words = words;
        this.lengthInMilisec = (int)((float)(words.get(words.size() - 1).getWordRange().getEnd() - words.get(0).getWordRange().getStart())/words.get(0).getWordRange().getFramesPerSecond()) * 1000;
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
        int[] wordPrintSums = new int[7];
        for(int i = 0; i < words.size(); i++){
            int[] wordPrint = words.get(i).getPrint();
            for(int k = 0; k < wordPrintSums.length; k++){
                wordPrintSums[k] += wordPrint[k];
            }
        }
        int[] print = new int[9];
        print[0] = words.size();
        print[1] = lengthInMilisec;
        for(int k = 0; k < wordPrintSums.length; k++){
            print[k + 2] = wordPrintSums[k]/print[0];
        }

        return print;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
