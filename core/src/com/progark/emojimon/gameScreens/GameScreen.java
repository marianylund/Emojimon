package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.mapTiles.TiledMapStage;

public class GameScreen implements Screen {

    private TiledMap map;
    private TiledMapRenderer renderer;
    private OrthographicCamera camera;

    private AssetManager assetManager;
    private Texture tiles;
    private Texture texture;
    private BitmapFont font;
    private SpriteBatch batch;

    private GameBoardController gameBoardController;
    final Emojimon game;

    Skin skin;
    Stage stage;

    public GameScreen(final Emojimon game) {
        this.game = game;
        this.gameBoardController = new GameBoardController();
        this.gameBoardController.createStandardGameBoard();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        this.assetManager = assetManager;

    }


    @Override
    public void show() {
        //skin = new Skin(Gdx.files.internal("blacktri.png"));
        stage = new Stage(new ScreenViewport());

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / (h - 40)) * 10, 10);
        camera.update();

        font = new BitmapFont();
        batch = new SpriteBatch();

        {
            tiles = new Texture(Gdx.files.internal("blacktri2.png"));
            TextureRegion[][] splitTiles = TextureRegion.split(tiles, 64, 64);
            map = new TiledMap();
            MapLayers layers = map.getLayers();
            for (int i = 0; i < 1; i++) {
                TiledMapTileLayer layer = new TiledMapTileLayer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/8);
                for (int x = 0; x < 2; x++) {
                    for (int y = 0; y < 8; y++) {
                        int ty = (int)(y);
                        int tx = (int)(x);
                        Cell cell = new Cell();
                        cell.setTile(new StaticTiledMapTile(splitTiles[0][0]));
                        layer.setCell(x, y, cell);

                    }
                }
                layers.add(layer);
            }
        }

        renderer = new OrthogonalTiledMapRenderer(map, 1/32f);
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

    }


    @Override
    public void render (float dt) {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Prepare for stage drawing by updating the viewport
        stage.getViewport();
        stage.act();
        stage.draw();

        // Prepare for embedded map drawing by applying the desired viewport for the map
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        camera.update();
        renderer.setView(camera);
        renderer.render();

    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}