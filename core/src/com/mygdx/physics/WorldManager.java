package com.mygdx.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

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
        polygonShape.setAsBox(32,32);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((x*64)+32,(y*64)+32);
        bodyDef.type = BodyDef.BodyType.StaticBody;



       /* FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = obstacle;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.shape = polygonShape;*/


        Body body = world.createBody(bodyDef);
        body.createFixture(polygonShape,1f);
    }

    public Body createPlayerBody(Actor player){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

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

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getBody() == saveBody || contact.getFixtureB().getBody() == saveBody){
            System.out.println("Contact Body !!!!!!!!!!!!!!!!");



        }
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
