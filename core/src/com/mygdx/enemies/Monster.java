package com.mygdx.enemies;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.mygdx.path.NodeGraph;
import com.mygdx.path.WorldGraph;
import com.mygdx.physics.WorldManager;
import com.mygdx.survival.BaseActor;
import com.mygdx.survival.PlayerSingleton;
import com.mygdx.survival.TextureAnimated;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Monster extends MonsterBase {

   private boolean onAction = false;
    private ActionMonster moveToAction;
    private float timeOut = 0f;

    public Monster(HashMap<String, TextureAnimated> mapAnimation, String name, float mass) {
        this.setName(name);
        this.sprite = new Sprite(mapAnimation.get("RUN").texture);
        this.packRegions = mapAnimation;
        this.mass = mass;
        this.speed = speed;

        this.setBounds(0,0,512,512);
        sprite.setBounds(0,0,512,512);
        this.setOrigin(256,256);
        this.setTouchable(Touchable.enabled);

        // scale du sprite
        this.sprite.setScale(0.18f);
        // application d'une couleur plus sombre
        this.sprite.setColor(0.7f,0.6f,0.6f,1f);

        // body
        body = WorldManager.getInstance().createMonsterBody(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
        sprite.setRegion(packRegions.get(nameAnimation).regions.get((int)indRegion));
        sprite.draw(batch);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        timeOut += delta;
        setPosition(body.getPosition().x,body.getPosition().y);
        sprite.setPosition(body.getPosition().x - getOriginX(),body.getPosition().y - getOriginY());
        sprite.setRotation(this.getRotation());


            // indice animation
            indRegion += delta * this.packRegions.get(nameAnimation).nbAnimation  * 1.2f;
            if(indRegion > this.packRegions.get(nameAnimation).nbAnimation - 1)
                indRegion = 0;

            // on récupère une nouvelle action
            if(!queueAction.isEmpty() && !onAction){
                onAction = true;
                moveToAction = queueAction.removeFirst();
                timeOut = 0f;
            }

            if(moveToAction != null) {
                Vector2 dir = moveToAction.pos.cpy();
                dir = dir.sub(body.getPosition().x, body.getPosition().y);
                dir.nor();

                // détermination de l'angle de rotation
                float angle = dir.angle();
                // création de la rotation avec lerp
                RotateToAction rotateToAction = new RotateToAction();
                rotateToAction.setRotation(angle);
                rotateToAction.setUseShortestDirection(true);
                rotateToAction.setDuration(0.25f);
                this.addAction(rotateToAction);

                // applicatoin de la force de déplacement
                body.setLinearVelocity(dir.x * (speed + moveToAction.deltaSpeed), dir.y * (speed + moveToAction.deltaSpeed));

                if(body.getPosition().dst(moveToAction.pos) < 8)
                    onAction =  false;

                if(timeOut > 1.5f){
                    moveToAction.pos.set(body.getPosition().x,body.getPosition().y);
                    timeOut = 0f;
                }
            }

            // timeout pour éviter les blocages, force le noeud testé à la position actuelle



    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);


    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x,y);
      body.setTransform(x,y,0f);


    }

    @Override
    public void setPosition(float x, float y, int alignment){
       super.setPosition(x,y,alignment);

    }

    public  GraphPath<NodeGraph> prepareDestination(float x, float y){
        int nodeX = (int) (x /64);
        int nodeY = (int) (y /64);

        NodeGraph goal = WorldGraph.getInstance().getNode(nodeX,nodeY);
        if(goal == null)
            return null;

        System.out.println("MONSTER Goal: " + goal.x + " : " + goal.y);

        int startX = (int) (this.getX() / 64);
        int startY = (int) (this.getY() / 64);
        NodeGraph start = WorldGraph.getInstance().getNode(startX,startY);
        if(start == null)
            return null;

        // lancement de la recherche
        GraphPath<NodeGraph> paths = WorldGraph.getInstance().findPath(start,goal);

        this.clearActions();
        queueAction.clear();
        // Creation du pool d'action


        Random rx = new Random();
        Random ry = new Random();
        double rangeMin = -16f;
        double rangeMax = 16f;

        for(int i=1;i<paths.getCount();i++){
            float deltaX = (float) (rangeMin + (rangeMax - rangeMin) * rx.nextDouble());
            float deltaY = (float) (rangeMin + (rangeMax - rangeMin) * ry.nextDouble());
            float deltaSpeed = rx.nextInt(16);
            ActionMonster action = new ActionMonster(new Vector2((paths.get(i).x * 64) + 32 + deltaX,(paths.get(i).y * 64) + 32 + deltaY),deltaSpeed);
            queueAction.addLast(action);

        }

        onAction = false;

        return paths;
    }

    @Override
    public void setDestination(float x, float y) {
        // réception de la position de la nodeGoal
        destination.set(x,y);
        GraphPath<NodeGraph> paths = prepareDestination(x, y);
        if(paths == null)
            return;


    }

    public ActionMonster getMoveToAction() {
        return moveToAction;
    }
}
