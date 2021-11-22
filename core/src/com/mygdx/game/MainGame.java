package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainGame implements Screen {
	public static final float WORLD_WIDTH = 480;
	public static final float WORLD_HEIGHT = 800;
	private Button UP;
	private Button DOWN;
	private Button LEFT;
	private Button RIGHT;
	private Stage stage;
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


	}


	@Override
	public void render(float delta) {

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
