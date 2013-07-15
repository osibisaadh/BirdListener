import com.musicg.graphic.GraphicRender;
import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;


/**
 * Created with IntelliJ IDEA.
 * User: Osibisaad
 * Date: 7/12/13
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {

    public static void main(String[] args) {
        try{
            Wave wave = new Wave("D:\\Downloads\\1.wav");
            Spectrogram spectrogram = wave.getSpectrogram();
            GraphicRender render = new GraphicRender();

            double[][] data = spectrogram.getNormalizedSpectrogramData();
            int count = 0;
            double max = 0;
            double min = 1;
            int dlength = 0;
            boolean dChanged = false;
            boolean first = true;
            for(double[] d : data){
                if(first){
                    dlength = d.length;
                    first = false;
                }
                for(int i = 0; i < d.length; i++){
                    System.out.print(d[i] + "--" );
                    if(max < d[i])
                        max = d[i];
                    else if(min > d[i])
                        min = d[i];
                }
                if(dlength < d.length){
                    dChanged = true;
                    dlength = d.length;
                }
                count++;
                System.out.println("");
            }

            System.out.println("x = " + count);
            System.out.println("y = " + dlength + " changed? " + dChanged);
            System.out.println("Max: " + max + "\nMin: " + min );
            render.setVerticalMarker(1000);
            render.renderSpectrogramData(data, "D:\\11.jpg");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
