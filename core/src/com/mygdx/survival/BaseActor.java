package com.mygdx.survival;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

public class BaseActor extends Actor {

    protected Queue<MoveToAction> queueAction = new Queue<MoveToAction>();

    public static  List<TextureRegion> prepareRegion(Texture text,int width,int height) {
        List<TextureRegion> regions = new ArrayList<TextureRegion>();
        for(int i=0;i<12;i++) {
            TextureRegion region = new TextureRegion(text, i*width, 0, width, height);
            regions.add(region);
        }
        return regions;
    }

}
