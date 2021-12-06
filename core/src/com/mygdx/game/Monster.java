package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Monster extends Actor{
    static final float SPEED = 2f;
    static final float GRAVITY = 5f;
    static final float SIZE = 0.5f;
    static final float WIDTH = 32f;
    static final float HEIGHT = 32f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    boolean isSleeping = true;
    Texture img;
    int flag = 1;
    float distance = 0;

    public Monster() {
        super();
    }

    public Monster(Vector2 position) {
        super();
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public void moveAround(){
        if(flag == 1){
            distance = distance + 2f;
            position.x = position.x + 2f;
            if(distance == 50f){
                flag = 0;
                distance = 0;
            }
        }
        else{
            distance = distance + 2f;
            position.x = position.x - 2f;
            if(distance == 50f){
                flag = 1;
                distance = 0;
            }
        }
    }


    public float getMonsterX(){
        return velocity.x;
    }
    public float getMonsterY(){
        return velocity.y;
    }
    public void setMonsterX(float x){
        velocity.x = x;
    }
    public void setMonsterY(float y){
        velocity.y = y;
    }
    public void activeMonster(){
        isSleeping = false;
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
        if (!isSleeping) {
            moveAround();
        }
    }
}
