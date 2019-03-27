package com.progark.emojimon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.progark.emojimon.gameScreens.CreateRulesetScreen;
import com.progark.emojimon.gameScreens.MainMenuScreen;

public class Emojimon extends Game {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 880;
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	/*	batch = new SpriteBatch();
		img = new Texture("badlogic.jpg"); */
	}

	@Override
	public void render () {
		super.render();
		 /* Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end(); */
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}


}
