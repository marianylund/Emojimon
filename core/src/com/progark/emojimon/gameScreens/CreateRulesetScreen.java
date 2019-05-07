package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.controller.FBC;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.factories.MoveValidationStrategyFactory.MoveValStrat;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory.MoveSetStrat;
import com.progark.emojimon.model.factories.CanClearStrategyFactory.CanClearStrat;
import com.progark.emojimon.model.factories.StartPiecePlacementStrategyFactory.PiecePlacementStrat;
import com.progark.emojimon.model.fireBaseData.Settings;

public class CreateRulesetScreen implements Screen {

    private Stage stage;
    final Emojimon game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;

    private String diceAmount, startPosition, diceSize;

    public CreateRulesetScreen(final Emojimon game) {
        this.game = game;
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

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Container container = new Container();
        container.setFillParent(true);

        TextButton backButton = new TextButton("Back", skin);
        TextButton createLobbyButton = new TextButton("Create Lobby!", skin);

        Label screenLabel = new Label("Create Ruleset", skin);
        Label moveValidationLabel = new Label("Moves allowed", skin);
        Label moveSetLabel = new Label("Move logic", skin);
        Label canClearLabel = new Label("Bear off", skin);
        Label boardSizeLabel = new Label("Board size", skin);
        Label numOfPiecesLabel = new Label("Number of pieces", skin);
        Label diceAmountLabel = new Label("Amount of dice", skin);
        Label diceSizeLabel = new Label("Dice size", skin);
        Label diceMultiplierLabel = new Label("Dice Multiplier", skin);

        TextField diceAmountField = new TextField("", skin);
        diceAmountField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        TextField diceSizeField = new TextField("", skin);
        diceSizeField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());

        final SelectBox<MoveValStrat> moveValidationStrategiesBox = new SelectBox<MoveValStrat>(skin);
        moveValidationStrategiesBox.setItems(MoveValStrat.BASIC, MoveValStrat.MOVEBACKWARDS);

        final SelectBox<MoveSetStrat> moveSetStrategiesBox = new SelectBox<MoveSetStrat>(skin);
        moveSetStrategiesBox.setItems(MoveSetStrat.BASIC);

        final SelectBox<CanClearStrat> canClearStrategiesBox = new SelectBox<CanClearStrat>(skin);
        canClearStrategiesBox.setItems(CanClearStrat.BASIC);

        final SelectBox<String> boardSizeBox = new SelectBox<String>(skin);
        boardSizeBox.setItems("20","24","30");

        final SelectBox<String> numOfPiecesBox = new SelectBox<String>(skin);
        numOfPiecesBox.setItems("11","15","19");

        final SelectBox<String> diceMultiplierBox = new SelectBox<String>(skin);
        diceMultiplierBox.setItems("1","2","3", "4");

        final SelectBox<String> diceAmountBox = new SelectBox<String>(skin);
        diceAmountBox.setItems("1","2","3","4","5");

        final SelectBox<String> diceSizeBox = new SelectBox<String>(skin);
        diceSizeBox.setItems("4","6","10","20");

        final SelectBox<String> startPositionBox = new SelectBox<String>(skin);
        startPositionBox.setItems("Top right","Top left","Bottom right","Bottom left");

        final SelectBox<String> numberOfRollsBox = new SelectBox<String>(skin);
        numberOfRollsBox.setItems("","Top left","Bottom right","Bottom left");

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new MainMenuScreen(game));
            }
        });

        createLobbyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Initiate GameBoardController
                GameManager.GetInstance().setGameBoardController(new GameBoardController());
                Settings settings = new Settings(
                        Integer.parseInt(boardSizeBox.getSelected()),
                        Integer.parseInt(numOfPiecesBox.getSelected()),
                        Integer.parseInt(diceAmountBox.getSelected()),
                        Integer.parseInt(diceSizeBox.getSelected()),
                        Integer.parseInt(diceMultiplierBox.getSelected()),
                        moveSetStrategiesBox.getSelected(),
                        moveValidationStrategiesBox.getSelected(),
                        canClearStrategiesBox.getSelected(),
                        PiecePlacementStrat.BASIC
                );
                FBC.I().get().addNewGame("TEST", settings);
                game.setScreen(new LobbyScreen(game));
            }
        });

        mainTable.defaults().width(Gdx.graphics.getWidth()/6).space(20);
        mainTable.columnDefaults(2).spaceLeft(Gdx.graphics.getWidth() / 6);
        mainTable.defaults().expandY();

        mainTable.add(backButton);
        mainTable.add(screenLabel);

        mainTable.row();
        mainTable.add(moveValidationLabel);
        mainTable.add(moveValidationStrategiesBox);

        mainTable.add(moveSetLabel);
        mainTable.add(moveSetStrategiesBox);

        mainTable.row();
        mainTable.add(canClearLabel);
        mainTable.add(canClearStrategiesBox);

        mainTable.add(boardSizeLabel);
        mainTable.add(boardSizeBox);

        mainTable.row();
        mainTable.add(numOfPiecesLabel);
        mainTable.add(numOfPiecesBox);

        mainTable.add(diceAmountLabel);
        mainTable.add(diceAmountBox);

        mainTable.row();
        mainTable.add(diceSizeLabel);
        mainTable.add(diceSizeBox);


        mainTable.row();
        mainTable.add(diceMultiplierLabel);
        mainTable.add(diceMultiplierBox);

        mainTable.row();
        mainTable.add(createLobbyButton).colspan(4);

        stage.addActor(mainTable);

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

    }
}
