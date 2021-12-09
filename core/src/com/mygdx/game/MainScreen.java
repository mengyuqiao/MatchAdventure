package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements Screen {
	private Game game;
	private SpriteBatch batch;
	protected Stage stage;
	private Viewport viewport;
	private OrthographicCamera camera;
	private TextureAtlas atlas;
	protected Skin skin;
	private Texture background;
	private Music bgm;

	public MainScreen(Game game){
		this.game = game;
		atlas = new TextureAtlas("uiskin.atlas");
		skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
		background = new Texture("MainBackground.jpg");
		background = new Texture("MainBackground.jpg");
		FileHandle bgmHandle = Gdx.files.internal("bgm.wav");
		bgm = Gdx.audio.newMusic(bgmHandle);
		bgm.setLooping(true);
		bgm.play();

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();

		stage = new Stage(viewport, batch);
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		//Create Table
		Table mainTable = new Table();
		mainTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Set table to fill stage
		mainTable.setFillParent(true);
		//Set alignment of contents in the table.
		Label nameLabel = new Label("Match Adventure", skin);
		//Create buttons
		Label playButton = new Label("Play",skin);
		Label exitButton = new Label("Exit", skin);
		int x = Gdx.graphics.getHeight();
		int unit = x / 4;

		float scale = unit / playButton.getHeight();

		float width = playButton.getWidth() * scale;


		nameLabel.setFontScale(8, 8);
		playButton.setFontScale(4, 4);
		exitButton.setFontScale(4, 4);

		//Add listeners to buttons
		playButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainGame(game));
				bgm.dispose();
			}
		});

		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//Gdx.app.exit();
				game.setScreen(new GameOverScreen(game));
			}
		});

		//Add buttons to table
		mainTable.add(nameLabel).size(width, unit).padBottom(20).row();
		mainTable.add(playButton).size(width, unit).padBottom(20).row();
		mainTable.add(exitButton).size(width, unit).padBottom(20).row();

		//Add table to stage
		stage.addActor(mainTable);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getBatch().end();

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
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
		bgm.dispose();
		stage.dispose();
		batch.dispose();
		background.dispose();
	}
}
