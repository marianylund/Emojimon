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
import com.badlogic.gdx.math.Rectangle;
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
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Position;


import java.util.List;

import sun.rmi.runtime.Log;

public class GameScreenStandard extends ApplicationAdapter implements Screen {

    private Stage stage;
    final Emojimon game;
    private GameBoardController gameBoardController;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch; // ubrukt, må finne ut av textureatlas
    private TextureRegion triangle;
    private Image cells;

    private TextureAtlas boardAtlas;
    private TextureAtlas emojiAtlas;

    private TextureRegion triUpWhite;
    private TextureRegion triDownWhite;
    private TextureRegion triUpRed;
    private TextureRegion triDownRed;

    private Label debugLabel;

    float sw = Gdx.graphics.getWidth();
    float sh = Gdx.graphics.getHeight();

    private int fieldReference;


    public GameScreenStandard(final Emojimon game) {
        Gdx.graphics.setContinuousRendering(true);
        this.game = game;
        this.gameBoardController = new GameBoardController();//need to be changed to the singelton reference
        this.gameBoardController.createStandardGameBoard();

        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        skin.getFont("font").getData().setScale(1.5f,1.5f);

        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport);

        boardAtlas = new TextureAtlas(Gdx.files.internal("Board/Output/triangles.atlas"));
        emojiAtlas = new TextureAtlas(Gdx.files.internal("Emojis/Output/emojiatlas.atlas"));

        triUpWhite = boardAtlas.findRegion("Triangle-white-up");
        triDownWhite = boardAtlas.findRegion("Triangle-white-down");
        triUpRed = boardAtlas.findRegion("Triangle-red-up");
        triDownRed = boardAtlas.findRegion("Triangle-red-down");


        cells = new Image(new Texture(Gdx.files.internal("blacktri3.png")));
        //triangle.setDrawable(new SpriteDrawable(new Sprite(emojiRegion)));
    }

    @Override
    public void create() {
        //triangle = new TextureRegion(new Texture(Gdx.files.internal("blacktri3.png")));
    }

<<<<<<< Updated upstream
    private Container createGameBoard(){
=======
    private Container createChosenEmojiContainer() {
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

    private Container createEmojiChoicesContainer() {
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

    private Container createButtonContainer() {
        // Add Back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.setTransform(true);
        backButton.setScale(3f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        //Add button container
        Container buttonContainer = new Container();

        float cw = sw * 0.8f;
        float ch = sh * 0.5f;
        buttonContainer.center();
        buttonContainer.setPosition(sw * 0.5f - backButton.getWidth(), ch * 0.66f);
        buttonContainer.fillX();

        buttonContainer.setActor(backButton);

        return buttonContainer;
    }

    private Container createGameBoard() {
>>>>>>> Stashed changes
        // Create GameBoardContainer
        Container gameBoardContainer = new Container();
        gameBoardContainer.setSize(sw * 0.8f, sh);
        gameBoardContainer.setPosition(sw * 0.1f, 0);
        gameBoardContainer.fillX(); // TODO fill y too?
        gameBoardContainer.fillY();

        Table gameBoard = new Table();
        Table out1 = new Table();
        Table home1 = new Table();
        Table out0 = new Table();
        Table home0 = new Table();

        int boardSize = gameBoardController.getBoardSize();
        int trianglesPerZone = boardSize / 4;

        addTriangles(out1, trianglesPerZone, false, 1 + trianglesPerZone * 2);
        addTriangles(out0, trianglesPerZone, true, 1 + trianglesPerZone);
        addTriangles(home1, trianglesPerZone, false, 1 + trianglesPerZone * 3);
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

    private Container createSideMenu() {
        Container sideMenuContainer = new Container();
        sideMenuContainer.setSize(sw * 0.1f, sh);
        sideMenuContainer.setPosition(0, 0);
        sideMenuContainer.fillY(); sideMenuContainer.fillX();

        Table sideMenu = new Table();

        // Add leave button
        TextButton backButton = new TextButton("Back", skin);
<<<<<<< Updated upstream
        backButton.addListener(new ClickListener(){
=======
        backButton.setTransform(true);
        backButton.setScale(2f);
        backButton.addListener(new ClickListener() {
>>>>>>> Stashed changes
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
<<<<<<< Updated upstream
        sideMenu.add(backButton).expand().uniform(); sideMenu.row();//.pad(10);
=======
        sideMenu.add(backButton);
        sideMenu.row();//.pad(10);
>>>>>>> Stashed changes

        // Add Turn emoji
        TextureAtlas.AtlasRegion emojiRegion = emojiAtlas.findRegion(GameManager.GetInstance().getEmoji());
        sideMenu.add(new Image(emojiRegion)).size(100);
        sideMenu.row().pad(10);

        // Add timer label wannabe, is used for debug for now
<<<<<<< Updated upstream
        debugLabel = new Label("Debug:", skin);
        sideMenu.add(debugLabel); sideMenu.row().pad(10);
=======
        debugLabel = new Label("Debug: ", skin);
        sideMenu.add(debugLabel);
        sideMenu.row().pad(10);
>>>>>>> Stashed changes
        // Add throw dice button

        TextButton diceButton = new TextButton("Throw\nDice", skin);
        diceButton.setTransform(true);
        //diceButton.setScale(3f);
        diceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameBoardController.rollDice();
                //gameBoardController.getDieList().get(0);
                debugLabel.setText(gameBoardController.getDieList().get(0).getValue() + " " + gameBoardController.getDieList().get(1).getValue());
                // TODO begrenes hvor mange ganger man kaster terning
            }
        });
        sideMenu.add(diceButton).expand().uniform();
        sideMenuContainer.setActor(sideMenu);
        return sideMenuContainer;
    }


    private Container createPlayer1Board() {
        // Add player1's goal
        Container player1home = new Container();
        player1home.setSize(sw * 0.1f, sh * 0.4f);
        player1home.setPosition(sw * 0.9f, 0);
        player1home.fillX();

        Table scoreboard = new Table();

        Image img = new Image(new Texture(Gdx.files.internal("rect.png")));
        img.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                debugLabel.setText("scorefield");

            }
        });
        scoreboard.add(img);

        return player1home;
    }

    private Container createBarBoard() {
        // Add bar field
        Container barfield = new Container();
        barfield.setSize(sw * 0.1f, sh * 0.2f);
        barfield.setPosition(sw * 0.9f, sh * 0.4f);
        barfield.fillX();

        return barfield;
    }


    private Container createPlayer0Board() {
        // Add player0's goal
        Container player0home = new Container();
        player0home.setSize(sw * 0.1f, sh * 0.4f);
        player0home.setPosition(sw * 0.9f, sh * 0.6f);
        player0home.fillX();


        return player0home;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true);

        stage.addActor(createSideMenu());
        stage.addActor(createGameBoard());
        stage.addActor(createPlayer1Board());
        stage.addActor(createPlayer0Board());
        stage.addActor(createBarBoard());
    }

    private void addTriangles(Table t, int n, boolean rotationUp, int startTriangle) {
        TextureRegion chosenTriangle = null;
        TextureRegion emoji = null;
        final List<Position> positions = gameBoardController.getBoardPositions();
        for (int i = 0; i < n; i++) {
            Position position = positions.get(i + 1);
            if (position.getPieceCount() > 0) {
                //Todo Velge riktig emoji
                emoji = emojiAtlas.findRegion(GameManager.GetInstance().getEmoji());
                Image chosenImage = new Image(emoji);
                t.add(chosenImage).pad(10).size(40, 40);
<<<<<<< Updated upstream
            }
            if(rotationUp){
                if(i%2 == 0){
=======

                chosenImage.setDrawable(new SpriteDrawable(new Sprite(emoji)));
            }


            if (rotationUp) {
                if (i % 2 == 0) {
>>>>>>> Stashed changes
                    chosenTriangle = triUpWhite;
                } else {
                    chosenTriangle = triUpRed;
                }
            } else {

                if (i % 2 == 0) {
                    chosenTriangle = triDownWhite;
                } else {
                    chosenTriangle = triDownRed;
                }
            }
            Image triangle = new Image(chosenTriangle);

            if (rotationUp) {
                final int triangleNumber = startTriangle + n - i - 1;
                triangle.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        List<Move> movelist = gameBoardController.getMoves(GameManager.GetInstance().getLocalPlayer());
                        if (fieldReference == triangleNumber) {
                            fieldReference = 0;
                            debugLabel.setText(fieldReference);
                        } else if (fieldReference == 0) {
                            fieldReference = triangleNumber;

                            Position pos = positions.get(triangleNumber);

                            for (int i = 0; i < movelist.size(); i++) {
                                if (movelist.get(i).startPosition == pos.getPositionIndex()) {

                                    System.out.print(movelist.get(i).startPosition);
                                    System.out.print(movelist.get(i).endPosition);
                                    System.out.print(movelist.get(i).die.getValue());
                                    System.out.println("");

                                }
                            }
                            debugLabel.setText(fieldReference);
                        } else {
                            for (int i = 0; i < movelist.size(); i++) {
                                if (movelist.get(i).startPosition == fieldReference && movelist.get(i).endPosition == triangleNumber) {
                                    gameBoardController.doMove(movelist.get(i));
                                    System.out.print("noe skjer!");
                                    movelist = gameBoardController.getMoves(GameManager.GetInstance().getLocalPlayer());
                                }
                            }
                            debugLabel.setText(fieldReference + " " + triangleNumber);
                        }
                    }
                });
            } else {
                final int triangleNumber = startTriangle + i;
                triangle.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (fieldReference == triangleNumber) {
                            fieldReference = 0;
                            debugLabel.setText(fieldReference);
                        } else if (fieldReference == 0) {
                            fieldReference = triangleNumber;
                            debugLabel.setText(fieldReference);
                        } else {
                            //Todo combine the direction of the player with the dices thrown so to check if a move is allowed
                            /*
                            Position pos = positions.get(triangleNumber);
                            if(pos.getPieceCount()>0);
                            gameBoardController.getMoves(GameManager.GetInstance().getLocalPlayer());
                            */
                            debugLabel.setText(fieldReference + " " + triangleNumber);
                        }
                    }
                });
            }

            t.add(triangle).pad(10).size(120, 400);

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

    private void OnTriangleClick() {

    }


}