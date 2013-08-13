package com.example.BirdI;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ohardy
 * Date: 8/13/13
 * Time: 2:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class BirdiRecorder {

    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    private int i;
    private MediaRecorder recorder = null;

    private MediaPlayer   mPlayer = null;

    public BirdiRecorder() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/birdi";
        File folder = new File(mFileName);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            System.out.println("YAY");
        } else {
            System.out.println("NEJ");
        }
    }

    private String getNextName(){
        try{
            String fileName = "bird_recording_";
            System.out.println(mFileName + "/" + fileName);
            File file = new File(mFileName);
            int lastNum = 1;
            if(file.listFiles().length > 0){
                for(File f : file.listFiles()){
                    System.out.println(f.getName());
                    int tempNum = Integer.parseInt(f.getName().split("_")[2].split("\\.")[0]);
                    if(tempNum > lastNum)
                        lastNum = tempNum;
                }
            }
            fileName += lastNum + 1;
            return fileName;
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public void startRecording() {
        // TODO Auto-generated method stub
        i++;
        String fileName = mFileName + "/" + getNextName() + ".3gp";
        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        recorder.start();
    }

    public void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }
}
