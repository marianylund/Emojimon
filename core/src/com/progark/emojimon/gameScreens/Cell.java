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
Maintains a triangle Image and a VerticalGroup of emojis

TODO: add texture for highlighting cell as currently selected
TODO: way of displaying emojis when position count exceeds emojiNumber
 */
public class Cell extends Stack {

    private Image triangle;
    private TextureRegion standardTriangle;
    private TextureRegion highlightedTriangle;
    private TextureRegion localPlayerEmoji;
    private TextureRegion otherPlayerEmoji;
    private final int positionIndex;
    private VerticalGroup emojiGroup;
    private final Position position;

    private BitmapFont bitmapFont;
    private int emojiNumber;

    private boolean highlighted;

    private int emojiDrawLimit = 3;

    public Cell(TextureRegion standardTriangle, TextureRegion highlightedTriangle, TextureRegion localPlayerEmoji, TextureRegion otherPlayerEmoji, final int positionIndex, Position position, boolean rotationUp){
        this.standardTriangle = standardTriangle;
        this.highlightedTriangle = highlightedTriangle;
        this.localPlayerEmoji = localPlayerEmoji;
        this.otherPlayerEmoji = otherPlayerEmoji;
        this.positionIndex = positionIndex;
        this.position = position;

        //create triangle image
        this.triangle = new Image(standardTriangle);

        //add actors to stack
        this.addActor(this.triangle);

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

    public void highlight(){
        if(!highlighted){
            triangle.setDrawable(new SpriteDrawable(new Sprite(highlightedTriangle)));
            highlighted = true;
        }
    }

    public void removeHighlight(){
        if(highlighted){
            triangle.setDrawable(new SpriteDrawable(new Sprite(standardTriangle)));
            highlighted = false;
        }
    }

    public void setActive(boolean active){
        //TODO: change to "active" texture to better show the active cell

        /*if(active){
            triangle.setColor(selectedTriangleColor);
        }
        else{
            triangle.setColor(standardTriangleColor);
            System.out.println(standardTriangleColor.r + " " + standardTriangleColor.g + " " + standardTriangleColor.b);
        }*/
    }

    public boolean getHighlighted(){
        return highlighted;
    }

}
