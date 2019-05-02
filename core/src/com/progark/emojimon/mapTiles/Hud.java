package com.progark.emojimon.mapTiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.gameScreens.ChooseGameScreen;
import com.progark.emojimon.gameScreens.CreateRulesetScreen;

public class Hud {

    private TextureAtlas atlas;
    private Stage stage;
    private FitViewport stageViewport;
    private Skin skin;
    private Emojimon game;

    public Hud(SpriteBatch spriteBatch, final Emojimon game) {
        stageViewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/8);
        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        this.game = game;
        //import skin to be used for GUI elements
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);

        Table table = new Table();

        Gdx.input.setInputProcessor(stage);

        // Create table to contain menu items
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        //Create label and buttons to be displayed
        TextButton gobackbutton = new TextButton("Back", skin);
        TextButton doublebutton = new TextButton("Double", skin);
        TextButton resignbutton = new TextButton("Resign", skin);

        //Add listeners to buttons to add functionality to them when clicked
        gobackbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new CreateRulesetScreen(game));
            }
        });
        doublebutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new ChooseGameScreen(game));
            }
        });
        resignbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add labels and buttons to menu
        mainTable.add(gobackbutton).pad(10).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/8);
        mainTable.add(doublebutton).pad(10).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/8);
        mainTable.add(resignbutton).pad(10).width(Gdx.graphics.getWidth()/3).height(Gdx.graphics.getHeight()/8);

        stage.addActor(table);
    }

    public Stage getStage() { return stage; }

    public void dispose(){
        stage.dispose();
    }
}
