package com.mygdx.lights;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class FlashLight extends Actor  {

    private PointLight pointLight;
    private int distance;

    private boolean state = false;
    private float deltaTime = 0f;

    public FlashLight(Vector2 position, int  distance, Color color, Stage stage){
        this.distance = distance;
        this.pointLight = LightManagerSingleton.getInstance().addNativePointLight(position.x,position.y,distance,color);
        stage.addActor(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        deltaTime += delta;
        if(deltaTime > 1f){
            deltaTime = 0f;
            state = !state;
        }

        if(state){
            pointLight.setDistance(0);
        }else
            pointLight.setDistance(this.distance);



    }


}
