package Drivers;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {
        try{
            //Check out audio File info
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("testData\\1.wav"));
            AudioFormat format = audioInputStream.getFormat();
            System.out.println("Channels: " + format.getChannels());
            System.out.println("Encoding: " + format.getEncoding());
            System.out.println("FrameRate: " + format.getFrameRate());
            System.out.println("Frame Size: " + format.getFrameSize());
            System.out.println("SampleRate: " + format.getSampleRate());
            System.out.println("BigEdian: " + format.isBigEndian());
            System.out.println("Framlength: " + audioInputStream.getFrameLength());
            System.out.println("available: " +audioInputStream.available());
            //Create byte array for data
            byte[] data = new byte[audioInputStream.available()];
            audioInputStream.read(data);
            System.out.println(data.length/2);
            System.out.println(data);
            int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
            // Set buffer size of 1024 frames.
            int numBytes = 1024 * bytesPerFrame;
            byte[] audioBytes = new byte[numBytes];
            int totalFramesRead =0;

            int numBytesRead = 0;
            int numFramesRead = 0;
            // Try to read numBytes bytes from the file.
            int count = 0;
//            while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
//
//                // Calculate the number of frames actually read.
//                numFramesRead = numBytesRead / bytesPerFrame;
//                totalFramesRead += numFramesRead;
//                // Here, do something useful with the audio data that's
//                // now in the audioBytes array...
//                for(int i = 0; i < numBytesRead; i++){
//                    data[count++] = audioBytes[i];
//                }
//
//
//            }
//            double[] realdata = new double[data.length/8];
//            for(int i = 0; i < realdata.length; i++){
//                realdata[i] = toDouble(new byte[]{data[i*8],data[i*8 + 1],data[i*8 + 2],data[i*8 + 3], data[i*8 + 4],data[i*8 + 5], data[i*8 + 6],data[i*8 + 7]});
//            }
//            for(int i = 0; i< realdata.length; i++){
//                System.out.println(realdata[i]);
//            }
//
//            DoubleFFT_1D fft = new DoubleFFT_1D(2048);
//            fft.complexForward(realdata);
//
//            for(int i = 0; i< realdata.length; i++){
//                System.out.println("FFT " + i + ": " + realdata[i]);
//            }
//
//
//            System.out.println("Total bytes read: " + totalFramesRead * bytesPerFrame);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
