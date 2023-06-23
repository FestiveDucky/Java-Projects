package Battleship;

public class Ship
{
    public static final int UNSET = -1;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int row;
    private int col;
    private int length;
    private int direction;

    public Ship(int length)
    {
        this.length = length;
        this.direction = UNSET;
        row = -1;
        col = -1;
    }

    public boolean isLocationSet()
    {
        return row != -1 && col != -1;
    }

    public boolean isDirectionSet()
    {
        return direction != UNSET;
    }

    public void setLocation(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public int getLength()
    {
        return length;
    }

    public int getDirection()
    {
        return direction;
    }

    private String directionToString()
    {
        if (direction == UNSET)
        {
            return "unset";
        }
        else if (direction == VERTICAL)
        {
            return "vertical";
        }
        return "horizontal";
    }

    public String toString()
    {
        return "A " + directionToString() + " ship of length " + length + " at (" + col + ", " + row + ") ";
    }

}