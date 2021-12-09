package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WinScreen implements Screen {
	private Viewport viewport;
	private Stage stage;
	private OrthographicCamera camera;
	private Game game;
	private SpriteBatch batch;
	private Label winLabel;
	private Label nextlevelLabel;
	private TextureAtlas atlas;
	protected Skin skin;
	private Label ExitLabel;
	private Music bgm1;
	public WinScreen(Game game){
		this.game = game;
		atlas = new TextureAtlas("uiskin.atlas");
		skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewport.apply();
		FileHandle bgmHandle1 = Gdx.files.internal("win.mp3");
		bgm1 = Gdx.audio.newMusic(bgmHandle1);
		bgm1.play();
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		stage = new Stage(viewport,batch);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Label.LabelStyle font1 = new Label.LabelStyle(new BitmapFont(), Color.RED);
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

		Table table = new Table();
		table.center();
		table.setFillParent(true);
		winLabel = new Label("Virtory !!!", font1);
		winLabel.setFontScale(6,6);
		nextlevelLabel = new Label("Next level", font);
		nextlevelLabel.setFontScale(4,4);
		ExitLabel = new Label("Menu Screen", font);
		ExitLabel.setFontScale(4,4);
		nextlevelLabel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainGame(game));
				bgm1.dispose();
			}
		});
		ExitLabel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainScreen(game));
				bgm1.dispose();
			}
		});
		table.add(winLabel).expandX();
		table.row();
		table.add(nextlevelLabel).expandX().padTop(30);
		table.row();
		table.add(ExitLabel).expandX().padTop(30);
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
}
