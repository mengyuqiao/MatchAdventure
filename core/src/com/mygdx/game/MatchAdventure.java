package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MatchAdventure extends Game {
	private Game game;
	@Override
	public void create () {
		this.game = this;
		setScreen(new MainScreen(this.game));
	}
}
