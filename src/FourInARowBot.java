import java.util.*;
import java.util.Arrays;

public class FourInARowBot {
    //TODO try and make it so that you can adjust how many move ahead it can look
    public static int[][] board = new int[6][7];
    public static char[][] boardDisplay = new char[6][7];
    public static boolean game = true;
    public static String whoWon = "Tie";
    public static Random rand = new Random();

    public static void botMove(int movesAhead) {
        ArrayList<Integer> evaluation = new ArrayList<>();
        if (checkForConnectionLength(4, 1)) {
            return;
        }

        //Tests each possible move on board and gives it an evaluation
        int ev = 0;

        for (int i = 0; i < 7; i++) {
            if (i >= 2 && i <= 4) {
                ev += 3;
            } else if (i == 1 || i == 5) {
                ev += 2;
            } else {
                ev += 1;
            }

            if (move(i, 2, 2)) {
                if (checkForConnectionLength(4, 2)) {
                    ev += 1000;
                    delMove(i);
                    evaluation.add(ev);
                    break;
                }
                if (checkForConnectionLength(3, 2)) {
                    ev += 20;
                }
                if (checkForConnectionLength(2, 2)) {
                    ev += 2;
                }
            } else {
                evaluation.add(-1000000000);
                continue;
            }

            for (int j = 0; j < 7; j++) {
                if (move(j, 1, 2)) {
                    if (checkForConnectionLength(4, 1)) {
                        ev -= 10000;
                    }
                    if (checkForConnectionLength(3, 1)) {
                        ev -= 30;
                    }
                    delMove(j);
                }
            }
            delMove(i);
            evaluation.add(ev);
            ev = 0;
        }

        //player moves
        System.out.println(evaluation);
        ArrayList<Integer> bestMove = new ArrayList<>();
        bestMove.add(0);

        int count = -1;
        for (int i : evaluation) {
            count++;
            if (i == evaluation.get(bestMove.get(0))) {
                bestMove.add(count);
            } else if (i > evaluation.get(bestMove.get(0))) {
                bestMove = new ArrayList<>();
                bestMove.add(count);
            }
        }
        System.out.println(bestMove);
        if (evaluation.get(bestMove.get(0)) != 0) {
            int r1 = rand.nextInt(bestMove.size());
            move(bestMove.get(r1), 2, 2);
//            move(r1, 2);
        }
    }

    public static void delMove(int column) {
        int row = 0;
        for (int i = 0; i < 6; i++) {
            if (board[row][column] == 0) {
                row++;
            } else {
                break;
            }
        }
        if (row >= 6) {
            row = 5;
        }
        if (row >= 0) {
            if (board[row][column] != 0) {
                board[row][column] = 0;
            }
        }
    }

    public static boolean move(int column, int who, int actualWho) {
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
                    board[row][column] = who;
                    return true;
                }
            } else {
                if (actualWho != 2) {
                    System.out.println("That column is full!");
                }
            }
        } else {
            System.out.println("It is not possible to move there!");
        }
        return false;
    }

    public static boolean checkLength(int length, int[] lis, int who) {
        int[] comp = {who,who,who,who};
        if (length == 4 && lis[0] == comp[0] && lis[1] == comp[1] && lis[2] == comp[2] && lis[3] == comp[3]) {
            return true;
        } else if (length == 3) {
            for (int c = 0; c < 4; c++) {
                comp = new int[] {who,who,who,who};
                comp[c] = 0;
                int count = 0;
                for (int b = 0; b < 4; b++) {
                    if (lis[b] == comp[b]) {
                        count++;
                    }
                }
                if (count == 4) {
                    return true;
                }
            }
        } else if (length == 2) {
            for (int c = 0; c < 3; c++) {
                comp = new int[] {who,who,who,who};
                comp[c] = 0;
                comp[c+1] = 0;
                int count = 0;
                for (int b = 0; b < 4; b++) {
                    if (lis[b] == comp[b]) {
                        count++;
                    }
                }
                if (count == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkForConnectionLength(int length, int who) {
        //Checks if someone has won (row)
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                int[] row = {board[i][j], board[i][j + 1], board[i][j + 2], board[i][j + 3]};
                if (checkLength(length, row, who)) {
                    return true;
                }
            }
        }
        //Checks if someone has won (column)
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                int[] column = {board[j][i], board[j + 1][i], board[j + 2][i], board[j + 3][i]};
                if (checkLength(length, column, who)) {
                    return true;
                }
            }
        }
        //Checks if someone has won (diagonal (Top left -> Bottom right))
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int[] diag1 = {board[i][j], board[i + 1][j + 1], board[i + 2][j + 2], board[i + 3][j + 3]};
                if (checkLength(length, diag1, who)) {
                    return true;
                }
            }
        }
        //Checks if someone has won (diagonal (Top right -> Bottom left))
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                int[] diag2 = {board[i][j], board[i + 1][j - 1], board[i + 2][j - 2], board[i + 3][j - 3]};
                if (checkLength(length, diag2, who)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (game) {
            if (checkForConnectionLength(4, 1)) {
                whoWon = "Player";
                game = false;
            } else if (checkForConnectionLength(4, 2)) {
                whoWon = "Computer";
                game = false;
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
            //Asks player where they want to go
            while (game) {
                System.out.println("Num of slot: ");
                int column = sc.nextInt() - 1;

                if (move(column, 1, 1)) {
                    break;
                }
            }
            //Computer move
            botMove(1);
        }
    }
}
