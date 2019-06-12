package com.mygdx.enemies;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class EnemyStage extends Stage{
    private static EnemyStage ourInstance = new EnemyStage();

    public static EnemyStage getInstance() {
        return ourInstance;
    }

    private EnemyStage() {

    }

}
