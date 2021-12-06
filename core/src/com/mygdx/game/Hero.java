package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Image;
import java.util.Random;

public class Hero extends Actor {
    static final float SPEED = 20f;
    static final float GRAVITY = 5f;
    static final float SIZE = 0.5f;
    static final float WIDTH = 32f;
    static final float HEIGHT = 32f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    boolean isJumping = true;
    boolean left = true;
    boolean right = true;
    int hp = 5;
    Texture img;
    int r = 1;//denote the directio

    public Hero() {
        super();
    }

    public Hero(Vector2 position) {
        super();
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public void attack(){}

    public void getHit(){
        hp--;
    }

    public void moveLeft(){
        position.x = position.x+2;
    }

    public void moveRight(){
        position.x = position.x-2;
    }

    public void moveUp(){
        isJumping = true;
    }

    public void moveDown(){
        isJumping = true;
    }

    public boolean testDead(){
        return hp <= 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void shoot(Attack attack){
        if(left == true){
            attack.heroshootRight();
        }
        else if(right == true){
            attack.heroshootLeft();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // add velocity to position
        position.add(velocity);
        // change velocity
        if (isJumping) {
            moveUp();
        }
    }
}
