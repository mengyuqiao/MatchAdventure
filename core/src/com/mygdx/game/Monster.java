package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Monster extends Actor{
    static final float SPEED = 1f;
    static final float GRAVITY = 5f;
    static final float SIZE = 0.5f;
    static final float WIDTH = 16f;
    static final float HEIGHT = 16f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    boolean isDead = true;
    Texture img;
    int flag = 1; //denote the direction of monster
    float distanceLeft = 0;
    float distanceRight = 0;
    String id;

    int r = 1;//denote the directio

    public Monster() {
        super();
    }

    public Monster(Vector2 position) {
        super();
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public String getId(){
        return id;
    }

    public void setID(String id){
        this.id = id;
    }

    public int fireRight(Attack attack){
        int draw = 0;
        distanceLeft = distanceLeft + SPEED;
        if(flag == 1){
            position.x = position.x + SPEED;
            if(25f > distanceLeft && distanceLeft < 50f){
                draw = 1;
                attack.position.set(position.x + 10,position.y);
            }
            if(distanceLeft == 50f){
                flag = 0;
                distanceLeft = 0;
            }
        }
        else{
            position.x = position.x - SPEED;
            if(distanceLeft == 50f){
                flag = 1;
                distanceLeft = 0;
            }
        }
        return draw;
    }

    public int fireLeft(Attack attack){
        int draw = 0;
        distanceRight = distanceRight + SPEED;
        if(flag == 1){
            position.x = position.x - SPEED;
            if(25f > distanceRight && distanceRight < 50f){
                draw = 1;
                attack.position.set(position.x - 10,position.y);
            }
            if(distanceRight == 50f){
                flag = 0;
                distanceRight = 0;
            }
        }
        else{
            position.x = position.x + SPEED;
            if(distanceRight == 50f){
                flag = 1;
                distanceRight = 0;
            }
        }
        return draw;
    }

    public void activeMonster(Attack attack){
        attack.isActive = true;
        isDead= false;
    }

    public void setDead(Attack attack){
        attack.isActive = false;
        isDead = true;
    }

    public void attack(){ }
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
        if (!isDead) {
        }
    }
}
