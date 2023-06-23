import java.util.*;

public class TicTacToe {
    //TODO
    public static Random rand = new Random();
    // Creating the board
    // 0 = no one, 1 = player, 2 = computer
    public static int[][] board =  new int[3][3];
    public static String[][] boardDisplay = {{"(1)", "(2)", "(3)"}, {"(4)", "(5)", "(6)"}, {"(7)", "(8)", "(9)"}};

    //Method to check if player can move to a position
    public static boolean canMove(int col) {
        int row;
        int column = col;
        if (column < 3) {
            row = 0;
        } else if (column < 6) {
            column -= 3;

            row = 1;
        } else {
            column -= 6;
            row = 2;
        }
        if (board[row][column] != 0) {
            return false;
        } else {
            board[row][column] = 1;
            return true;
        }
    }
    //Does all of the computer checks
    public static void computerChecks(int computerMove) {
        if (computerBlockRow()) return;
        else if (computerBlockColumn()) return;
        else if (computerBlockDiagonal1()) return;
        else if (computerBlockDiagonal2()) return;
        else otherMoves(computerMove);
    }
    //Checks if the computer needs ot block a row
    public static boolean computerBlockRow() {
        for (int d = 2; d > 0; d--) {
            for (int i = 0; i < 3; i++) {
                int sum = 0;
                int count = 0;
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] != 0) {
                        count++;
                    }
                    sum += board[i][j];
                    //2+2+0, 2+1+1, 2+2+1, 1+0+0, 2+0+0, 1+1+0
                }
                if (sum == d*2 && count == 2) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == 0) {
                            board[i][j] = 2;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //Checks if the computer needs to block a column
    public static boolean computerBlockColumn() {
        for (int d = 2; d > 0; d--) {
            for (int i = 0; i < 3; i++) {
                int sum = 0;
                int count = 0;
                for (int j = 0; j < 3; j++) {
                    if (board[j][i] != 0) {
                        count++;
                    }
                    sum += board[j][i];
                }
                if (sum == d*2 && count == 2) {
                    for (int j = 0; j < 3; j++) {
                        if (board[j][i] == 0) {
                            board[j][i] = 2;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Checks if the computer needs to block diagonal 1
    public static boolean computerBlockDiagonal1() {
        for (int d = 2; d > 0; d--) {
            int sum = 0;
            int count = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] != 0) {
                    count++;
                }
                sum += board[i][i];
            }
            if (sum == d*2 && count == 2) {
                for (int i = 0; i < 3; i++) {
                    if (board[i][i] == 0) {
                        board[i][i] = 2;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Checks if the computer needs to block diagonal 1
    public static boolean computerBlockDiagonal2() {

        for (int d = 2; d > 0; d--) {
            int sum = 0;
            int count = 0;
            if (board[0][2] != 0) {
                count++;
            }
            if (board[1][1] != 0) {
                count++;
            }
            if (board[2][0] != 0) {
                count++;
            }
            sum += board[0][2] + board[1][1] + board[2][0];

            if (sum == 2 && count == 2) {
                if (board[0][2] == 0) {
                    board[0][2] = 2;
                } else if (board[1][1] == 0) {
                    board[1][1] = 2;
                } else {
                    board[2][0] = 2;
                }
                return true;
            }
        }
        return false;
    }

    public static void otherMoves(int move) {
        if (move < 5) {
            if (board[1][1] == 0) {
                board[1][1] = 2;
            } else {
                int r = rand.nextInt(4);
                if (r == 0 && board[0][0] == 0) {
                    board[0][0] = 2;
                } else if (r == 1 && board[0][2] == 0) {
                    board[0][2] = 2;
                } else if (r == 2 && board[2][0] == 0) {
                    board[2][0] = 2;
                } else if (r == 3 && board[2][2] == 0) {
                    board[2][2] = 2;
                }
            }
        } else {
            int r1 = rand.nextInt(3);
            int r2 = rand.nextInt(3);
            board[r1][r2] = 2;
        }
    }

    public static void main(String[] args) {
        int computerMove = 0;
        Scanner sc = new Scanner(System.in);
        boolean game = true;
        String whoWon = "Tie";


        // The game loop
        while (game) {
            //Checks if someone has won (row)
            for (int i = 0; i < 3; i++) {
                if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != 0) {
                    game = false;
                    if (board[i][0] == 1) {
                        whoWon = "Player";
                    } else {
                        whoWon = "Computer";
                    }
                }
            }
            //Checks if someone has won (column)
            for (int i = 0; i < 3; i++) {
                if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != 0) {
                    game = false;
                    if (board[0][i] == 1) {
                        whoWon = "Player";
                    } else {
                        whoWon = "Computer";
                    }
                }
            }
            //Checks if someone has won (diagonal (Top left -> Bottom right))
            if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != 0) {
                game = false;
                if (board[0][0] == 1) {
                    whoWon = "Player";
                } else {
                    whoWon = "Computer";
                }
            }
            //Checks if someone has won (diagonal (Top right -> Bottom left))
            if (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != 0) {
                game = false;
                if (board[0][2] == 1) {
                    whoWon = "Player";
                } else {
                    whoWon = "Computer";
                }
            }
            // Checks if board if full to end game in tie
            int count = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] != 0) {
                        count++;
                    }
                }
            }
            if (count == 9) {
                game = false;
            }
            // Prints the board out
            System.out.println("Board:");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 1) {
                        boardDisplay[i][j] = " X ";
                    } else if (board[i][j] == 2) {
                        boardDisplay[i][j] = " O ";
                    }
                    System.out.print(boardDisplay[i][j] + " ");
                }
                System.out.println("");
            }
            // Prints who won or if it was a tie
            if (!game && whoWon.equals("Tie")) {
                System.out.println("Tie!");
            } else if (!game) {
                System.out.println(whoWon + " wins!");
            }
            // Asks where the player wants to go
            while (game) {
                System.out.println("Num of slot: ");
                int where = sc.nextInt() - 1;

                if (where <= 8 && where >= 0) {
                    if (canMove(where)) {
                        break;
                    } else {
                        System.out.println("It is not possible to move there!");
                    }
                } else {
                    System.out.println("It is not possible to move there!");
                }

            }
            // Computer move
            computerChecks(computerMove);
        }
    }
}
