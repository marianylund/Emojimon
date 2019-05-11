package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.model.Position;

import java.util.Observable;
import java.util.Observer;

/*
Custom cell class extending Stack (libgdx UI group)
Maintains a currentImage Image and a VerticalGroup of emojis
TODO: way of displaying emojis when position count exceeds emojiNumber
 */
public class Cell extends Stack implements Observer {




    private Image currentImage;
    private TextureRegion standardTexture;
    private TextureRegion highlightedTexture;
    private TextureRegion greenHighlightTexture;
    private TextureRegion localPlayerEmoji;
    private TextureRegion otherPlayerEmoji;
    private Image emojiImage;
    private final int positionIndex;
    private VerticalGroup emojiGroup;

    private int emojiNumber;

    private boolean highlighted;

    private int emojiDrawLimit = 1;

    private Skin skin;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Label.LabelStyle style;

    private Label pieceCountLabel;
    //private int currentPieceCount = -1;
    private boolean rotationUp;

    private Position position;
    private boolean dirtyPositionData;


    public Cell(TextureRegion standardTexture, TextureRegion highlightedTexture, TextureRegion greenHighlightTexture, TextureRegion localPlayerEmoji, TextureRegion otherPlayerEmoji, final int positionIndex, boolean rotationUp){
        this.standardTexture = standardTexture;
        this.highlightedTexture = highlightedTexture;
        this.greenHighlightTexture = greenHighlightTexture;
        this.localPlayerEmoji = localPlayerEmoji;
        this.otherPlayerEmoji = otherPlayerEmoji;
        this.positionIndex = positionIndex;
        this.rotationUp = rotationUp;

            atlas = new GameSkin().getAtlas();
            skin = new GameSkin().getSkin();
//            skin.getFont("font").getData().setScale(3f,3f);
            font = new GameSkin().generateFont(50);
            style = new Label.LabelStyle(font, Color.ORANGE);

            this.pieceCountLabel = new Label("", style);

        //create currentImage image
        this.currentImage = new Image(this.standardTexture);

        //add actors to stack
        this.addActor(this.currentImage);

        //add emoji group
        emojiGroup = new VerticalGroup();
        emojiImage = new Image();
        if(rotationUp){
            emojiGroup.bottom();
            emojiGroup.addActor(pieceCountLabel);
            emojiGroup.addActor(emojiImage);
        }else{
            emojiGroup.addActor(emojiImage);
            emojiGroup.addActor(pieceCountLabel);
        }

        this.add(emojiGroup);

        //add clicklistener
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new CellClickEvent(positionIndex));
            }
        });
    }

    @Override
    public void act(float delta) {
        if(dirtyPositionData){
            updateEmojiGroup(position);
            dirtyPositionData = false;
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public int getPositionIndex(){
        return positionIndex;
    }

    public void updateEmojiGroup(final Position position) {
        if(position != null){
            if (position.getPieceCount() == 0) {
                emojiImage.setVisible(false);
                pieceCountLabel.setVisible(false);
            } else if (position.getPieceCount() == 1) {
                emojiImage.setVisible(true);
                if (position.getOwner() == GameManager.GetInstance().getLocalPlayer()) {
                    emojiImage.setDrawable(new SpriteDrawable(new Sprite(localPlayerEmoji)));
                } else {
                    emojiImage.setDrawable(new SpriteDrawable(new Sprite(otherPlayerEmoji)));
                }
            } else {
                emojiImage.setVisible(true);
                if (position.getOwner() == GameManager.GetInstance().getLocalPlayer()) {
                    emojiImage.setDrawable(new SpriteDrawable(new Sprite(localPlayerEmoji)));
                } else {
                    emojiImage.setDrawable(new SpriteDrawable(new Sprite(otherPlayerEmoji)));
                }
                pieceCountLabel.setVisible(true);
                pieceCountLabel.setText(position.getPieceCount());
            }
        }
    }

    public void addPlayerEmoji(Position position){
        Image image;
        if(position.getPieceCount() > 0) {
            if (position.getOwner() == GameManager.GetInstance().getLocalPlayer()) {
                image = new Image(localPlayerEmoji);
            } else {
                image = new Image(otherPlayerEmoji);
            }
            emojiGroup.addActor(image);
        }
    }

    public void highlight(boolean chosen){
        if(!highlighted){
            if(!chosen){
                currentImage.setDrawable(new SpriteDrawable(new Sprite(this.highlightedTexture)));
            } else {
                currentImage.setDrawable(new SpriteDrawable(new Sprite(greenHighlightTexture)));
            }
            highlighted = true;
        }
    }

    public void removeHighlight(){
        if(highlighted){
            currentImage.setDrawable(new SpriteDrawable(new Sprite(this.standardTexture)));
            highlighted = false;
        }
    }

    public boolean getHighlighted(){
        return highlighted;
    }

    /*
    @Override
    public void update(Observable observable, Object o) {
        this.updateEmojiGroup((Position)o);
    }*/

    @Override
    public void update(Observable observable, Object o) {
        position = (Position)o;
        dirtyPositionData = true;

        //updateEmojiGroup(position);
    }
}