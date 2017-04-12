package com.pennypop.project;

import com.badlogic.gdx.Game;

/**
 * Connect4Game
 * 
 * @author Fengling Wang
 */
public class Connect4Game extends Game {
      public Connect4Game() {
    	  
	  }
	
	  public void create () {
		  this.setScreen(new MainScreen(this));
	  }
	 
	  public void render () {
	    super.render();
	  }
	 
	  public void dispose () {
	  }
}