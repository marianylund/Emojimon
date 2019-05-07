package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.GameManager;


import sun.rmi.runtime.Log;

public class GameScreenStandard extends ApplicationAdapter implements Screen {

    private Stage stage;
    final Emojimon game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch; // ubrukt, må finne ut av textureatlas
    private TextureRegion triangle;
    private Image cells;

    private TextureAtlas boardAtlas;

    private TextureRegion triUpWhite;
    private TextureRegion triDownWhite;
    private TextureRegion triUpRed;
    private TextureRegion triDownRed;

    private Label debugLabel;

    float sw = Gdx.graphics.getWidth();
    float sh = Gdx.graphics.getHeight();



    public GameScreenStandard(final Emojimon game) {
        Gdx.graphics.setContinuousRendering(true);
        this.game = game;

        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);


        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport);

        boardAtlas = new TextureAtlas(Gdx.files.internal("Board/Output/triangles.atlas"));

        triUpWhite = boardAtlas.findRegion("Triangle-white-up");
        triDownWhite = boardAtlas.findRegion("Triangle-white-down");
        triUpRed = boardAtlas.findRegion("Triangle-red-up");
        triDownRed = boardAtlas.findRegion("Triangle-red-down");


        cells = new Image(new Texture(Gdx.files.internal("blacktri3.png")));
        //triangle.setDrawable(new SpriteDrawable(new Sprite(emojiRegion)));
    }

    @Override
    public void create(){
        //triangle = new TextureRegion(new Texture(Gdx.files.internal("blacktri3.png")));
    }

    private Container createChosenEmojiContainer(){
        Container container = new Container();
        // Chosen emoji
        Table chosenEmojiTable = new Table();
        Label chosenEmojiLabel = new Label("Chosen emoji: ", skin);

        chosenEmojiTable.add(chosenEmojiLabel).pad(20).center();
        //chosenEmojiTable.add(chosenEmoji).size(100).center();

        container.setActor(chosenEmojiTable);

        float cw = sw * 0.8f;
        float ch = sh * 0.2f;

        container.setSize(cw, ch);
        container.setPosition((sw - cw) / 2.0f, (sh - ch));
        container.fillX();

        return container;
    }
    private Container createEmojiChoicesContainer(){
        Table imagesTable = new Table();

        Container container = new Container();

        float cw = sw * 0.8f;
        float ch = sh * 0.5f;

        container.setSize(cw, ch);
        container.setPosition((sw - cw) / 2.0f, (sh - ch) / 2f);
        container.fillX();

        ScrollPane sp = new ScrollPane(imagesTable);
        sp.setFillParent(true);

        container.setActor(sp);

        return container;
    }

    private Container createGameBoard(){
        // Create GameBoardContainer
        Container gameBoardContainer = new Container();
        gameBoardContainer.setSize(sw * 0.8f, sh);
        gameBoardContainer.setPosition(sw * 0.1f, 0);
        gameBoardContainer.fillX(); // TODO fill y too?

        Table gameBoard = new Table();
        Table out1 = new Table();
        Table home1 = new Table();
        Table out0 = new Table();
        Table home0 = new Table();

        int boardSize = 24;
        int trianglesPerZone = boardSize/4;

        addTriangles(out1, trianglesPerZone, false, 1+trianglesPerZone*2);
        addTriangles(out0, trianglesPerZone, true, 1+trianglesPerZone);
        addTriangles(home1, trianglesPerZone, false, 1+trianglesPerZone*3);
        addTriangles(home0, trianglesPerZone, true, 1);

        gameBoard.add(out1);
        gameBoard.add().pad(40); //middle of the board
        gameBoard.add(home1);
        gameBoard.row().pad(50);
        gameBoard.add(out0);
        gameBoard.add().pad(40); //middle of the board
        gameBoard.add(home0);

        ScrollPane sp = new ScrollPane(gameBoard);
        sp.setFillParent(true);

        gameBoardContainer.setActor(sp);
        return gameBoardContainer;
    }

    private Container createSideMenu(){
        Container sideMenuContainer = new Container();
        sideMenuContainer.setSize(sw * 0.1f, sh);
        sideMenuContainer.setPosition(0, 0);
        sideMenuContainer.fillX(); // TODO fill y too?

        Table sideMenu = new Table();

        // Add leave button
        TextButton backButton = new TextButton("Back", skin);
        backButton.setTransform(true);
        backButton.setScale(2f);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        sideMenu.add(backButton); sideMenu.row();//.pad(10);

        // Add Turn emoji
        TextureAtlas emojiAtlas = new TextureAtlas(Gdx.files.internal("Emojis/Output/emojiatlas.atlas"));
        TextureAtlas.AtlasRegion emojiRegion = emojiAtlas.findRegion(GameManager.GetInstance().getEmoji());
        sideMenu.add(new Image(emojiRegion)).size(100); sideMenu.row().pad(10);

        // Add timer label wannabe, is used for debug for now
        debugLabel = new Label("Debug: ", skin);
        sideMenu.add(debugLabel); sideMenu.row().pad(10);
        // Add throw dice button

        TextButton diceButton = new TextButton("Throw Dice", skin);
        diceButton.setTransform(true);
        //diceButton.setScale(3f);
        diceButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                debugLabel.setText("Pew Pew");
                // TODO throw dice
            }
        });
        sideMenu.add(diceButton);
        sideMenuContainer.setActor(sideMenu);
        return sideMenuContainer;
    }

    private Container createSideBoard(){
        Container sideBoardContainer = new Container();
        sideBoardContainer.setSize(sw * 0.1f, sh);
        sideBoardContainer.setPosition(sw * 0.9f, 0); //TODO does it even make sense?
        sideBoardContainer.fillX(); // TODO fill y too?

        // Add player1's goal

        // Add dieded pieces

        // Add player0's goal

        return sideBoardContainer;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true);

        stage.addActor(createSideMenu());
        stage.addActor(createGameBoard());
        stage.addActor(createSideBoard());
    }

    private void addTriangles(Table t, int n, boolean rotationUp, int startTriangle){
        TextureRegion chosenTriangle = null;
        for(int i = 0; i < n; i++){
            if(rotationUp){
                if(i%2 == 0){
                    chosenTriangle = triUpWhite;
                }else{
                    chosenTriangle = triUpRed;
                }
            }else{

                if(i%2 == 0){
                    chosenTriangle = triDownWhite;
                }else{
                    chosenTriangle = triDownRed;
                }
            }
            Image triangle = new Image(chosenTriangle);

            if(rotationUp){
                final int triangleNumber = startTriangle + n - i - 1;
                triangle.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        debugLabel.setText(triangleNumber);
                    }
                });
            } else {
                final int triangleNumber = startTriangle + i;
                triangle.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        debugLabel.setText(triangleNumber);
                    }
                });
            }

            t.add(triangle).pad(10).size(120,400);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
//        batch.begin();
//        batch.draw(spritesheet, 0, 0);
//        batch.end();
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        //triangle.dispose();
    }


}