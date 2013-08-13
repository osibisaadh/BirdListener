package com.example.BirdI;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyActivity extends Activity {

    private ImageButton listenButton;
    private RotateAnimation anim;
    private BirdRecorder recorder;
    private boolean recording = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listenButton =(ImageButton)findViewById(R.id.listenButton);

        anim = new RotateAnimation(0,20,150,150);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setRepeatCount(Animation.ABSOLUTE);
        anim.setDuration(0);

    }



    public void onListenButtonClick(View view){
        Log.d("2","clicked");
        if(recording == false){
            recording = true;
            listenButton.startAnimation(anim);
            recorder = new BirdRecorder();
            listenButton.setImageResource(R.drawable.stop);
            recorder.startRecording();
        }
        else{
            listenButton.setAnimation(null);
            listenButton.setImageResource(R.drawable.listen);
            recording = false;
            recorder.stopRecording();
        }


    }

    public void onStopButtonClick(View view){
        recorder.stopRecording();

    }

//    public class TouchListener implements View.OnTouchListener{
//
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
////            ImageButton button = (ImageButton) view;
////            button.setImageDrawable(Drawable.createFromPath("res/drawable/listen_pressed.png"));
//            return true;
//        }
//    }
}
