package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Image;
import java.util.Random;

public class Hero extends Actor {
    static final float SPEED = 2f;
    static final float GRAVITY = 1f;
    static final float WIDTH = 32f;
    static final float HEIGHT = 32f;

    Vector2 position = new Vector2();
    Vector2 velocity = new Vector2();
    boolean isJumping = false;
    boolean left = false;
    boolean right = true;
    boolean onTheGround = true;
    int hp = 5;
    Texture img;
    Texture attackSheet;
    Animation<TextureRegion> attackAnimation;
    TextureRegion reg;
    float stateTime;
    boolean isAttacking = false;
    int immuneTime = 0;
    int jumpTime = 2;
    Texture[] imgs;

    public Hero() {
        super();
        img = new Texture("32x32_match.png");
        Texture temp = new Texture("32x32_match_immune.png");
        imgs = new Texture[2];
        imgs[0] = img;
        imgs[1] = temp;

        attackSheet = new Texture(Gdx.files.internal("32x32_matchfire.png"));
        TextureRegion[][] tmp = TextureRegion.split(attackSheet, attackSheet.getWidth() / 6, attackSheet.getHeight());
        TextureRegion[] attackFrames = new TextureRegion[6];
        int cnt = 0;
        for (TextureRegion[] regions : tmp){
            for (TextureRegion region : regions){
                attackFrames[cnt++] = region;
            }
        }
        attackAnimation = new Animation<>(0.15f, attackFrames);
        stateTime = 0f;
        reg = attackAnimation.getKeyFrame(stateTime);
    }

    public void attack(){
        reg = attackAnimation.getKeyFrame(stateTime);
    }

    public void getHit(){
        if (immuneTime == 0){
            immuneTime = 80;
            hp--;
        }
    }

    public void moveLeft(){
        position.x -= SPEED;
        left = true;
        right = false;
    }

    public void moveRight(){
        position.x += SPEED;
        right = true;
        left = false;
    }

    public void moveUp(){
        if (jumpTime != 0){
            velocity.y += 10;
            jumpTime--;
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

        attack();
        stateTime += delta;
        reg = attackAnimation.getKeyFrame(stateTime,false);
        if (attackAnimation.isAnimationFinished(stateTime)){
            isAttacking = false;
            stateTime = 0;
        }

        if (immuneTime != 0){
            immuneTime--;
            if (immuneTime % 16 == 0){
                img = imgs[immuneTime/16%2];
            }
        }

        // add velocity to position
        if (!onTheGround) {
            if (velocity.y > -2*GRAVITY){
                velocity.y -= GRAVITY;
            }
        }else {
            velocity.y = -GRAVITY;
            jumpTime = 2;
        }
        position.add(velocity);
    }
}
