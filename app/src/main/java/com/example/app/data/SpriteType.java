package com.example.app.data;

import android.graphics.Bitmap;

/**
 * Created by John on 3/1/14.
 */
public class SpriteType {

    private Bitmap image;

    public Bitmap getImage(){return this.image;}

    public SpriteType(Bitmap image, float widthPercent, float heightPercent){
        this.image = image;
        int width = (int)(DataModel.toAbsoluteWidth(widthPercent) + .5f);
        int height = (int)(DataModel.toAbsoluteHeight(heightPercent) + .5f);
        this.image = Bitmap.createScaledBitmap(image, width, height, true);
    }
}
