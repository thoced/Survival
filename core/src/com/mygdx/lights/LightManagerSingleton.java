package com.mygdx.lights;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.physics.WorldManager;

public class LightManagerSingleton {
    private static LightManagerSingleton ourInstance = new LightManagerSingleton();


    public static LightManagerSingleton getInstance() {
        return ourInstance;
    }

    public RayHandler rayHandler;

    public int MAX_RAY = 128;

    private LightManagerSingleton() {

        rayHandler = new RayHandler(WorldManager.getInstance().world);
        rayHandler.setShadows(true);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(4);
        rayHandler.setAmbientLight(0.035f);
    }

    public ConeLight addConeLight(int x, int y, int distance,float coneDegre, float coneDirection, Color color){
        return new ConeLight(this.rayHandler, MAX_RAY, color, distance,x, y, coneDirection, coneDegre);
    }
    public PointLight addPointLight(int x, int y, int distance, Color color){
        return new PointLight(this.rayHandler,MAX_RAY,color,distance,x,y);
    }


}
