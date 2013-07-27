package fingerprint;

import com.musicg.wave.extension.Spectrogram;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/16/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class FingerPrint {

    private List<Phrase> phrases;

    public FingerPrint(Spectrogram spectrogram){
        double[][] data = spectrogram.getNormalizedSpectrogramData();
        double[][] wordData = new double[data.length][];
        for(int i =0; i < data.length; i++){
            for(int j = 0; j < data[i].length; i++){
                double value = data[i][j];

            }
        }
    }

}
