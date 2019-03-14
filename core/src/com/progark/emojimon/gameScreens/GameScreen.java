package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen {

    final Emojimon game;

    OrthographicCamera camera;

    public GameScreen(final Emojimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }
}
