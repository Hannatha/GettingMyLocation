package sg.edu.rp.myapplicationdev.android.gettingmylocation;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class MyService extends Service {
    public MyService() {
    }

    private MediaPlayer player = new MediaPlayer();

    @Override
// execution of the service will stop on calling this method
    public void onDestroy() {
        super.onDestroy();

        // stopping the process
        player.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    // execution of service will start on calling this method
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "music.mp3");
            Log.d("TAG", file.getPath());
            // specify the path of the audio file
            player.setDataSource(file.getPath());
            player.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // providing the boolean value as true to play the audio on loop
        player.setLooping(true);

        // starting the process
        player.start();

        // returns the status of the program
        return START_STICKY;
    }
}