package com.np;

import com.np.pieces.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Board Class that handles all operations regarding piece movement and board state.
 */

public class Board {

    //Class Attributes
    private int turn;
    private final ArrayList<Piece> blackTaken = new ArrayList<>();
    private final ArrayList<Piece> whiteTaken = new ArrayList<>();
    private final com.np.pieces.Piece[][] board;

    /**
     * Function to generate game pieces using the child classes of the abstract parent class Piece. This also sets the
     * position of the pieces
     */

    private void genPieces() {
        //White Pawn Gen
        for (int i = 0; i < 8; i++) {
            board[i][6] = new Pawn(i, 6, 'w');
        }
        //Black Pawn Gen
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(i, 1, 'b');
        }
        //King generation
        board[4][7] = new King(4, 7, 'w');
        board[4][0] = new King(4, 0, 'b');
        //Queen generation
        board[3][7] = new Queen(3, 7, 'w');
        board[3][0] = new Queen(3, 0, 'b');
        //Bishop generation
        board[2][7] = new Bishop(2, 7, 'w');
        board[5][7] = new Bishop(5, 7, 'w');
        board[2][0] = new Bishop(2, 0, 'b');
        board[5][0] = new Bishop(5, 0, 'b');
        //Rook generation
        board[7][0] = new Rook(7, 0, 'b');
        board[7][7] = new Rook(7, 7, 'w');
        board[0][0] = new Rook(0, 0, 'b');
        board[0][7] = new Rook(0, 7, 'w');
        //Knight generation
        board[1][7] = new Knight(1, 7, 'w');
        board[6][7] = new Knight(6, 7, 'w');
        board[1][0] = new Knight(1, 0, 'b');
        board[6][0] = new Knight(6, 0, 'b');
    }

    /**
     * Generates the 2d array of type Piece to store the piece data.
     */

    public Board() {
        board = new com.np.pieces.Piece[8][8];
        genPieces();
        turn = 0;
    }

    /**
     * Returns the current piece located at (int x, int y)
     * @param x current x-coordinate to be queried.
     * @param y current y-coordinate to be queried.
     * @return the Piece object stored at (int x, int y)
     */

    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    /**
     * Get the current board-state turn.
     * @return int representing the current turn of the board.
     */

    public int getTurn() {
        return turn;
    }

    /**
     * Method to move a piece to (int x, int y) on the board.
     * @param piece the current piece to be moved.
     * @param x x-coordinate that the piece will be moved to.
     * @param y y-coordinate that the piece will be moved to.
     */

    public void movePiece (Piece piece, int x, int y) {
        ArrayList<int[]> validMoves = piece.generateValidMoves(this);
        int[] movingTo = {x, y};

        for(int[] i : validMoves) {
            System.out.println("Legal Move: " + Arrays.toString(i));
            //Check for a piece in the new location; If not null, piece is present
            if(board[movingTo[0]][movingTo[1]] != null && Arrays.equals(i, movingTo) && board[x][y].getTeam() != piece.getTeam()) {

                if(piece.getTeam() == 'w') {blackTaken.add(board[x][y]);}
                else {whiteTaken.add(board[x][y]);}

                //delete the captured piece and move the new piece there
                board[x][y] = null;
                int[] tempLoc = piece.getPos();
                board[x][y] = piece;
                piece.updatePos(x, y);
                board[tempLoc[0]][tempLoc[1]] = null;
                turn++;
                return;
            }

            if (Arrays.equals(i, movingTo)) {
                int[] tempLoc = piece.getPos();
                board[x][y] = piece;
                piece.updatePos(x, y);
                board[tempLoc[0]][tempLoc[1]] = null;
                turn++;
                return;
            }
        }

    }

    /**
     * Method to add a piece to the game board.
     * @param x x-coordinate of the new piece.
     * @param y y-coordinate of the new piece.
     * @param p Piece object to be added to the board.
     */

    public void addPiece(int x, int y, Piece p) {
        board[x][y] = p;
    }

    /**
     * Method to remove a piece from the board at (int x, int y)
     * @param x x-coordinate of the piece to be removed.
     * @param y y-coordinate of the piece to be removed.
     */

    public void removePiece(int x, int y) {
        board[x][y] = null;
    }

    /**
     * Method to check for a board state where one of the kings is checkmated.
     * @return a boolean of whether the win condition is met.
     */

    public boolean winConditionMet() {

        for(Piece p : blackTaken) {

            if(p instanceof King) {return true;}

        }

        for(Piece p : whiteTaken) {

            if(p instanceof King) {return true;}

        }

        return false;

    }

    /**
     * Accessor method
     * @return the ArrayList of the captured white elements
     */

    public ArrayList<Piece> getWhiteTaken() {return whiteTaken;}

    /**
     * Accessor method
     * @return the ArrayList of the captured black elements.
     */

    public ArrayList<Piece> getBlackTaken() {return blackTaken;}
}