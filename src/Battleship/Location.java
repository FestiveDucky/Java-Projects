package Battleship;

public class Location
{
    public static final int UNGUESSED = 0;
    public static final int HIT = 1;
    public static final int MISSED = 2;

    private int status;
    private boolean ship;

    public Location()
    {
        status = UNGUESSED;
        ship = false;
    }

    public boolean checkHit()
    {
        return status == HIT;
    }

    public boolean checkMiss()
    {
        return status == MISSED;
    }

    public boolean isUnguessed()
    {
        return status == UNGUESSED;
    }

    public void markHit()
    {
        status = HIT;
    }

    public void markMiss()
    {
        status = MISSED;
    }

    public boolean hasShip()
    {
        return ship;
    }

    public void setShip(boolean val)
    {
        ship = val;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getStatus()
    {
        return status;
    }
}