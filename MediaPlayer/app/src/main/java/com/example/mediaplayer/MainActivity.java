package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    // Variables
    private Button playPause;
    private Button browse_btn;
    private Boolean isPlaying;


    private Uri uri;
    private VideoView video_view;

    private static final int REQUEST_PICK_VIDEO = 1;

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*"); // Set the MIME type to filter for video files

        startActivityForResult(intent, REQUEST_PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_VIDEO && resultCode == RESULT_OK) {
            uri = data.getData();
            video_view.setVideoURI(uri);
            video_view.start();
            playPause.setText("Pause");
            isPlaying = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiation
        playPause = findViewById(R.id.btn_play_pause);
        browse_btn = findViewById(R.id.browse_btn);
        isPlaying = false;
        uri = null;
        video_view = findViewById(R.id.video_view);

        // Default Text
        browse_btn.setText("Browse Files");
        playPause.setText("Play");

        browse_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openFilePicker();
            }
        });

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri == null){
                    Toast.makeText( getApplicationContext() , "Select Video first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isPlaying){
                    playPause.setText("Play");
                    video_view.pause();
                    isPlaying = false;
                }else{
                    playPause.setText("Pause");
                    video_view.start();
                    isPlaying = true;
                }
            }
        });

        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                isPlaying = false;
                Toast.makeText( getApplicationContext() , "Video Completed!!", Toast.LENGTH_SHORT).show();
                playPause.setText("Play");
            }
        });
    }
}
