/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/16/13
 * Time: 1:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class WavTest {
    public static void main(String[] args){
        WaveFile waveFile = new WaveFile("testData\\1.wav");
        System.out.println("Number of samples: " + waveFile.getHeader().getNumOfSamples());
        System.out.println(waveFile.getHeader().getBitsPerSample());
        short[] amplitudes = waveFile.getData();
        for(int i = 0; i < waveFile.getHeader().getSampleRate(); i++){
            for(int j = 0; j < (amplitudes[i]+5000)/100;j++ )
                System.out.print(" ");
            System.out.println(amplitudes[i]);
        }
    }

}
