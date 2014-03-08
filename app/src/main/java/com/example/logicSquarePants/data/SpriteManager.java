package com.example.logicSquarePants.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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
        levelSelectManager = new LevelSelectManager();
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
        for (Sprite s : sprites){
            s.drawSelf(c);
        }
        levelSelectManager.showScreen(c, 1);
    }
}
