package com.pennypop.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Game Screen
 * 
 * @author Fengling Wang
 */
public class GameScreen implements Screen {
	 
    private final Stage stage;
    private final SpriteBatch spriteBatch;
    final ProjectApplication game;
    private final Screen parent;
    private boolean gameFinish = false;
    
    private Texture backTexture;
    private TextureRegion backTextureRegion;
    private TextureRegionDrawable backTexRegionDrawable;
    private ImageButton backButton;
    
    private BitmapFont font;
 
    public GameScreen(ProjectApplication game, Screen parent, SpriteBatch spriteBatch) {
        this.game = game;
        this.parent = parent;
        this.spriteBatch = spriteBatch;
        stage = new Stage();
    }
 
    @Override
    public void render(float delta) {
        game.clearWhite();
        stage.act();
        stage.draw();
        
        spriteBatch.begin();
        font.draw(spriteBatch, "asdfasdfa", 500, 500);
        spriteBatch.end();
        
        if (gameFinish) {
            game.setScreen(parent);
        }
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
		// font 
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), false);
		font.setColor(Color.RED);
		
        // back button
        backTexture = new Texture(Gdx.files.internal("assets/sfxButton.png"));
        backTextureRegion = new TextureRegion(backTexture);
        backTexRegionDrawable = new TextureRegionDrawable(backTextureRegion);
        
        backButton = new ImageButton(backTexRegionDrawable); //Set the button up
        backButton.setPosition(400, 350);
		
		stage.addActor(backButton);
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
    	stage.dispose();
    }
}