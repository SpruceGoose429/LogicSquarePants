package com.example.app.game;

import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.app.R;
import com.example.app.data.DataModel;
import com.example.app.data.SpriteManager;
import com.example.app.data.XMLParser;

public class MainActivity extends ActionBarActivity {

    // singleton
    private static MainActivity main;
    public static MainActivity getMain(){return main;}

    private SpriteManager spriteManager;
    public SpriteManager getSpriteManager(){return this.spriteManager;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the singleton
        main = this;
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // create the screen
        DrawView screen = new DrawView(this);
        // set the content view
        setContentView(screen);
        ///////////////////////////////////preliminaries done
        // init the DataModel so it knows screen dimensions
        DataModel.getDataModel();
        spriteManager = new SpriteManager();
        XMLParser parser = new XMLParser();
        parser.parseDataFromXML(getResources(), R.xml.example);
        spriteManager.initBackground(BitmapFactory.decodeResource(getResources(), R.drawable.background));

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
