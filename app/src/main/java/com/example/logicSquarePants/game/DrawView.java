package com.example.logicSquarePants.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.logicSquarePants.data.AudioManager;
import com.example.logicSquarePants.data.DataModel;

/**
 * Created by John on 2/28/14.
 */
public class DrawView extends View {

    //for zooming in and out
    private RectF mCurrentViewport =
            new RectF(0, 0, 10, 10);
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    private boolean moved;

    // for moving the map
    float lastX;
    float lastY;
    long dragTime;

    private MotionEvent me;

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
                AudioManager audioManager = MainActivity.getMain().getAudioManager();
                audioManager.play("select");
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
        if (DataModel.getDataModel().getMenuOn() == false){
            this.setOnTouchListener(otl);
        } else {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                        int x[][] = DataModel.getDataModel().getLevelSelectManager().getButtonX();
                        int y[][] = DataModel.getDataModel().getLevelSelectManager().getButtonY();
                        int width = DataModel.getDataModel().getLevelSelectManager().getLevelWidth();
                        int height = DataModel.getDataModel().getLevelSelectManager().getLevelHeight();
                        int num_x = DataModel.getDataModel().getLevelSelectManager().getLevelPerRow();
                        int num_y = DataModel.getDataModel().getLevelSelectManager().getLevelPerColumn();
                        float bufferWidth = DataModel.getDataModel().getLevelSelectManager().getBuffer();

                        // int buffer = DataModel.getDataModel().getLevelSelectManager();
                        for (int i = 0; i < x.length; i++){
                            for (int j = 0; j < x[0].length; j++){
                                if (motionEvent.getX() >= x[i][j] && motionEvent.getX() <= x[i][j] + width && motionEvent.getY() >= y[i][j] && motionEvent.getY() <= y[i][j] + height){
                                    DataModel.getDataModel().getLevelSelectManager().processLevel((x.length * j) + i + 1);
                                    return true;
                                }
                            }
                        }

                        double _x=motionEvent.getX();
                        double _y=motionEvent.getY();
                        int i = _x/(double)(bufferWidth+width) > num_x-1? num_x-1:(int) (_x/(double)(bufferWidth+width));
                        int j = _y/(double)(bufferWidth+height) > num_y-1? num_x-1:(int) (_y/(double)(bufferWidth+height));
                        int level = j*3+i;
                        //DataModel.getDataModel().getLevelSelectManager().processLevel(level);
                    }
                    return true;
                }
            });
        }

        DataModel.getDataModel().setOnTouchListener(otl);
        // Paint it again!
        this.invalidate();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            lastX = detector.getCurrentSpanX();
            lastY = detector.getCurrentSpanY();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            moved = true;
            if (DataModel.getDataModel().getMenuOn()){
                return true;
            }

            float spanX = detector.getCurrentSpanX();
            float spanY = detector.getCurrentSpanY();

            float newWidth = lastX / spanX * mCurrentViewport.width();
            float newHeight = lastY / spanX * mCurrentViewport.height();

            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();


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
