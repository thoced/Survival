package com.mygdx.survival;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;

public class BaseActor extends Actor {

    protected Sprite sprite;
    protected List<TextureRegion> regions;
    protected float indRegion = 0;
    protected Queue<MoveToAction> queueAction = new Queue<MoveToAction>();

    public static  List<TextureRegion> prepareRegion(Texture text,int width,int height) {
        List<TextureRegion> regions = new ArrayList<TextureRegion>();
        for(int i=0;i<text.getWidth() / width;i++) {
            TextureRegion region = new TextureRegion(text, i*width, 0, width, height);
            regions.add(region);
        }
        return regions;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
