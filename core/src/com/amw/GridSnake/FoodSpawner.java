package com.amw.GridSnake;

import com.amw.GridSnake.GameScreen.Grid;
import com.amw.GridSnake.GameScreen.Snake;
import com.amw.GridSnake.GameScreen.SnakeBlock;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Void on 11/18/2014.
 */
public class FoodSpawner extends Actor
{
    Array<Food> foodList;
    Array<SnakeBlock> snakeBlockList;
    Array<Coordinate> available;

    Grid grid;
    Snake snake;

    public FoodSpawner(Grid g, Snake s)
    {
        this.grid = g;
        this.snake = s;
        foodList = new Array<Food>();
        foodList.add(new Food(GameScreen.BLOCK_NUMBER_X,GameScreen.BLOCK_NUMBER_Y, GameScreen.BLOCK_WIDTH,GameScreen.BLOCK_HEIGHT,GameScreen.solidify));

    }

    public void spawnFood()
    {

        available = grid.getFreeTiles(1);

        if(available.size > 0) {
            int rand = MathUtils.random(0, available.size - 1);
            Coordinate newFoodPosition = available.get(rand);

            foodList.get(0).reposition(newFoodPosition.x, newFoodPosition.y);
        }
    }

    public void empty()
    {
        foodList.clear();
    }

    public Array<Food> getFoods()
    {
        return this.foodList;
    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        for(Food f : foodList)
        {
            f.draw(batch, alpha);
        }
    }

}