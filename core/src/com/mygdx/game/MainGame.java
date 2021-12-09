package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.awt.Event;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame implements Screen {
	private Game game;
	public static final float WORLD_WIDTH = 480;
	public static final float WORLD_HEIGHT = 800;
	private ImageButton UP;
	private ImageButton Shoot;
	private ImageButton LEFT;
	private ImageButton RIGHT;
	private Stage stage;
	private Hero hero;
	private Monster fireMonster1;
	private Monster fireMonster;
	private Monster fireMonster2;
	private Monster fireMonster3;
	private Monster fireMonster4;
	private Monster fireMonster5;
	private Monster fireMonster6;
	private Attack fire;
	private Attack fire2;
	private Attack fireBall;
	private Attack fireBall2;
	private TiledMap map;
	protected Skin skin;
	private Texture upTexture;
	private Texture rightTexture;
	private Texture leftTexture;
	private Texture shootTexture;
	private Texture portalTexture;
	private TextureRegion UpTextureRegion;
	private TextureRegion RightTextureRegion;
	private TextureRegion leftTextureRegion;
	private TextureRegion shootTextureRegion;
	private TextureRegionDrawable upTextureRegionDrawable;
	private TextureRegionDrawable leftTextureRegionDrawable;
	private TextureRegionDrawable rightTextureRegionDrawable;
	private TextureRegionDrawable shootTextureRegionDrawable;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Texture background;
	private Music bgm;
	private Music bgm1;
	private Music bgm2;
	private Label hp;
	private int herohp = 5;
	private Texture fairy1;
	private Texture fairy2;
	private Array<Rectangle> tiles = new Array<Rectangle>();
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};

	private ArrayList<Monster> monsters = new ArrayList<>();
	private ArrayList<Texture> fairys = new ArrayList<>();
	boolean flag = false;
	public MainGame(Game game){
		this.game = game;
		background = new Texture("background1.png");
		FileHandle bgmHandle = Gdx.files.internal("bgm.wav");
		bgm = Gdx.audio.newMusic(bgmHandle);
		bgm.setLooping(true);
		bgm.play();
		FileHandle bgmHandle1 = Gdx.files.internal("stomp.wav");
		bgm1 = Gdx.audio.newMusic(bgmHandle1);
		FileHandle bgmHandle2 = Gdx.files.internal("bump.wav");
		bgm2 = Gdx.audio.newMusic(bgmHandle2);
	}

	public void createLeftMonster(Monster fireMonster2){
		fireMonster2.setType("L");
		fireMonster2.img = new Texture("antislime.png");
		fireMonster2.activeMonster();
		monsters.add(fireMonster2);
		fire2 = new Attack();
		fire2.img = new Texture("antifire.png");
		fire2.setType("L");
		fireMonster2.setAttack(fire2);
	}

	public void createRightMonster(Monster fireMonster){
		fireMonster.setType("R");
		fireMonster.img = new Texture("slime.png");
		fireMonster.activeMonster();
		monsters.add(fireMonster);
		fire = new Attack();
		fire.img = new Texture("fire.png");
		fire.setType("R");
		fireMonster.setAttack(fire);
	}

	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		upTexture = new Texture(Gdx.files.internal("upbutton.png"));
		UpTextureRegion = new TextureRegion(upTexture);
		upTextureRegionDrawable = new TextureRegionDrawable(UpTextureRegion);
		leftTexture = new Texture(Gdx.files.internal("leftbutton.png"));
		leftTextureRegion = new TextureRegion(leftTexture);
		leftTextureRegionDrawable = new TextureRegionDrawable(leftTextureRegion);
		rightTexture = new Texture(Gdx.files.internal("rightbutton.png"));
		RightTextureRegion = new TextureRegion(rightTexture);
		rightTextureRegionDrawable = new TextureRegionDrawable(RightTextureRegion);
		rightTexture = new Texture(Gdx.files.internal("rightbutton.png"));
		RightTextureRegion = new TextureRegion(rightTexture);
		rightTextureRegionDrawable = new TextureRegionDrawable(RightTextureRegion);
		shootTexture = new Texture(Gdx.files.internal("attack.png"));
		shootTextureRegion = new TextureRegion(shootTexture);
		shootTextureRegionDrawable = new TextureRegionDrawable(shootTextureRegion);
		Table mainTable = new Table();
		mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Set table to fill stage
		mainTable.setFillParent(true);
		//Set alignment of contents in the table.
		mainTable.center();
		Button.ButtonStyle style = new Button.ButtonStyle();
		UP = new ImageButton(upTextureRegionDrawable);
		LEFT = new ImageButton(leftTextureRegionDrawable);
		RIGHT = new ImageButton(rightTextureRegionDrawable);
		Shoot = new ImageButton(shootTextureRegionDrawable);
		UP.getImage().setFillParent(true);
		UP.setSize(250,250);
		UP.setPosition(0, 50);
		LEFT.getImage().setFillParent(true);
		LEFT.setSize(250,250);
		LEFT.setPosition(1250, 50);
		RIGHT.getImage().setFillParent(true);
		RIGHT.setSize(250,250);
		RIGHT.setPosition(1620, 60);
		Shoot.setSize(150,150);
		Shoot.setPosition(125, 500);

		// create hero
		hero = new Hero();
		hero.img = new Texture("32x32_match.png");

		// create a camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 160, 160);
		camera.update();

		// load the map
		map = new TmxMapLoader().load("matchadventure.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		//create left fire monster 1
		fireMonster1 = new Monster();
		createLeftMonster(fireMonster1);
		fireMonster1.position.set(800,32);

		//create right right monster 0
		fireMonster = new Monster();
		createRightMonster(fireMonster);
		fireMonster.position.set(400,130);

		//create left fire monster 2
		fireMonster2 = new Monster();
		createLeftMonster(fireMonster2);
		fireMonster2.position.set(900,130);

		//create left fire monster 3
		fireMonster3 = new Monster();
		createLeftMonster(fireMonster3);
		fireMonster3.position.set(900,225);

		//create right right monster 4
		fireMonster4 = new Monster();
		createRightMonster(fireMonster4);
		fireMonster4.position.set(400,320);

		//create left fire monster 5
		fireMonster5 = new Monster();
		createLeftMonster(fireMonster5);
		fireMonster5.position.set(900,320);

		//create left fire monster 6
		fireMonster6 = new Monster();
		createLeftMonster(fireMonster6);
		fireMonster5.position.set(700,416);

		//hero's attack (fireBall->left fireBall2->right)
		fireBall = new Attack();
		fireBall.img = new Texture("antifireball.png");
		fireBall2 = new Attack();
		fireBall2.img = new Texture("fireball.png");

		//create fairy
		fairy1 = new Texture("fairy.png");
		fairys.add(fairy1);

		// set hero's position at the start position
		int x = 32, y = 32;
		hero.position.set(x,y);
		UP.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				bgm1.play();hero.moveUp();
			}
		});
		Shoot.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hero.isAttacking = true;
			}
		});

		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.YELLOW);
		hp = new Label(String.format("HP:%d",hero.hp), font);
		hp.setFontScale(4,4);
		hp.setX(Gdx.graphics.getWidth()-300);
		hp.setY(Gdx.graphics.getHeight()-100);

		stage.addActor(UP);
		stage.addActor(LEFT);
		stage.addActor(RIGHT);
		stage.addActor(Shoot);
		stage.addActor(hp);

		portalTexture = new Texture("portal.png");
	}


	@Override
	public void render(float delta) {
		// clear the screen
		ScreenUtils.clear(0.7f, 0.7f, 1.0f, 1);
		stage.getBatch().begin();
		stage.getBatch().draw(background, -hero.position.x, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getBatch().end();
		stage.getBatch().begin();
		stage.getBatch().draw(background, Gdx.graphics.getWidth()-hero.position.x, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getBatch().end();
		// let the camera follow the hero
		camera.position.set(hero.position.x + 20, hero.position.y + 60, 0);
		camera.update();

		// set the TiledMapRenderer view based on what the
		// camera sees, and render the map
		renderer.setView(camera);
		renderer.render();
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			bgm1.play();hero.moveUp();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			hero.moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			hero.moveLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			hero.isAttacking = true;
		}
		if(LEFT.isPressed()){
			hero.moveLeft();
		}
		if(RIGHT.isPressed()){
			hero.moveRight();
		}

		hp.setText(String.format("HP:%d",hero.hp));
		collisionDetection();

		stage.addActor(hero);
		stage.act();
		stage.draw();

		Batch batch = renderer.getBatch();
		batch.begin();
		Rectangle fairyRec = new Rectangle();
		if(flag == false){
			fairyRec.set(350,130,fairy1.getWidth(),fairy1.getWidth());
			batch.draw(fairy1,350,130,fairy1.getWidth(),fairy1.getHeight());
		}
		if (hero.isAttacking){
			if (hero.right){
				fireBall2.position.set(hero.position.x + 30, hero.position.y + 10);
				batch.draw(fireBall2.img,fireBall2.position.x,fireBall2.position.y,Attack.WIDTH,
						Attack.HEIGHT);
				batch.draw(hero.reg, hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
				for(int i = 0; i < 3; i++ ){
					fireBall2.shootRight();
					batch.draw(fireBall2.img,fireBall2.position.x,fireBall2.position.y,Attack.WIDTH,
							Attack.HEIGHT);
				}
				for(Monster monster: monsters){
					monsterDestroyDetection(fireBall2,monster);
				}
			}else {
				fireBall.position.set(hero.position.x - 15, hero.position.y + 10);
				batch.draw(fireBall.img,fireBall.position.x,fireBall.position.y,Attack.WIDTH,
						Attack.HEIGHT);
				batch.draw(hero.flipReg, hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
				for (int i = 0; i < 3; i++){
					fireBall.shootLeft();
					batch.draw(fireBall.img,fireBall.position.x,fireBall.position.y,Attack.WIDTH,
							Attack.HEIGHT);
				}
				for(Monster monster: monsters){
					monsterDestroyDetection(fireBall,monster);
				}
			}
		}else {
			if (hero.right){
				batch.draw(hero.img, hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
			}else {
				batch.draw(hero.img, hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT, 0, 0, 32, 32, true, false);
			}
			Rectangle heroRec = new Rectangle();
			heroRec.set(hero.position.x,hero.position.y,Hero.WIDTH,Hero.HEIGHT);
			if(Intersector.overlaps(fairyRec,heroRec)){
				hero.hp++;
				flag = true;
			}

		}

		for(Monster fireMonster : monsters) {
			if (!fireMonster.isDead) {
				batch.draw(fireMonster.img, fireMonster.position.x, fireMonster.position.y, Monster.WIDTH,
						Monster.HEIGHT);
				if(fireMonster.getType() == "R"){
					fireMonster.fireRight();
				}
				else{
					fireMonster.fireLeft();
				}
				if (fireMonster.getAttack().isActive) {
					batch.draw(fireMonster.getAttack().img, fireMonster.getAttack().position.x,
							fireMonster.getAttack().position.y,
							fire.WIDTH,
							fire.HEIGHT);
				}
				heroDestroyDetection(fireMonster.getAttack());
			}
		}

		batch.draw(portalTexture, 880, 416, 40, 48);

		batch.end();
		if(hero.testDead()){
			game.setScreen(new GameOverScreen(game));
			bgm.dispose();
		}

		if (hero.position.x < 880+40 && hero.position.x > 880-32 && hero.position.y < 400+48 && hero.position.y > 400-32){
			game.setScreen(new WinScreen(game));
			bgm.dispose();
		}

	}

	@Override
	public void resize(int width, int height) {

	}


	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	public void heroDestroyDetection(Attack fire){
		Rectangle heroRec = new Rectangle();
		Rectangle fireRec = new Rectangle();
		heroRec.set(hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
		fireRec.set(fire.position.x,fire.position.y,Attack.WIDTH,Attack.HEIGHT);
		if (Intersector.overlaps(heroRec,fireRec)){
			bgm2.play();
			Gdx.app.log("hit hero", "yes!");
			hero.getHit();
		}
	}

	public void monsterDestroyDetection(Attack fireball, Monster fireMonster){
		Rectangle monsterRec = new Rectangle();
		Rectangle bulletRec = new Rectangle();
		monsterRec.set(fireMonster.position.x, fireMonster.position.y, fireMonster.WIDTH,
				fireMonster.HEIGHT);
		bulletRec.set(fireball.position.x,fireball.position.y,fireball.WIDTH,fireball.HEIGHT);
		if (Intersector.overlaps(monsterRec,bulletRec)){
			Gdx.app.log("hit monster", "yes!");
			fireMonster.setDead();
		}
	}

	public void collisionDetection(){
		// use a rectangle to store hero's position and do the comparison
		Rectangle heroRec = rectPool.obtain();
		heroRec.set(hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);

		// move hero's rect
		heroRec.x += hero.velocity.x;
		heroRec.y += hero.velocity.y;

		// check collision
		hero.onTheGround = false;
		boolean collisionX = false, collisionY = false;
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("solid_layer");

		// check x collision
		if (hero.velocity.x < 0){
			// top left
			collisionX = layer.getCell((int)(heroRec.getX()/16),(int)((heroRec.getY() + Hero.HEIGHT)/16))!=null;
			// middle left
			if (!collisionX){
				collisionX = layer.getCell((int)(heroRec.getX()/16),(int)((heroRec.getY() + Hero.HEIGHT/4)/16))!=null;
			}
			if (!collisionX){
				collisionX = layer.getCell((int)(heroRec.getX()/16),(int)((heroRec.getY() + Hero.HEIGHT*3/4)/16))!=null;
			}
		}else if (hero.velocity.x > 0){
			// top left
			collisionX = layer.getCell((int)(heroRec.getX() + Hero.WIDTH)/16,(int)((heroRec.getY() + Hero.HEIGHT)/16))!=null;
			// middle left
			if (!collisionX){
				collisionX = layer.getCell((int)(heroRec.getX() + Hero.WIDTH)/16,(int)((heroRec.getY() + Hero.HEIGHT/4)/16))!=null;
			}
			if (!collisionX){
				collisionX = layer.getCell((int)(heroRec.getX() + Hero.WIDTH)/16,(int)((heroRec.getY()+ Hero.HEIGHT*3/4)/16))!=null;
			}
		}
		// x collision is true: move back
		if (collisionX){
			heroRec.x -= hero.velocity.x;
			hero.velocity.x = 0;
		}

		// check y collision
		if (hero.velocity.y <= 0){
			// top left
//			collisionY = layer.getCell((int)((heroRec.getX()+ Hero.WIDTH)/16),(int)((heroRec.getY())/16))!=null;
			// middle left
			collisionY = layer.getCell((int)((heroRec.getX() + Hero.WIDTH/2)/16),(int)((heroRec.getY())/16))!=null;
			if (!collisionY){
				collisionY = layer.getCell((int)((heroRec.getX())/16),(int)((heroRec.getY())/16))!=null;
			}
			if (collisionY){
				hero.onTheGround = true;
			}
		}else if (hero.velocity.y > 0){
			// top left
			collisionY = layer.getCell((int)((heroRec.getX() + Hero.WIDTH)/16),(int)((heroRec.getY() + Hero.HEIGHT)/16))!=null;
			// middle left
			if (!collisionY){
				collisionY = layer.getCell((int)((heroRec.getX() + Hero.WIDTH/2)/16),(int)((heroRec.getY() + Hero.HEIGHT)/16))!=null;
			}
			if (!collisionY){
				collisionY = layer.getCell((int)((heroRec.getX())/16),(int)((heroRec.getY() + Hero.HEIGHT)/16))!=null;
			}
		}
		// y collision is true: move back
		if (collisionY){
			heroRec.y -= hero.velocity.y;
			hero.velocity.y = 0;
		}

		rectPool.free(heroRec);

		// move hero
		hero.position.add(hero.velocity);
		if (hero.position.x > Gdx.graphics.getWidth()- Hero.WIDTH){
			hero.position.x = Gdx.graphics.getWidth()- Hero.WIDTH;
		}else if (hero.position.x < 0){
			hero.position.x = 0;
		}
		if (hero.position.y > Gdx.graphics.getHeight()- Hero.HEIGHT){
			hero.position.y = Gdx.graphics.getHeight()- Hero.HEIGHT;
		}else if (hero.position.y < 0){
			game.setScreen(new GameOverScreen(game));
			bgm.dispose();
		}
	}
}