package com.np.pieces;

import java.util.ArrayList;
import com.np.Board;

/**
 * Rook child-class of the Piece class.
 */

public class Rook extends Piece{

    public Rook(int x, int y, char t) {
        xPos = x;
        yPos = y;
        this.x = x * 64;
        this.y = y * 64;
        team = t;
    }

    /**
     * Method to generate valid/legal moves for the Rook piece.
     * @param board current board-state to calculate legal moves.
     * @return the ArrayList consisting of [x, y] coordinates of possible legal moves.
     */

    @Override
    public ArrayList<int[]> generateValidMoves(Board board) {
        //clear previously valid moves
        validMoves.clear();

        //check y-spaces above
        for(int j = yPos - 1; j >= 0; j--) {
            //if we encounter a piece, stop possible movement
            if(board.getPiece(xPos, j) != null && board.getPiece(xPos, j).getTeam() == this.team) break;

            validMoves.add(new int[]{xPos, j});

            if(board.getPiece(xPos, j) != null && board.getPiece(xPos, j).getTeam() != this.team) break;

        }

        //below
        for(int j = yPos + 1; j <= 7; j++) {

            if(board.getPiece(xPos, j) != null && board.getPiece(xPos, j).getTeam() == this.team) break;

            validMoves.add(new int[]{xPos, j});

            if(board.getPiece(xPos, j) != null && board.getPiece(xPos, j).getTeam() != this.team) break;

        }

        //check x-spaces
        for(int i = xPos - 1; i >= 0; i--) {

            if(board.getPiece(i, yPos) != null && board.getPiece(i, yPos).getTeam() == this.team) break;

            validMoves.add(new int[]{i, yPos});

            if(board.getPiece(i, yPos) != null && board.getPiece(i, yPos).getTeam() != this.team) break;
        }

        for(int i = xPos + 1; i <= 7; i++) {

            if(board.getPiece(i, yPos) != null && board.getPiece(i, yPos).getTeam() != this.team) break;

            validMoves.add(new int[]{i, yPos});

            if(board.getPiece(i, yPos) != null && board.getPiece(i, yPos).getTeam() != this.team) break;
        }

        return validMoves;
    }
}
