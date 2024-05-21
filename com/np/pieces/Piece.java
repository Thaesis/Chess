package com.np.pieces;

import java.util.ArrayList;
import com.np.Board;

/**
 * Abstract piece class to provide consistent data for all pieces.
 */

public abstract class Piece {
    public int xPos;
    public int yPos;
    public int x;
    public int y;
    protected char team;
    public int moves;
    protected final ArrayList<int[]> validMoves = new ArrayList<>();

    /**
     * Function to update the internal storage of the piece's location
     * @param x current x-value
     * @param y current y-value
     */

    public void updatePos(int x, int y) {
        xPos = x;
        yPos = y;
        this.x = xPos * 64;
        this.y = yPos * 64;
    }

    /**
     * Method to generate valid/legal moves for the generic piece.
     * @param board current board-state to calculate legal moves.
     * @return the ArrayList consisting of [x, y] coordinates of possible legal moves.
     */

    public ArrayList<int[]> generateValidMoves(Board board) {return null;}

    /**
     * Function to return the currently stored location.
     * @return of type int representing the current location.
     */

    public int[] getPos() {
        return new int[]{xPos, yPos};
    }

    /**
     * Function to return the char of the team for a given piece.
     * @return of type char where 'w' = team white and 'b' = team black.
     */

    public char getTeam() {
        return team;
    }

    /**
     * Increments move attribute of the piece.
     */

    public void incMoves(){
        moves++;
    }
}
