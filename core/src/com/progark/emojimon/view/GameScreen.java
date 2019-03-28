package com.progark.emojimon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.mapTiles.TiledMapStage;

import java.lang.reflect.Array;

public class GameScreen implements Screen {

    final Emojimon game;

    OrthographicCamera camera;


    public GameScreen(final Emojimon game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }
    TiledMap tiledMap = new TiledMap();
    MapLayers layers = tiledMap.getLayers();
    //logic for assigning content too the tilemap based on the information recieved

    Stage stage = new TiledMapStage(tiledMap);

    //Gdx.input.setInputProcessor(stage);

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        stage.act();
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
