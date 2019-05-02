package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.mapTiles.Hud;
import com.progark.emojimon.mapTiles.TiledMapStage;

public class GameScreen implements Screen {

    private Hud hud;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;

    private AssetManager assetManager;
    private Texture tiles;
    private Texture texture;
    private TextureAtlas atlas;
    private BitmapFont font;
    private SpriteBatch batch;

    private GameBoardController gameBoardController;
    final Emojimon game;

    Skin skin;
    Stage stage;
    Stage buttonstage;
    Stage scorestage;

    public GameScreen(final Emojimon game) {
        this.game = game;
        this.gameBoardController = new GameBoardController();
        this.gameBoardController.createStandardGameBoard();
        camera = new OrthographicCamera();
        this.assetManager = assetManager;

        //import skin to be used for GUI elements
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);


        batch = new SpriteBatch();
        hud = new Hud(batch);


    }


    @Override
    public void show() {

        //setting up the gameboard
        stage = new Stage(new ScreenViewport());

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //camera = new OrthographicCamera();
        camera.setToOrtho(false, (w/h)*((float)gameBoardController.getBoardSize()/2), (float)gameBoardController.getBoardSize()/2); // må settes dynamisk
        camera.update();

        font = new BitmapFont();
        batch = new SpriteBatch();

        tiles = new Texture(Gdx.files.internal("blacktri2.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 64, 64);
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        for (int i = 0; i < 1; i++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(2, gameBoardController.getBoardSize()/2, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/(gameBoardController.getBoardSize()/2));
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < (gameBoardController.getBoardSize()/2); y++) {
                    int ty = (int)(y);
                    int tx = (int)(x);
                    Cell cell = new Cell();
                    cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
                    layer.setCell(x, y, cell);

                }
            }
            layers.add(layer);
            System.out.println(layer.getWidth() +", "+ layer.getHeight());
        }



        renderer = new OrthogonalTiledMapRenderer(map, 1/64f);
        //renderer = new IsometricTiledMapRenderer(map, 1/64f);
        Stage stage = new TiledMapStage(map);
        Gdx.input.setInputProcessor(stage);
/*
        //Setting up the buttons
        Gdx.input.setInputProcessor(buttonstage);

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
        mainTable.add(gobackbutton).pad(10).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getWidth()/6);
        mainTable.row();
        mainTable.add(doublebutton).pad(10).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getWidth()/6);
        mainTable.row();
        mainTable.add(resignbutton).pad(10).width(Gdx.graphics.getWidth()/2);

        //Add menu to stage as an actor
        buttonstage.addActor(mainTable);
*/
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
        hud.dispose(); //dispose our hud
        batch.dispose(); //dispose the spriteBatch
    }


    @Override
    public void render (float dt) {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //prepare for the drawing of buttons on the far side
        Gdx.gl.glViewport( 0,Gdx.graphics.getHeight()*(7/8),Gdx.graphics.getWidth(),Gdx.graphics.getHeight() / 8 );
/*
        // Prepare for stage drawing by updating the viewport
        buttonstage.getViewport();
        buttonstage.act();
        buttonstage.draw();
*/
        // Prepare for embedded map drawing by applying the desired viewport for the map
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        stage.getViewport();
        stage.act();
        stage.draw();

        camera.update();
        renderer.setView(camera);
        renderer.render();

        //prepare for the drawing of the scoring area
       // Gdx.gl.glViewport( 0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/8);
    }

    @Override
    public void resize (int width, int height) {

        stage.getViewport().update(width, height, true);
       // buttonstage.getViewport().update(width, height, true);
        //scorestage.getViewport().update(width,height,true);
        //hud.getStage().getViewport().update(width, height);

    }
}