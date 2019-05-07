package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
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

    private Container createGameBoard() {
        // Create background
        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("rect.png")),
                3, 3, 3, 3);
        NinePatchDrawable background = new NinePatchDrawable(patch);

        // Create GameBoardContainer
        Container gameBoardContainer = new Container();
        gameBoardContainer.setBackground(background);
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

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        sideMenu.add(backButton).expand().uniform(); sideMenu.row();//.pad(10);

        // Add Turn emoji
        TextureAtlas.AtlasRegion emojiRegion = emojiAtlas.findRegion(GameManager.GetInstance().getEmoji());
        sideMenu.add(new Image(emojiRegion)).size(100);
        sideMenu.row().pad(10);

        // Add timer label wannabe, is used for debug for now
        debugLabel = new Label("Debug:", skin);
        sideMenu.add(debugLabel); sideMenu.row().pad(10);

        // Add throw dice button

        TextButton diceButton = new TextButton("Throw\nDice", skin);
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


    private Container createSideBoard(){
        Container sideBoardContainer = new Container();
        sideBoardContainer.setSize(sw * 0.1f, sh);
        sideBoardContainer.setPosition(sw * 0.9f, 0);
        sideBoardContainer.fillX(); sideBoardContainer.fillY();

        Table sideBoard = new Table();
        // Add player1's goal
        Table player1goal = new Table();

        Image img = new Image(new Texture(Gdx.files.internal("rect.png")));
        img.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                debugLabel.setText("player1goal");

            }
        });
        player1goal.add(img);
        sideBoard.add(player1goal); sideBoard.row();
        // Add dieded pieces
        Table barField = new Table();

        Image img2 = new Image(new Texture(Gdx.files.internal("rect.png")));
        img2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                debugLabel.setText("barField");

            }
        });
        barField.add(img2);
        sideBoard.add(barField);sideBoard.row();
        // Add player0's goal
        Table player0goal = new Table();

        Image img0 = new Image(new Texture(Gdx.files.internal("rect.png")));
        img0.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                debugLabel.setText("player0goal");
                List<Move> movelist = gameBoardController.getMoves(GameManager.GetInstance().getLocalPlayer());
                if(GameManager.GetInstance().isItLocalPlayerTurn()){

                }

            }
        });
        player0goal.add(img0);
        sideBoard.add(player0goal);

        sideBoardContainer.setActor(sideBoard);
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

    private void addTriangles(Table t, int n, boolean rotationUp, int startTriangle) {
        TextureRegion chosenTriangle = null;
        TextureRegion emoji = null;
        final List<Position> positions = gameBoardController.getBoardPositions();
        for (int i = 0; i < n; i++) {
            Position position = positions.get(i + 1);
/*            if (position.getPieceCount() > 0) {
                //Todo Velge riktig emoji
                emoji = emojiAtlas.findRegion(GameManager.GetInstance().getEmoji());
                Image chosenImage = new Image(emoji);
                t.add(chosenImage).pad(10).size(40, 40);
            }*/
            if (rotationUp) {
                if (i % 2 == 0) {
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

            emoji =  emojiAtlas.findRegion(GameManager.GetInstance().getEmoji());
            Image sEmoji = new Image(emoji);

            Stack stack = new Stack();

            VerticalGroup group = new VerticalGroup();

            group.addActor(sEmoji);
            group.bottom();

            stack.add(triangle);
            stack.add(group);

            t.add(stack).pad(10).size(120,400);

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