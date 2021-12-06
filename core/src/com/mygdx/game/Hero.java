package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Image;

public class Hero extends Actor {
    static final float SPEED = 20f;
    static final float GRAVITY = 5f;
    static final float SIZE = 0.5f;
    static final float WIDTH = 32f;
    static final float HEIGHT = 32f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    boolean isJumping = false;
    int hp = 5;
    Texture img;

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
        velocity.x = -SPEED;
    }

    public void moveRight(){
        velocity.x = SPEED;
    }

    public void moveUp(){
        isJumping = true;
        velocity.y = 20f;
    }

    public boolean testDead(){
        return hp <= 0;
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
        if (isJumping){
            velocity.y -= GRAVITY;
        }
        velocity.x = 0;
    }
}
