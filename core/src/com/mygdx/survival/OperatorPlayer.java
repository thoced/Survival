package com.mygdx.survival;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.mygdx.lights.LightManagerSingleton;
import com.mygdx.path.NodeGraph;
import com.mygdx.path.WorldGraph;

import java.util.List;

public class OperatorPlayer extends BaseActor {
    private final ConeLight coneLight;
    private final ConeLight coneLightPeripherique;
    private final PointLight haloLight;


    private float col;

    public OperatorPlayer(Texture text, List<TextureRegion> regions, final String name){
        this.setName(name);
        sprite = new Sprite(text);
        this.regions = regions;
        sprite.setScale(0.5f);
        this.setBounds(0,0,256,256);
        sprite.setBounds(0,0,256,256);
        this.setOrigin(128,128);
        this.setTouchable(Touchable.enabled);

         coneLight = LightManagerSingleton.getInstance().addConeLight(0,0,788,25f,0f,new Color(0.7f,0.6f,0.6f,0.8f));
         coneLightPeripherique = LightManagerSingleton.getInstance().addConeLight(0,0,512,60f,0f, new Color(0.1f,0.1f,0.1f,1f));
         haloLight = LightManagerSingleton.getInstance().addPointLight(0,0,256,new Color(0.1f,0.1f,0.1f,1f));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
        sprite.setRegion(regions.get((int)indRegion));
        sprite.draw(batch);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setPosition(getX() - getOriginX(),getY() - getOriginY());
        sprite.setRotation(this.getRotation());

        if(getActions().size > 0) {
            indRegion += delta * 12;
            if (indRegion > 11)
                indRegion = 0;
        }
        else {

            // on récupère une nouvelle action
            if(!queueAction.isEmpty()) {

                MoveToAction moveToAction = queueAction.removeFirst();
                Vector2 vSource = new Vector2(this.getX(),this.getY());
                Vector2 vTarget = new Vector2(moveToAction.getX(),moveToAction.getY());
                Vector2 diff = vTarget.sub(vSource);
                float lenght = diff.len();
                diff.nor();
                float angle = diff.angle();

                // création d'une action de rotation
                RotateToAction rotateToAction = new RotateToAction();
                rotateToAction.setRotation(angle);
                rotateToAction.setDuration(0.25f);
                rotateToAction.setUseShortestDirection(true);
                // ajout des action
                this.addAction(rotateToAction);
                this.addAction(moveToAction);
            }
            else
                indRegion = 0;
        }

    // on vérifie si le player touche quelque chose
        Actor actorHit = this.getStage().hit(this.getX(),this.getY(),true);
        if(actorHit != null && actorHit.getName().equals("door")){
            System.out.println("door hit !!!!");
        }

        // updateLight
        coneLight.setPosition(this.getX(),this.getY());
        coneLight.setDirection(sprite.getRotation());
       coneLightPeripherique.setPosition(coneLight.getPosition());
        coneLightPeripherique.setDirection(sprite.getRotation());
        haloLight.setPosition(coneLight.getPosition());


    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x,y);

    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);

    }

    public void setDestination(float x,float y){

        // réception de la position de la nodeGoal
        int nodeX = (int) (x /64);
        int nodeY = (int) (y /64);

        NodeGraph goal = WorldGraph.getInstance().getNode(nodeX,nodeY);
        if(goal == null)
            return;

        System.out.println("GOAL: " + goal.x + " : " + goal.y);

        int startX = (int) (this.getX() / 64);
        int startY = (int) (this.getY() / 64);
        NodeGraph start = WorldGraph.getInstance().getNode(startX,startY);
        if(start == null)
            return;

        // lancement de la recherche
        GraphPath<NodeGraph> paths = WorldGraph.getInstance().findPath(start,goal);
        System.out.println("paths: " + paths.getCount());

        this.clearActions();
        queueAction.clear();
        // Creation du pool d'action
        for(int i=1;i<paths.getCount();i++){
            NodeGraph node = paths.get(i);
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition((node.x * 64)+32,(node.y * 64) +32);
            moveToAction.setDuration(0.25f);
            moveToAction.setInterpolation(Interpolation.linear);
            queueAction.addLast(moveToAction);
        }

    }


}
