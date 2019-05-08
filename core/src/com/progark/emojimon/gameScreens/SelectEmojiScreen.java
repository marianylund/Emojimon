package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.progark.emojimon.Emojimon;
import com.progark.emojimon.GameManager;

public class SelectEmojiScreen extends ApplicationAdapter implements Screen {

    private Stage stage;
    final Emojimon game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch; // ubrukt, må finne ut av textureatlas
    private Image chosenEmoji;

    float sw = Gdx.graphics.getWidth();
    float sh = Gdx.graphics.getHeight();



    public SelectEmojiScreen(final Emojimon game) {
        Gdx.graphics.setContinuousRendering(true);
        this.game = game;

        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), atlas);


        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        stage = new Stage(viewport);

        atlas = new TextureAtlas(Gdx.files.internal("Emojis/Output/emojiatlas.atlas"));

        TextureAtlas.AtlasRegion emojiRegion = atlas.findRegion(GameManager.GetInstance().getLocalPlayerEmoji());
        if(emojiRegion == null){
            throw new IllegalArgumentException("There is no emoji with the name: " + GameManager.GetInstance().getLocalPlayerEmoji());
        } else {
            chosenEmoji = new Image(emojiRegion);
        }
    }

    @Override
    public void create(){

    }

    private void changeChosenEmoji(){
        TextureAtlas.AtlasRegion emojiRegion = atlas.findRegion(GameManager.GetInstance().getLocalPlayerEmoji());
        if(emojiRegion == null){
            throw new IllegalArgumentException("There is no emoji with the name: " + GameManager.GetInstance().getLocalPlayerEmoji());
        } else {

            chosenEmoji.setDrawable(new SpriteDrawable(new Sprite(emojiRegion)));

        }
    }

    private Container createChosenEmojiContainer(){
        Container container = new Container();
        // Chosen emoji
        Table chosenEmojiTable = new Table();
        Label chosenEmojiLabel = new Label("Chosen emoji: ", skin);

        chosenEmojiTable.add(chosenEmojiLabel).pad(20).center();
        chosenEmojiTable.add(chosenEmoji).size(100).center();

        container.setActor(chosenEmojiTable);

        float cw = sw * 0.8f;
        float ch = sh * 0.2f;

        container.setSize(cw, ch);
        container.setPosition((sw - cw) / 2.0f, (sh - ch));
        container.fillX();

        return container;
    }
    private Container createEmojiChoicesContainer(){
        Table imagesTable = new Table();

        addAllEmojis(imagesTable);

        Container container = new Container();

        float cw = sw * 0.8f;
        float ch = sh * 0.5f;

        container.setSize(cw, ch);
        container.setPosition((sw - cw) / 2.0f, (sh - ch) / 2f);
        container.fillX();

        ScrollPane sp = new ScrollPane(imagesTable);
        sp.setFillParent(true);

        container.setActor(sp);

        return container;
    }
    private Container createButtonContainer(){
        // Add Back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.setTransform(true);
        backButton.setScale(3f);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        //Add button container
        Container buttonContainer = new Container();

        float cw = sw * 0.8f;
        float ch = sh * 0.5f;
        buttonContainer.center();
        buttonContainer.setPosition(sw * 0.5f - backButton.getWidth(), ch * 0.66f);
        buttonContainer.fillX();

        buttonContainer.setActor(backButton);

        return buttonContainer;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.addActor(createChosenEmojiContainer());
        stage.addActor(createEmojiChoicesContainer());
        stage.addActor(createButtonContainer());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
//        batch.begin();
//        batch.draw(spritesheet, 0, 0);
//        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void addEmoji(Table table, final String emojiName){
        TextureAtlas.AtlasRegion emojiRegion = atlas.findRegion(emojiName);
        if(emojiRegion == null){
            throw new IllegalArgumentException("There is no emoji with the name: " + emojiName);
        } else {

            Image emojiImage = new Image(emojiRegion);
            table.add(emojiImage).size(200);
            // Add listeners for each emoji
            emojiImage.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameManager.GetInstance().setLocalPlayerEmoji(emojiName);
                    changeChosenEmoji();
                }
            });
            table.add().padLeft(20);
        }
    }

    private void addAllEmojis(Table imagesTable){
        addEmoji(imagesTable,"face-with-tears-of-joy_1f602");
        addEmoji(imagesTable,"smiling-face-with-halo_1f607");
        addEmoji(imagesTable,"grinning-face-with-star-eyes_1f929");
        addEmoji(imagesTable,"brain_1f9e0");
        addEmoji(imagesTable,"eyes_1f440");
        addEmoji(imagesTable,"pile-of-poo_1f4a9");
        addEmoji(imagesTable,"smiling-face-with-horns_1f608");
        addEmoji(imagesTable,"extraterrestrial-alien_1f47d");
        addEmoji(imagesTable,"collision-symbol_1f4a5");
        addEmoji(imagesTable,"hundred-points-symbol_1f4af");
        addEmoji(imagesTable,"wastebasket_1f5d1");
        addEmoji(imagesTable,"shocked-face-with-exploding-head_1f92f");
        addEmoji(imagesTable,"smiling-face-with-sunglasses_1f60e");
        addEmoji(imagesTable,"face-screaming-in-fear_1f631");
        addEmoji(imagesTable,"aubergine_1f346");
        addEmoji(imagesTable,"winking-face_1f609");
    }
}