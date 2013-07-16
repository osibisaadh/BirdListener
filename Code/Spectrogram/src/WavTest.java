/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/16/13
 * Time: 1:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class WavTest {
    public static void main(String[] args){
        WaveFile waveFile = new WaveFile("D:\\Downloads\\1.wav");
        System.out.println(waveFile.getHeader().getNumOfSamples());
        System.out.println(waveFile.getHeader().getBitsPerSample());
        short[] amplitudes = waveFile.getSampleAmplitudes();
        for(int i = 0; i < 1000; i++){
            for(int j = 0; j < (amplitudes[i]+600)/12;j++ )
                System.out.print(" ");
            System.out.println(i +":" +amplitudes[i]);
        }
    }

}
