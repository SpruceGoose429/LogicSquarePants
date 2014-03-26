package com.example.logicSquarePants.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.logicSquarePants.R;
import com.example.logicSquarePants.game.MainActivity;

/**
 * Created by John on 3/26/14.
 */
// A class to manage bitmaps corresponding to digits so we don't have to worry about screen size differences
public class DigitsManager {
    private static DigitsManager digitsManager;
    private Bitmap digits[];
    private DigitsManager(){
        Bitmap source = BitmapFactory.decodeResource(MainActivity.getMain().getResources(), R.drawable.digits);
        digits = new Bitmap[10];
        for (int i = 0; i < digits.length; i++){
            digits[i] = Bitmap.createBitmap(source, i * (source.getWidth()/10), 0, source.getWidth()/10, source.getHeight());
        }
    }

    public static DigitsManager getDigitsManager(){
        if (digitsManager == null){
            digitsManager = new DigitsManager();
        }
        return digitsManager;
    }

    public void drawNumber(Canvas c, int number, int x, int y, int width, int height){
//        if (width == 0){
//            width = (int)((height * (float)digits[0].getWidth()) / (float)digits[0].getHeight() + .5f);
//        }
//        if (height == 0){
//            height = (int)((width * (float)digits[0].getHeight()) / (float)digits[0].getHeight() + .5f);
//        }
        int length = (number == 0) ? 1 : (int) Math.log10(number) + 1;
        int count = 1;
        int m = 10;
        while (count <= length) {
            c.drawBitmap(digits[number % m / (m / 10)], null, new Rect(x + (int)((count-1) * (width/(float)length) + .5f), y, x + (int)((count) * (width/(float)length)), y + height), new Paint());
            m *= 10;
            count++;
        }
    }
}
