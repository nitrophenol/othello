import java.io.*;
import java.util.*;

public class Othello {
    int turn;
    int winner;
    int board[][];

    // add required class variables here

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
        // Student can choose to add preprocessing here
    }

    // add required helper functions here

    public int boardScore() {
        /*
         * Complete this function to return num_black_tiles - num_white_tiles if turn =
         * 0,
         * and num_white_tiles-num_black_tiles otherwise.
         */
        int sumb = 0;
        int sumw = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) {
                    sumb++;
                } else if (board[i][j] == 1) {
                    sumw++;
                }
            }
        }
        if (turn == 0) {
            return sumb - sumw;
        } else {
            return sumw - sumb;
        }
    }

    private int boardscore(int[][] arr) {
        /*
         * Complete this function to return num_black_tiles - num_white_tiles if turn =
         * 0,
         * and num_white_tiles-num_black_tiles otherwise.
         *
         */
        int sumb = 0;
        int sumw = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (arr[i][j] == 0) {
                    sumb++;
                } else if (arr[i][j] == 1) {
                    sumw++;
                }
            }
        }
        if (turn == 0) {
            return sumb - sumw;
        } else {
            return sumw - sumb;
        }
    }

    public int bestMove(int k) {
        return minimax(k, board, turn, true)[1];

    }

    private int[] minimax(int depth, int[][] board1, int turn1, boolean nt) {
        // Base case: if depth is 0 or the game is over, return the score of the board
        if (depth == 0) {
            return new int[] { boardscore(board1), -1 };
        }

        if (Varr(turn1, board1).isEmpty()) {
            return minimax(depth - 1, board1, 1 - turn1, !nt);
        }

        if (nt) {
            int bestScore = Integer.MIN_VALUE;
            ArrayList<Integer> validMoves = Varr(turn1, board1);
            int bestmove = -1;
            for (int move : validMoves) {
                int[][] copy = getBoardcopy(board1);
                int row = move / 8;
                int col = move % 8;
                copy = makeMove(row, col, turn1, copy);
                int[] score = minimax(depth - 1, copy, 1 - turn1, !nt);
                if (score[0] > bestScore) {
                    bestScore = score[0];
                    bestmove = move;
                }
                if (score[0] == bestScore) {
                    if (move < bestmove) {
                        bestmove = move;
                    }
                }
            }
            return new int[] { bestScore, bestmove };
        } else {
            int bestScore = Integer.MAX_VALUE;
            ArrayList<Integer> validMoves = Varr(turn1, board1);
            int bestmove = -1;
            for (int move : validMoves) {
                int[][] copy = getBoardcopy(board1);
                int row = move / 8;
                int col = move % 8;
                copy = makeMove(row, col, turn1, copy);
                int[] score = minimax(depth - 1, copy, 1 - turn1, !nt);
                if (score[0] < bestScore) {
                    bestScore = score[0];
                    bestmove = move;
                }
                if (score[0] == bestScore) {
                    if (move < bestmove) {
                        bestmove = move;
                    }
                }
            }
            return new int[] { bestScore, bestmove };
        }
    }

    private boolean helper(int row, int col, int player, int[][] board) {
        if (board[row][col] != -1) {
            return false;
        }
        int opponent = (player == 0) ? 1 : 0;
    
        // Check left
        int r = row;
        int c = col - 1;
        boolean foundOpponent = false;
        while (true) {
           // System.out.println("r: " + r + " c: " + c);
            if (c < 0 || board[r][c] != opponent) {
                break;
            }
            foundOpponent = true;
            c--;
          // System.out.println("r: " + r + " c: " + c);

        }
        if (foundOpponent && c >= 0 && board[r][c] == player) {
            //System.out.println("left");
            return true;
        }
    
        // Check right
        r = row;
        c = col + 1;
        foundOpponent = false;
        while (true) {
            //System.out.println("r: " + r + " c: " + c);
            if (c >= 8 || board[r][c] != opponent) {
                break;
            }
            foundOpponent = true;
            c++;
          //  System.out.println("r: " + r + " c: " + c);
        }
        if (foundOpponent && c < 8 && board[r][c] == player) {
            //System.out.println("right");
            return true;
        }
    
        // Check up
        r = row - 1;
        c = col;
        foundOpponent = false;
        while (true) {
            if (r < 0 || board[r][c] != opponent) {
                //System.out.println("r: " + r + " c: " + c);
                break;
            }
            foundOpponent = true;
            r--;
            //System.out.println("r: " + r + " c: " + c);
        }
        if (foundOpponent && r >= 0 && board[r][c] == player) {
            //System.out.println("up");
            return true;
        }
    
        // Check down
        r = row + 1;
        c = col;
        foundOpponent = false;
        while (true) {

            if (r >= 8 || board[r][c] != opponent) {
               // System.out.println( "r: " + r + " c: " + c);
                break;
            }
            foundOpponent = true;
            r++;
             // System.out.println( "r: " + r + " c: " + c);
        }
        if (foundOpponent && r < 8 && board[r][c] == player) {
             // System.out.println( "r: " + r + " c: " + c);
            return true;
        }
    
        //up-left diagonal
        r = row - 1;
        c = col - 1;
        foundOpponent = false;
        while (true) {
             // System.out.println( "r: " + r + " c: " + c);
            if (r < 0 || c < 0 || board[r][c] != opponent) {
                break;
            }
            foundOpponent = true;
            r--;
            c--;
             // System.out.println( "r: " + r + " c: " + c);
        }
        if (foundOpponent && r >= 0 && c >= 0 && board[r][c] == player) {
            return true;
        }
    
        // up-right diagonal
        r = row - 1;
        c = col + 1;
        foundOpponent = false;
        while (true) {
             // System.out.println( "r: " + r + " c: " + c);
            if (r < 0 || c >= 8 || board[r][c] != opponent) {
                break;
            }
            foundOpponent = true;
            r--;
            c++;
             // System.out.println( "r: " + r + " c: " + c);
        }
        if (foundOpponent && r >= 0 && c < 8 && board[r][c] == player) {
            return true;
             // System.out.println( "r: " + r + " c: " + c);
        }
        

        // down-left diagonal
        r = row + 1;
        c = col - 1;
        foundOpponent = false;
        while (true) {
            if (r >= 8 || c < 0 || board[r][c] != opponent) {
                break;
            }
            foundOpponent = true;
            r++;
            c--;
             // System.out.println( "r: " + r + " c: " + c);
        }
        if (foundOpponent && r < 8 && c >= 0 && board[r][c] == player) {
            return true;
             // System.out.println( "r: " + r + " c: " + c);
        }

        //down-right 
        r = row + 1;
        c = col + 1;
        foundOpponent = false;
 
        while (true) {
             // System.out.println( "r: " + r + " c: " + c);
            if (r >= 8 || c >= 8 || board[r][c] != opponent) {
                break;
            }
            foundOpponent = true;
            r++;
            c++;
             // System.out.println( "r: " + r + " c: " + c);
        }
        if (foundOpponent && r < 8 && c < 8 && board[r][c] == player) {
             // System.out.println( "r: " + r + " c: " + c);
            return true;
        }
        return false;
    }

    // Get a list of all valid moves for the given player on the given board
    private ArrayList<Integer> Varr(int player, int[][] board) {
        ArrayList<Integer> validMoves = new ArrayList<Integer>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (helper(row, col, player, board)) {
                    validMoves.add(row * 8 + col);
                }
            }
        }
        return validMoves;
    }

    private int[][] makeMove(int row, int col, int player, int[][] board) {
        if (!helper(row, col, player, board)) {
            return board;
        }
        board[row][col] = player;
        int opponent = 1 - player;
        int[][] d = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
        for (int[] ele : d) {
            int r = row + ele[0];
            int c = col + ele[1];
            // System.out.println(r + " " + c);
            // System.out.println(board[r][c]);
            // System.out.println(opponent);
            while (true) {
                if (r < 0 || r >= 8 || c < 0 || c >= 8) {
                  //  System.out.println("break");
                    break;

                }
                if (board[r][c] != opponent) {
                    // System.out.println("break");
                    break;
                }
                r += ele[0];
                c += ele[1];
            }
            if (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == player) {
                // System.out.println("in");
                // System.out.println(r + " " + c);
              
                r = row + ele[0];
                c = col + ele[1];
                // System.out.println(ele[0] + " " + ele[1]);
                // System.out.println(board[r][c]);
                while (board[r][c] == opponent) {
                    // System.out.println("in2");
                   
                    board[r][c] = player;
                    //System.out.println(r + " " + c);
                    r += ele[0];
                    c += ele[1];
                }
            }
        }
        return board;
    }

    public ArrayList<Integer> fullGame(int k) {
        /*
         * Complete this function to compute and execute the best move for each player
         * starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and
         * the turn
         * at each step. In the end, modify the winner variable as required.
         * 
         */

        ArrayList<Integer> moves = new ArrayList<Integer>();
        while (winner == -1) {

            if (turn == 0) {
                // System.out.println("Black's turn");
                ArrayList<Integer> validMoves = Varr(turn, board);
                if (validMoves.size() == 0) {
                    turn = 1 - turn;
                    validMoves = Varr(turn, board);
                    if (validMoves.size() == 0) {
                        int a = boardScore();
                        /*
                         * Complete this function to compute and execute the best move for each player
                         * starting from
                         * the current turn using k-step look-ahead. Accordingly modify the board and
                         * the turn
                         * at each step. In the end, modify the winner variable as required.
                         * 
                         */
                        if (a > 0) {
                           // System.out.println("Black wins");
                            winner = 1;
                        } else if (a < 0) {
                            winner = 0;
                        } else {
                            winner = -1;
                        }
                        break;
                    } else {
                        continue;
                    }
                }
                int[] move = new int[2];
                // System.out.println("Valid moves for black are: " + validMoves);
                // System.out.println(move.length);
                move = minimax(k, board, turn, true);
                // board = makeMove(move[1] / 8, move[1] % 8, turn, board);

                board = makeMove(move[1] / 8, move[1] % 8, turn, board);
                moves.add(move[1]);
                turn = 1;
            } else if (turn == 1) {
                // for (int index = 0; index < .length; index++) {

                // }
                //System.out.println("White's turn");
                ArrayList<Integer> validMoves = Varr(turn, board);
                if (validMoves.size() == 0) {
                    turn = 1 - turn;
                    validMoves = Varr(turn, board);
                    if (validMoves.size() == 0) {
                        int a = boardScore();
                        if (a > 0) {
                            //System.out.println(validMoves);
                            //System.out.println("White wins");
                            winner = 1;
                        } else if (a < 0) {
                            // System.out.println("Black wins"
                            // );
                            // System.out.println(validMoves);
                            winner = 0;
                        } else {
                            // System.out.println("Draw");
                            // System.out.println(validMoves);
                            winner = -1;
                        }
                        break;
                    } else {
                        continue;
                    }
                }
                int[] move = new int[2];
                move = minimax(k, board, turn, true);
                board = makeMove(move[1] / 8, move[1] % 8, turn, board);
                moves.add(move[1]);
                turn = 0;
            } else {
                break;
            }

        }

        return moves;
        // return new ArrayList<Integer>();
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    private int[][] getBoardcopy(int[][] board) {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }

    private void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -1) {
                    System.out.print((i + 1) + "" + (j + 1) + " ");
                } else if (board[i][j] == 0) {
                    System.out.print("b  ");
                } else if (board[i][j] == 1) {
                    System.out.print("w  ");
                }
            }
            System.out.println("");
        }
    }

    // public static void main(String[] args) {

    //     try {
    //         Othello game = new Othello("/home/mayank/Videos/Q2/input.txt");
    //         // ArrayList<Integer> moves = game.fullGame(Integer.parseInt(args[1]));
    //         // for(int i = 0; i < moves.size(); ++i) {
    //         // System.out.println(moves.get(i));
    //         // }
    //         System.out.println(game.bestMove(2));
    //         // System.out.println(game.fullGame(3));
    //         // System.out.println(game.getWinner());
    //         System.out.println(game.Varr(0, game.board));
    //     } catch (Exception e) {
    //         System.out.println("Error: " + e);
    //     }
    // }
}