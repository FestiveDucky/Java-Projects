package Battleship;

public class Grid {
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;

    private final Location[][] grid;

    public Grid() {
        grid = new Location[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                grid[i][j] = new Location();
            }
        }
    }

    public void markHit(int row, int col) {
        grid[row][col].markHit();
    }

    public void markMiss(int row, int col) {
        grid[row][col].markMiss();
    }

    public void setStatus(int row, int col, int status) {
        grid[row][col].setStatus(status);
    }

    public int getStatus(int row, int col) {
        return grid[row][col].getStatus();
    }

    public boolean alreadyGuessed(int row, int col) {
        return !grid[row][col].isUnguessed();
    }

    public void setShip(int row, int col, boolean val) {
        grid[row][col].setShip(val);
    }

    public boolean hasShip(int row, int col) {
        return grid[row][col].hasShip();
    }

    public Location get(int row, int col) {
        return grid[row][col];
    }

    public int numRows() {
        return grid.length;
    }

    public int numCols() {
        return grid[0].length;
    }

    public void printStatus() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < numRows(); i++) {
            System.out.print((char) (i + 65));
            System.out.print(" ");
            for (int j = 0; j < numCols(); j++) {
                if (getStatus(i, j) == Location.MISSED) {
                    System.out.print("O ");
                } else if (getStatus(i, j) == Location.HIT) {
                    System.out.print("X ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.print("\n");
        }
    }

    public void printShips() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < numRows(); i++) {
            System.out.print((char) (i + 65));
            System.out.print(" ");
            for (int j = 0; j < numCols(); j++) {
                if (hasShip(i, j)) {
                    System.out.print("X ");
                } else {
                    System.out.print("- ");
                }

            }
            System.out.print("\n");
        }
    }

    public boolean addShip(Ship s) {
        int rows, cols;
        if (s.getDirection() == Ship.HORIZONTAL) {
            cols = s.getLength();
            rows = 1;
            if (cols + s.getCol() >= numCols()) {
                return true;
            }
        } else {
            rows = s.getLength();
            cols = 1;
            if (rows + s.getRow() >= numRows()) {
                return true;
            }
        }

        for (int i = s.getRow(); i < s.getRow() + rows; i++) {
            for (int j = s.getCol(); j < s.getCol() + cols; j++) {
                setShip(i, j, true);
            }
        }
        return false;
    }
}