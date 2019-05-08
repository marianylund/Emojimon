package com.progark.emojimon.mapTiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Cell extends ClickListener {

    private Image backgroundImage;
    private int positionIndex;
    private Image playeremoji;

    private BitmapFont bitmapFont;
    private int emojiNumber;

    public Cell(Image standardImage, int positionIndex, int emojiumber){
        this.backgroundImage = standardImage;
        this.positionIndex = positionIndex;
        this.emojiNumber = emojiumber;

    }

    public int getPositionIndex(){
        return positionIndex;

    }

    public void setStandardImage(Image image, TextureAtlas.AtlasRegion region){
        backgroundImage = image;
        backgroundImage.setDrawable(new SpriteDrawable(new Sprite(region)));

    }
    public void setPlayeremoji(Image image, TextureAtlas.AtlasRegion region){
        playeremoji = image;
        playeremoji.setDrawable(new SpriteDrawable(new Sprite(region)));
    }

    public void addEmoji(int i){
        emojiNumber += i;
    }

    public int getEmojiNumber(){
        return emojiNumber;
    }

    public void addListener(ClickListener clickListener) {
        System.out.print("noe skjer");
    }
}
