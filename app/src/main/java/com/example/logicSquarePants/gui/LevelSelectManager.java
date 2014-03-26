package com.example.logicSquarePants.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import com.example.logicSquarePants.R;
import com.example.logicSquarePants.data.DataModel;
import com.example.logicSquarePants.data.SpriteManager;
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

    private int r;
    private int g;
    private int b;
    private int rD;
    private int gD;
    private int bD;
    private int iterations;

    public LevelSelectManager(){
        setButtonX(new int[LEVEL_PER_ROW][LEVEL_PER_COLUMN]);
        setButtonY(new int[LEVEL_PER_ROW][LEVEL_PER_COLUMN]);

        iterations = 0;
        r = (int)(Math.random() * 256);
        g = (int)(Math.random() * 256);
        b = (int)(Math.random() * 256);
        rD = (int)(Math.random() * 256);
        gD = (int)(Math.random() * 256);
        bD = (int)(Math.random() * 256);

    }
    public void showScreen(Canvas c, int maxLevelAttained) {
        int count = 0;
        int x, y; // top-left position for drawing level
        int screenWidth = DataModel.screenWidth;
        int screenHeight = DataModel.screenHeight;
        setLevelWidth((int) ((screenWidth - (BUFFER * (LEVEL_PER_ROW + 1))) / LEVEL_PER_ROW + .5f));
        setLevelHeight((int) ((screenHeight - (BUFFER * (LEVEL_PER_COLUMN + 1))) / LEVEL_PER_COLUMN + .5f));

        Paint p = new Paint();
        p.setColor(Color.WHITE);
        c.drawRect(0, 0, screenWidth, screenHeight, p);
        p.setColor(Color.argb(100, r, g, b));
        c.drawRect(0, 0, screenWidth, screenHeight, p);

        for (int j = 0; j < LEVEL_PER_COLUMN; j++) {
            for (int i = 0; i < LEVEL_PER_ROW; i++) {
                count++;
                x = (int) (((BUFFER * (i + 1) + (getLevelWidth() * i))));
                y = (int) (((BUFFER * (j + 1) + (getLevelHeight() * j))));
                drawLevelBitmap(c, x, y,  count, DataModel.getDataModel().getMaxLevelAttained());
                getButtonX()[i][j] = x;
                getButtonY()[i][j] = y;
            }
        }
        // Update the colors
        if (iterations % 4 == 0){
            if (r < rD){
                r++;
            } else if (r > rD){
                r--;
            } else {
                rD = (int)(Math.random() * 256);
            }
            if (g < gD){
                g++;
            } else if (g > gD){
                g--;
            } else {
                gD = (int)(Math.random() * 256);
            }
            if (b < bD){
                b++;
            } else if (b > bD){
                b--;
            } else {
                bD = (int)(Math.random() * 256);
            }
        }
        iterations++;
        if (iterations == Integer.MAX_VALUE){
            iterations = 0;
        }
    }

    private void drawLevelBitmap(Canvas c, int x, int y, int level, int maxLevelAttained){
        Bitmap b = null;
        if (maxLevelAttained < level){
            b = BitmapFactory.decodeResource(MainActivity.getMain().getResources(), R.drawable.lock);
            b = Bitmap.createScaledBitmap(b, getLevelWidth(), getLevelHeight(), true);

        } else {
            b = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            b = Bitmap.createScaledBitmap(b, getLevelWidth(), getLevelHeight(), true);
            Canvas bitmapCanvas = new Canvas(b);

            int width = (int)(levelWidth * .6f + .5f);
            int height = (int)(levelHeight * .7f + .5f);
            DigitsManager.getDigitsManager().drawNumber(bitmapCanvas, level, (int)((levelWidth/2.0f) - (width/2.0f) + .5f), (int)((levelHeight/2.0f) - (height/2.0f) + .5f), width, height);
        }
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(1);
        c.drawLine(x, y, x + levelWidth, y, p);
        c.drawLine(x, y, x, y + levelHeight, p);
        c.drawLine(x, y + levelHeight, x + levelWidth, y + levelHeight, p);
        c.drawLine(x + levelWidth, y, x + levelWidth, y + levelHeight, p);
        c.drawBitmap(b, x, y, p);
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
