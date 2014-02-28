package com.example.app.game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by John on 2/28/14.
 */
public class DrawView extends View {

    public DrawView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);




        invalidate();
    }
}
