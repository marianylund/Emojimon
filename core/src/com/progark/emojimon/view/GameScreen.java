package com.progark.emojimon.view;

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
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.mapTiles.TiledMapStage;
import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.interfaces.Die;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    final Emojimon game;

    OrthographicCamera camera;

    //gamesettings
    private GameBoardController gameBoardController;
    private TiledMap tiledMap;
    private Stage stage;
    private Texture tiles;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;

    public GameScreen(final Emojimon game, GameBoardController gameBoardController) {
        this.game = game;
        this.gameBoardController = gameBoardController;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {
        //creating the visual gameboard
        TiledMap tiledMap = new TiledMap();
        MapLayers layers = tiledMap.getLayers();
        int boardSize = gameBoardController.getBoardSize();

        TiledMapTileLayer triangleLayer = new TiledMapTileLayer(2, boardSize / 2, Gdx.graphics.getWidth() / 2, ((Gdx.graphics.getHeight() / 4) * 3) / (boardSize / 2));
        tiles = new Texture(Gdx.files.internal("blacktri.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/4) * 3/(boardSize / 2));
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < boardSize / 2; y++) {
                TiledMapTileLayer.Cell triangleCell = new TiledMapTileLayer.Cell();
                triangleCell.setTile(new StaticTiledMapTile(splitTiles[x][y]));
                triangleLayer.setCell(x, y, triangleCell);

            }
        }
        layers.add(triangleLayer);
        Stage stage = new TiledMapStage(tiledMap);
        Gdx.input.setInputProcessor(stage);

        //listeneres for actions


        renderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        camera.update();
        renderer.setView(camera);
        renderer.render();
        stage.act();
        stage.draw();
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
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
