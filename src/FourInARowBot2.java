import java.util.*;

public class FourInARowBot2 {
    public static int[][] board = new int[6][7];
    public static char[][] boardDisplay = new char[6][7];
    public static boolean game = true;
    public static String whoWon = "Tie";
    public static Random rand = new Random();
    public static int totalSearches = 0;

    public static Pair evaluate(int movesAhead, boolean first, boolean computerTurn) {
        ArrayList<Integer> evaluation = new ArrayList<>();
        if (checkForConnectionLength(4, 1) > 0) {
            return new Pair(0, 0);
        }

        //Tests each possible move on board and gives it an evaluation
        int ev = 0;

        for (int i = 0; i < 7; i++) {
            // Gives moves a slight score bonus based on their locations
            if (first) {
                if (i >= 2 && i <= 4) {
                    ev += 3;
                } else if (i == 1 || i == 5) {
                    ev += 2;
                } else {
                    ev += 1;
                }
            }
            int whoTurn = computerTurn ? 2 : 1;
            if (move(i, whoTurn, 2)) {
                if (checkForConnectionLength(4, whoTurn) > 0) {
                    ev += 10000000 + 100 * movesAhead;
                    delMove(i);
                    evaluation.add(ev);
                    break;
                }
                if (movesAhead == 0) {
                    totalSearches++;
                    ev += 50 * checkForConnectionLength(3, whoTurn);
                    ev += 5 * checkForConnectionLength(2, whoTurn);
                }

                if (movesAhead > 0) {
                    Pair opponentBestMove = evaluate(movesAhead - 1, false, !computerTurn);
                    ev -= opponentBestMove.getValue();
                }

            } else {
                // If the column is full
                evaluation.add(-1000000000);
                continue;
            }

            delMove(i);
            evaluation.add(ev);
            ev = 0;
        }
        if (first) {
            System.out.println(evaluation);
        }
        ArrayList<Integer> bestMove = new ArrayList<>();
        bestMove.add(0);

        int count = -1;
        for (int i : evaluation) {
            count++;
            if (i == evaluation.get(bestMove.get(0))) {
                bestMove.add(count);
            } else if (i > evaluation.get(bestMove.get(0))) {
                bestMove.clear();
                bestMove.add(count);
            }
        }

//        int r1 = rand.nextInt(bestMove.size());
//        return new Pair(bestMove.get(r1), evaluation.get(bestMove.get(r1)));
        return new Pair(bestMove.get(0), evaluation.get(bestMove.get(0)));
    }

    public static void botMove(int movesAhead) {
        totalSearches = 0;
        Pair evaluation = evaluate(movesAhead, true, true);
        System.out.println(evaluation);
        move(evaluation.getKey(), 2, 2);
        System.out.println("Total Moves Searched: " + totalSearches);
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
        int count = 0;
        for (int i : lis) {
            if (i == who) {
                count++;
            } else if (i != 0) {
                return false;
            }
        }
        return count == length;
    }

    public static Integer checkForConnectionLength(int length, int who) {
        int count = 0;
        int other = new int[]{2, 1}[who-1];
        //Checks if someone has won (row)
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                int[] row = {board[i][j], board[i][j + 1], board[i][j + 2], board[i][j + 3]};
                if (checkLength(length, row, who)) {
                    count++;
                } else if (checkLength(length, row, other)) {
                    count--;
                }
            }
        }
        //Checks if someone has won (column)
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                int[] column = {board[j][i], board[j + 1][i], board[j + 2][i], board[j + 3][i]};
                if (checkLength(length, column, who)) {
                    count++;
                } else if (checkLength(length, column, other)) {
                    count--;
                }
            }
        }
        //Checks if someone has won (diagonal (Top left -> Bottom right))
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int[] diag1 = {board[i][j], board[i + 1][j + 1], board[i + 2][j + 2], board[i + 3][j + 3]};
                if (checkLength(length, diag1, who)) {
                    count++;
                } else if (checkLength(length, diag1, other)) {
                    count--;
                }
            }
        }
        //Checks if someone has won (diagonal (Top right -> Bottom left))
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                int[] diag2 = {board[i][j], board[i + 1][j - 1], board[i + 2][j - 2], board[i + 3][j - 3]};
                if (checkLength(length, diag2, who)) {
                    count++;
                } else if (checkLength(length, diag2, other)) {
                    count--;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        boolean computerMoveFirst = false;
        int difficulty = 4;
        int requirement = 10;
        Scanner sc = new Scanner(System.in);
        int totalMoves = 0;

        while (game) {
            totalMoves += 2;
            if (computerMoveFirst) {
                botMove(difficulty);
            }

            if (checkForConnectionLength(4, 1) > 0) {
                whoWon = "Player";
                game = false;
            } else if (checkForConnectionLength(4, 2) > 0) {
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
            // Prints out who won
            if (!game && whoWon.equals("Tie")) {
                System.out.println("Tie!");
            } else if (!game) {
                System.out.println(whoWon + " wins!");
            }

            // Asks player where they want to go
            while (game) {
                System.out.println("Num of slot: ");
                int column;
                String input = sc.nextLine();
                try {
                    column = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid slot!");
                    continue;
                }

                if (move(column, 1, 1)) {
                    break;
                }
            }

            if (!computerMoveFirst) {
                botMove(difficulty);
            }

            if (totalMoves >= requirement) {
                totalMoves -= requirement;
                difficulty += 2;
                requirement -= 2;
                System.out.println("Increased search depth to: " + difficulty);
            }

        }
    }

    static class Pair {
        private final Integer value;
        private final Integer key;

        public Pair(Integer k, Integer v) {
            key = k;
            value = v;
        }

        public Integer getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }
    }

}
