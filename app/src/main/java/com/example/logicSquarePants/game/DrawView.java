package com.example.logicSquarePants.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.logicSquarePants.data.DataModel;

/**
 * Created by John on 2/28/14.
 */
public class DrawView extends View {

    //for zooming in and out
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    private boolean moved;

    MotionEvent me;

    // for moving the map
    float lastX;
    float lastY;
    long dragTime;

    public DrawView(Context context) {
        super(context);
        dragTime = System.currentTimeMillis();
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mScaleFactor = DataModel.getDataModel().getScaleFactor();
        // Scale the picture
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);

        // Update the sprite manager
        MainActivity.getMain().getSpriteManager().update(canvas);

        // Restore scaling
        canvas.restore();

        @SuppressLint("DrawAllocation") OnTouchListener otl = new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                me = ev;
                int col;
                int row;
                // Let the ScaleGestureDetector inspect all events.
                DataModel dataModel = DataModel.getDataModel();
                mScaleDetector.onTouchEvent(ev);
                if (ev.getPointerCount() > 1){
                    dragTime = System.currentTimeMillis() + 200;
                }
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    moved = false;
                }
                if (ev.getAction() == MotionEvent.ACTION_UP){
                    if(moved == false) {

                        lastX = DataModel.getDataModel().toUnscaledX(ev.getX());
                        lastY = DataModel.getDataModel().toUnscaledY(ev.getY());
                        col = dataModel.calculateCol(lastX);
                        row = dataModel.calculateRow(lastY);
                        if (col < 0 || col >= dataModel.getColCount() || row < 0 || row >= dataModel.getRowCount())
                            return true;
                        if (dataModel.getCurrentNodes()[row][col] == false) {
                            dataModel.getCurrentNodes()[row][col] = true;
                        } else if (dataModel.getCurrentNodes()[row][col] == true) {
                            dataModel.getCurrentNodes()[row][col] = false;
                        }
                    }
                } else if (ev.getAction() == MotionEvent.ACTION_MOVE && ev.getPointerCount() == 1 && System.currentTimeMillis() >= dragTime){
                    moved = true;
                    float newX = ev.getX();
                    float newY = ev.getY();
                    if (DataModel.getDataModel().getMenuOn() == false){
                        DataModel.getDataModel().setTransX(DataModel.getDataModel().getTransX() + (newX - lastX));
                        DataModel.getDataModel().setTransY(DataModel.getDataModel().getTransY() + (newY - lastY));
                    }
                    // make sure there is no white space
                    DataModel.getDataModel().examineBounds();
                    lastX = newX;
                    lastY = newY;
                }
                return true;
            }
        };
        this.setOnTouchListener(otl);
        DataModel.getDataModel().setOnTouchListener(otl);
        // Paint it again!
        this.invalidate();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {


        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            moved = true;
            if (DataModel.getDataModel().getMenuOn()){
                return true;
            }

            MotionEvent.PointerCoords outPointerCoordinates = new MotionEvent.PointerCoords();
            me.getPointerCoords(0, outPointerCoordinates);
            float x1 = outPointerCoordinates.x;
            float y1 = outPointerCoordinates.y;

            me.getPointerCoords(1, outPointerCoordinates);
            float x2 = outPointerCoordinates.x;
            float y2 = outPointerCoordinates.y;

            float transRatio = 3000;

            float x3 = (x2 + x1) / 2;
            float y3 = (y2 + y1) / 2;

            x3 = DataModel.toRelativeWidth(x3);
            y3 = DataModel.toRelativeHeight(y3);

            DataModel.getDataModel().setTransX(DataModel.getDataModel().getTransX() + (transRatio * x3/100.0f));
            DataModel.getDataModel().setTransY(DataModel.getDataModel().getTransY() + (transRatio * y3/100.0f));

            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            //Update the dataModel with the scale factor
            DataModel.getDataModel().setScaleFactor(mScaleFactor);
            // Make sure there is no white space
            DataModel.getDataModel().examineBounds();
            invalidate();
            return true;
        }
    }
}
