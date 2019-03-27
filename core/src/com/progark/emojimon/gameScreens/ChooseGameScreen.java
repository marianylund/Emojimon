package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.progark.emojimon.Emojimon;

public class ChooseGameScreen {

    final Emojimon game;

    OrthographicCamera camera;

    public ChooseGameScreen(final Emojimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }
}
