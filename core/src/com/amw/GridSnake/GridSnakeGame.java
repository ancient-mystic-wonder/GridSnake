package com.amw.GridSnake;

import com.badlogic.gdx.Game;

public class GridSnakeGame extends Game {

	MainMenuScreen mainMenuScreen;
	GameScreen gameScreen;
	InstructionsScreen instructionsScreen;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		 mainMenuScreen = new MainMenuScreen(this);
		 gameScreen = new GameScreen(this);
		 instructionsScreen = new InstructionsScreen(this);
         setScreen(mainMenuScreen);  
	}
	
	public void startGame()
	{
		setScreen(gameScreen);  
	}
	
	public void showInstructions()
	{
		setScreen(instructionsScreen);
	}
	
	public void mainMenu()
	{
		setScreen(mainMenuScreen);
	}

}
