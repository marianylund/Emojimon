package com.progark.emojimon.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.progark.emojimon.Emojimon;

public class CreateRulesetScreen {


    final Emojimon game;

    OrthographicCamera camera;

    public CreateRulesetScreen(final Emojimon game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

}
