package com.progark.emojimon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.progark.emojimon.controller.FBC;
import com.progark.emojimon.gameScreens.MainMenuScreen;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.fireBaseData.Converter;
import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;

import java.util.ArrayList;
import java.util.List;



public class Emojimon extends Game {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    SpriteBatch batch;
    Texture img;
    GameBoardController gameBoardController;

    public Emojimon(FirebaseControllerInterface firebaseControllerInterface){
        FBC.I().setFirebase(firebaseControllerInterface);
    }
	
	@Override
	public void create () {
        setScreen(new MainMenuScreen(this));
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		//Test creation of gameboard
		gameBoardController = new GameBoardController();
		gameBoardController.createStandardGameBoard();


		//test write to Firebase

		//uncomment this when testing on android
        /*
        FBC.I().get().addNewGame("Player0Olala");

		List<String> strategies = new ArrayList<String>();
		strategies.add("BASIC"); strategies.add("BASIC"); strategies.add("BASIC");
        FBC.I().get().addNewGame("Player0Olala", strategies);

		FBC.I().get().joinGame();
        String tempGameID = FBC.I().get().getGameIDs()[0].toString();
        FBC.I().get().setGameBoardByGameID(tempGameID, Converter.fromBoardPositionsToList(gameBoardController.getBoardPositions()));
        FBC.I().get().setGameStatusByGameID(tempGameID, "playing");
        */
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

        //FBC.I().get().addLastTurnByGameID("GameID00", false, "12:35", dices, createTempDoubleArrayList());
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
