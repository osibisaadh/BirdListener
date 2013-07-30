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

    private List<Word> words;

    public Phrase(List<Word> words) {
        this.words = words;
    }

    public double match(Phrase phrase){
        List<Word> first;
        List<Word> second;
        if(phrase.words.size() > words.size()){
            first = new ArrayList<Word>(phrase.words);
            second = new ArrayList<Word>(words);
        }
        else{
            first = new ArrayList<Word>(words);
            second = new ArrayList<Word>(phrase.words);
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
            second.remove(similarity[i][0]);
        }

        return 0.0;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
