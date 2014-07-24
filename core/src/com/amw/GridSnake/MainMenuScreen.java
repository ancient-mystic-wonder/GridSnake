package com.amw.GridSnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen implements Screen {

	
	GridSnakeGame game;
	private Stage stage;
	
	private float BUTTON_WIDTH = 200f;
    private float BUTTON_HEIGHT = 60f;
    private final float BUTTON_SPACING = 10f;
    
    Color gray = new Color(74/255f, 78/255f, 79/255f, 1);
    Color green = new Color(182/255f, 222/255f, 159/255f, 1);
    // constructor to keep a reference to the main Game class
     public MainMenuScreen(GridSnakeGame game){
             this.game = game;
             //create();
     }

     public void create () {
    	 
    	 
     }

     
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		//Gdx.graphics.getGL20().glClearColor( 1, 0, 0, 1 );
		Gdx.gl.glClearColor(182/255f, 222/255f, 159/255f, 1);
		//Gdx.gl.glClearColor(185/255f, 199/255f, 51/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Table.drawDebug(stage);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		 
		
        stage = new Stage(new StretchViewport(320, 480));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        
        
        /// Button style and font
        
        Texture upTexture =  new Texture(Gdx.files.internal("textures/playButtonTexture.png"));
        Texture downTexture =  new Texture(Gdx.files.internal("textures/playButtonTexture_down.png"));
        upTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        downTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        TextureRegion upRegion = new TextureRegion(upTexture);
        TextureRegion downRegion = new TextureRegion(downTexture);
        BitmapFont buttonFont = new BitmapFont(Gdx.files.internal("fonts/digiffiti.fnt"),
        		Gdx.files.internal("fonts/digiffiti.png"),false);
        BitmapFont companyFont = new BitmapFont(Gdx.files.internal("fonts/digiffiti.fnt"),
        		Gdx.files.internal("fonts/digiffiti.png"),false);
        TextButtonStyle style = new TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;
        style.fontColor = gray;
        
        BUTTON_WIDTH = upTexture.getWidth()*0.95f;
        BUTTON_HEIGHT = upTexture.getHeight()*0.95f;
        
        
        // BUTTON INSTANTIATION
        
        TextButton playButton = new TextButton("PLAY",style);
        playButton.setWidth(BUTTON_WIDTH);
        playButton.setHeight(BUTTON_HEIGHT);
        
        
        TextButton instructionsButton = new TextButton("INSTRUCTIONS",style);
        instructionsButton.setWidth(BUTTON_WIDTH);
        instructionsButton.setHeight(BUTTON_HEIGHT);
        
        playButton.addListener( new ClickListener()
        {
        	public void clicked(InputEvent event,float x,float y)
        	{
        		game.startGame();
        	}
        });
        
        instructionsButton.addListener( new ClickListener()
        {
        	public void clicked(InputEvent event,float x,float y)
        	{
        		game.showInstructions();
        	}
        });

        
        
        LabelStyle labelStyle = new LabelStyle(companyFont,gray);
        
        // LOGO
        
        Texture logoTexture = new Texture(Gdx.files.internal("textures/Gridsnake Logo 2.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        Image logo = new Image(logoTexture);
        logo.setScale(1.3f, 1.3f);  
        logo.setOrigin(logo.getWidth()/2, logo.getHeight()/2);

        // Company name
        
        Label subtitle = new Label("ANCIENT MYSTIC WONDER GAMES", labelStyle);
        subtitle.setFontScale(0.7f,0.7f);
        
        
        
        //table.add(title);
        table.add(logo).padTop(5).padBottom(5);
        table.row();
        table.add(subtitle).pad(2);
        table.row();
        table.add(playButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).pad(5);
        table.row();
        table.add(instructionsButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).pad(5);
        stage.addActor(table);
        
        // Add widgets to the table here.
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
