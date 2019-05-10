package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
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
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    private TextureAtlas boardAtlas;
    private TextureAtlas emojiAtlas;

    //Triangle textures
    private TextureRegion triUpWhite;
    private TextureRegion triDownWhite;
    private TextureRegion triUpRed;
    private TextureRegion triDownRed;
    private TextureRegion highUpWhite;
    private TextureRegion highDownWhite;
    private TextureRegion highUpRed;
    private TextureRegion highDownRed;
    private TextureRegion greenHighUpWhite;
    private TextureRegion greenHighDownWhite;
    private TextureRegion greenHighUpRed;
    private TextureRegion greenHighDownRed;
    // Board, goal textures
    private TextureRegion squareBoard;
    private TextureRegion squareBoardHighlighted;
    private TextureRegion squareBoardGreenHighlighted;
    private TextureRegion line;
    //Emoji textures
    private TextureRegion localPlayerEmoji;
    private TextureRegion otherPlayerEmoji;


    private Label debugLabel;

    float sw = Gdx.graphics.getWidth();
    float sh = Gdx.graphics.getHeight();

    private int fieldReference;

    private ArrayList<Cell> boardCells = new ArrayList<Cell>();

    private boolean diceThrown = false; //has dice been thrown?
    private int selectedTriangleIndex = -1; //index of currently selected triangle
    private Label waitingForTurnLabel;

    public GameScreenStandard(final Emojimon game) {
        Gdx.graphics.setContinuousRendering(true);
        this.game = game;
        GameManager.GetInstance().createApp(game);
        this.gameBoardController = GameManager.GetInstance().getGameBoardController();

        // Get UI skin
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        skin.getFont("font").getData().setScale(1.5f,1.5f);

        // Fix Camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport);

        // Add regions used in the game
        emojiAtlas = new TextureAtlas(Gdx.files.internal("Emojis/Output/emojiatlas.atlas"));
        boardAtlas = new TextureAtlas(Gdx.files.internal("Board/Output/board.atlas"));

        //Find triangle regions
        triUpWhite = boardAtlas.findRegion("Triangle-white-up");
        triDownWhite = boardAtlas.findRegion("Triangle-white-down");
        triUpRed = boardAtlas.findRegion("Triangle-red-up");
        triDownRed = boardAtlas.findRegion("Triangle-red-down");

        highUpWhite = boardAtlas.findRegion("Triangle-white-up-highlighted");
        highDownWhite = boardAtlas.findRegion("Triangle-white-down-highlighted");
        highUpRed = boardAtlas.findRegion("Triangle-red-up-highlighted");
        highDownRed = boardAtlas.findRegion("Triangle-red-down-highlighted");

        greenHighUpWhite = boardAtlas.findRegion("Triangle-white-up-chosen");
        greenHighDownWhite = boardAtlas.findRegion("Triangle-white-down-chosen");
        greenHighUpRed = boardAtlas.findRegion("Triangle-red-up-chosen");
        greenHighDownRed = boardAtlas.findRegion("Triangle-red-down-chosen");

        //Find board regions
        squareBoard = boardAtlas.findRegion("board");
        squareBoardHighlighted = boardAtlas.findRegion("board-highlighted");
        squareBoardGreenHighlighted = boardAtlas.findRegion("board-chosen");
        line = boardAtlas.findRegion("line");

        //Find emoji regions
        localPlayerEmoji = emojiAtlas.findRegion(GameManager.GetInstance().getLocalPlayerEmoji());
        otherPlayerEmoji = emojiAtlas.findRegion(GameManager.GetInstance().getOtherPlayerEmoji());

        //initialize boardcells
        int boardSize = gameBoardController.getBoardSize();
        boardCells = new ArrayList<Cell>();
        //should contain cell for all board positions + bar + 2 goals
        while(boardCells.size() < boardSize+3) boardCells.add(null);
    }

    @Override
    public void create() {

    }

    private Container createGameBoard() {
        // Create GameBoardContainer
        Container gameBoardContainer = new Container();
        gameBoardContainer.setSize(sw * 0.8f, sh);
        gameBoardContainer.setPosition(sw * 0.1f, 0);
        gameBoardContainer.fillX();
        gameBoardContainer.fillY();

        // Create background
        NinePatch patch = new NinePatch(squareBoard,
                3, 3, 3, 3);
        NinePatchDrawable background = new NinePatchDrawable(patch);
        gameBoardContainer.setBackground(background);

        // Create board tables
        Table gameBoard = new Table();

        Table out1 = new Table();
        Table home1 = new Table();
        Table out0 = new Table();
        Table home0 = new Table();

        // Create triangles
        int boardSize = gameBoardController.getBoardSize();
        int trianglesPerZone = boardSize / 4;

        addCells(home0, trianglesPerZone, true, 1);
        addCells(out0, trianglesPerZone, true, 1 + trianglesPerZone);
        addCells(out1, trianglesPerZone, false, 1 + trianglesPerZone * 2);
        addCells(home1, trianglesPerZone, false, 1 + trianglesPerZone * 3);

        // Add bar
        Table barField = new Table();

        Cell barCell = new Cell(squareBoard, squareBoardHighlighted, squareBoardGreenHighlighted, localPlayerEmoji, otherPlayerEmoji, 0, gameBoardController.getBoardPositions().get(0), true);
        barField.add(barCell).size(sw * 0.05f, sw * 0.05f);
        boardCells.set(0, barCell);

        Image middle1 = new Image(line);
        Image middle2 = new Image(line);
        float middleBoardHeight = (sh- sw * 0.05f)/2;
        // first row
        gameBoard.add(out1);
        gameBoard.add(middle1).size(sw * 0.05f, middleBoardHeight).expand().center(); //middle of the board
        gameBoard.add(home1);
        gameBoard.row();
        // second row
        gameBoard.add();
        gameBoard.add(barField);
        gameBoard.add();
        gameBoard.row();
        // third row
        gameBoard.add(out0);
        gameBoard.add(middle2).size(sw * 0.05f, middleBoardHeight).expand().center(); //middle of the board
        gameBoard.add(home0);

        ScrollPane sp = new ScrollPane(gameBoard);
        sp.setFillParent(true);

        gameBoardContainer.setActor(sp);

        return gameBoardContainer;
    }

    private Container createSideMenu() {
        // Side Menu contains back button, emojiturn and throw dice button

        Container sideMenuContainer = new Container();
        sideMenuContainer.setSize(sw * 0.1f, sh);
        sideMenuContainer.setPosition(0, 0);
        sideMenuContainer.fillY(); sideMenuContainer.fillX();

        Table sideMenu = new Table();

        // Add leave button
        sideMenu.add(createButton("Back", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.GetInstance().clearGameData();
                game.setScreen(new MainMenuScreen(game));
            }
        })).expand().uniform(); sideMenu.row();


        // Add Turn emoji
        if(GameManager.GetInstance().isItLocalPlayerTurn()){
            TextureAtlas.AtlasRegion emojiRegion = emojiAtlas.findRegion(GameManager.GetInstance().getLocalPlayerEmoji());
            sideMenu.add(new Image(emojiRegion)).size(100);
            sideMenu.row().pad(10);
        }
        else{
            //todo
            TextureAtlas.AtlasRegion emojiRegion = emojiAtlas.findRegion(GameManager.GetInstance().getOtherPlayerEmoji());
            sideMenu.add(new Image(emojiRegion)).size(100);
            sideMenu.row().pad(10);
        }

        // Add timer label wannabe, is used for debug for now
        debugLabel = new Label("Debug:", skin);
        sideMenu.add(debugLabel); sideMenu.row().pad(10);

        // Add throw dice button
        sideMenu.add(createButton("Throw\nDice", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(GameManager.GetInstance().isItLocalPlayerTurn()){
                    gameBoardController.rollDice();
                    diceThrown = true;
                    highlightStartPositions();
                    //gameBoardController.getDieList().get(0);
                    debugLabel.setText(gameBoardController.getDieList().get(0).getValue() + " " + gameBoardController.getDieList().get(1).getValue());
                }
                return;

                // TODO begrenes hvor mange ganger man kaster terning
            }
        })).expand().uniform();

        sideMenu.row();
        // Add throw dice button
        sideMenu.add(createButton("End\nTurn", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameBoardController.endTurn();
            }
        })).expand().uniform();

        sideMenu.row();
        waitingForTurnLabel = new Label("WAITING", skin);
        sideMenu.add(waitingForTurnLabel);

        sideMenuContainer.setActor(sideMenu);
        return sideMenuContainer;
    }


    private Container createPlayerGoals(){
        Container sideBoardContainer = new Container();

        sideBoardContainer.setSize(sw * 0.1f, sh);
        sideBoardContainer.setPosition(sw * 0.9f, 0);
        sideBoardContainer.fillX(); sideBoardContainer.fillY();

        Table sideBoard = new Table();

        // Add player1's goal
        Position player1Goal = gameBoardController.getPlayerGoal(1);
        Cell player1GoalCell = new Cell(squareBoard, squareBoardHighlighted, squareBoardGreenHighlighted, localPlayerEmoji, otherPlayerEmoji, player1Goal.getPositionIndex(), player1Goal, true);
        sideBoard.add(player1GoalCell).size(sw * 0.1f, sh/2).expand().center();
        boardCells.set(player1Goal.getPositionIndex(), player1GoalCell);

        sideBoard.row();

        // Add player0's goal
        Position player0Goal = gameBoardController.getPlayerGoal(0);
        Cell player0GoalCell = new Cell(squareBoard, squareBoardHighlighted, squareBoardGreenHighlighted, localPlayerEmoji, otherPlayerEmoji, player0Goal.getPositionIndex(), player0Goal, true);
        sideBoard.add(player0GoalCell).size(sw * 0.1f, sh/2).expand().center();
        boardCells.set(player0Goal.getPositionIndex(), player0GoalCell);

        sideBoardContainer.setActor(sideBoard);

        return sideBoardContainer;
    }

    private Table createGoal(ClickListener listener){
        Table player0goal = new Table();

        Image player0goalImage = new Image(squareBoard);
        player0goalImage.addListener(listener);
        player0goal.add(player0goalImage).size(sw * 0.1f, sh/2).expand().center();
        return player0goal;
    }

    private TextButton createButton(String buttonText, ClickListener listener){
        TextButton button = new TextButton(buttonText, skin);
        button.addListener(listener);
        return button;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        //stage.setDebugAll(true);
        stage.addActor(createSideMenu());
        stage.addActor(createGameBoard());
        stage.addActor(createPlayerGoals());



        //add listener for cell clicks
        stage.addListener(new CellClickEventListener() {
            @Override
            public void OnClick(CellClickEvent event, int index) {
                OnTriangleClick(index);
            }
        });

    }

    //adds numberOfCells cells to table t
    //created cells are also added to boardCells list
    private void addCells(Table t, int numberOfCells, boolean rotationUp, int startTriangle) {
        TextureRegion chosenTriangle = null;
        TextureRegion chosenHighlightedTriangle = null;
        TextureRegion chosenGreenHighlightImage = null;

        final List<Position> positions = gameBoardController.getBoardPositions();

        //initialize cells with correct textures
        for (int i = 0; i < numberOfCells; i++) {
            //init cell
            Cell boardCell;
            int cellPositionIndex;
            if (rotationUp) {
                cellPositionIndex = startTriangle + numberOfCells - i - 1;
                if (i % 2 == 0) {
                    chosenTriangle = triUpWhite;
                    chosenHighlightedTriangle = highUpWhite;
                    chosenGreenHighlightImage = greenHighUpWhite;
                } else {
                    chosenTriangle = triUpRed;
                    chosenHighlightedTriangle = highUpRed;
                    chosenGreenHighlightImage = greenHighUpRed;
                }
            } else {
                cellPositionIndex = startTriangle + i;
                if (i % 2 == 0) {
                    chosenTriangle = triDownWhite;
                    chosenHighlightedTriangle = highDownWhite;
                    chosenGreenHighlightImage = greenHighDownWhite;
                } else {
                    chosenTriangle = triDownRed;
                    chosenHighlightedTriangle = highDownRed;
                    chosenGreenHighlightImage = greenHighDownRed;
                }
            }

            boardCell = new Cell(chosenTriangle, chosenHighlightedTriangle, chosenGreenHighlightImage, localPlayerEmoji, otherPlayerEmoji, cellPositionIndex, positions.get(cellPositionIndex), rotationUp);

            boardCells.set(cellPositionIndex, boardCell);
            t.add(boardCell).pad(10).size(120,400);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if (GameManager.GetInstance().gameOver) {
            game.setScreen(new GameOverScreen(game, GameManager.GetInstance().getWinningPlayer() ));
        }

        // Check if player WAITING should be displayed.
        if (GameManager.GetInstance().isItLocalPlayerTurn()) {
            waitingForTurnLabel.setVisible(false);
        } else if (!GameManager.GetInstance().isItLocalPlayerTurn()) {
            waitingForTurnLabel.setVisible(true);
        }

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

    //Handle click on triangle at index clickedTriangleIndex
    //will select or deselect
    private void OnTriangleClick(int clickedTriangleIndex) {
        System.out.println(clickedTriangleIndex);

        if(diceThrown){
            List<Move> availableMoves = gameBoardController.getMoves(GameManager.GetInstance().getLocalPlayerIndex());

            //handle click based on whether we have a triangle selected
            if(selectedTriangleIndex == -1){
                //only accept clicks if triangle is highlighted
                if(boardCells.get(clickedTriangleIndex).getHighlighted()){
                    for(Move move : availableMoves){
                        //remove startposition highlights
                        boardCells.get(move.startPosition).removeHighlight();

                        if(move.startPosition == clickedTriangleIndex){
                            //no triangle selected: select this triangle and highlight possible end positions
                            selectedTriangleIndex = clickedTriangleIndex;

                            boardCells.get(clickedTriangleIndex).highlight(true);

                            //highlight any endpositions on board
                            if(move.endPosition >= 1 && move.endPosition < boardCells.size()){
                                //System.out.println("Endposition: " + move.endPosition);
                                //System.out.println("Highlighting: " + boardCells.get(move.endPosition-1).getPositionIndex());
                                boardCells.get(move.endPosition).highlight(false);
                            }
                        }
                    }
                }
            }
            else{
                //check for matching move
                for(Move move : availableMoves){
                    if(move.startPosition == selectedTriangleIndex){
                        if(move.endPosition < 1 || move.endPosition > gameBoardController.getBoardSize()){
                            System.out.println(String.format("Move from %d to %d", move.startPosition, move.endPosition));
                        }
                        //remove all highlights
                        boardCells.get(move.startPosition).removeHighlight();
                        boardCells.get(move.endPosition).removeHighlight();
                        if(move.endPosition == clickedTriangleIndex){
                            //do move
                            gameBoardController.doMove(move, false);
                            UpdatePiecesOnBoardCells();
                            break;
                        }
                    }
                }
                //deselect triangle

                selectedTriangleIndex = -1;

                highlightStartPositions();
            }

        }
    }

    private void highlightStartPositions(){
        List<Move> availableMoves = gameBoardController.getMoves(GameManager.GetInstance().getLocalPlayerIndex());

        HashSet<Integer> highlightedPositions = new HashSet<Integer>();
        for(Move move : availableMoves){
            //avoid highlighting same position multiple times
            if(highlightedPositions.contains(move.startPosition)){
                continue;
            }

            boardCells.get(move.startPosition).highlight(false);
            highlightedPositions.add(move.startPosition);
        }
    }

    private void UpdatePiecesOnBoardCells(){
        for(Cell cell : boardCells){
            cell.updateEmojiGroup();
        }
    }




}