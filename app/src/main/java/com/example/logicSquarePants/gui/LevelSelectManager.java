package com.example.logicSquarePants.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import com.example.logicSquarePants.R;
import com.example.logicSquarePants.data.DataModel;
import com.example.logicSquarePants.game.MainActivity;

/**
 * Created by John on 3/7/14.
 */
public class LevelSelectManager {
    private static final float BUFFER = 5;
    private static final int LEVEL_PER_ROW = 3;
    private static final int LEVEL_PER_COLUMN = 5;

    private int levelWidth;
    private int levelHeight;

    private int[][] buttonX;
    private int[][] buttonY;

    public LevelSelectManager(){
        setButtonX(new int[LEVEL_PER_ROW][LEVEL_PER_COLUMN]);
        setButtonY(new int[LEVEL_PER_ROW][LEVEL_PER_COLUMN]);
    }
    public void showScreen(Canvas c, int maxLevelAttained) {
        int count = 0;
        int x, y; // top-left position for drawing level
        int screenWidth = DataModel.screenWidth;
        int screenHeight = DataModel.screenHeight;
        setLevelWidth((int) ((screenWidth - (BUFFER * (LEVEL_PER_ROW + 1))) / LEVEL_PER_ROW + .5f));
        setLevelHeight((int) ((screenHeight - (BUFFER * (LEVEL_PER_COLUMN + 1))) / LEVEL_PER_COLUMN + .5f));

        for (int i = 0; i < LEVEL_PER_ROW; i++) {
            for (int j = 0; j < LEVEL_PER_COLUMN; j++) {
                count++;
                x = (int) (((BUFFER * (i + 1) + (getLevelWidth() * i))));
                y = (int) (((BUFFER * (j + 1) + (getLevelHeight() * j))));
                drawLevelBitmap(c, x, y,  count + 1, DataModel.getDataModel().getMaxLevelAttained());
                getButtonX()[i][j] = x;
                getButtonY()[i][j] = y;
            }
        }

    }

    private void drawLevelBitmap(Canvas c, int x, int y, int level, int maxLevelAttained){
        Bitmap b = null;
        if (maxLevelAttained < level){
            b = BitmapFactory.decodeResource(MainActivity.getMain().getResources(), R.drawable.lock);
            b = Bitmap.createScaledBitmap(b, getLevelWidth(), getLevelHeight(), true);

        } else {
            b = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(b);
            b = Bitmap.createScaledBitmap(b, getLevelWidth(), getLevelHeight(), true);
            bitmapCanvas = new Canvas(b);

            Paint p=createAvailableLevel(level,Color.WHITE,Color.RED);
            bitmapCanvas.drawRect(0, 0, 1, 1, p);
            bitmapCanvas.drawText(String.valueOf(level), getLevelWidth() /5, 300, p);

            p=createAvailableLevel(level+1,Color.WHITE,Color.GREEN);
            bitmapCanvas.drawRect(100, 0, 1, 1, p);
            bitmapCanvas.drawText(String.valueOf(level+1), getLevelWidth() *2, 300, p);


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

    public int getLevelWidth() {
        return levelWidth;
    }

    public void setLevelWidth(int levelWidth) {
        this.levelWidth = levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    }

    public int[][] getButtonX() {
        return buttonX;
    }

    public void setButtonX(int[][] buttonX) {
        this.buttonX = buttonX;
    }

    public int[][] getButtonY() {
        return buttonY;
    }

    public void setButtonY(int[][] buttonY) {
        this.buttonY = buttonY;
    }
    public void processLevel(int level){
        Toast.makeText(MainActivity.getMain().getApplicationContext(), String.valueOf(level), Toast.LENGTH_SHORT).show();
    }

    public int  getLevelPerRow(){ return this.LEVEL_PER_ROW;}

    public int  getLevelPerColumn(){ return this.LEVEL_PER_COLUMN;}

    public float  getBuffer(){ return this.BUFFER;}


}
