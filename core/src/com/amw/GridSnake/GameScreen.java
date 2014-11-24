package com.amw.GridSnake;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Iterator;


public class GameScreen implements Screen {

	GridSnakeGame game;
	private FPSLogger fpsLogger;

    // constructor to keep a reference to the main Game class
     public GameScreen(GridSnakeGame game){
             this.game = game;
             fpsLogger = new FPSLogger();
             //create();
     }
	
     public enum Move {
 	    LEFT, RIGHT, UP, DOWN, NONE
 	}
 	
 	public class SnakeBlock extends Actor {
         Texture texture = new Texture(Gdx.files.internal("textures/SnakeBodyTexture.png"),true);
         Texture headTexture = new Texture(Gdx.files.internal("textures/SnakeHeadTexture.png"));
         Texture tailTexture = new Texture(Gdx.files.internal("textures/SnakeTailTexture.png"));
         int indexX;
         int indexY;
         float blockWidth;
         float blockHeight;
         boolean flip = false;
         boolean isHead = false;
         boolean isTail = false;
         Array<Move> moveList;
         Move previousMove = Move.NONE;

         
         public SnakeBlock(int x, int y, float w, float h)
         {
         	this.indexX = x;
             this.indexY = y;
             this.blockWidth = w;
             this.blockHeight = h;
             setBounds(x*w, y*h, w, h);
             setOrigin(blockWidth/2,blockHeight/2);
             moveList = new Array<Move>();
         }
         
         public SnakeBlock(SnakeBlock head, Move[] offsetMoves)
         {
         	this.indexX = head.indexX;
             this.indexY = head.indexY;
             this.blockWidth = head.blockWidth;
             this.blockHeight = head.blockHeight;
             setBounds(indexX*blockWidth,indexY*blockHeight,blockWidth,blockHeight);
             setOrigin(blockWidth/2,blockHeight/2);
             moveList = new Array<Move>();
             
             for (Move currentMove : offsetMoves)
             {
             	moveSnakeBlock(currentMove);
             	moveList.add(oppositeMove(currentMove));
             }
         }
         
         public SnakeBlock(SnakeBlock block)
         {
         	this.indexX = block.indexX;
             this.indexY = block.indexY;
             this.blockWidth = block.blockWidth;
             this.blockHeight = block.blockHeight;
             setBounds(indexX*blockWidth,indexY*blockHeight,blockWidth,blockHeight);
             setOrigin(blockWidth/2,blockHeight/2);
             moveList = new Array<Move>();
             
             for (Move currentMove : block.moveList)
             	moveList.add(currentMove); 
         }
         
         {
        	 
        	 if(solidify)
        	 {
        		 texture = new Texture(Gdx.files.internal("textures/snake/SnakeBodyTexture_solid.png"),true);
                 headTexture = new Texture(Gdx.files.internal("textures/snake/SnakeHeadTexture_solid.png"));
                 tailTexture = new Texture(Gdx.files.internal("textures/snake/SnakeTailTexture_solid.png"));
        	 }
        	 
        	 texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	 headTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	 tailTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
         }
         
         public void changeHeadStatus(boolean b)
         {
         	this.isHead = b;
         }
         
         public void changeTailStatus(boolean b)
         {
         	this.isTail = b;
         }
         
         public Move oppositeMove(Move m)
         {
         	if(m==Move.LEFT)
         		return Move.RIGHT;
         	if(m==Move.RIGHT)
         		return Move.LEFT;
         	if(m==Move.UP)
         		return Move.DOWN;
         	if(m==Move.DOWN)
         		return Move.UP;
         	return Move.NONE;
         }
         
         public void addMove(Move m)
         {
         	moveList.add(m);
         }
         
         public void popFromMoveStack()
         {
         	Move newMove = moveList.removeIndex(0);
         	moveSnakeBlock(newMove);
         }
         
         public void moveSnakeBlock(Move move)
         {
         	switch (move)
         	{
         	case LEFT:
         		this.indexX -= 1;	
         		break;
         		
         	case RIGHT:
         		this.indexX += 1;
         		break;
         		
         	case UP:
         		this.indexY += 1;
         		break;
         		
         	case DOWN:
         		this.indexY -= 1;
         		break;
 			default:
 				break;
         	}
         	
         	if(isHead)
         		rotateSnakeBlock(move);
         	else if (isTail)
         	{
         		rotateSnakeBlock(moveList.get(0));
         	}
         	
         	this.previousMove = move;
         	this.setPosition(indexX*getWidth(),indexY*getHeight());
         	
         }
         
         public void rotateSnakeBlock(Move move)
         {
        	 switch (move)
          	{
          	case LEFT:
          			this.setRotation(0);
          			this.flip = false;
          		break;
          		
          	case RIGHT:
          			this.setRotation(0);
          			this.flip = true;
          		break;
          		
          	case UP:
 	         	this.setRotation(-90);
 	         	this.flip = false;
          		break;
          		
          	case DOWN:
 	         	this.setRotation(90);
 	         	this.flip = false;
          		break;
  			default:
  				break;
          	}
         }
         
         
         @Override
         public void draw(Batch batch, float alpha){
         	batch.setColor(1.0f, 1.0f, 1.0f, alpha/255);
         	Texture toDraw = texture;
         	if (this.isHead)
         		toDraw = headTexture;
         	else if(this.isTail)
         		toDraw = tailTexture;
         	else
         		toDraw = texture;
         	
         	batch.draw(toDraw, indexX*getWidth(), indexY*getHeight(),
         			getOriginX(), getOriginY(), 
         			getWidth(), getHeight(), 
         			getScaleX(), getScaleY(), getRotation(), 
         			0, 0, toDraw.getWidth(), toDraw.getHeight(), 
         			flip, false);

         	batch.setColor(Color.WHITE);
             //System.out.println(indexX*getX()+" "+indexY*getY());
         }
         
     }
 	
 	public class Snake extends Actor
 	{
 		Array<SnakeBlock> snakeBlockList;

 		Grid grid;
 		SnakeBlock snakeHead;
 		SnakeBlock snakeTail;
 		int currentX, currentY;
 		Move previousMove = Move.NONE;
 		
 		float maxLife = 1.0f;
 		float currentLife = 1.0f;
 		//float maxLife = 15f;
 		//float currentLife = 15f;
 		
 		boolean isAlive = true;
        boolean isFlashing = false;
        float flashInterval = 0.5f;
        float flashAlpha = 255f;
        float flashTimer = 0;
 		
 		public Snake(Grid g)
 		{
 			this.grid = g;
 			this.currentX = 0;
 			this.currentY = 0;
 			snakeHead = new SnakeBlock(0,0,BLOCK_WIDTH,BLOCK_HEIGHT);
 			snakeHead.changeHeadStatus(true);
 			snakeBlockList = new Array<SnakeBlock>(g.BLOCK_NUMBER_X*g.BLOCK_NUMBER_Y);
 			snakeBlockList.add(snakeHead);
 			
 			Move[] moveArray = new Move[]{Move.RIGHT};
 			SnakeBlock s1 = new SnakeBlock(snakeHead,moveArray);
 			moveArray = new Move[]{Move.RIGHT,Move.RIGHT};
 			SnakeBlock s2 = new SnakeBlock(snakeHead,moveArray);
 			snakeBlockList.add(s1);
 			snakeBlockList.add(s2);
 			snakeTail = s2;
 			snakeTail.changeTailStatus(true);
 		}
 		
 		public Snake(Grid g, int startX, int startY)
 		{
 			this.grid = g;
 			this.currentX = startX;
 			this.currentY = startY;
 			snakeHead = new SnakeBlock(startX,startY,BLOCK_WIDTH,BLOCK_HEIGHT);
 			snakeBlockList = new Array<SnakeBlock>(g.BLOCK_NUMBER_X*g.BLOCK_NUMBER_Y);
 			snakeBlockList.add(snakeHead);
 		}
 		
 		public Move getMoveType(int oldX, int oldY, int newX, int newY)
 		{
 			if(oldX > newX)
 				return Move.LEFT;
 			if(oldX < newX)
 				return Move.RIGHT;
 			if(oldY > newY)
 				return Move.DOWN;
 			if(oldY < newY)
 				return Move.UP;
 			return Move.NONE;
 		}
 		
 		public Boolean checkValidMove(int newX, int newY)
 		{
 			Move currentMove = getMoveType(currentX,currentY,newX,newY);
 			if(snakeHead.previousMove == Move.LEFT && currentMove==Move.RIGHT)
 				return false;
 			if(snakeHead.previousMove == Move.RIGHT && currentMove==Move.LEFT)
 				return false;
 			if(snakeHead.previousMove == Move.DOWN && currentMove==Move.UP)
 				return false;
 			if(snakeHead.previousMove == Move.UP && currentMove==Move.DOWN)
 				return false;
 			if (newX >= grid.BLOCK_NUMBER_X || newX < 0 || newY >= grid.BLOCK_NUMBER_Y || newY < 0)
 				return false;
 			return true;
 		}


        /**
         *
         * @param newX
         * @param newY
         *
         * This function checks if the new position is really new, if it is valid, and if snake is still alive
         * It gets called every update step
         *
         */
 		public boolean checkMove(int newX, int newY)
 		{
 			if((newX != currentX || newY != currentY) && checkValidMove(newX,newY) && isAlive)
 			{
 				Move currentMove = getMoveType(currentX,currentY,newX,newY);
 				moveBlocks(currentMove);
 				//System.out.println(currentMove);
 				currentX = newX; currentY = newY;
 				currentLife = maxLife;
                return true;
 			}
            return false;
 		}
 		
 		public void checkCompensationMove(int touchX, int touchY)
 		{
 			currentX = snakeHead.indexX; currentY = snakeHead.indexY;
 			/*System.out.println("touch: " + touchX + " " + touchY);
 			System.out.println("current: " + currentX + " " + currentY);
 			System.out.println("snakehead: " + snakeHead.indexX+" "+snakeHead.indexY);
 			if((touchX != currentX || touchY != currentY) && isAlive)
 			{
 				Move currentMove = Move.NONE;
 				if (touchX > currentX && checkValidMove(currentX+1,currentY))
 					currentMove = getMoveType(currentX,currentY,currentX+1,currentY);
 				if (touchX < currentX && checkValidMove(currentX-1,currentY))
 					currentMove = getMoveType(currentX,currentY,currentX-1,currentY);
 				if (touchY > currentY && checkValidMove(currentX,currentY+1))
 					currentMove = getMoveType(currentX,currentY,currentX,currentY+1);
 				if (touchY < currentY && checkValidMove(currentX,currentY-1))
 					currentMove = getMoveType(currentX,currentY,currentX,currentY-1);
 				moveBlocks(currentMove);
 				//System.out.println(currentMove);
 				
 				currentLife = maxLife;
 			}*/
 		}


        public void checkFull()
        {
            if(this.getLength() >= BLOCK_NUMBER_X*BLOCK_NUMBER_Y)
                grid.stopGame("full");
        }
 		
 		public void moveBlocks(Move move)
 		{
 			previousMove = move;
 			for(SnakeBlock currentSnakeBlock : snakeBlockList)
 			{
 				currentSnakeBlock.addMove(move);
 				currentSnakeBlock.popFromMoveStack();
 			}
 		}

 		public float getAlpha(float life)
 		{
 			//return ((255/maxLife)*life);
 			return (life/maxLife)*255;
 		}

        public void flashOnKill()
        {
            this.isFlashing = true;
        }
 		
 		
 		@Override
 		public void act(float delta)
 		{
 			if(grid.isPlaying)
 			{
 				currentLife -= delta;
 				if (currentLife < 0)
 				{
 					currentLife = 0;
 					grid.stopGame("timer");
 				}
 				//checkMove(grid.getIndex_X(),grid.getIndex_Y());
 				//checkCompensationMove(grid.getIndex_X(),grid.getIndex_Y());
                checkFull();
 			}

            if(this.isFlashing)
            {
                flashTimer += delta;
                if (flashTimer >= flashInterval)
                {
                    if(flashAlpha==255f)
                        flashAlpha = 0f;
                    else
                        flashAlpha = 255f;
                    flashTimer = 0;
                }
            }

 		}
 		
 		
 		@Override
 		public void draw(Batch batch, float alpha)
 		{
 			float newAlpha;

            if(this.isFlashing)
                newAlpha = flashAlpha;

            // get life's corresponding alpha value
            else
 			    newAlpha = getAlpha(currentLife);

 			for(SnakeBlock currentSnakeBlock : snakeBlockList)
 			{
 				currentSnakeBlock.draw(batch, newAlpha);
 			}
         }
 		
 		public void elongate()
 		{ 			
 			SnakeBlock lastBlock = snakeBlockList.get(snakeBlockList.size-1);
 			SnakeBlock newBlock = new SnakeBlock(lastBlock);
 			newBlock.moveSnakeBlock(newBlock.oppositeMove(lastBlock.previousMove));
 			newBlock.moveList.insert(0,lastBlock.previousMove);
 			snakeBlockList.add(newBlock);
 			lastBlock.changeTailStatus(false);
 			newBlock.changeTailStatus(true);
 			snakeTail = newBlock;
 			snakeTail.rotateSnakeBlock(lastBlock.previousMove);
 		
            if(this.getLength() > grid.bestLength)
                grid.bestLength = this.getLength();
 		
 		}
 		
 		public void reduceTail()
 		{
 			SnakeBlock lastBlock = snakeBlockList.removeIndex(snakeBlockList.size-1);
 			snakeTail = snakeBlockList.get(snakeBlockList.size-1);
 			snakeTail.changeTailStatus(true);
 			snakeTail.rotateSnakeBlock(snakeTail.moveList.get(0));
 		}
 		
 		public void resetSnake()
 		{
 			this.currentX = 0;
 			this.currentY = 0;
 			
 			// dispose all snakeblocks
 			for (SnakeBlock currentBlock : snakeBlockList)
 			{
 				currentBlock.remove();
 			}
 			
 			snakeBlockList.clear();
 			snakeHead = new SnakeBlock(0,0,BLOCK_WIDTH,BLOCK_HEIGHT);
 			snakeHead.changeHeadStatus(true);
 			snakeBlockList.add(snakeHead);
 			
 			Move[] moveArray = new Move[]{Move.RIGHT};
 			SnakeBlock s1 = new SnakeBlock(snakeHead,moveArray);
 			moveArray = new Move[]{Move.RIGHT,Move.RIGHT};
 			SnakeBlock s2 = new SnakeBlock(snakeHead,moveArray);
 			snakeBlockList.add(s1);
 			snakeBlockList.add(s2);
 			snakeTail = s2;
 			snakeTail.changeTailStatus(true);
 			
 			currentLife = maxLife;
            this.isFlashing = false;
 		}
 		
 		public int getLength()
 		{
 			return this.snakeBlockList.size;
 		}
 		
 	}
 	

 	

 	

 	
 	public class Grid extends Actor
 	{
 		int current_index_x;
 		int current_index_y;
 		float width = 20;
 		float height = 20;
 		int BLOCK_NUMBER_X;
 		int BLOCK_NUMBER_Y;
 		boolean isPlaying = false;
 		boolean showingOtherScreen = false;
 		Texture texture = new Texture(Gdx.files.internal("textures/GridTexture.png"));
 		Sound eatSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bite.mp3"));
 		Sound eatTailSound = Gdx.audio.newSound(Gdx.files.internal("sounds/EatTail.wav"));
 		Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
        Sound letGoSound = Gdx.audio.newSound(Gdx.files.internal("sounds/letgo.wav"));
 		
 		Snake snake;
 		FoodSpawner foodSpawner;
        ObstacleSpawner obstacleSpawner;
 		GameUI gameUI;
 		Stage stage;
 		GridSnakeGame game;
 		
 		int points = 0;
 		float timer = 0;
 		int bestLength = 3;

 		public Grid(int x, int y, float w, float h)
 		{
 			BLOCK_NUMBER_X = x;
 			BLOCK_NUMBER_Y = y;
 			width = w;
 			height = h;
 			setBounds(0,0,this.width,this.height);
 			
 			addListener(new InputListener()
 			{
 	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
 	            {
 	            	Grid target = (Grid)event.getTarget();
 	            	target.changeCurrentBox(x, y);

                    // you may only start the game if there is no other screen, not yet playing, and snake isnt flashing
 	            	if (!target.showingOtherScreen && !target.isPlaying && !target.snake.isFlashing)
 	            		target.tryToStartGame();
 	                
 	            	return true;
 	            }
 	            
 	            public void touchDragged(InputEvent event, float x, float y, int pointer)
 	            {
 	            	Grid target = (Grid)event.getTarget();
 	            	if (target.isPlaying)
 	            	{
 	            		target.changeCurrentBox(x, y);
 	            	}
 	            }
 	            
 	            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
 	            {
 	            	Grid target = (Grid)event.getTarget();
                    if(target.isPlaying)
 	            	    target.stopGame("letitgo");
 	            }	            
 	        });
 		}
 		
 		
 		 @Override
         public void draw(Batch batch, float alpha){
 			batch.setColor(1,1,1,1);
 			for(int x=0; x< BLOCK_NUMBER_X; x++)
 	        {
 	        	for(int y=0; y< BLOCK_NUMBER_Y; y++)
 	        		batch.draw(texture, x*BLOCK_WIDTH, y*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
 	        }
         }
 		
 		public void changeCurrentBox(float coordX, float coordY)
 		{
 			int index_x = (int)(coordX / BLOCK_WIDTH);
 			int index_y = (int)(coordY / BLOCK_HEIGHT);
 			
 			current_index_x = index_x;
 			current_index_y = index_y;
 		}
 		
 		public int getIndex_X()
 		{
 			return current_index_x;
 		}
 		
 		public int getIndex_Y()
 		{
 			return current_index_y;
 		}
 		
 		@Override
 		public void act(float delta)
 		{
 			
 			if(this.isPlaying)
 			{
                if (snake.checkMove(getIndex_X(),getIndex_Y())) {
                    snake.checkCompensationMove(getIndex_X(), getIndex_Y());
                    checkEatTail();
                    checkEat();
                    checkKill();
                }
 				
 				timer+=delta;
 				gameUI.updateTimer(timer);
 			}
 			
 			if(!snake.isAlive)
 				this.isPlaying = false;

 		}
 		
 		public void tryToStartGame()
 		{
 			if(current_index_x == snake.snakeHead.indexX && current_index_y == snake.snakeHead.indexY)
 			{
 				this.isPlaying = true;
 				snake.isAlive = true;
 				gameUI.start();
 			}
 		}
 		
 		public void stopGame(final String defeatString)
 		{
 			if(this.isPlaying)
 			{
 				this.isPlaying = false;
 				snake.isAlive = false;

                // make snake flash first, then showScore
                snake.flashOnKill();

                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        showScore(defeatString);
                    }
                }, 1.5f);
 			}

            if(defeatString.equals("letitgo"))
                letGoSound.play();
            else if(defeatString.equals("hit"))
                hitSound.play();
 		}
 		
 		// This only gets called after the score dialog's button is pressed
 		public void resetBoard()
 		{
 			this.showingOtherScreen = false;
			snake.resetSnake();
			gameUI.stop();
			points = 0;
			timer = 0;
			bestLength = 3;
			//foodSpawner.empty();
			foodSpawner.spawnFood();
            obstacleSpawner.empty();
 		}
 		
 		public void checkEat()
 		{
 			SnakeBlock snakeHead = snake.snakeHead;
 			boolean eat = false;

 			
 			for (Iterator<Food> it = foodSpawner.foodList.iterator(); it.hasNext(); ) 
 			{
 			    Food f = it.next();
 			    if(f.indexX == snakeHead.indexX && f.indexY == snakeHead.indexY)
 				{
 					eat = true;
 					//it.remove();
 				}
 			}
 			
 			if (eat)
 			{
 				snake.elongate();
 				if(snake.getLength() < BLOCK_NUMBER_X*BLOCK_NUMBER_Y) {
                    foodSpawner.spawnFood();
                    checkSpawnObstacle();
                }
 				points+=1;
 				gameUI.updateScore(points);
 				gameUI.updateMultiplier(snake.getLength());
 				eatSound.play();
 			}
 		}
 		
 		public void checkEatTail()
 		{
 			SnakeBlock snakeHead = snake.snakeHead;
 			SnakeBlock snakeTail = snake.snakeTail;
 			 			
 			if(snakeHead.indexX == snakeTail.indexX && snakeHead.indexY == snakeTail.indexY)
 			{
 				snake.reduceTail();
 				gameUI.updateMultiplier(snake.getLength());
 				eatTailSound.play();
 			}
 		}

        public void checkKill()
        {
            boolean kill = false;
            String s = "";
            SnakeBlock snakeHead = snake.snakeHead;
            SnakeBlock snakeTail = snake.snakeTail;
            for(SnakeBlock currentSnakeBlock : snake.snakeBlockList)
            {
                if(!currentSnakeBlock.equals(snakeHead) && !currentSnakeBlock.equals(snakeTail))
                    if(snakeHead.indexX == currentSnakeBlock.indexX && snakeHead.indexY == currentSnakeBlock.indexY)
                    {
                        kill = true;
                        s = "self";
                        break;
                    }
            }

            for(Obstacle currentObstacle : obstacleSpawner.obstacleList)
            {
                if(snakeHead.indexX == currentObstacle.indexX && snakeHead.indexY == currentObstacle.indexY)
                {
                    kill = true;
                    s = "obstacle";
                    break;
                }
            }

            if (kill)
                grid.stopGame(s);
        }

        public void checkSpawnObstacle()
        {
            if(points > 50)
                obstacleSpawner.spawnObstacle(1);
            else if (points > 100)
                obstacleSpawner.spawnObstacle(2);
            else if (points > 150)
                obstacleSpawner.spawnObstacle(3);
            else
                obstacleSpawner.spawnObstacle(4);
        }
 		
 		public void showScore(String defeatString)
 		{
 			 this.showingOtherScreen = true;
	         //Skin uiSkin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
 			 
		         
	         ScoreDialog scoreDialog = new ScoreDialog(grid, defeatString);
	         //scoreDialog.pack();
	         scoreDialog.setPosition((this.width/2)-(scoreDialog.getWidth()/2), 0);
	         MoveToAction move = new MoveToAction();
	         move.setPosition((this.width/2)-(scoreDialog.getWidth()/2), (this.height/2)+(60));
	         move.setDuration(0.4f);
	         scoreDialog.addAction(move);
	         
	         stage.addActor(scoreDialog);
 		}
 		
 		public void exitGame()
 		{
 			this.snake.clear();
 			this.foodSpawner.clear();
            this.obstacleSpawner.empty();
 			this.clear();
 			this.game.mainMenu();
 			
 		}

        public Array<Coordinate> getFreeTiles(int mode) // mode: 1- exclude snake   2-exclude snake and food
        {

            Array available = new Array<Coordinate>(GameScreen.BLOCK_NUMBER_X*GameScreen.BLOCK_NUMBER_Y);

            Array<SnakeBlock> snakeBlockList = snake.snakeBlockList;

            for (int x = 0; x < GameScreen.BLOCK_NUMBER_X; x++)
                for (int y = 0; y < GameScreen.BLOCK_NUMBER_Y; y++)
                    available.add(new Coordinate(x, y));


            for (SnakeBlock currentSnakeBlock : snakeBlockList) {
                int removeX = currentSnakeBlock.indexX;
                int removeY = currentSnakeBlock.indexY;
                available.removeValue(new Coordinate(removeX, removeY), false);
            }

            if (mode == 2) {
                for (Food currentFood : foodSpawner.getFoods()) {
                    int removeX = currentFood.indexX;
                    int removeY = currentFood.indexY;
                    available.removeValue(new Coordinate(removeX, removeY), false);
                }
            }

            return available;
        }
 		
 		public void setSnake(Snake s)
 		{
 			this.snake = s;
 		}
 		
 		public void setFoodSpawner(FoodSpawner f)
 		{
 			this.foodSpawner = f;
 		}
 		
 		public void setGameUI(GameUI ui)
 		{
 			this.gameUI = ui;
 		}
 		
 		public void setStage(Stage s)
 		{
 			this.stage = s;
 		}
 		
 		public void setGame(GridSnakeGame g)
 		{
 			this.game = g;
 		}

        public void setObstacleSpawner(ObstacleSpawner s) {this.obstacleSpawner = s;}
 	}
 	
 	
 	private Stage stage;
     int STAGE_WIDTH;
     int STAGE_HEIGHT;
     
     int GRID_WIDTH;
     int GRID_HEIGHT;
     
     int CURRENT_WIDTH;
     int CURRENT_HEIGHT;
     public static float BLOCK_WIDTH;
     public static float BLOCK_HEIGHT;
     public static int BLOCK_NUMBER_X = 7;
     public static int BLOCK_NUMBER_Y = 8;
     Grid grid;
     GameUI gameUI;
     
     public static boolean solidify = false;
     
     public int getGCD(int a, int b) { return b==0 ? a : getGCD(b, a%b); }
     
     public void create() {   
    	 
    	 Gdx.graphics.setVSync(false);   
    	 Gdx.input.setCatchBackKey(true);
    	 
     	 STAGE_WIDTH = 320;
         STAGE_HEIGHT = 480;
         
         CURRENT_WIDTH = Gdx.graphics.getWidth();
         CURRENT_HEIGHT = Gdx.graphics.getHeight();
         
         GRID_WIDTH = STAGE_WIDTH;
         GRID_HEIGHT = STAGE_HEIGHT;
         
         if (GRID_WIDTH < GRID_HEIGHT)
        	 GRID_HEIGHT = GRID_WIDTH;
         else
        	 GRID_WIDTH = GRID_HEIGHT;
         
         BLOCK_WIDTH = (float)GRID_WIDTH / BLOCK_NUMBER_X;
         BLOCK_HEIGHT = (float)GRID_HEIGHT / BLOCK_NUMBER_Y;
         
         if (BLOCK_WIDTH>BLOCK_HEIGHT)
        	 BLOCK_HEIGHT=BLOCK_WIDTH;
         else
        	 BLOCK_WIDTH=BLOCK_HEIGHT;
     	
         int gcd = getGCD(STAGE_WIDTH,STAGE_HEIGHT);         
         int STAGE_ASPECT_WIDTH = STAGE_WIDTH/gcd;
         int STAGE_ASPECT_HEIGHT = STAGE_HEIGHT/gcd;
         
         gcd = getGCD(CURRENT_WIDTH,CURRENT_HEIGHT); 
         int CURRENT_ASPECT_WIDTH = CURRENT_WIDTH/gcd;
         int CURRENT_ASPECT_HEIGHT = CURRENT_HEIGHT/gcd;
         
         System.out.println(STAGE_WIDTH+":"+STAGE_HEIGHT);
         System.out.println(CURRENT_ASPECT_WIDTH+":"+CURRENT_ASPECT_HEIGHT);
         
         
         // check if we gonna solidify the blocks
         float REAL_BLOCK_WIDTH = (float)CURRENT_WIDTH / BLOCK_NUMBER_X;
         float REAL_BLOCK_HEIGHT = (float)CURRENT_HEIGHT / BLOCK_NUMBER_Y;
         
         if (REAL_BLOCK_WIDTH < REAL_BLOCK_HEIGHT)
        	 REAL_BLOCK_HEIGHT = REAL_BLOCK_WIDTH;
         else
        	 REAL_BLOCK_WIDTH = REAL_BLOCK_HEIGHT;
         

         if(REAL_BLOCK_WIDTH<40)
        	 solidify = true;
         
         //System.out.println((float)STAGE_ASPECT_WIDTH/(float)CURRENT_ASPECT_WIDTH + " " +
        //		 (float)STAGE_ASPECT_HEIGHT/(float)CURRENT_ASPECT_HEIGHT);
         
         // we have 2:3
         // for 240x320 == 3:4 (2/3 < 3/4)
         if((float)STAGE_ASPECT_WIDTH/(float)CURRENT_ASPECT_WIDTH <= (float)STAGE_ASPECT_HEIGHT/(float)CURRENT_ASPECT_HEIGHT)
         {
        	 stage = new Stage(new StretchViewport(320,480));
         }
         
         // for 480x800 == 3:5 (2/3 > 3/5)
         else
        	 stage = new Stage(new FitViewport(320,480));
         

         
         //stage.getCamera().position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
         Gdx.input.setInputProcessor(stage);
         
         grid = new Grid(BLOCK_NUMBER_X,BLOCK_NUMBER_Y,GRID_WIDTH,GRID_HEIGHT);
         grid.setTouchable(Touchable.enabled);
         stage.addActor(grid);
         
         Snake snake = new Snake(grid);
         FoodSpawner foodSpawner = new FoodSpawner(grid,snake);
         ObstacleSpawner obstacleSpawner = new ObstacleSpawner(grid,snake);

         
         gameUI = new GameUI(grid);
         gameUI.stop();
         
         grid.setSnake(snake);
         grid.setFoodSpawner(foodSpawner);
         grid.setObstacleSpawner(obstacleSpawner);
         grid.setGameUI(gameUI);
         grid.setStage(stage);
         grid.setGame(game);
         
         stage.addActor(snake);
         stage.addActor(foodSpawner);
         stage.addActor(obstacleSpawner);
         stage.addActor(gameUI);

         foodSpawner.spawnFood();

         //WindowStyle newStyle = new WindowStyle();
         //newStyle.titleFont = new BitmapFont();      
     }

     @Override
     public void dispose() {
     }

     @Override
     public void render(float delta) {    
         Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
      // output the current FPS
        fpsLogger.log();
         
         stage.act(Gdx.graphics.getDeltaTime());
         
         //gameUI.debug(); // turn on all debug lines (table, cell, and widget)
         //Table.drawDebug(stage);
         stage.draw();
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
	public void show() {
		// TODO Auto-generated method stub
		create();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
