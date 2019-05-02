package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;

public class CreateRulesetScreen implements Screen {

    private Stage stage;
    final Emojimon game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;
    private String diceAmount;
    private String startPosition;
    private String diceSize;

    public CreateRulesetScreen(final Emojimon game) {
        this.game = game;
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, game.WIDTH, game.HEIGHT);
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

        TextButton backButton = new TextButton("Back", skin);
        TextButton createLobbyButton = new TextButton("Create Lobby!", skin);

        Label screenLabel = new Label("Create Ruleset", skin);
        Label diceAmountLabel = new Label("Amount of dice", skin);
        Label diceSizeLabel = new Label("Dice size", skin);
        Label startPositionLabel = new Label("Start position", skin);
        Label allowNegativeRoll = new Label("Number of rolls", skin);

        TextField diceAmountField = new TextField("", skin);
        diceAmountField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        TextField diceSizeField = new TextField("", skin);
        diceSizeField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());

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

//        diceAmountField.setTextFieldListener(new TextField.TextFieldListener() {
//            @Override
//            public void keyTyped(TextField textField, char c) { //bug her et sted, appen kræsjer hvis all text slettes
//                int value = Integer.parseInt(textField.getText());
//                diceAmount = value;
//            }
//        });

        createLobbyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LobbyScreen(game));
            }
        });

        startPositionBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startPosition = startPositionBox.getSelected();
                System.out.println(startPosition);
            }
        });

        diceAmountBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diceAmount = diceAmountBox.getSelected();
                System.out.println(diceAmount);
            }
        });

        diceSizeBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                diceSize = diceSizeBox.getSelected();
                System.out.println(diceSize);
            }
        });

        mainTable.add(backButton).pad(20);
        mainTable.add(screenLabel).pad(20);
        // mainTable.add(screenLabel).pad(10).width(Value.percentWidth(.75F, mainTable));
        mainTable.row();
        mainTable.add(diceAmountLabel).pad(Gdx.graphics.getWidth()/24);
        mainTable.add(diceAmountBox).pad(Gdx.graphics.getWidth()/24).width(Value.percentWidth(.25F, mainTable));
        mainTable.row();
        mainTable.add(diceSizeLabel).pad(Gdx.graphics.getWidth()/24);
        mainTable.add(diceSizeBox).pad(Gdx.graphics.getWidth()/24).width(Value.percentWidth(.25F, mainTable));
        mainTable.row();
        mainTable.add(startPositionLabel).pad(Gdx.graphics.getWidth()/24);
        mainTable.add(startPositionBox).pad(Gdx.graphics.getWidth()/24).width(Value.percentWidth(.25F, mainTable));
        mainTable.row();
        mainTable.add(createLobbyButton).pad(20).colspan(2);
        mainTable.row();

        stage.addActor(mainTable);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage);
//        Skin mySkin = new Skin(Gdx.files.internal("skin/uiskin.json"));
//        final TextButton backbtn = new TextButton("Back",mySkin);
//        //button2.setSize(col_width*4,row_height);
//        backbtn.setPosition(0, Gdx.graphics.getHeight()-backbtn.getHeight());
//        backbtn.addListener(new InputListener(){
//            //            @Override
////            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
////                System.out.println("Touch up");
////            }
//            @Override
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                System.out.println("Back");
//                game.setScreen(new MainMenuScreen(game));
//                return true;
//            }
//        });
//        stage.addActor(backbtn);
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
