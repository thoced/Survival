package com.mygdx.enemies;

import com.badlogic.gdx.math.Vector2;

public class ActionMonster {

    public ActionMonster(Vector2 pos, float deltaSpeed) {
        this.pos = pos;
        this.deltaSpeed = deltaSpeed;

    }

    public Vector2 pos;

    public float deltaSpeed;
}
