package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class SelectGameRulesMenuScreen {


    final Emojimon game;

    OrthographicCamera camera;

    public SelectGameRulesMenuScreen(final Emojimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

}
