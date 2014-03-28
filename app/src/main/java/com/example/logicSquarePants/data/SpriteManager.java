package com.example.logicSquarePants.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.logicSquarePants.R;
import com.example.logicSquarePants.game.MainActivity;
import com.example.logicSquarePants.gui.LevelSelectManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by John on 3/1/14.
 */
public class SpriteManager {

    private HashMap<String, SpriteType> spriteTypes;
    private ArrayList<Sprite> sprites;

    private Sprite background;
    private LevelSelectManager levelSelectManager;

    public SpriteManager(){
        levelSelectManager = DataModel.getDataModel().getLevelSelectManager();
        spriteTypes = new HashMap<String, SpriteType>();
        sprites = new ArrayList<Sprite>();
    }

    public Sprite initBackground(Bitmap pic){
        spriteTypes.put("background", new SpriteType(pic, 100, 100));
        background = new Sprite(spriteTypes.get("background"), 0,0);
        sprites.add(background);
        return background;
    }

    public void update(Canvas c){

        DataModel dataModel = DataModel.getDataModel();
        float transX = dataModel.getTransX();
        float transY = dataModel.getTransY();
        for (Sprite s : sprites){
            s.drawSelf(c);
        }

        if (DataModel.getDataModel().getMenuOn()){
            levelSelectManager.showScreen(c, 1);
            transX = 0;
            transY = 0;
            DataModel.getDataModel().setScaleFactor(1.0f);
        } else {
            Paint paint = new Paint();
            paint.setStrokeWidth(3);
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);

            //test change

            if(dataModel.isWon()) {
                c.drawText("You win!", 500, 500, paint);
                return;
            }

            dataModel.setWon(true);
            for(int i=0; i<dataModel.getRowCount(); i++) {
                for(int j=0; j<dataModel.getColCount(); j++) {
                    if(dataModel.getCurrentNodes()[i][j] != dataModel.getCorrectNodes()[i][j]) {
                        dataModel.setWon(false);
                    }
                }
            }

            paint.setColor(Color.YELLOW);

            for (int i=0; i<dataModel.getRowCount(); i++) {
                for(int j=0; j<dataModel.getColCount(); j++) {
                    if(dataModel.getCurrentNodes()[i][j] == true) {
                        c.drawRect(10 + j * DataModel.NODE_WIDTH + transX, 10 + i * DataModel.NODE_HEIGHT + transY, 10 + (j + 1) * DataModel.NODE_WIDTH + transX, 10 + (i + 1) * DataModel.NODE_HEIGHT + transY, paint);
                    }
                }
            }
            paint.setColor(Color.BLACK);
            for (int i = 0 ; i <= DataModel.getDataModel().getColCount(); i++){
                c.drawLine(i * DataModel.NODE_WIDTH + 10 + transX, 10 + transY, i * DataModel.NODE_WIDTH + 10 + transX, DataModel.NODE_WIDTH * DataModel.getDataModel().getRowCount() + 10 + transY, paint);
            }

            for (int j = 0 ; j <= DataModel.getDataModel().getRowCount(); j++) {
                c.drawLine(10 + transX, j * DataModel.NODE_HEIGHT + 10 + transY, DataModel.NODE_HEIGHT * DataModel.getDataModel().getColCount() + 10 + transX, j * DataModel.NODE_HEIGHT + 10 + transY, paint);
            }
        }
    }
}
