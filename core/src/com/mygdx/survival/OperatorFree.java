package com.mygdx.survival;

import box2dLight.ConeLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.mygdx.lights.LightManagerSingleton;
import com.mygdx.path.WorldGraph;
import com.mygdx.physics.WorldManager;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

import java.util.List;

public class OperatorFree extends OperatorPlayer {

  //  private final ConeLight coneLight;
  //  private final ConeLight coneLightPeripherique;
    private Vector2 destination;
    private float currentViewX;
    private float currentViewY;
    private Body body;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private MoveByAction moveToAction;



    public OperatorFree(Texture text, List<TextureRegion> regions, String name) {
        super(text, regions, name);
        destination = new Vector2(this.getX(),this.getY());

        shapeRenderer.setAutoShapeType(true);
        // creation de la physique
        body = WorldManager.getInstance().createPlayerBody(this);


    }



   /* public OperatorFree(Texture text, List<TextureRegion> regions, String name) {
            this.setName(name);
            sprite = new Sprite(text);
            this.regions = regions;
            sprite.setScale(0.5f);
            this.setBounds(0,0,256,256);
            sprite.setBounds(0,0,256,256);
            this.setOrigin(128,128);
            this.setTouchable(Touchable.enabled);
            destination = new Vector2(this.getX(),this.getY());

            coneLight = LightManagerSingleton.getInstance().addConeLight(0,0,788,25f,0f,new Color(0.7f,0.6f,0.6f,0.8f));
            coneLightPeripherique = LightManagerSingleton.getInstance().addConeLight(0,0,512,60f,0f, new Color(0.2f,0.1f,0.1f,1f));

    }*/

   public void setViewDirection(float vX,float vY){

       // sauvegarde de la direction de vue pour éviter de relancer une action si l'utilisateur ne modifie pas l'angle
       if(currentViewX == vX && currentViewY == vY)
           return;

       // sauvegarde de la nouvelle position
       currentViewX = vX;
       currentViewY = vY;


       Vector2 vTarget = new Vector2(vX,vY);
       Vector2 diff = vTarget.sub(this.getX(),this.getY());
       diff.nor();
       float angle = diff.angle();

       RotateToAction rotateToAction = new RotateToAction();
       rotateToAction.setUseShortestDirection(true);
       rotateToAction.setDuration(0.5f);
       rotateToAction.setRotation(angle);
       this.addAction(rotateToAction);
   }

    public void setDestination(float x, float y) {

       destination.set(x,y);

       this.clearActions();

        moveToAction = new MoveByAction();
       // moveToAction.setPosition(destination.x,destination.y);
        Vector2 diff = destination.sub(new Vector2(this.getX(),this.getY()));
        float duration = diff.len() / 128f;
        moveToAction.setAmount(diff.x,diff.y);
        moveToAction.setDuration(duration);
        moveToAction.setActor(this);
        this.addAction(moveToAction);

        diff.nor();
        float angle = diff.angle();

        // création d'une action de rotation
        RotateToAction rotateToAction = new RotateToAction();
        rotateToAction.setRotation(angle);
        rotateToAction.setDuration(0.25f);
        rotateToAction.setUseShortestDirection(true);
        this.addAction(rotateToAction);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
      //  sprite.setPosition(getX() - getOriginX(),getY() - getOriginY());
        setPosition(body.getPosition().x,body.getPosition().y);
        sprite.setPosition(body.getPosition().x - getOriginX(),body.getPosition().y - getOriginY());


        if(!getActions().contains(moveToAction,true)){
            body.setLinearVelocity(0,0);
            indRegion = 0;
        }



    }



    @Override
    public void moveBy(float x, float y) {
        //super.moveBy(x, y);
        body.setLinearVelocity(x*128,y*128);

    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);



        sprite.setOrigin(this.getOriginX(),this.getOriginY());
        sprite.setRegion(regions.get((int)indRegion));
        sprite.draw(batch);


       /* shapeRenderer.setProjectionMatrix(this.getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(body.getPosition().x,body.getPosition().y,50f,32);
        shapeRenderer.end();*/

       // System.out.println("body: " + body.getPosition().x + ": " + body.getPosition().y);


    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        body.setTransform(x,y,0f);

    }
}
