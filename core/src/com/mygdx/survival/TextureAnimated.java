package com.mygdx.survival;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class TextureAnimated {

    public TextureAnimated(String fileTexture,String nameAnimated,int width,int height){
        texture = new Texture(fileTexture);
        sprite = new Sprite(texture);
        this.nameAnimated = nameAnimated;
        regions = BaseActor.prepareRegion(texture,width,height);
        this.nbAnimation = regions.size();
    }

    public int nbAnimation;

    public String nameAnimated;

    public Texture texture;

    public Sprite sprite;

    public List<TextureRegion> regions;
}
