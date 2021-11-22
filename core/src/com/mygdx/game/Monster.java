package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Monster extends Actor{
    static final float SPEED = 2f;
    static final float GRAVITY = 5f;
    static final float SIZE = 0.5f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    boolean isJumping = false;

    public Monster() {
        super();
    }

    public Monster(Vector2 position) {
        super();
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public void attack(){}

    public void moveLeft(){
        velocity.x = -20f;
    }

    public void moveRight(){
        velocity.x = 20f;
    }

    public void moveUp(){
        isJumping = true;
        velocity.y = 20f;
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
