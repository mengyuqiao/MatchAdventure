package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Image;
import java.util.Random;

public class Hero extends Actor {
    static final float SPEED = 2f;
    static final float GRAVITY = 1f;
    static final float SIZE = 0.5f;
    static final float WIDTH = 32f;
    static final float HEIGHT = 32f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    boolean isJumping = false;
    boolean left = false;
    boolean right = true;
    boolean onTheGround = true;
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
        position.x += SPEED;
        left = true;
        right = false;
    }

    public void moveRight(){
        position.x -= SPEED;
        right = true;
        left = false;
    }

    public void moveUp(){
        velocity.y += 10;
        if (left){
            moveLeft();
        }else {
            moveRight();
        }
        isJumping = false;
        onTheGround = false;
    }

    public boolean testDead(){
        return hp <= 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void shoot(Attack attack){
        if(left){
            attack.heroshootRight();
        }
        else if(right){
            attack.heroshootLeft();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // change velocity
        if (isJumping) {
            moveUp();
        }

        // add velocity to position
        if (!onTheGround) {
            if (velocity.y > -2*GRAVITY){
                velocity.y -= GRAVITY;
            }
        }else {
            velocity.y = -GRAVITY;
        }
        position.add(velocity);
    }
}
