package Battleship;
public class Player
{
    private static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};

    private Ship[] playerShips;
    private int ships;
    private int hitSections;
    private int shipSum;
    private Grid playerGrid;
    private int newShipSunk;

    public Player()
    {
        playerShips = new Ship[SHIP_LENGTHS.length];
        playerGrid = new Grid();
        ships = 0;
        hitSections = 0;
        shipSum = 0;
        newShipSunk = 0;

        for (int i = 0; i < numShips(); i++)
        {
            shipSum += SHIP_LENGTHS[i];
        }
    }

    public void checkIfShipSunk(int row, int col)
    {
        for (int z = 0; z < ships; z++)
        {
            Ship s = playerShips[z];
            int r, c;
            if (s.getDirection() == Ship.HORIZONTAL)
            {
                c = s.getLength();
                r = 1;
            }
            else
            {
                r = s.getLength();
                c = 1;
            }

            boolean containsPoint = false;
            int sectionsHit = 0;

            for (int i = s.getRow(); i < s.getRow() + r; i++)
            {
                for (int j = s.getCol(); j < s.getCol() + c; j++)
                {
                    if (i == row && j == col)
                    {
                        containsPoint = true;
                    }
                    if (playerGrid.get(i, j).checkHit())
                    {
                        sectionsHit++;
                    }
                }
            }

            if (containsPoint)
            {
                if (sectionsHit == s.getLength())
                {
                    newShipSunk = s.getLength();
                    break;
                }
                else
                {
                    return;
                }
            }
        }
    }

    public int shipSunk()
    {
        return newShipSunk;
    }

    public void setShipSunk(int val)
    {
        newShipSunk = val;
    }

    public boolean attacked(int row, int col)
    {
        if (playerGrid.get(row, col).hasShip())
        {
            playerGrid.get(row, col).markHit();
            checkIfShipSunk(row, col);
            hitSections++;
            return true;
        }
        else
        {
            playerGrid.get(row, col).markMiss();
            return false;
        }
    }

    public int getHitSections()
    {
        return hitSections;
    }

    public int getShipSums()
    {
        return shipSum;
    }

    public boolean addShip(Ship s)
    {
        if (checkOverlap(s))
        {
            return true;
        }
        playerShips[ships] = s;
        ships++;
        if (playerGrid.addShip(s))
        {
            ships--;
            return true;
        }
        return false;
    }

    public boolean checkOverlap(Ship s)
    {
        for (int z = 0; z < ships; z++)
        {
            Ship s2 = playerShips[z];
            int r1, c1, r2, c2;
            if (s.getDirection() == Ship.HORIZONTAL)
            {
                c1 = s.getLength();
                r1 = 1;
            }
            else
            {
                r1 = s.getLength();
                c1 = 1;
            }
            if (s2.getDirection() == Ship.HORIZONTAL)
            {
                c2 = s2.getLength();
                r2 = 1;
            }
            else
            {
                r2 = s2.getLength();
                c2 = 1;
            }

            for (int i = s.getRow(); i < s.getRow() + r1; i++)
            {
                for (int j = s.getCol(); j < s.getCol() + c1; j++)
                {
                    for (int k = s2.getRow(); k < s2.getRow() + r2; k++)
                    {
                        for (int l = s2.getCol(); l < s2.getCol() + c2; l++)
                        {
                            if (i==k && j==l)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean wasGuessed(int row, int col)
    {
        if (playerGrid.get(row, col).isUnguessed())
        {
            return false;
        }
        return true;
    }

    public static int numShips()
    {
        return SHIP_LENGTHS.length;
    }

    public static int getShipLength(int i)
    {
        return SHIP_LENGTHS[i];
    }

    public void showPlayerGrid()
    {
        playerGrid.printStatus();
    }

    public void showShipGrid()
    {
        playerGrid.printShips();
    }
}