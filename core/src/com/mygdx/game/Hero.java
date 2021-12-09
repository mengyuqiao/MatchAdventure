package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    Animation<TextureRegion> attackAnimation;
    Animation<TextureRegion> flipAttackAnimation;
    TextureRegion reg;
    TextureRegion flipReg;
    float stateTime, stateTime2;
    boolean isAttacking = false;
    int immuneTime = 0;
    int jumpTime = 2;
    Texture[] imgs;
    Texture[] flipImgs;

    public Hero() {
        super();
        Texture image = new Texture("32x32_match.png");
        Texture temp = new Texture("32x32_match_immune.png");
        imgs = new Texture[2];
        flipImgs = new Texture[2];
        imgs[0] = image;
        imgs[1] = temp;
        flipImgs[0] = image;
        TextureRegion tempRegion = new TextureRegion(image,0,0,32,32);
        tempRegion.flip(false, true);
        flipImgs[1] = tempRegion.getTexture();
        img = imgs[0];

        Texture attackSheet = new Texture(Gdx.files.internal("32x32_matchfire.png"));
        Texture attackSheet2 = new Texture(Gdx.files.internal("32x32_matchfire2.png"));
        TextureRegion[][] tmp1 = TextureRegion.split(attackSheet, attackSheet.getWidth() / 6, attackSheet.getHeight());
        TextureRegion[][] tmp2 = TextureRegion.split(attackSheet2, attackSheet2.getWidth() / 6, attackSheet2.getHeight());
        TextureRegion[] attackFrames = new TextureRegion[6];
        TextureRegion[] flipAttackFrames = new TextureRegion[6];
        int cnt = 0;
        for (TextureRegion[] regions : tmp1){
            for (TextureRegion region : regions){
                attackFrames[cnt++] = region;
            }
        }
        int cnt2 = 0;
        for (TextureRegion[] regions2 : tmp2){
            for (TextureRegion region2 : regions2){
                region2.flip(true, false);
                flipAttackFrames[cnt2++] = region2;
            }
        }
        attackAnimation = new Animation<>(0.15f, attackFrames);
        flipAttackAnimation = new Animation<>(0.15f, flipAttackFrames);
        System.out.println(flipAttackAnimation);
        System.out.println(attackAnimation);
        stateTime = 0f;
        stateTime2 = 0f;
        reg = attackAnimation.getKeyFrame(stateTime);
        flipReg = flipAttackAnimation.getKeyFrame(stateTime2);
    }

    public void attack(){
        reg = attackAnimation.getKeyFrame(stateTime);
        flipReg = flipAttackAnimation.getKeyFrame(stateTime2);
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
            attack.heroshootLeft();
            attack.position.set(position.x - 20, position.y + 20);
        }
        else if(right){
            attack.heroshootRight();
            attack.position.set(position.x + 20, position.y + 20);
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
        stateTime2 += delta;
        reg = attackAnimation.getKeyFrame(stateTime,false);
        flipReg = flipAttackAnimation.getKeyFrame(stateTime2,false);
        if (attackAnimation.isAnimationFinished(stateTime)){
            isAttacking = false;
            stateTime = 0;
        }
        if (flipAttackAnimation.isAnimationFinished(stateTime2)){
            isAttacking = false;
            stateTime2 = 0;
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
