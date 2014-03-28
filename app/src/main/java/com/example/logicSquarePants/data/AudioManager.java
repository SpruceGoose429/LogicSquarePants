package com.example.logicSquarePants.data;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.logicSquarePants.R;
import com.example.logicSquarePants.game.MainActivity;

import java.util.HashMap;

/**
 * Created by Steven Liao on 3/18/14.
 */
public class AudioManager extends Service implements MediaPlayer.OnErrorListener
{
    private IBinder mediaBinder = new Binder();
    private HashMap<String, MediaPlayer> mp3Map;

    public AudioManager()
    {
        mp3Map = new HashMap<String, MediaPlayer>();
    }

    public void loadAudio(String audioName, int audioNameFile)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.getMain(), audioNameFile);
        mp3Map.put(audioName, mediaPlayer);
    }

    public void play(String audioName)
    {
        MediaPlayer music = mp3Map.get(audioName);
        music.start();
    }

    public IBinder onBind(Intent i)
    {
        return mediaBinder;
    }
    public boolean onError(MediaPlayer mediaPlayer, int x, int y)
    {
        return false;
    }
}
