package com.example.app.data;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by John on 3/1/14.
 */
public class Sprite {
    private SpriteType spriteType;
    private float x;
    private float y;


    public Sprite(SpriteType spriteType, float xPercent, float yPercent){
        this.spriteType = spriteType;
        x = DataModel.getDataModel().toAbsoluteWidth(xPercent);
        y = DataModel.getDataModel().toAbsoluteHeight(yPercent);
    }


    public void drawSelf(Canvas c){
        c.drawBitmap(spriteType.getImage(), DataModel.getDataModel().getTransX() + x, DataModel.getDataModel().getTransY() + y, new Paint());
    }
}
