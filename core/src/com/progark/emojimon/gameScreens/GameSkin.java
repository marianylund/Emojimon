package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameSkin {

    private Skin skin;
    private TextureAtlas atlas;

    public GameSkin(){
        this.atlas = new TextureAtlas(Gdx.files.internal("testSkins/neon/skin/neon-ui.atlas"));
        this.skin = new Skin(Gdx.files.internal("testSkins/neon/skin/neon-ui.json"), atlas);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public Skin getSkin(){
        return skin;
    }
}
