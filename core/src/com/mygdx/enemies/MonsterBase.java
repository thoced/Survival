package com.mygdx.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Queue;

import java.util.List;

public abstract class MonsterBase extends Actor {

    protected Vector2 destination = new Vector2();

    protected Sprite sprite;

    protected List<TextureRegion> regions;

    protected float indRegion;

    protected Queue<ActionMonster> queueAction = new Queue<ActionMonster>();

    protected Body body;

    protected float speed = 256f;

    protected float mass = 75f;

    public abstract void setDestination(float x,float y);

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}
