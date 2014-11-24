package com.amw.GridSnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Void on 11/18/2014.
 */
public class Food extends Actor
{
    Texture texture = new Texture(Gdx.files.internal("textures/FoodTexture.png"));
    int indexX;
    int indexY;
    float blockWidth;
    float blockHeight;

    public Food(int x, int y, float w, float h, boolean solidify)
    {
        this.indexX = x;
        this.indexY = y;
        this.blockWidth = w;
        this.blockHeight = h;
        setBounds(x*w, y*h, w, h);

        if (solidify)
            texture = new Texture(Gdx.files.internal("textures/FoodTexture_solid.png"));

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }

    public void reposition(int x, int y)
    {
        this.indexX = x;
        this.indexY = y;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,indexX*getWidth(),indexY*getHeight(),getWidth(), getHeight());
        //System.out.println(indexX*getX()+" "+indexY*getY());
    }


}
