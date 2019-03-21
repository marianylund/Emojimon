package com.progark.emojimon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.progark.emojimon.controller.FBC;
import com.progark.emojimon.controller.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;

import sun.rmi.runtime.Log;

public class Emojimon extends Game {
	SpriteBatch batch;
	Texture img;

    public Emojimon(FirebaseControllerInterface firebase){
        FBC.I().setFirebase(firebase);
    }
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		//test write to Firebase
		FBC.I().get().addNewGame(new Player(0, 0, true), new ArrayList<List<Integer>>());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}


}
