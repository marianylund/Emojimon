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

public class MainMenuScreen implements Screen {

    private Stage stage;
    final Emojimon game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;

    public MainMenuScreen(final Emojimon game) {
        this.game = game;

        //import skin to be used for GUI elements
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        skin.getFont("font").getData().setScale(3f,3f);

//        //Generate font
//        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Roboto-Regular.ttf"));
//        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        fontParameter.size = 12;
//        font = fontGenerator.generateFont(fontParameter);


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
        Label emojimonLabel = new Label("Emojimon", skin);
        TextButton createGameButton = new TextButton("Create game", skin);
        TextButton joinGameButton = new TextButton("Join game", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        TextButton selectEmojiButton = new TextButton("Select emoji", skin);

        //Add listeners to buttons to add functionality to them when clicked
        createGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new CreateRulesetScreen(game));
            }
        });
        joinGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((Game)Gdx.app.getApplicationListener()).setScreen(new ChooseGameScreen(game));
                GameManager.GetInstance().joinGame();

            }
        });
        selectEmojiButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SelectEmojiScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add labels and buttons to menu
        mainTable.add(emojimonLabel).pad(10).colspan(2).height(Gdx.graphics.getHeight()*0.20f);
        mainTable.row();
        mainTable.add(createGameButton).pad(0.1f).width(Gdx.graphics.getWidth()*0.40f).height(Gdx.graphics.getHeight()*0.50f);
        mainTable.add(joinGameButton).pad(0.1f).width(Gdx.graphics.getWidth()*0.40f).height(Gdx.graphics.getHeight()*0.50f);
        mainTable.row();
        mainTable.add(selectEmojiButton).pad(10).colspan(2).width(Gdx.graphics.getWidth()*0.8f).height(Gdx.graphics.getHeight()*0.20f);
        mainTable.row();
//        mainTable.add(exitButton).pad(10);

        //Add menu to stage as an actor
        stage.addActor(mainTable);
    }

    public void create(){

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if (GameManager.GetInstance().getGameData() != null) {
            game.setScreen(new GameScreenStandard(game));
        }
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
