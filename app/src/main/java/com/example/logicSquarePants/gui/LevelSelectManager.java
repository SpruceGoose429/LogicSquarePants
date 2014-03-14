package com.example.logicSquarePants.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

<<<<<<< HEAD
import com.example.logicSquarePants.R;
=======
import com.example.app.R;
>>>>>>> origin/master
import com.example.logicSquarePants.data.DataModel;
import com.example.logicSquarePants.game.MainActivity;

import java.util.logging.Level;

/**
 * Created by John on 3/7/14.
 */
public class LevelSelectManager {
    private static final float BUFFER = 5;
    private static final int LEVEL_PER_ROW = 3;
    private static final int NUM_OF_COLUMNS = 5;

    private int levelWidth;
    private int levelHeight;

    public LevelSelectManager(){

    }
    public void showScreen(Canvas c, int maxLevelAttained){
        int count = 0;
        int screenWidth = DataModel.screenWidth;
        int screenHeight = DataModel.screenHeight;
        levelWidth = (int) ((screenWidth - (BUFFER * (LEVEL_PER_ROW + 1))) / LEVEL_PER_ROW + .5f);
        levelHeight = (int) ((screenHeight - (BUFFER * (NUM_OF_COLUMNS + 1))) / NUM_OF_COLUMNS + .5f);
        for (int i = 0; i < LEVEL_PER_ROW; i++){
            count++;
            drawLevelBitmap(c, (int) (((BUFFER * (i + 1) + (levelWidth * i)))), (int) BUFFER, i + 1, 1);
        }
    }
    private void drawLevelBitmap(Canvas c, int x, int y, int level, int maxLevelAttained){
        Bitmap b;
        if (maxLevelAttained < level){
             b = BitmapFactory.decodeResource(MainActivity.getMain().getResources(), R.drawable.lock);
             b = Bitmap.createScaledBitmap(b, levelWidth, levelHeight, true);

        } else {
            b = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(b);
            b = Bitmap.createScaledBitmap(b, levelWidth, levelHeight, true);
            bitmapCanvas = new Canvas(b);

            Paint p=createAvailableLevel(level,Color.WHITE,Color.RED);
            bitmapCanvas.drawRect(0, 0, 1, 1, p);
            bitmapCanvas.drawText(String.valueOf(level), levelWidth/5, 300, p);

            p=createAvailableLevel(level+1,Color.WHITE,Color.GREEN);
            bitmapCanvas.drawRect(100, 0, 1, 1, p);
            bitmapCanvas.drawText(String.valueOf(level+1), levelWidth*2, 300, p);


        }
        c.drawBitmap(b, x, y, new Paint());
    }
    
    public Paint createAvailableLevel(int level, int bg, int text){

        Paint p = new Paint();
       // p.setColor(bg);
        p.setColor(text);
        p.setTextSize(300);
        return p;
    }

}
