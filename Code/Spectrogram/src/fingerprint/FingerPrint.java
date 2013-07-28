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

    private static final double AMP_THRESHHOLD = 0.8;

    private List<Phrase> phrases;

    public FingerPrint(Spectrogram spectrogram){
        double[][] data = spectrogram.getSpectrogram();
        System.out.println(data.length);
        List<Point> wordPoints = findPoints(data);
        System.out.println(wordPoints.size());
        for(Point p : wordPoints){
            System.out.println("\tstart: " + p.getStart() + " end: " + p.getEnd());
        }

    }



    private List<Point> findPoints(double[][] data){
        List<Point> wordPoints = new ArrayList<Point>();
        for(int i =0; i < data.length; i++){
            double maxAmp = 0.0;
            for(int j = 0; j < data[i].length; j++){
                if(data[i][j] > maxAmp)
                    maxAmp = data[i][j];
            }
            if(maxAmp > AMP_THRESHHOLD){
                int end = findEndPoint(data,i+1);
                wordPoints.add(new Point(i, end,data));
                i = end + 1;
            }

        }
        return wordPoints;
    }

    private int findEndPoint(double[][] data, int start){
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

}
