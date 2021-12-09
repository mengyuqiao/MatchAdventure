package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Monster extends Actor{
    static final float SPEED = 1f;
    static final float WIDTH = 16f;
    static final float HEIGHT = 16f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    boolean isDead = true;
    Texture img;
    int flag = 1; //denote the direction of monster
    float distanceLeft = 0;
    float distanceRight = 0;
    String type;
    Attack attack;

    public Monster() {
        super();
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    public Attack getAttack(){
        return attack;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public void fireRight(){
        distanceLeft = distanceLeft + SPEED;
        if(flag == 1){
            position.x = position.x + SPEED;
            if(45f > distanceLeft && distanceLeft < 50f){
                attack.isActive = true;
                attack.position.set(position.x + 10,position.y);
            }
            if(distanceLeft == 50f){
                flag = 0;
                distanceLeft = 0;
            }
        }
        else{
            attack.isActive = false;
            position.x = position.x - SPEED;
            if(distanceLeft == 50f){
                flag = 1;
                distanceLeft = 0;
            }
        }
    }

    public void fireLeft(){
        distanceRight = distanceRight + SPEED;
        if(flag == 1){
            position.x = position.x - SPEED;
            if(45f > distanceRight && distanceRight < 50f){
                attack.isActive = true;
                attack.position.set(position.x - 10, position.y);
            }
            if(distanceRight == 50f){
                flag = 0;
                distanceRight = 0;
            }
        }
        else{
            attack.isActive = false;
            position.x = position.x + SPEED;
            if(distanceRight == 50f){
                flag = 1;
                distanceRight = 0;
            }
        }
    }

    public void activeMonster(){
        isDead= false;
    }

    public void setDead(){
        attack.isActive = false;
        isDead = true;
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
