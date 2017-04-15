package com.pennypop.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;

/**
 * This is where you screen code will go, any UI should be in here
 * 
 * @author Richard Taylor
 */
public class MainScreen implements Screen {
	
	public final Stage stage;
	public final SpriteBatch spriteBatch;
	// fonts
    private BitmapFont font;
    // buttons
    private Texture sfxTexture;
    private TextureRegion sfxTextureRegion;
    private TextureRegionDrawable sfxTexRegionDrawable;
    private ImageButton sfxButton;
    private Texture apiTexture;
    private TextureRegion apiTextureRegion;
    private TextureRegionDrawable apiTexRegionDrawable;
    private ImageButton apiButton;
    private Texture gameTexture;
    private TextureRegion gameTextureRegion;
    private TextureRegionDrawable gameTexRegionDrawable;
    private ImageButton gameButton;
    // weather api info
    private boolean displayApi = false;
    private String weatherTitle = "Current Weather";
    private String weatherCity;
    private String weatherDescription;
    private String weatherData;
    // game
    private final ProjectApplication game;
    private boolean gameOn = false;
	
	public MainScreen(ProjectApplication game) {
		this.game = game;
		spriteBatch = new SpriteBatch();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, spriteBatch);
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		stage.dispose();
		font.dispose();
	}

	@Override
	public void render(float delta) {
		game.clearWhite();
		
		stage.act(delta);
		stage.draw();
		
		// Title font
        spriteBatch.begin();
        font.draw(spriteBatch, "PennyPop", 500, 500);
        spriteBatch.end();
        
        // Weather API fonts
        if (displayApi) {
            spriteBatch.begin();
            font.setColor(Color.CYAN);
            font.draw(spriteBatch, weatherTitle, 700, 500);
            font.setColor(Color.BLUE);
            font.draw(spriteBatch, weatherCity, 700, 450);
            font.setColor(Color.RED);
            font.draw(spriteBatch, weatherDescription, 700, 400);
            font.draw(spriteBatch, weatherData, 700, 350);
            spriteBatch.end();
        }
        
        // game
        if (gameOn) {
        	game.setScreen(new GameScreen(game, this, spriteBatch));
        	// dispose();
        }
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		// font 
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), false);
		font.setColor(Color.RED);
		
        // SFX button
        sfxTexture = new Texture(Gdx.files.internal("assets/sfxButton.png"));
        sfxTextureRegion = new TextureRegion(sfxTexture);
        sfxTexRegionDrawable = new TextureRegionDrawable(sfxTextureRegion);
        
        sfxButton = new ImageButton(sfxTexRegionDrawable); //Set the button up
        sfxButton.setPosition(400, 350);
        sfxButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // button sound
                Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("assets/button_click.wav"));
            	buttonSound.play();
                return true;
            }
        });
        
        // API button
        apiTexture = new Texture(Gdx.files.internal("assets/apiButton.png"));
        apiTextureRegion = new TextureRegion(apiTexture);
        apiTexRegionDrawable = new TextureRegionDrawable(apiTextureRegion);
        
        apiButton = new ImageButton(apiTexRegionDrawable); //Set the button up
        apiButton.setPosition(500, 350);
        apiButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // button sound
                Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("assets/button_click.wav"));
            	buttonSound.play();
            	
            	// show weather
                String sURL = "http://api.openweathermap.org/data/2.5/weather?q=San%20Francisco,US&appid=2e32d2b4b825464ec8c677a49531e9ae"; //just a string
                
				try {
					// Connect to the URL using java's native library
					URL url;
					url = new URL(sURL);
	                HttpURLConnection request = (HttpURLConnection) url.openConnection();
	                request.connect();
	                

	                // Convert to a JSON object to print data
	                JsonParser jp = new JsonParser(); //from gson
	                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	                JsonObject rootobj = root.getAsJsonObject(); 
	                weatherCity = rootobj.get("name").getAsString(); 
	                JsonArray weatherObj = rootobj.getAsJsonArray("weather");
	                weatherDescription = weatherObj.get(0).getAsJsonObject().get("description").getAsString();
	                weatherData = 
	                		rootobj.get("main").getAsJsonObject().get("temp").getAsString()
	                				+ " degrees, " 
	                				+ rootobj.get("wind").getAsJsonObject().get("speed").getAsString()
	                				+ "mph wind";
	                
	                displayApi = true;
	        		
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
                return true;
            }
        });
        
        // Game button
        gameTexture = new Texture(Gdx.files.internal("assets/gameButton.png"));
        gameTextureRegion = new TextureRegion(gameTexture);
        gameTexRegionDrawable = new TextureRegionDrawable(gameTextureRegion);
        
        gameButton = new ImageButton(gameTexRegionDrawable); //Set the button up
        gameButton.setPosition(600, 350);
        gameButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                // button sound
                Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("assets/button_click.wav"));
            	buttonSound.play();
            	
            	gameOn = true;
            	
                return true;
            }
        });
        
        
        stage.addActor(sfxButton); 
        stage.addActor(apiButton); 
        stage.addActor(gameButton); 
	}

	@Override
	public void pause() {
		// Irrelevant on desktop, ignore this
	}

	@Override
	public void resume() {
		// Irrelevant on desktop, ignore this
	}

}
