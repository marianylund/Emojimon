package com.progark.emojimon.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.progark.emojimon.Emojimon;

public class ChooseLobbyScreen {

    final Emojimon game;

    OrthographicCamera camera;

    public ChooseLobbyScreen(final Emojimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }
}
