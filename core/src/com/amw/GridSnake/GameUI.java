package com.amw.GridSnake;

import com.amw.GridSnake.GameScreen.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameUI extends Table {

	
	private float BUTTON_WIDTH = 300f;
    private final float BUTTON_HEIGHT = 60f;
    private final float BUTTON_SPACING = 10f;
	private Grid grid;
	Label tapScreenLabel;
	Label multiplierLabel;
	TextButton tapScreenButton;
	Label scoreLabel;
	Label timerLabel;
	int timer=0;
	
	Color gray = new Color(74/255f, 78/255f, 79/255f, 1);
	Color green = new Color(182/255f, 222/255f, 159/255f, 1);
	
	public GameUI(Grid grid)
	{
		this.grid = grid;
        //Table table = new Table();
        setFillParent(true);        
        
        BitmapFont buttonFont = new BitmapFont(Gdx.files.internal("fonts/digiffiti.fnt"),
        		Gdx.files.internal("fonts/digiffiti.png"),false);

        BitmapFont visitorFont = new BitmapFont(Gdx.files.internal("fonts/munro.fnt"),
        		Gdx.files.internal("fonts/munro.png"),false);
        
		LabelStyle labelStyle = new LabelStyle(buttonFont, gray);
		LabelStyle visitorStyle = new LabelStyle(visitorFont,gray);
		

		int s = grid.points;
        
		
		Texture upTexture =  new Texture(Gdx.files.internal("textures/playButtonTexture_down.png"));
		upTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion upRegion = new TextureRegion(upTexture);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.font = visitorFont;
		buttonStyle.fontColor = green;
		buttonStyle.up = new TextureRegionDrawable(upRegion);
		buttonStyle.font.setScale(0.4f);
		tapScreenButton = new TextButton("DRAG THE SNAKE HEAD", buttonStyle);
		tapScreenButton.padTop(10).padBottom(10);

		
		multiplierLabel = new Label("x3",visitorStyle);
		scoreLabel = new Label("SCORE: 0",visitorStyle);
		timerLabel = new Label("0:00",visitorStyle);
        
        visitorStyle.font.setScale(0.5f, 0.5f);
        
        this.top().left();
        
        add(multiplierLabel).left().padLeft(2);
        row();
        add(scoreLabel).left().padLeft(2);
        row();
        add(timerLabel).left().padLeft(2);
        row();
        //add(tapScreenLabel).expand();
        add(tapScreenButton).expand().width(300).height(38);
        row();
	}
	
	
	public void start()
	{
		tapScreenButton.setVisible(false);
	}
	
	public void stop()
	{
		tapScreenButton.setVisible(true);
		scoreLabel.setText("SCORE: 0");
		multiplierLabel.setText("x3");
		timerLabel.setText("0:00");
		timer=0;
	}
	
	public void updateScore(int score)
	{
		String newScore = Integer.toString(score);
		scoreLabel.setText("SCORE: " + newScore);
	}
	
	public void updateMultiplier(int multiplier)
	{
		String newMultiplier = Integer.toString(multiplier);
		multiplierLabel.setText("x" + newMultiplier);
	}
	
	public void updateTimer(float newTime)
	{
		int minutes = (int)newTime/60;
		int seconds = (int)newTime%60;
		
		String secondsString = String.format("%02d", seconds);
		
		String timeText = Integer.toString(minutes)+":"+secondsString;
		timerLabel.setText(timeText);
	}
	
	
	/*
	@Override
	public void act(float delta)
	{
		
		if(grid.isPlaying)
		{
			tapScreen.setText("");
		}
		
		else
		{
			tapScreen.setText("Tap and hold on the snake head to start...");
		}
	}
	*/
	
}
