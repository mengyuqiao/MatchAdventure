package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hero extends Actor {
    public enum State {
        IDLE, WALKING, JUMPING, DYING
    }

    static final float SPEED = 2f; // unit per second
    static final float JUMP_VELOCITY = 1f;
    static final float SIZE = 0.5f; // half a unit

    Vector2 position = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State  state = State.IDLE;
    boolean  facingLeft = true;

    public Hero() {
        super();
    }

    public Hero(Vector2 position) {
        super();
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
