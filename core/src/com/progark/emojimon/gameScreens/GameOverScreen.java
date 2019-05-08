package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.controller.FBC;

public class GameOverScreen implements Screen {

    private Stage stage;
    final Emojimon game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private Boolean gameWon;

    public GameOverScreen(final Emojimon game, int winningPlayer) {
        this.game = game;
        this.gameWon = gameWon;

        //import skin to be used for GUI elements
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create table to contain menu items
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        //Create label and buttons to be displayed
        Label gameOverLabel = new Label("GAME OVER", skin);
        Label youWonLabel = new Label("YOU WON", skin);
        Label youLostLabel = new Label("YOU LOST", skin);
        TextButton backToMainMenuBtn = new TextButton("Back to Main Menu", skin);

        //Add listeners to buttons to add functionality to them when clicked
        backToMainMenuBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        //Add labels and buttons to menu
        mainTable.add(gameOverLabel).pad(10);
        mainTable.row();
        if (GameManager.GetInstance().getLocalPlayer() == GameManager.GetInstance().getWinningPlayer()) {
            mainTable.add(youWonLabel).pad(10);
        } else {mainTable.add(youLostLabel).pad(10);}
        mainTable.row();
        mainTable.add(backToMainMenuBtn).pad(10).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getWidth()/6);
        mainTable.row();

        //Add menu to stage as an actor
        stage.addActor(mainTable);


        //Add menu to stage as an actor
    }

    public void create(){

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        /**/
    }

    @Override
    public void dispose() {
    }
}
