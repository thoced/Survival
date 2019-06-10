package com.mygdx.lights;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    public void loadLightsLayer(MapLayer lightLayer,Stage stage){
        if(lightLayer != null){

              for(MapObject obj : lightLayer.getObjects()){
                if(obj.getClass() == RectangleMapObject.class){
                   Color color = new Color(1,1,1,1);
                   int   distance = 64;
                   boolean isStrobe = false;
                   boolean isFlash = false;

                   Vector2 center = new Vector2();
                   center = ((RectangleMapObject)obj).getRectangle().getCenter(center);

                    if(((RectangleMapObject)obj).getProperties().containsKey("flash")){
                        isFlash = (Boolean)((RectangleMapObject)obj).getProperties().get("flash");
                    }

                    if(((RectangleMapObject)obj).getProperties().containsKey("strobe")){
                        isStrobe = (Boolean)((RectangleMapObject)obj).getProperties().get("strobe");
                    }
                    if(((RectangleMapObject)obj).getProperties().containsKey("color")){
                        color = (Color) ((RectangleMapObject)obj).getProperties().get("color");
                    }
                    if(((RectangleMapObject)obj).getProperties().containsKey("distance")){
                        distance = (Integer) ((RectangleMapObject)obj).getProperties().get("distance");
                    }

                    // ajout de la lumière

                    if(isStrobe)
                        addStrobeLight(center.x,center.y,distance,color,stage);
                    else if(isFlash)
                        addFlashLight(center.x,center.y,distance,color,stage);

                    else addLight(center.x,center.y,distance,color,stage);
                }
            }
        }

    }

    public FlashLight addFlashLight(float x, float y, int distance,Color color,Stage stage){
        return new FlashLight(new Vector2(x,y),distance,color,stage);
    }

    public StrobeLight addStrobeLight(float x, float y, int distance,Color color,Stage stage){
        return new StrobeLight(new Vector2(x,y),distance,color,stage);
    }

    public Light addLight(float x, float y, int distance,Color color,Stage stage){
        return new Light(x,y,distance,color,stage);
    }

    public ConeLight addNativeConeLight(float x, float y, int distance,float coneDegre, float coneDirection, Color color){
        return new ConeLight(this.rayHandler, MAX_RAY, color, distance,x, y, coneDirection, coneDegre);
    }
    public PointLight addNativePointLight(float x, float y, int distance, Color color){
        return new PointLight(this.rayHandler,MAX_RAY,color,distance,x,y);
    }


}
