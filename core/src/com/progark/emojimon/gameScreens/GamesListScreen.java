package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.controller.FBC;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.soap.Text;

import static com.badlogic.gdx.graphics.Color.BLACK;

public class GamesListScreen implements Screen {

    private Stage stage;
    final Emojimon game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;
    private Table gamesTable;
    private ScrollPane scrollPane;
    private HashMap<String, String> gamesList = new HashMap<String, String>();
    private boolean listUpdated = false;
    private BitmapFont font;
    private Label.LabelStyle style;

    public GamesListScreen(final Emojimon game) {
        this.game = game;
        atlas = new GameSkin().getAtlas();
        skin = new GameSkin().getSkin();
        font = new GameSkin().generateFont(40);
        style = new Label.LabelStyle(font, Color.ORANGE);
        skin.getFont("font").getData().setScale(3f,3f);
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
        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        gamesTable = new Table();
        gamesTable.setFillParent(true);
        gamesTable.add(backBtn);
        gamesTable.row();
        scrollPane = new ScrollPane(gamesTable);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
    }

    @Override
    public void render(float delta) {

        if (GameManager.GetInstance().getGameData() != null &&
                GameManager.GetInstance().getGameData().getStatus() == GameManager.GameStatus.STARTET) {
            game.setScreen(new GameScreenStandard(game));
        }

        // Redraws list of games when finished downloading from Firebase
        if (GameManager.GetInstance().getAllWaitingGamesList() != null && listUpdated == false) {
            gamesList = GameManager.GetInstance().getAllWaitingGamesList();
            reDrawTable();
            listUpdated = true;
            System.out.println("UPDATED");
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
        listUpdated = false;
    }

    @Override
    public void dispose() {

    }

    public void reDrawTable () {
        for (final String key : gamesList.keySet()){
            System.out.println(key);

            final Label gameName = new Label(gamesList.get(key), style);
            gameName.setAlignment(Align.left);
            gameName.setFontScale(2);

            TextButton joinBtn = new TextButton("Join", skin);
            joinBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("JOINED GAME");
                    //FBC.I().get().joinGameById(key);
                    GameManager.GetInstance().joinGame(key);
                    game.setScreen(new LobbyScreen(game));
                }
            });

            gamesTable.add(gameName).size(150).width(500);
            gamesTable.add(joinBtn);
            gamesTable.row();
        }
    }
}
