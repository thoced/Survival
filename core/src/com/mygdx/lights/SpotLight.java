package com.mygdx.lights;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;


public class SpotLight extends Actor  {

    private ConeLight coneLight;

    private Actor owner;

    public SpotLight(Actor owner, Vector2 origin){
        this.owner = owner;

        this.coneLight  = LightManagerSingleton.getInstance().addConeLight(0,0,788,25f,0f,new Color(0.7f,0.6f,0.6f,0.8f));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // deplacemnet du coneLight
        coneLight.setPosition(owner.getX(),owner.getY());
        coneLight.setDirection(owner.getRotation());
    }


}
