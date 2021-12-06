package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Iterator;

public class MainGame implements Screen {
	private Game game;
	public static final float WORLD_WIDTH = 480;
	public static final float WORLD_HEIGHT = 800;
	private Button UP;
	private Button DOWN;
	private Button LEFT;
	private Button RIGHT;
	private Stage stage;
	private Hero hero;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Array<Rectangle> tiles = new Array<Rectangle>();
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};

	public MainGame(Game game){
		this.game = game;
	}

	public void show() {
		Gdx.input.setInputProcessor(stage);

		Table mainTable = new Table();
		mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Set table to fill stage
		mainTable.setFillParent(true);
		//Set alignment of contents in the table.
		mainTable.center();
		Button.ButtonStyle style = new Button.ButtonStyle();
		UP = new Button(style);
		UP.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		DOWN = new Button(style);
		DOWN.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		LEFT = new Button(style);
		LEFT.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});
		RIGHT = new Button(style);
		RIGHT.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});

		// load the map
		map = new TmxMapLoader().load("matchadventure.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		// create hero
		hero = new Hero();
		hero.img = new Texture("Ball.png");
		// set hero's position at the start position
		MapLayer game_objects = map.getLayers().get("game_objects");
		System.out.println("!!!!!!!!!!!!!!!!!!"+game_objects);
		int x = 32, y = 32;
		hero.position.set(x,y);

		// create a camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 160, 160);
		camera.update();
		// camera.translate(-32,0);
	}


	@Override
	public void render(float delta) {
		// clear the screen
		ScreenUtils.clear(0.7f, 0.7f, 1.0f, 1);

		// let the camera follow the koala, x-axis only
		camera.update();

		// set the TiledMapRenderer view based on what the
		// camera sees, and render the map
		renderer.setView(camera);
		renderer.render();

		// render the ball
		Batch batch = renderer.getBatch();
		batch.begin();
		batch.draw(hero.img, hero.position.x, hero.position.y, Hero.WIDTH, Hero.HEIGHT);
		batch.end();
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
					heroRec.x -= hero.velocity.x;
					hero.velocity.x = 0;
				}else if(heroRec.x < tile.x + tile.width && hero.velocity.x < 0){
					heroRec.x -= hero.velocity.x;
					hero.velocity.x = 0;
				}
				else if (heroRec.y + heroRec.height > tile.y && hero.velocity.y > 0){
					heroRec.y -= hero.velocity.y;
					hero.velocity.y = 0;
				}else if(heroRec.y < tile.y + tile.height && hero.velocity.y < 0){
					heroRec.y -= hero.velocity.y;
					hero.velocity.y = 0;
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

	public void heroMoveLeft(){
		camera.translate(20, 0);
		hero.moveLeft();
	}
}
