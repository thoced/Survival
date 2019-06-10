package com.mygdx.lights;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
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
        rayHandler.setAmbientLight(0.005f);
    }

    public void loadLightsLayer(MapLayer lightLayer){
        if(lightLayer != null){

              for(MapObject obj : lightLayer.getObjects()){
                if(obj.getClass() == RectangleMapObject.class){
                   Color color = new Color(1,1,1,1);
                   int   distance = 64;
                   Vector2 center = new Vector2();
                   center = ((RectangleMapObject)obj).getRectangle().getCenter(center);

                    if(((RectangleMapObject)obj).getProperties().containsKey("color")){
                        color = (Color) ((RectangleMapObject)obj).getProperties().get("color");
                    }
                    if(((RectangleMapObject)obj).getProperties().containsKey("distance")){
                        distance = (Integer) ((RectangleMapObject)obj).getProperties().get("distance");
                    }

                    // ajout de la lumière
                    addPointLight(center.x,center.y,distance,color);
                }
            }
        }

    }

    public ConeLight addConeLight(float x, float y, int distance,float coneDegre, float coneDirection, Color color){
        return new ConeLight(this.rayHandler, MAX_RAY, color, distance,x, y, coneDirection, coneDegre);
    }
    public PointLight addPointLight(float x, float y, int distance, Color color){
        return new PointLight(this.rayHandler,MAX_RAY,color,distance,x,y);
    }


}
