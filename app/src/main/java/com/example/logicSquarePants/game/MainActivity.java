package com.example.logicSquarePants.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.logicSquarePants.R;
import com.example.logicSquarePants.data.AudioManager;
import com.example.logicSquarePants.data.DataModel;
import com.example.logicSquarePants.data.SpriteManager;
import com.example.logicSquarePants.data.XMLParser;
import com.example.logicSquarePants.game.DrawView;

public class MainActivity extends ActionBarActivity {

    private static MainActivity main;
    private SpriteManager spriteManager;
    private AudioManager audioManager;

    public static MainActivity getMain(){
        return main;
    }
    public SpriteManager getSpriteManager(){return this.spriteManager;}
    public AudioManager getAudioManager() {return audioManager;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = this;
        DrawView screen = new DrawView(this);
        setContentView(screen);

        ///////////////////////////////////preliminaries done
        // init the DataModel so it knows screen dimensions
        DataModel.getDataModel();
        spriteManager = new SpriteManager();
        audioManager = new AudioManager();
        loadMusic();
        XMLParser parser = new XMLParser();
        parser.parseDataFromXML(getResources(), R.xml.level1);
        Bitmap b = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
        Canvas backgroundCanvas = new Canvas(b);
        Paint p = new Paint();
        p.setColor(Color.rgb(200,200,200));
        backgroundCanvas.drawRect(0,0,1,1, p);
        spriteManager.initBackground(b);

    }

    public void loadMusic()
    {
        audioManager.loadAudio("select", R.raw.select);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
        }
    }

}
