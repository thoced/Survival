package com.mygdx.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.enemies.Monster;
import com.mygdx.enemies.MonsterBase;

public class WorldManager implements ContactListener{
    private static WorldManager ourInstance = new WorldManager();

    public static WorldManager getInstance() {
        return ourInstance;
    }

    public World world;

    private Body saveBody;

    short obstacle = 0x0001;

    private WorldManager() {
        world = new World(new Vector2(0,0),true);
        world.setContactListener(this);
    }

    public void addWalk(int x, int y){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(30,30);

      /*  Vector2[] vertices = new Vector2[8];
        vertices[0] = new Vector2(-32,18);
        vertices[1] = new Vector2(-18,32);
        vertices[2] = new Vector2(18,32);
        vertices[3] = new Vector2(32,18);

        vertices[4] = new Vector2(32,-18);
        vertices[5] = new Vector2(18,-32);
        vertices[6] = new Vector2(-18,-32);
        vertices[7] = new Vector2(-32,-18);*/

        //polygonShape.set(vertices);


        //polygonShape.setRadius(32f);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((x*64)+32,(y*64)+32);
        bodyDef.type = BodyDef.BodyType.StaticBody;


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = obstacle;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.0f;
        fixtureDef.shape = polygonShape;



        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.createFixture(polygonShape,1f);
    }

    public Body createPlayerBody(Actor player){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearVelocity.set(0,0);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(24f);
        circleShape.setPosition(new Vector2(player.getX(),player.getY()));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = obstacle;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.shape = circleShape;

        saveBody = world.createBody(bodyDef);
        saveBody.createFixture(fixtureDef);
        saveBody.setUserData(player);
        return saveBody;

    }

    public Body createMonsterBody(MonsterBase monster){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearVelocity.set(0,0);
        bodyDef.angularVelocity = 1f;
        bodyDef.angularDamping = 0f;
        bodyDef.fixedRotation = true;


        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(18f);
        circleShape.setPosition(new Vector2(monster.getX(),monster.getY()));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = false;
        fixtureDef.filter.categoryBits = obstacle;
        fixtureDef.density = 0.7f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 1f;
        fixtureDef.shape = circleShape;

        saveBody = world.createBody(bodyDef);
        MassData massData = new MassData();
        massData.mass = monster.getMass();
        saveBody.setMassData(massData);
        saveBody.setAngularDamping(100f);
        saveBody.setLinearDamping(100f);
        saveBody.createFixture(fixtureDef);
        saveBody.setUserData(monster);

        return saveBody;
    }

    @Override
    public void beginContact(Contact contact) {

        

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
