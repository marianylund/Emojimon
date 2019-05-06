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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.mapTiles.Hud;
import com.progark.emojimon.mapTiles.TiledMapStage;

public class GameScreen implements Screen {

    private Hud hud;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera gamecamera;
    private Viewport gameviewport;
    private OrthographicCamera guicamera;
    private Viewport guiviewport;

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
        this.assetManager = assetManager;

        //import skin to be used for GUI elements
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);


        batch = new SpriteBatch();
        hud = new Hud(batch, game);

        guicamera = new OrthographicCamera();
        guiviewport = new FitViewport(Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight()/8, guicamera);
        guiviewport.apply();
        this.buttonstage = new Stage(guiviewport);

    }


    @Override
    public void show() {

        //setting up the gameboard
        stage = new Stage(new ScreenViewport());

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        //camera = new OrthographicCamera();
        gamecamera = new OrthographicCamera();
        gameviewport = new ExtendViewport(Gdx.graphics.getWidth(), (float)(Gdx.graphics.getHeight()/8)*6, gamecamera);
        //gameviewport.setScreenBounds(0,Gdx.graphics.getHeight()/8,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/8*6);
        gamecamera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // må settes dynamisk

        gamecamera.update();

        font = new BitmapFont();

        tiles = new Texture(Gdx.files.internal("blacktri3.png"));
        TextureRegion blacktri = new TextureRegion(new Texture(Gdx.files.internal("blacktri.png")));
        TextureRegion blacktri2 = new TextureRegion(new Texture(Gdx.files.internal("blacktri2.png")));
        TextureRegion blacktri3 = new TextureRegion(tiles);

        //TextureRegion[][] splitTiles = TextureRegion.split(tiles, Gdx.graphics.getWidth()/2, ((Gdx.graphics.getHeight()/8)*6)/(gameBoardController.getBoardSize()/2));
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        for (int i = 0; i < 1; i++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(2, gameBoardController.getBoardSize()/2, Gdx.graphics.getWidth()/2, ((Gdx.graphics.getHeight()/8)*6)/(gameBoardController.getBoardSize()/2));
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < (gameBoardController.getBoardSize()/2); y++) {
                    int ty = (int)(y);
                    int tx = (int)(x);
                    Cell cell = new Cell();
                    if(y == 0){
                        cell.setTile(new StaticTiledMapTile(blacktri));

                    }else if (y== 1){
                        cell.setTile(new StaticTiledMapTile(blacktri2));

                    } else {
                        cell.setTile(new StaticTiledMapTile(blacktri3));

                    }
                    layer.setCell(x, y, cell);

                }
            }
            layers.add(layer);
        }



        renderer = new OrthogonalTiledMapRenderer(map);
        //renderer = new IsometricTiledMapRenderer(map, 1/64f);
        Stage stage = new TiledMapStage(map);
        Gdx.input.setInputProcessor(stage);


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

        //scoring area
        //Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/8);
        buttonstage.getViewport();
        buttonstage.act();
        buttonstage.draw();

        // Prepare for embedded map drawing by applying the desired viewport for the map
        Gdx.gl.glViewport(0, Gdx.graphics.getHeight()/8, Gdx.graphics.getWidth(),  (Gdx.graphics.getHeight()/8)*6);
        stage.getViewport().apply();
        stage.act();
        stage.draw();

        renderer.setView(gamecamera);
        renderer.render();

        //prepare for the drawing of buttons on the far side
        //Gdx.gl.glViewport( 0,(Gdx.graphics.getHeight()*8)*7,Gdx.graphics.getWidth(),Gdx.graphics.getHeight() / 8 );
        //batch.setProjectionMatrix(hud.getStage().getCamera().combined); //set the spriteBatch to draw what our stageViewport sees
        //hud.getStage().act(dt); //act the Hud
        //hud.getStage().draw(); //draw the Hud
/*
        // Prepare for stage drawing by updating the viewport
        buttonstage.getViewport();
        buttonstage.act();
        buttonstage.draw();
*/

        //prepare for the drawing of the scoring area
       // Gdx.gl.glViewport( 0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/8);
    }

    @Override
    public void resize (int width, int height) {

        stage.getViewport().update(width, height, true);
        buttonstage.getViewport().update(width, height, true);

        guicamera.position.set(guicamera.viewportWidth / 2, guicamera.viewportHeight / 2, 0);
        guicamera.update();
        //scorestage.getViewport().update(width,height,true);
        //hud.getStage().getViewport().update(width, height);

    }
}