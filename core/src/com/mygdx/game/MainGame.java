package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame implements Screen {
	private Game game;
	public static final float WORLD_WIDTH = 480;
	public static final float WORLD_HEIGHT = 800;
	private ImageButton UP;
	private Button Shoot;
	private ImageButton LEFT;
	private ImageButton RIGHT;
	private Stage stage;
	private Hero hero;
	private Monster fireMonster;
	private Monster fireMonster2;
	private Monster shooter;
	private Attack fire;
	private Attack fire2;
	private Attack bullet;
	private TiledMap map;
	protected Skin skin;
	private Texture upTexture;
	private Texture rightTexture;
	private Texture leftTexture;
	private TextureRegion UpTextureRegion;
	private TextureRegion RightTextureRegion;
	private TextureRegion leftTextureRegion;
	private TextureRegionDrawable upTextureRegionDrawable;
	private TextureRegionDrawable leftTextureRegionDrawable;
	private TextureRegionDrawable rightTextureRegionDrawable;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Texture background;
	private Music bgm;
	private Array<Rectangle> tiles = new Array<Rectangle>();
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};

	public MainGame(Game game){
		this.game = game;
		background = new Texture("backgroundtest.jpg");
		FileHandle bgmHandle = Gdx.files.internal("bgm.wav");
		bgm = Gdx.audio.newMusic(bgmHandle);
		bgm.setLooping(true);
		bgm.play();
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
		Shoot = new Button(skin);
		UP.getImage().setFillParent(true);
		UP.setSize(250,200);
		UP.setPosition(0, 250);
		LEFT.getImage().setFillParent(true);
		LEFT.setSize(250,250);
		LEFT.setPosition(1250, 50);
		RIGHT.getImage().setFillParent(true);
		RIGHT.setSize(250,250);
		RIGHT.setPosition(1620, 60);
		Shoot.setSize(150,150);
		Shoot.setPosition(100, 150);

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

		//create fire monster
		fireMonster = new Monster();
		fireMonster.setID(1);
		fireMonster.img = new Texture("slime.png");
		fireMonster2 = new Monster();
		fireMonster2.setID(2);
		fireMonster2.img = new Texture("antislime.png");
		//create shooter
		//shooter = new Monster();
		//shooter.img = new Texture("bird.png");
		//shooter.setType("shooter");

		//create fire
		fire = new Attack();
		fire.img = new Texture("fire.png");
		fire.setType("L");
		fire2 = new Attack();
		fire2.img = new Texture("antifire.png");
		fire2.setType("R");


		// set hero's position at the start position
		int x = 32, y = 32;
		hero.position.set(x,y);
		UP.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hero.moveUp();
//				if (hero.left){
//					camera.translate(+2, 0);
//				}else {
//					camera.translate(-2, 0);
//				}
			}
		});
		Shoot.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				bullet.position.set(hero.position.x+10,hero.position.y+20);
			}
		});
		//set monster's position
		fireMonster.position.set(400,130);
		fire.position.set(410,130);
		fireMonster.activeMonster();
		fireMonster2.position.set(900,130);
		fire2.position.set(885,130);
		fireMonster2.activeMonster();

		//shooter.activeMonster();
		stage.addActor(UP);
		stage.addActor(LEFT);
		stage.addActor(RIGHT);
		stage.addActor(Shoot);

	}


	@Override
	public void render(float delta) {


		// clear the screen
		ScreenUtils.clear(0.7f, 0.7f, 1.0f, 1);

		// let the camera follow the koala, x-axis only
		camera.position.set(hero.position.x + 60, hero.position.y + 60, 0);
		camera.update();

		// set the TiledMapRenderer view based on what the
		// camera sees, and render the map
		renderer.setView(camera);
		renderer.render();
		if(LEFT.isPressed()){
//			camera.translate(+2,0);
			hero.moveLeft();
			hero.left = true;
			hero.right = false;
		}
		if(RIGHT.isPressed()){
//			camera.translate(-2,0);
			hero.moveRight();
			hero.right = true;
			hero.left = false;
		}

		collisionDetection();

		stage.addActor(hero);
		stage.addActor(fireMonster);

		// render the ball,firemonster,shooter
		Batch batch = renderer.getBatch();
		batch.begin();
		batch.draw(hero.img, hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
		batch.draw(fireMonster.img,fireMonster.position.x,fireMonster.position.y,Monster.WIDTH,
				Monster.HEIGHT);
		batch.draw(fireMonster2.img,fireMonster2.position.x,fireMonster2.position.y,Monster.WIDTH,
				Monster.HEIGHT);
		//batch.draw(shooter.img,shooter.position.x,shooter.position.y,Monster.WIDTH,Monster
		//.HEIGHT);

		batch.draw(fire.img,fire.position.x,fire.position.y ,fire.WIDTH,
					fire.HEIGHT);
		batch.draw(fire2.img,fire2.position.x,fire2.position.y ,fire.WIDTH,
				fire.HEIGHT);


		//batch.draw(bullet.img,bullet.position.x,bullet.position.y,bullet.WIDTH,
		//		bullet.HEIGHT);

		batch.end();
		fireMonster.fireRight(fire);
		fireMonster2.fireLeft(fire2);
		heroDestroyDetection();
		//shooter.shoot(bullet);


		stage.act();
		stage.draw();
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
	public void heroDestroyDetection(){
		Rectangle heroRec = new Rectangle();
		Rectangle fireRec = new Rectangle();
		heroRec.set(hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
		fireRec.set(fire.position.x,fire.position.y,Attack.WIDTH,Attack.HEIGHT);
		if (Intersector.overlaps(heroRec,fireRec)){
			Gdx.app.log("hit", "yes!");
			hero.getHit();
		}
	}

	public void monsterDestroyDetection(){
		Rectangle monsterRec = new Rectangle();
		Rectangle bulletRec = new Rectangle();
		monsterRec.set(fireMonster.position.x, fireMonster.position.y, fireMonster.WIDTH,
				fireMonster.HEIGHT);
		bulletRec.set(bullet.position.x,bullet.position.y,bullet.WIDTH,bullet.HEIGHT);
		if (Intersector.overlaps(monsterRec,bulletRec)){
			fireMonster.setDead();
		}
	}

	public void collisionDetection(){
		// use a rectangle to store hero's position and do the comparison
		Rectangle heroRec = rectPool.obtain();
		heroRec.set(hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
		// check hero's velocity on x and y axes
		int startX, startY, endX, endY;
		startX = (int)(hero.position.x + hero.velocity.x);
		endX = (int)(hero.position.x + Hero.WIDTH + hero.velocity.x);
		startY = (int)(hero.position.y + hero.velocity.y);
		endY = (int)(hero.position.y + Hero.HEIGHT + hero.velocity.y);
		// get solid_layer rects
		getSolidTiles(startX, startY, endX, endY, tiles);
		// move hero's rect
		heroRec.x += hero.velocity.x;
		heroRec.y += hero.velocity.y;
		// check collision
		for (Rectangle tile : tiles) {
			if (heroRec.overlaps(tile)) {
				if (heroRec.x + heroRec.width > tile.x && hero.velocity.x > 0){
					heroRec.x = tile.x - heroRec.width;
					hero.position.x = tile.x - heroRec.width;
					hero.velocity.x = 0;
				}else if(heroRec.x < tile.x + tile.width && hero.velocity.x < 0){
					heroRec.x = tile.x + tile.width;
					hero.position.x = tile.x + tile.width;
					hero.velocity.x = 0;
				}
				else if (heroRec.y + heroRec.height > tile.y && hero.velocity.y > 0){
					heroRec.y = tile.y - heroRec.height;
					hero.position.y = tile.y - heroRec.height;
					hero.velocity.y = 0;
				}else if(heroRec.y < tile.y + tile.height && hero.velocity.y < 0){
					heroRec.y = tile.y + tile.height;
					hero.position.y = tile.y + tile.height;
					hero.velocity.y = 0;
					hero.onTheGround = true;
				}
			}
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
			hero.position.y = 0;
		}
	}

	private void getSolidTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		// get walls layer
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("solid_layer");
		rectPool.freeAll(tiles);
		tiles.clear();
		// add wall tile rectangles into tiles
		for (int y = (startY/16); y <= (endY/16); y++) {
			for (int x = (startX/16); x <= (endX/16); x++) {
				TiledMapTileLayer.Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x*16, y*16, 16, 16);
					tiles.add(rect);
				}
			}
		}
	}
}
