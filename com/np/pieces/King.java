package com.np.pieces;

import com.np.Board;
import jdk.jfr.Experimental;

import java.util.ArrayList;

/**
 * King child-class of the Piece class.
 */

public class King extends Piece {
    private boolean check = false;

    public King(int x, int y, char t) {
        xPos = x;
        yPos = y;
        this.x = x * 64;
        this.y = y * 64;
        team = t;
    }

    /**
     * Method to generate valid/legal moves for the King piece.
     * @param board current board-state to calculate legal moves.
     * @return the ArrayList consisting of [x, y] coordinates of possible legal moves.
     */

    @Override
    public ArrayList<int[]> generateValidMoves(Board board) {

        validMoves.clear();

        //top bounds of the box around the king
        for (int j = yPos - 1; j <= yPos + 1; j++) {
            for (int i = xPos - 1; i <= xPos + 1; i++) {

                int[] index = {i, j};

                if (index[0] > 7 || index[0] < 0 || index[1] > 7 || index[1] < 0) continue;

                if (board.getPiece(i, j) != null && board.getPiece(i, j) instanceof King) {
                    ((King) board.getPiece(i, j)).threatened();
                }

                if (board.getPiece(i, j) != null && board.getPiece(i, j).getTeam() == team) continue;

                validMoves.add(new int[]{i, j});
            }
        }

        return validMoves;

    }

    /**
     * Accessor method of King
     * @return whether the King is threatened by check.
     */

    public boolean isThreatened() {
        return check;
    }

    /**
     * Gate to swap whether the king is currently threatened.
     */

    public void threatened() {check = !check;}

}