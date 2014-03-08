package com.example.app.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.app.game.MainActivity;

/**
 * Created by John on 2/28/14.
 */
public class DataModel {

    // Singleton
    private static DataModel dataModel;

    // Screen stuff
    private static int screenWidth;
    private static int screenHeight;
    private View.OnTouchListener onTouchListener;
    // current translation
    private float transX;
    private float transY;
    // current scale of the screen
    private float scaleFactor;
    public static final int NODE_WIDTH = 200;
    public static final int NODE_HEIGHT = 200;

    // Game specific stuff
    private int rowCount;
    private int colCount;
    private boolean[][] correctNodes;
    private boolean[][] currentNodes;

    // Singleton getter
    public static DataModel getDataModel(){
        if (dataModel == null){
            dataModel = new DataModel();
        }
        return dataModel;
    }

    private DataModel(){
        transX = 0.0f;
        transY = 0.0f;
        scaleFactor = 1.0f;
        // Find screen size
        Point size = new Point();
        WindowManager w = MainActivity.getMain().getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
            w.getDefaultDisplay().getSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        }else{
            Display d = w.getDefaultDisplay();
            screenWidth = d.getWidth();
            screenHeight = d.getHeight();
        }
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener){this.onTouchListener = onTouchListener;}
    public float getTransX(){return this.transX;}
    public float getTransY(){return this.transY;}
    public void setTransX(float transX){this.transX = transX;}
    public void setTransY(float transY){this.transY = transY;}
    public void setScaleFactor(float scaleFactor){this.scaleFactor = scaleFactor;}
    public float getScaleFactor(){return this.scaleFactor;}

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
    public void setColumnCount(int colCount) {
        this.colCount = colCount;
    }
    public void setCorrectNodes(boolean[][] nodes) {
        this.correctNodes = nodes;
    }
    public void setCurrentNodes(boolean[][] nodes) {
        this.currentNodes = nodes;
    }
    public int getRowCount() {
        return rowCount;
    }
    public int getColCount() {
        return colCount;
    }

    public boolean[][] getCorrectNodes() {
        return correctNodes;
    }

    public static float toRelativeWidth(float x){
        return (x / (float)screenWidth) * 100.0f;
    }
    public static float toRelativeHeight(float y){
        return (y / (float)screenHeight) * 100.0f;
    }
    public static float toAbsoluteWidth(float x){
        return (x * (float)screenWidth / 100.0f);
    }
    public static float toAbsoluteHeight(float y){
        return (y * (float)screenHeight / 100.0f);
    }

    public int calculateCol(float x) {
        return (int)(x - 10) / NODE_WIDTH;
    }

    public int calculateRow(float y) {
        return (int)(y - 10 ) / NODE_HEIGHT;
    }

    public boolean[][] getCurrentNodes() {
        return currentNodes;
    }

    public static Bitmap convertImage565(Bitmap image){
        Bitmap newB = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(newB);
        c.drawBitmap(image, 0, 0, new Paint());
        return newB;
    }

    public static Bitmap convertImage4444(Bitmap image){
        Bitmap newB = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(newB);
        c.drawBitmap(image, 0, 0, new Paint());
        return newB;
    }

    public float toUnscaledX(float x){
        x /= scaleFactor;
        x -= transX;
        return x;
    }
    public float toUnscaledY(float y){
        y /= scaleFactor;
        y -= transY;
        return y;
    }

    public void examineBounds(){
        if (scaleFactor < 1.0f){
            scaleFactor = 1.0f;
        }
        if (transX > 0){
            transX = 0;
        }
        if (transY > 0){
            transY = 0;
        }
        if (scaleFactor >= 1.0f && -1.0f * transX > screenWidth - (screenWidth / scaleFactor)){
            transX = -1.0f * (screenWidth - (screenWidth / scaleFactor));
        }
        if (scaleFactor >= 1.0f && -1.0f * transY > screenHeight - (screenHeight / scaleFactor)){
            transY = -1.0f * (screenHeight - (screenHeight / scaleFactor));
        }
    }

}
