package com.mygdx.lights;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LightManagerSingleton {
    private static LightManagerSingleton ourInstance = new LightManagerSingleton();
    public  World world;

    public static LightManagerSingleton getInstance() {
        return ourInstance;
    }

    public RayHandler rayHandler;

    public int MAX_RAY = 128;

    private LightManagerSingleton() {
        world = new World(new Vector2(0,-1),false);
        rayHandler = new RayHandler(world);
        rayHandler.setShadows(true);
        rayHandler.setAmbientLight(0.065f);
    }

    public ConeLight addConeLight(int x, int y, int distance,float coneDegre, float coneDirection, Color color){
        return new ConeLight(this.rayHandler, MAX_RAY, color, distance,x, y, coneDirection, coneDegre);
    }

    public void addWalk(int x, int y){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(32,32);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((x*64)+32,(y*64)+32);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = LightManagerSingleton.getInstance().world.createBody(bodyDef);
        body.createFixture(polygonShape,1f);
    }
}
