package com.progark.emojimon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.progark.emojimon.controller.FBC;
import com.progark.emojimon.gameScreens.GameOverScreen;
import com.progark.emojimon.gameScreens.MainMenuScreen;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.fireBaseData.Converter;
import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;

import java.util.ArrayList;
import java.util.List;


public class Emojimon extends Game {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 600;
    SpriteBatch batch;
    Texture img;
    GameBoardController gameBoardController;
    private Music music;

    public Emojimon(FirebaseControllerInterface firebaseControllerInterface) {
        FBC.I().setFirebase(firebaseControllerInterface);
    }
	
	@Override
	public void create () {
        GameManager.GetInstance().createApp(this);
        setScreen(new MainMenuScreen(this));
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        GameManager.GetInstance().createPreference();
        if(!GameManager.GetInstance().getPreferences().isEmoji().isEmpty()){
            GameManager.GetInstance().setLocalPlayerEmoji(GameManager.GetInstance().getPreferences().isEmoji());
        }
        //music credit: patrickdearteaga.com
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/Humble Match.ogg"));
        music.setLooping(true);
        music.play();
    }

    //debugging

    private List<List<Integer>> createTempDoubleArrayList() {
        List<Integer> action = new ArrayList<Integer>();
        //from;to;
        action.add(2);
        action.add(3);
        List<List<Integer>> actions = new ArrayList<List<Integer>>();
        actions.add(action);
        return actions;
    }

    private void tempLastTurn() {
        List<Integer> dices = new ArrayList<Integer>();
        dices.add(5);
        dices.add(3);

        //FBC.I().get().updateLastTurn("GameID00", false, "12:35", dices, createTempDoubleArrayList());
    }


    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();

    }

    @Override
    public void render() {
        super.render();
		/*
		Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			batch.draw(img, 0, 0);
			batch.end();
		 */
    }
}
