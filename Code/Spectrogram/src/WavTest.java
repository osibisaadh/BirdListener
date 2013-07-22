import com.musicg.graphic.GraphicRender;

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
//        WaveFile waveFile = new WaveFile("F:\\Music\\Busker Busker\\Busker Busker - Wrap Up (2012)\\Busker Busker - Wrap Up [www.k2nblog.com]\\01 그댈 마주하는건 힘들어 (그마힘).wav");
        System.out.println("Number of samples: " + waveFile.getHeader().getNumOfSamples());
        System.out.println(waveFile.getHeader().getBitsPerSample());
        short[] amplitudes = waveFile.getData();
        Spectrogram spectrogram = new Spectrogram(waveFile);
        double[] data = spectrogram.getSpectrogramData();

//        for(int i = 0; i < waveFile.getHeader().getSampleRate(); i++){
//            for(int j = 0; j < (amplitudes[i]+5000)/100;j++ )
//                System.out.print(" ");
//            System.out.println(amplitudes[i]);
//        }

            GraphicRender render = new GraphicRender();
            render.renderSpectrogramData(spectrogram.getFFTSpectrogramData(1024, 0), "D:\\wwwwd.jpg" );


//        System.out.println("Length: " + data.length);
//        for(int i =0; i < 100; i++){
//            for(int j = 0; j < (data[i]+40000)/3000;j++ )
//                System.out.print(" ");
//            System.out.println(i + ": " + data[i]);
//        }
        data = spectrogram.getFrequencyIntensity();
        System.out.println("Amps!!!!________________________");
        System.out.println("Length: " + data.length);
        for(int i =0; i < 100; i++){
            for(int j = 0; j < (data[i]+5000)/500;j++ )
                System.out.print("*");
            System.out.println("");
//            System.out.println(i + ": " + data[i]);
        }

        data = new FastFT().getMagnitudes(spectrogram.getFrequencyIntensity());
        System.out.println("Amps!!!!________________________");
        System.out.println("Length: " + data.length);
        for(int i =0; i < 100; i++){
            for(int j = 0; j < (data[i]+5000)/500;j++ )
                System.out.print("*");
            System.out.println("");
//            System.out.println(i + ": " + data[i]);
        }

    }

}
