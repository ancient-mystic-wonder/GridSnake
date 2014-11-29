package com.amw.GridSnake;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Void on 11/18/2014.
 */
public class ObstacleSpawner extends Actor
{
    Array<Obstacle> obstacleList;
    Array<GameScreen.SnakeBlock> snakeBlockList;
    Array<Coordinate> available;
    GameScreen.Grid grid;
    GameScreen.Snake snake;
    private int obstacleNumber = 6;

    public ObstacleSpawner(GameScreen.Grid g, GameScreen.Snake s)
    {
        this.grid = g;
        this.snake = s;
        obstacleList = new Array<Obstacle>();

        for(int i=0; i<obstacleNumber; i++)
            obstacleList.add(new Obstacle(-1,-1, GameScreen.BLOCK_WIDTH,GameScreen.BLOCK_HEIGHT,GameScreen.solidify));

    }

    public void spawnObstacle(int number)
    {
        available = grid.getFreeTiles(3);

        for (Obstacle currentObstacle : obstacleList)
            currentObstacle.reposition(-1, -1);

        if(available.size > 0) {
            for(int i=0; i<number; i++) {
                int rand = MathUtils.random(0, available.size - 1);
                Coordinate newFoodPosition = available.get(rand);
                obstacleList.get(i).reposition(newFoodPosition.x, newFoodPosition.y);
            }
        }


    }

    public void empty()
    {
        for(Obstacle o : obstacleList)
            o.reposition(-1,-1);
    }

    public Array<Obstacle> getObstacles()
    {
        return this.obstacleList;
    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        for(Obstacle o : obstacleList)
        {
            o.draw(batch, alpha);
        }
    }

}
