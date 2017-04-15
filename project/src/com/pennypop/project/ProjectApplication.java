package com.pennypop.project;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The {@link ApplicationListener} for this project, create(), resize() and
 * render() are the only methods that are relevant
 * 
 * @author Richard Taylor
 * */
public class ProjectApplication extends Game implements ApplicationListener {
	public static boolean gameOn = false;
	public static Screen mainScreen;

	public static void main(String[] args) {
		new LwjglApplication(new ProjectApplication(), "PennyPop", 1280, 720,
				true);
	}

	@Override
	public void create() {
		mainScreen = new MainScreen(this);
		setScreen(mainScreen);
	}

	@Override
	public void dispose() {
		//mainScreen.hide();
		//mainScreen.dispose();
	}

	@Override
	public void pause() {
		//mainScreen.pause();
	}

	@Override
	public void render() {
		super.render();
		//clearWhite();
		//mainScreen.render(Gdx.graphics.getDeltaTime());
	}

	/** Clears the screen with a white color */
	public void clearWhite() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		//mainScreen.resize(width, height);
	}

	@Override
	public void resume() {
		//mainScreen.resume();
	}
}
