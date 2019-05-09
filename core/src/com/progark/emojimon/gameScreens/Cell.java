package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.model.Position;

/*
Custom cell class extending Stack (libgdx UI group)
Maintains a backgroundImage Image and a VerticalGroup of emojis

TODO: add texture for highlighting cell as currently selected
TODO: way of displaying emojis when position count exceeds emojiNumber
 */
public class Cell extends Stack {

    private Image backgroundImage;
    private TextureRegion standardTexture;
    private TextureRegion highlightedTexture;
    private TextureRegion localPlayerEmoji;
    private TextureRegion otherPlayerEmoji;
    private final int positionIndex;
    private VerticalGroup emojiGroup;
    private final Position position;

    private BitmapFont bitmapFont;
    private int emojiNumber;

    private boolean highlighted;

    private int emojiDrawLimit = 1;

    public Cell(TextureRegion standardTexture, TextureRegion highlightedTexture, TextureRegion localPlayerEmoji, TextureRegion otherPlayerEmoji, final int positionIndex, Position position, boolean rotationUp){
        this.standardTexture = standardTexture;
        this.highlightedTexture = highlightedTexture;
        this.localPlayerEmoji = localPlayerEmoji;
        this.otherPlayerEmoji = otherPlayerEmoji;
        this.positionIndex = positionIndex;
        this.position = position;

        //create backgroundImage image
        this.backgroundImage = new Image(standardTexture);

        //add actors to stack
        this.addActor(this.backgroundImage);

        //add emoji group
        emojiGroup = new VerticalGroup();
        if(rotationUp){
            emojiGroup.bottom();
        }
        this.add(emojiGroup);
        updateEmojiGroup();

        //add clicklistener
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fire(new CellClickEvent(positionIndex));
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public int getPositionIndex(){
        return positionIndex;
    }

    //update emojigroup based on position data
    public void updateEmojiGroup(){
        if(emojiGroup.getChildren().size != position.getPieceCount()){
            emojiGroup.clearChildren();
            for(int i = 0; i < position.getPieceCount(); i++){
                if(i >= emojiDrawLimit) break;
                Image image;

                //use local or other player's emoji?
                if(position.getOwner() == GameManager.GetInstance().getLocalPlayer()){
                    image = new Image(localPlayerEmoji);
                }
                else{
                    image = new Image(otherPlayerEmoji);
                }
                //image.setScale(0.7f);
                emojiGroup.addActor(image);
            }
        }
    }

    public void highlight(){
        if(!highlighted){
            backgroundImage.setDrawable(new SpriteDrawable(new Sprite(highlightedTexture)));
            highlighted = true;
        }
    }

    public void removeHighlight(){
        if(highlighted){
            backgroundImage.setDrawable(new SpriteDrawable(new Sprite(standardTexture)));
            highlighted = false;
        }
    }

    public void setActive(boolean active){
        //TODO: change to "active" texture to better show the active cell

        /*if(active){
            backgroundImage.setColor(selectedTriangleColor);
        }
        else{
            backgroundImage.setColor(standardTriangleColor);
            System.out.println(standardTriangleColor.r + " " + standardTriangleColor.g + " " + standardTriangleColor.b);
        }*/
    }

    public boolean getHighlighted(){
        return highlighted;
    }

}
