package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Attack extends Actor {
    static final float SPEED = 0.5f;
    static final float GRAVITY = 5f;
    static final float SIZE = 0.1f;
    static final float WIDTH = 16f;
    static final float HEIGHT = 16f;
    int count = 0;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();

    Texture img;
    float distance = 0;
    int flag = 0;
    String type;
    public Attack() {
        super();
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }


    public int shootRight(){
        position.x = position.x + SPEED;
        distance = distance + 2.5f;
        if(distance > 400f){
            position.x = 50;
            distance = 0;
            return 1;
        }
        return 0;
    }
    public int heroshootRight(){
        position.x = position.x + SPEED;
        distance = distance + 2.5f;
        if(distance > 400f){
            position.x = 50;
            distance = 0;
            return 1;
        }
        return 0;
    }

    public int heroshootLeft(){
        position.x = position.x - SPEED;
        distance = distance + 2.5f;
        if(distance > 500f){
            position.x = 50;
            distance = 0;
            return 1;
        }
        return 0;
    }
    public int shootLeft(){
        position.x = position.x - SPEED;
        distance = distance + 2.5f;
        if(distance > 500f){
            position.x = 50;
            distance = 0;
            return 1;
        }
        return 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        // add velocity to position
        position.add(velocity);
        // change velocity
    }
}
