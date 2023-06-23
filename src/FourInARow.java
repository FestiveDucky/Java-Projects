import java.util.*;
public class FourInARow {
    public static int[][] board = new int[6][7];
    public static char[][] boardDisplay = new char[6][7];

    public static void main(String[] args) {
        boolean who = true;
        boolean game = true;
        String whoWon = "Tie";
        Scanner sc = new Scanner(System.in);

        while (game) {
            //Checks if someone has won (row)
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 4; j++) {
                    if (board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2] && board[i][j] == board[i][j+3] && board[i][j] != 0) {
                        game = false;
                        if (board[i][j] == 1) {
                            whoWon = "Player1";
                        } else {
                            whoWon = "Player2";
                        }
                    }
                }
            }
            //Checks if someone has won (column)
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[j][i] == board[j+1][i] && board[j][i] == board[j+2][i] && board[j][i] == board[j+3][i] && board[j][i] != 0) {
                        game = false;
                        if (board[j][i] == 1) {
                            whoWon = "Player1";
                        } else {
                            whoWon = "Player2";
                        }
                    }
                }
            }
            //Checks if someone has won (diagonal (Top left -> Bottom right))
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (board[i][j] == board[i+1][j+1] && board[i][j] == board[i+2][j+2] && board[i][j] == board[i+3][j+3] && board[i][j] != 0) {
                        game = false;
                        if (board[i][j] == 1) {
                            whoWon = "Player1";
                        } else {
                            whoWon = "Player2";
                        }
                    }
                }
            }
            //Checks if someone has won (diagonal (Top right -> Bottom left))
            for (int i = 0; i < 3; i++) {
                for (int j = 3; j < 7; j++) {
                    if (board[i][j] == board[i+1][j-1] && board[i][j] == board[i+2][j-2] && board[i][j] == board[i+3][j-3] && board[i][j] != 0) {
                        game = false;
                        if (board[i][j] == 1) {
                            whoWon = "Player1";
                        } else {
                            whoWon = "PLayer2";
                        }
                    }
                }
            }
            //Checks if there is a tie
            int count = 0;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (board[i][j] != 0) {
                        count++;
                    }
                }
            }
            if (count == 42) {
                game = false;
            }
            //Prints out the board
            System.out.println(" 1 2 3 4 5 6 7");
            System.out.println(" - - - - - - -");
            for (int i = 0; i < 6; i++) {
                System.out.print("|");
                for (int j = 0; j < 7; j++) {
                    if (board[i][j] == 1) {
                        boardDisplay[i][j] = 'X';
                    } else if (board[i][j] == 2) {
                        boardDisplay[i][j] = 'O';
                    } else {
                        boardDisplay[i][j] = ' ';
                    }
                    if (j != 6) {
                        System.out.print(boardDisplay[i][j] + " ");
                    } else {
                        System.out.print(boardDisplay[i][j]);
                    }
                }
                System.out.println("|");
            }
            //Prints out who won
            if (!game && whoWon.equals("Tie")) {
                System.out.println("Tie!");
            } else if (!game) {
                System.out.println(whoWon + " wins!");
            }
            while (game) {
                System.out.println("Num of slot: ");
                int column = sc.nextInt() - 1;
                int row = 0;

                if (column <= 6 && column >= 0) {
                    for (int i = 0; i < 6; i++) {
                        if (board[row][column] == 0) {
                            row++;
                        } else {
                            break;
                        }
                    }
                    row--;
                    if (row >= 0) {
                        if (board[row][column] == 0) {
                            if (who) {
                                board[row][column] = 1;
                                who = false;
                            } else {
                                board[row][column] = 2;
                                who = true;
                            }
                            break;
                        }
                    } else {
                        System.out.println("It is not possible to move there!");
                    }
                } else {
                    System.out.println("It is not possible to move there!");
                }

            }
        }
    }
}
