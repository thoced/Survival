package com.mygdx.lights;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ParticleControllerInfluencer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;


public class StrobeLight extends Actor  {

    private PointLight pointLight;
    private int distance;

    private boolean state = false;
    private float deltaTime = 0f;
    private double maxDeltaTime = 0f;

    public StrobeLight( Vector2 position,int  distance, Color color,Stage stage){
        this.distance = distance;
        this.pointLight = LightManagerSingleton.getInstance().addNativePointLight(position.x,position.y,distance,color);
        stage.addActor(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        deltaTime += delta;
        if(deltaTime > maxDeltaTime){

            double rangeMin = 0.0f;
            double rangeMax = 0.15f;
            Random r = new Random();
            maxDeltaTime = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            deltaTime = 0f;
            state = !state;
            if(state){
                pointLight.setDistance(0);
            }else
                pointLight.setDistance(this.distance);
        }





    }


}
