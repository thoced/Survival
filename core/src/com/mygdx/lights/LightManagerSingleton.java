package com.mygdx.lights;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LightManagerSingleton {
    private static LightManagerSingleton ourInstance = new LightManagerSingleton();
    public  World world;

    public static LightManagerSingleton getInstance() {
        return ourInstance;
    }

    public RayHandler rayHandler;

    private LightManagerSingleton() {
        world = new World(new Vector2(0,-1),false);
        rayHandler = new RayHandler(world);
        rayHandler.setShadows(true);
        rayHandler.setAmbientLight(0.254f);
    }

    public void addConeLight(int x, int y, int distance,float coneDegre, float coneDirection, Color color){
        ConeLight coneLight = new ConeLight(this.rayHandler, 8, color, distance,x, y, coneDirection, coneDegre);
    }
}
