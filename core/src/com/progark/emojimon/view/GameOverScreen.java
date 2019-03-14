package com.progark.emojimon.view;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameOverScreen {

    final Emojimon game;

    OrthographicCamera camera;

    public GameOverScreen(final Emojimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }
}
