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
    String type;
    boolean isActive = false;

    public Attack() {
        super();
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public void shootRight(){
        position.x = position.x + 20f;
        position.y = position.y - 5f;
    }

    public void shootLeft(){
        position.x = position.x - 20f;
        position.y = position.y - 5f;
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
