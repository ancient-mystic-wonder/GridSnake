package com.amw.GridSnake;

/**
 * Created by Void on 6/19/2014.
 */
public class Coordinate {
    public int x;
    public int y;
    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Coordinate)
        {
            Coordinate c = (Coordinate) o;
            if (c.x == this.x && c.y == this.y)
                return true;

        }
        return false;
    }
}
