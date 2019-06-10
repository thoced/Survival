package com.mygdx.lights;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class Light extends Actor  {

    private PointLight pointLight;
    private int distance;

    public Light(float x, float y, int  distance, Color color, Stage stage){
        this.distance = distance;
        this.pointLight = LightManagerSingleton.getInstance().addNativePointLight(x,y,distance,color);
        stage.addActor(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);





    }


}
