package com.progark.emojimon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.progark.emojimon.controller.FBC;
import com.progark.emojimon.controller.FirebaseControllerInterface;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import sun.rmi.runtime.Log;

public class Emojimon extends Game {
	SpriteBatch batch;
	Texture img;
	GameBoardController gameBoardController;

    public Emojimon(FirebaseControllerInterface firebase){
        FBC.I().setFirebase(firebase);
    }
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		//Test creation of gameboard
		gameBoardController = new GameBoardController();
		gameBoardController.createGameBoard(24, 6, "BASIC");
		//test write to Firebase

		FBC.I().get().addNewGame("Player0Olala");
		//tempLastTurn();
		//FBC.I().get().joinGame();
        String tempGameID = FBC.I().get().getGameIDs()[0].toString();
        FBC.I().get().setGameBoardByGameID(tempGameID, createTempDoubleArrayList());
        FBC.I().get().setGameStatusByGameID(tempGameID, "Playing");
	}

	//debugging

    private List<List<Integer>> createTempDoubleArrayList(){
        List<Integer> action = new ArrayList<Integer>();
        //from;to;
        action.add(2);action.add(3);
        List<List<Integer>> actions = new ArrayList<List<Integer>>();
        actions.add(action);
        return actions;
    }

    private void tempLastTurn(){
        List<Integer> dices = new ArrayList<Integer>();
        dices.add(5); dices.add(3);

        FBC.I().get().addLastTurnByGameID("GameID00", false, "12:35", dices, createTempDoubleArrayList());
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
