package com.amw.GridSnake;

import com.amw.GridSnake.GameScreen.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScoreDialog extends Table {

	Grid grid;
	private Window scoreDialog;
	String defeatText;
	Color gray = new Color(74/255f, 78/255f, 79/255f, 1);
	Color green = new Color(182/255f, 222/255f, 159/255f, 1);
	
	public ScoreDialog(Grid g, String deathType)
	{
		this.grid = g;
		determineText(deathType);
		
		
		// SET DIALOG
		WindowStyle dialogStyle = new WindowStyle();
		dialogStyle.titleFont = new BitmapFont(Gdx.files.internal("fonts/munro.fnt"),
        		Gdx.files.internal("fonts/munro.png"),false);
		Texture dialogTexture =  new Texture(Gdx.files.internal("scoreDialogSkin/ScoreDialog.png"));
		dialogTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion tr = new TextureRegion(dialogTexture);
		dialogStyle.titleFont = new BitmapFont();

		scoreDialog = new Window("", dialogStyle);
		scoreDialog.background(new TextureRegionDrawable(tr));
		
		
		// DIALOG TEXT
		BitmapFont munroFont = new BitmapFont(Gdx.files.internal("fonts/munro.fnt"),
        		Gdx.files.internal("fonts/munro.png"),false);
        
		LabelStyle labelStyle = new LabelStyle(munroFont, green);
		labelStyle.font.setScale(0.3f);
		scoreDialog.add(new Label(defeatText,labelStyle)).expand();
		scoreDialog.row();
		
		
		// time bonus
		float timer = grid.timer;
		int minutes = (int)timer/60;
		int seconds = (int)timer%60;
		
		int minutesMultiplier = ((int)timer/30)+1;
		
		String secondsString = String.format("%02d", seconds);
		
		String timeText = Integer.toString(minutes)+":"+secondsString;
		scoreDialog.add(new Label("Time Bonus: " + timeText + " - " + minutesMultiplier + "x",labelStyle)).expand();
		scoreDialog.row();
		
		
		// length bonus
		int lengthMultiplier = ((grid.bestLength-3)/5+1);
		scoreDialog.add(new Label("Length Bonus: " + grid.bestLength + " - " + lengthMultiplier + "x",labelStyle)).expand();
		scoreDialog.row();
		
		
		
		
		
		// calculate and recordscores
		int score = grid.points*minutesMultiplier*lengthMultiplier;
		String scoreString = Integer.toString(score);
		
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		int currentHighScore = prefs.getInteger("highscore", 0);
		
		if(score>currentHighScore)
		{
			prefs.putInteger("highscore", score);
			currentHighScore = score;
		}
		
		prefs.flush();
		
		scoreDialog.add(new Label("Your Score: " + scoreString,labelStyle)).expand();
		scoreDialog.row();
		scoreDialog.add(new Label("High Score: " + Integer.toString(currentHighScore),labelStyle)).expand();
		
		
		// SET BUTTONS
		Texture upTexture =  new Texture(Gdx.files.internal("textures/smallButtonTexture.png"));
        Texture downTexture =  new Texture(Gdx.files.internal("textures/smallButtonTexture_down.png"));
        upTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        downTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        TextureRegion upRegion = new TextureRegion(upTexture);
        TextureRegion downRegion = new TextureRegion(downTexture);
        BitmapFont buttonFont = new BitmapFont(Gdx.files.internal("fonts/digiffiti.fnt"),
        		Gdx.files.internal("fonts/digiffiti.png"),false);
        
		TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;
        style.fontColor = gray;
		
        TextButton playButton = new TextButton("PLAY",style);
        TextButton quitButton = new TextButton("QUIT",style);
		
        
        playButton.addListener( new ClickListener()
        {
        	public void clicked(InputEvent event,float x,float y)
        	{
        		ScoreDialog target = (ScoreDialog)event.getTarget().getParent().getParent();
        		target.setVisible(false);
        		grid.resetBoard();
        	}
        });
        
        quitButton.addListener( new ClickListener()
        {
        	public void clicked(InputEvent event,float x,float y)
        	{
        		grid.exitGame();
        	}
        });
        
		add(scoreDialog).width(300).height(205).colspan(2);
		row();
		add(playButton).pad(5);
        add(quitButton).pad(5);
		
	}
	
	private void determineText(String deathType)
	{
		if(deathType.equals("self"))
			defeatText="You hit yourself!";
        else if(deathType.equals("obstacle"))
            defeatText="You hit an obstacle!";
		else if(deathType.equals("letitgo"))
			defeatText="You let go of the snake!";
		else if(deathType.equals("timer"))
			defeatText="You didn't move!";
		else if(deathType.equals("full"))
			defeatText="The board is full!";
		else
			defeatText="You died!";
	}
	
}
