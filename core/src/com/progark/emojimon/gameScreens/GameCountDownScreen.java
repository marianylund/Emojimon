package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCountDownScreen {


    final Emojimon game;

    OrthographicCamera camera;

    public GameCountDownScreen(final Emojimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }
}
